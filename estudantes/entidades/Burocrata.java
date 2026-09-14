package estudantes.entidades;

import professor.entidades.*;

public class Burocrata {
    private int estresse = 0;
    private Mesa mesa;
    private Universidade universidade;
    
    public Burocrata(Mesa m, Universidade u){ // construtor
        this.mesa = m;
        this.universidade = u;
    }
    
    public void trabalhar(){
        CodigoCurso[] cursos = CodigoCurso.values(); // obtém um array com todos os cursos existentes na universidade
        
        for (CodigoCurso curso : cursos) { // percorre cada um dos cursos
            Documento[] documentosDoMonte = universidade.pegarCopiaDoMonteDoCurso(curso); // pega a lista de documentos disponíveis no monte desse curso específico
            
            for (Documento doc : documentosDoMonte) { // percorre cada documento desse monte específico
                int melhorIndice = escolherMelhorProcesso(doc); // Best-Fit para descobrir qual slot da mesa é o mais adequado para este documento
                
                if (melhorIndice != -1) { // se um processo compatível foi encontrado
                    Processo proc = mesa.getProcesso(melhorIndice); // obtém o processo correspondente na mesa
                    if (proc != null && universidade.removerDocumentoDoMonteDoCurso(doc, curso)) { // remove o documento do monte da universidade
                        proc.adicionarDocumento(doc); // insere o documento dentro do processo escolhido
                    }
                }
            }
        }
        
        for (int i = 0; i < 5; i++) { // percorre os 5 slots da mesa
            Processo proc = mesa.getProcesso(i); // pega o processo do slot i
            if (proc != null && processoPodeSerDespachado(proc)) { // avalia se pode ser despachado
                universidade.despachar(proc); // se o processo estiver pronto, ele é despachado, liberando o slot na mesa
            }
        }
    }

    private int escolherMelhorProcesso(Documento doc) {
        int melhorIndice = -1;
        int menorSobra = Integer.MAX_VALUE;
        
        for (int i = 0; i < 5; i++) { // itera pelos 5 slots da mesa
            Processo proc = mesa.getProcesso(i);
            if (proc != null && processoAceitaDocumento(proc, doc)) { //verifica se o slot possui um processo e se ele aceita o documento
                int paginasAtuais = 0;
                for (Documento d : proc.pegarCopiaDoProcesso()) {
                    paginasAtuais += d.getPaginas();
                } // calcula o total de páginas
                
                int sobra = 250 - (paginasAtuais + doc.getPaginas()); // calcula quantas páginas livres sobraria no processo se o documento fosse adicionado (teto 250 páginas)
                if (sobra >= 0 && sobra < menorSobra) { // se a sobra for positiva e menor do que a sobra registrada até agora, atualiza menor sobra e define o melhor indice
                    menorSobra = sobra; 
                    melhorIndice = i;
                }
            }
        }
        return melhorIndice;
    }

