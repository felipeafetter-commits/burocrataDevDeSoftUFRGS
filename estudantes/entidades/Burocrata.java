package estudantes.entidades;

import professor.entidades.*;

public class Burocrata {
    private int estresse = 0;
    private Mesa mesa;
    private Universidade universidade;
    
    public Burocrata(Mesa m, Universidade u){
        this.mesa = m;
        this.universidade = u;
    }
    
    public void trabalhar(){
        CodigoCurso[] cursos = CodigoCurso.values();
        
        for (CodigoCurso curso : cursos) {
            Documento[] documentosDoMonte = universidade.pegarCopiaDoMonteDoCurso(curso);
            
            for (Documento doc : documentosDoMonte) {
                int melhorIndice = escolherMelhorProcesso(doc);
                
                if (melhorIndice != -1) {
                    Processo proc = mesa.getProcesso(melhorIndice);
                    if (proc != null && universidade.removerDocumentoDoMonteDoCurso(doc, curso)) {
                        proc.adicionarDocumento(doc);
                    }
                }
            }
        }
        
        for (int i = 0; i < 5; i++) {
            Processo proc = mesa.getProcesso(i);
            if (proc != null && processoPodeSerDespachado(proc)) {
                universidade.despachar(proc);
            }
        }
    }

    private int escolherMelhorProcesso(Documento doc) {
        int melhorIndice = -1;
        int menorSobra = Integer.MAX_VALUE;
        
        for (int i = 0; i < 5; i++) {
            Processo proc = mesa.getProcesso(i);
            if (proc != null && processoAceitaDocumento(proc, doc)) {
                int paginasAtuais = 0;
                for (Documento d : proc.pegarCopiaDoProcesso()) {
                    paginasAtuais += d.getPaginas();
                }
                
                int sobra = 250 - (paginasAtuais + doc.getPaginas());
                if (sobra >= 0 && sobra < menorSobra) {
                    menorSobra = sobra;
                    melhorIndice = i;
                }
            }
        }
        return melhorIndice;
    }

    private boolean processoAceitaDocumento(Processo proc, Documento doc) {
        int paginasAtuais = 0;
        for (Documento d : proc.pegarCopiaDoProcesso()) {
            paginasAtuais += d.getPaginas();
        }
        if (paginasAtuais + doc.getPaginas() > 250) {
            return false;
        }

        Documento[] docsNoProcesso = proc.pegarCopiaDoProcesso();
        if (docsNoProcesso.length == 0) {
            return true;
        }

        boolean docEhPos = ehPosGraduacao(doc.getCodigoCurso());
        for (Documento d : docsNoProcesso) {
            if (ehPosGraduacao(d.getCodigoCurso()) != docEhPos) {
                return false;
            }
        }

        boolean docEhAdm = (doc instanceof DocumentoAdministrativo) && !(doc instanceof Ata);
        boolean docEhAcad = (doc instanceof DocumentoAcademico);

        for (Documento d : docsNoProcesso) {
            boolean dAdm = (d instanceof DocumentoAdministrativo) && !(d instanceof Ata);
            boolean dAcad = (d instanceof DocumentoAcademico);
            
            if (docEhAdm && dAcad) return false;
            if (docEhAcad && dAdm) return false;
        }

        if (isPortariaOuEditalSubstancialValido(doc)) {
            return false; 
        }
        for (Documento d : docsNoProcesso) {
            if (isPortariaOuEditalSubstancialValido(d)) {
                return false; 
            }
        }

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
    }

    private boolean processoPodeSerDespachado(Processo proc) {
        Documento[] docs = proc.pegarCopiaDoProcesso();
        if (docs.length == 0) {
            return false;
        }
        
        boolean apenasAtas = true;
        for (Documento d : docs) {
            if (!(d instanceof Ata)) {
                apenasAtas = false;
                break;
            }
        }
        if (apenasAtas) {
            return false;
        }

        for (Documento d : docs) {
            if (isPortariaOuEditalSubstancialValido(d)) {
                return true;
            }
        }

        int paginasTotais = 0;
        for (Documento d : docs) {
            paginasTotais += d.getPaginas();
        }

        if (paginasTotais >= 220 || docs.length >= 22) {
            return true;
        }

        return false;
    }

    private boolean ehPosGraduacao(CodigoCurso codigo) {
        return codigo.equals(CodigoCurso.POS_GRADUACAO_COMPUTACAO) || 
               codigo.equals(CodigoCurso.POS_GRADUACAO_ENGENHARIA_ELETRICA) || 
               codigo.equals(CodigoCurso.POS_GRADUACAO_MICROELETRONICA);
    }

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
    }

    private boolean destinatariosCompativeis(Documento[] docsAtuais, Documento novoDoc) {
        java.util.HashMap<String, Integer> destinatarios = new java.util.HashMap<>();
        int contagemOficiosCirculares = 0;

        java.util.List<Documento> todos = new java.util.ArrayList<>(java.util.Arrays.asList(docsAtuais));
        todos.add(novoDoc);

        for (Documento d : todos) {
            if (d instanceof Oficio) {
                contagemOficiosCirculares++;
                Oficio o = (Oficio) d;
                destinatarios.put(o.getDestinatario(), destinatarios.getOrDefault(o.getDestinatario(), 0) + 1);
            } else if (d instanceof Circular) {
                contagemOficiosCirculares++;
                Circular c = (Circular) d;
                for (String dest : c.getDestinatarios()) {
                    destinatarios.put(dest, destinatarios.getOrDefault(dest, 0) + 1);
                }
            }
        }

        if (contagemOficiosCirculares > 0) {
            for (int ocorrencias : destinatarios.values()) {
                if (ocorrencias >= contagemOficiosCirculares) {
                    return true; 
                }
            }
            return false;
        }
        return true;
    }

    private boolean diplomasCompativeis(Documento[] docsAtuais, Documento novoDoc) {
        boolean envolveDiploma = (novoDoc instanceof Diploma);
        for (Documento d : docsAtuais) {
            if (d instanceof Diploma) {
                envolveDiploma = true;
                break;
            }
        }

        if (envolveDiploma) {
            java.util.List<Documento> todos = new java.util.ArrayList<>(java.util.Arrays.asList(docsAtuais));
            todos.add(novoDoc);
            for (Documento d : todos) {
                if (!(d instanceof Diploma || d instanceof Certificado || d instanceof Ata)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean atestadosCompativeis(Documento[] docsAtuais, Documento novoDoc) {
        String categoriaBase = null;
        if (novoDoc instanceof Atestado) {
            categoriaBase = ((Atestado) novoDoc).getCategoria();
        }

        for (Documento d : docsAtuais) {
            if (d instanceof Atestado) {
                String cat = ((Atestado) d).getCategoria();
                if (categoriaBase == null) {
                    categoriaBase = cat;
                } else if (!categoriaBase.equals(cat)) {
                    return false;
                }
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