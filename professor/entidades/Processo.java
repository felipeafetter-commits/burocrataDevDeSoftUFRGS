package professor.entidades;

import estudantes.entidades.Documento;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * Classe que representa a pasta de um processo para colocar documentos dentro.
 * <br><br>
 * <strong>Não mexa aqui!!!</strong>
 * 
 * @author Jean Cheiran
 */
public class Processo {
    
    private LinkedList<Documento> documentos;
    
    protected Processo(){
        documentos = new LinkedList<>();
    }
    
    /**
     * Adiciona um documento ao processo.
     * <br><br>
     * O documento é incluído no início da lista do processo, mas isso não deve
     * fazer muita diferença para o burocrata se organizar.
     * 
     * @param documento incluído
     */
    public void adicionarDocumento(Documento documento){
        documentos.push(documento);
    }
    
    /**
     * Remove um documento do processo.
     * <br><br>
     * Esse método encontra e remove a primeira ocorrência do documento passado
     * como parâmetro no processo. A identificação do documento usa
     * o método <strong>equals</strong> para fazer a comparação, logo o
     * documento removido é o primeiro documento estruturalmente igual àquele
     * passado como parâmetro.
     * <br><br>
     * Em princípio, não devem existir documentos estruturalmente iguais no 
     * processo, mas isso não é garantido no simulador.
     * 
     * @param documento a ser removido
     * @return true se o documento existia no processo, ou false caso contrário
     */
    public boolean removerDocumento(Documento documento){
        if(documento != null){
            return documentos.remove(documento);
        }
        return false;
    }
    
    /**
     * Retorna um vetor contendo os documentos do processo.
     * <br><br>
     * O uso desse vetor deve ser cuidadoso, pois os elementos são
     * referências para os documentos que estão também no processo, ou seja, 
     * modificar o estado interno de um documento do vetor causa modificação no
     * mesmo documento do processo. Por outro lado, alterar o vetor não 
     * causa efeito no processo do curso, ou seja, remover um elemento do vetor 
     * não modifica em nada os documentos no processo.
     * <br><br>
     * Se você quiser remover um documento do processo de verdade, precisa
     * usar o método <strong>removerDocumento</strong>.
     * <br><br>
     * Se você quiser incluir ou devolver um documento no processo, precisa 
     * usar o método <strong>adicionarDocumento</strong>.
     * 
     * @return vetor de documentos cujos primeiros elementos são os documentos
     * colocados por último
     */
    public Documento[] pegarCopiaDoProcesso(){
        return Arrays.copyOf(documentos.toArray(), documentos.size(), Documento[].class);
    }
    
    /**
     * Retorna a quantidade total de documentos no processo.
     * 
     * @return quantidade de documentos no processo
     */
    public int contarDocumentos(){
        return documentos.size();
    }
    
    protected int contarPaginas(){
        int paginas = 0;
        for(Documento documento : documentos){
            paginas += documento.getPaginas();
        }
        return paginas;
    }
}