    private boolean processoAceitaDocumento(Processo proc, Documento doc) { // garante que o documento não viole nenhuma restrição
        int paginasAtuais = 0;
        for (Documento d : proc.pegarCopiaDoProcesso()) { 
            paginasAtuais += d.getPaginas();
        } // soma as páginas atuais do processo
        if (paginasAtuais + doc.getPaginas() > 250) {
            return false;
        } // valida se ultrapassa o teto de 250 páginas

        Documento[] docsNoProcesso = proc.pegarCopiaDoProcesso();
        if (docsNoProcesso.length == 0) { // se o processo estiver vazio, ele aceita qualquer documento inicial
            return true;
        }

        boolean docEhPos = ehPosGraduacao(doc.getCodigoCurso());
        for (Documento d : docsNoProcesso) {
            if (ehPosGraduacao(d.getCodigoCurso()) != docEhPos) {
                return false;
            }
        } // garante que cursos de pós-graduação não se misturem com graduação no mesmo processo

        boolean docEhAdm = (doc instanceof DocumentoAdministrativo) && !(doc instanceof Ata);
        boolean docEhAcad = (doc instanceof DocumentoAcademico);

        for (Documento d : docsNoProcesso) {
            boolean dAdm = (d instanceof DocumentoAdministrativo) && !(d instanceof Ata);
            boolean dAcad = (d instanceof DocumentoAcademico);
            
            if (docEhAdm && dAcad) return false;
            if (docEhAcad && dAdm) return false;
        } // impede a mistura de documentos administrativos e acadêmicos (Atas são uma exceção permitida)

        if (isPortariaOuEditalSubstancialValido(doc)) {
            return false; 
        }
        for (Documento d : docsNoProcesso) {
            if (isPortariaOuEditalSubstancialValido(d)) {
                return false; 
            }
        } // Portarias ou Editais válidos com 100 páginas ou mais devem tramitar sozinhos

        if (!destinatariosCompativeis(docsNoProcesso, doc)) {
            return false;
        }

        if (!diplomasCompativeis(docsNoProcesso, doc)) {
            return false;
        }

        if (!atestadosCompativeis(docsNoProcesso, doc)) {
            return false;
        }

        return true;
    } // valida todas as regras de compatibilidade do documento com o processo atual,
    // incluindo limite de páginas, graduação/pós-graduação, mistura entre
    // Documentos acadêmicos e administrativos, Portarias/Editais substanciais,
    // destinatários de Ofícios/Circulares, Diplomas e Atestados

    private boolean processoPodeSerDespachado(Processo proc) {
        Documento[] docs = proc.pegarCopiaDoProcesso();
        if (docs.length == 0) {
            return false;
        } // se o processo estiver vazio, não pode ser despachado
        
        boolean apenasAtas = true;
        for (Documento d : docs) {
            if (!(d instanceof Ata)) {
                apenasAtas = false;
                break;
            }
        }
        if (apenasAtas) {
            return false;
        } // um processo contendo exclusivamente Atas não pode ser despachado sozinho

        for (Documento d : docs) {
            if (isPortariaOuEditalSubstancialValido(d)) {
                return true;
            }
        } // se o processo contiver uma Portaria ou Edital substancial válido, ele deve ser despachado imediatamente

        int paginasTotais = 0;
        for (Documento d : docs) {
            paginasTotais += d.getPaginas();
        }

        if (paginasTotais >= 220 || docs.length >= 22) {
            return true;
        }

        return false;
    } // calcula o total de páginas acumuladas, se atingiu alta densidade (pelo menos 220 páginas) ou acumulou 22 ou mais documentos, autoriza o despacho para otimizar
    // a eficiência, caso contrário, continua aguardando mais documentos

    private boolean ehPosGraduacao(CodigoCurso codigo) {
        return codigo.equals(CodigoCurso.POS_GRADUACAO_COMPUTACAO) || 
               codigo.equals(CodigoCurso.POS_GRADUACAO_ENGENHARIA_ELETRICA) || 
               codigo.equals(CodigoCurso.POS_GRADUACAO_MICROELETRONICA);
    } // retorna true se o código do curso pertencer à pós-graduação.

    private boolean isPortariaOuEditalSubstancialValido(Documento d) {
        if (d instanceof Portaria) {
            Portaria p = (Portaria) d;
            return p.getPaginas() >= 100 && p.getValido();
        }
        if (d instanceof Edital) {
            Edital e = (Edital) d;
            return e.getPaginas() >= 100 && e.getValido();
        }
        return false;
    } // verifica se o documento é Portaria ou Edital com 100 páginas ou mais

