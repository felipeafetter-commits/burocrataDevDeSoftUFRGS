package professor.entidades;

import estudantes.entidades.*;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Classe que representa a secretaria que recebe e lida com os processos.
 * <br><br>
 * <strong>Não mexa aqui!!!</strong>
 * 
 * @author Jean Cheiran
 */
public class Secretaria {
    private int documentosDespachados = 0;
    private int documentosPerdidos = 0;
    private LinkedList<Processo> processosDespachadosCorretamente;
    private LinkedList<Processo> processosDespachadosComProblemas;
    
    protected Secretaria(){
        processosDespachadosCorretamente = new LinkedList<>();
        processosDespachadosComProblemas = new LinkedList<>();
    }
    
    protected void despachar(Processo processo, Burocrata burocrata){
        //perder documentos se exceder capacidade do processo
        if(processo.contarPaginas() > 250){
            documentosPerdidos += processo.contarDocumentos();
            burocrata.estressarMuito();
            return;
        }
        
        Documento[] copiaDoProcesso = processo.pegarCopiaDoProcesso();
        boolean processoComProblemas = false;
        
        //graduação X pós-graduação
        boolean graduacao = false, posgraduacao = false;
        for(Documento doc : copiaDoProcesso){
            if(doc.getCodigoCurso().equals(CodigoCurso.POS_GRADUACAO_COMPUTACAO) || doc.getCodigoCurso().equals(CodigoCurso.POS_GRADUACAO_ENGENHARIA_ELETRICA) || doc.getCodigoCurso().equals(CodigoCurso.POS_GRADUACAO_MICROELETRONICA)){
                posgraduacao = true;
            }else{
                graduacao = true;
            }
        }
        if(graduacao && posgraduacao){
            burocrata.estressar();
            processoComProblemas = true;
        }
        
        //administrativos X acadêmicos
        boolean administrativos = false, academicos = false;
        for(Documento doc : copiaDoProcesso){
            if(doc instanceof DocumentoAdministrativo){
                administrativos = true;
            }
            if(doc instanceof DocumentoAcademico){
                academicos = true;
            }
        }
        if(administrativos && academicos){
            burocrata.estressar();
            processoComProblemas = true;
        }
        
        //processo só com atas
        boolean apenasAtas = true;
        for(Documento doc : copiaDoProcesso){
            if(doc instanceof DocumentoAdministrativo){
                apenasAtas = false;
            }
            if(doc instanceof DocumentoAcademico){
                apenasAtas = false;
            }
        }
        if(apenasAtas){
            burocrata.estressar();
            processoComProblemas = true;
        }
        
        //portarias e editais substanciais
        boolean documentoSubstancialValido = false;
        for(Documento doc : copiaDoProcesso){
            if(doc instanceof Edital || doc instanceof Portaria){
                Norma norma = (Norma) doc;
                if(norma.getPaginas() >= 100 && norma.isValido()){
                    documentoSubstancialValido = true;
                }
            }
        }
        if(documentoSubstancialValido && copiaDoProcesso.length > 1){
            burocrata.estressar();
            processoComProblemas = true;
        }
        
        //circulares e ofícios para mesmo destinatário
        HashMap<String, Integer> destinatarios = new HashMap<>();
        int contagemDeOficiosECirculares = 0;
        for(Documento doc : copiaDoProcesso){
            if(doc instanceof Oficio){
                contagemDeOficiosECirculares++;
                Oficio oficio = (Oficio) doc;
                if(destinatarios.containsKey(oficio.getDestinatario())){
                    destinatarios.put(oficio.getDestinatario(), destinatarios.get(oficio.getDestinatario()) + 1);
                }else{
                    destinatarios.put(oficio.getDestinatario(), 1);
                }
            }
            if(doc instanceof Circular){
                contagemDeOficiosECirculares++;
                Circular circular = (Circular) doc;
                //se o mesmo destinatário aparecer mais de uma vez, não vai ser legal :(
                for(String destinatario : circular.getDestinatarios()){
                    if(destinatarios.containsKey(destinatario)){
                        destinatarios.put(destinatario, destinatarios.get(destinatario) + 1);
                    }else{
                        destinatarios.put(destinatario, 1);
                    }
                }
            }
        }
        if(contagemDeOficiosECirculares > 0){
            boolean existemCircularesEOficiosSemDestinatarioComum = true;
            for(int ocorrencias : destinatarios.values()){
                // >= é mais seguro por causa de possíveis repetições de destinatários no mesmo documento
                if(ocorrencias >= contagemDeOficiosECirculares){
                    existemCircularesEOficiosSemDestinatarioComum = false;
                }
            }
            if(existemCircularesEOficiosSemDestinatarioComum){
                burocrata.estressar();
                processoComProblemas = true;
            }
        }
        
        //diplomas só com diplomas, certificados e atas
        boolean diplomas = false, documentosNaoDiplomasCertificadosAtas = false;
        for(Documento doc : copiaDoProcesso){
            if(doc instanceof Diploma){
                diplomas = true;
            //não precisa testar Diploma, porque acabou de verificar acima
            }else if(!(doc instanceof Certificado) && !(doc instanceof Ata)){
                documentosNaoDiplomasCertificadosAtas = true;
            }
        }
        if(diplomas && documentosNaoDiplomasCertificadosAtas){
            burocrata.estressar();
            processoComProblemas = true;
        }
        
        //atestados da mesma categoria
        boolean atestadosComCategoriasMisturadas = false;
        String categoriaDoPrimeiroAtestadoEncontrado = null;
        for(Documento doc : copiaDoProcesso){
            if(doc instanceof Atestado){
                Atestado atestado = (Atestado) doc;
                if(categoriaDoPrimeiroAtestadoEncontrado == null){
                    categoriaDoPrimeiroAtestadoEncontrado = atestado.getCategoria();
                }else if(!categoriaDoPrimeiroAtestadoEncontrado.equals(atestado.getCategoria())){
                    atestadosComCategoriasMisturadas = true;
                }
            }
        }
        if(atestadosComCategoriasMisturadas){
            burocrata.estressar();
            processoComProblemas = true;
        }
        
        documentosDespachados += processo.contarDocumentos();
        
        if(!processoComProblemas){
            processosDespachadosCorretamente.push(processo);
        }else{
            processosDespachadosComProblemas.push(processo);
        }
    }
    
    protected int contarProcessosDespachados(){
        return processosDespachadosCorretamente.size() + processosDespachadosComProblemas.size();
    }
    
    protected int contarDocumentosDespachados(){
        return documentosDespachados;
    }
    
    protected int contarDocumentosPerdidos(){
        return documentosPerdidos;
    }
}