    private boolean destinatariosCompativeis(Documento[] docsAtuais, Documento novoDoc) { // Ofícios e Circulares só podem ficar juntos no mesmo processo se houver pelo menos um
        // destinatário em comum em todos eles
        java.util.HashMap<String, Integer> destinatarios = new java.util.HashMap<>(); // cria um mapa para contar quantas vezes cada destinatário aparece nos documentos
        int contagemOficiosCirculares = 0; // conta quantos documentos do tipo Ofício ou Circular existem no total (incluindo o documento novo)

        java.util.List<Documento> todos = new java.util.ArrayList<>(java.util.Arrays.asList(docsAtuais));
        todos.add(novoDoc); // cria uma lista temporária juntando todos os documentos

        for (Documento d : todos) {
            if (d instanceof Oficio) {
                contagemOficiosCirculares++;
                Oficio o = (Oficio) d;
                destinatarios.put(o.getDestinatario(), destinatarios.getOrDefault(o.getDestinatario(), 0) + 1); // pega o destinatário único do ofício (o.getDestinatario()) e soma +1 no mapa para ele
            } else if (d instanceof Circular) {
                contagemOficiosCirculares++;
                Circular c = (Circular) d;
                for (String dest : c.getDestinatarios()) {
                    destinatarios.put(dest, destinatarios.getOrDefault(dest, 0) + 1);
                }
            }
        } // como uma Circular pode ter vários destinatários, ela usa outro loop 
        // interno para pegar cada destinatário da lista da circular (c.getDestinatarios()) e somar +1 para cada um deles no mapa

        if (contagemOficiosCirculares > 0) { // se houver pelo menos um ofício ou circular no grupo...
            for (int ocorrencias : destinatarios.values()) { // verifica a pontuação de cada destinatário no mapa
                if (ocorrencias >= contagemOficiosCirculares) { // Se algum destinatário apareceu um número de vezes igual ao total de ofícios
                //  e circulares (contagemOficiosCirculares), significa que ele está presente em absolutamente todos 
                // eles, logo, há um destinatário em comum e a regra é satisfeita
                    return true; 
                }
            }
            return false; // falta de destinatário em comum
        }
        return true; // se não houver Ofício ou Circular no processo, a regra não se aplica e retorna true
    }

    private boolean diplomasCompativeis(Documento[] docsAtuais, Documento novoDoc) { // Diplomas só podem ser agrupados no mesmo processo se vierem
        // acompanhados exclusivamente de outros Diplomas, Certificados ou Atas
        boolean envolveDiploma = (novoDoc instanceof Diploma); // verifica se o documento novo é um Diploma
        for (Documento d : docsAtuais) {
            if (d instanceof Diploma) {
                envolveDiploma = true;
                break;
            }
        } // percorre os documentos que já estão no processo para ver se algum deles é Diploma

        if (envolveDiploma) {
            java.util.List<Documento> todos = new java.util.ArrayList<>(java.util.Arrays.asList(docsAtuais));
            todos.add(novoDoc);
            for (Documento d : todos) {
                if (!(d instanceof Diploma || d instanceof Certificado || d instanceof Ata)) {
                    return false;
                } // se encontrar qualquer documento que não seja Diploma, Certificado ou Ata, a regra é violada e retorna false imediatamente
            }
        }
        return true;
    }

    private boolean atestadosCompativeis(Documento[] docsAtuais, Documento novoDoc) { // Atestados no mesmo processo devem pertencer exatamente à mesma categoria
        String categoriaBase = null;
        if (novoDoc instanceof Atestado) {
            categoriaBase = ((Atestado) novoDoc).getCategoria();
        } // se o documento novo for um Atestado, guarda a categoria dele como referência base

        for (Documento d : docsAtuais) { // passa por cada documento que já está no processo
            if (d instanceof Atestado) { // se esse documento também for um atestado
                String cat = ((Atestado) d).getCategoria(); // pega a categoria dele
                if (categoriaBase == null) { // se o documento novo não for um Atestado mas os antigos são, definimos a categoria desse atestado antigo como base
                    categoriaBase = cat;
                } else if (!categoriaBase.equals(cat)) {
                    return false;
                } // se a categoria do Atestado antigo for diferente da categoria base, significa que há uma mistura de categorias proibida
            }
        }
        return true;
    }
    
    public int getEstresse(){
        return this.estresse;
    }
    
    public void estressar(){
        this.estresse++;
    }
    
    public void estressarMuito(){
        this.estresse += 10;
    }
}