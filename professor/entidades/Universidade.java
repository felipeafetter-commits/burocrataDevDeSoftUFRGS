package professor.entidades;

import estudantes.entidades.Burocrata;
import estudantes.entidades.Documento;
import java.util.HashMap;
import java.util.Random;

/**
 * Classe que representa a universidade com todos os seus recursos.
 * <br><br>
 * <strong>Não mexa aqui!!!</strong>
 * 
 * @author Jean Cheiran
 */
public class Universidade {
    
    /**
     * Constante usada pela classe Random como base para os números aleatórios.
     */
    public static final int SEMENTE = 1;
            
    private Burocrata burocrata;
    private Secretaria secretaria;
    private Mesa mesa;
    private HashMap<CodigoCurso,Curso> cursos;
    private Random gerador;
    
    /**
     * Construtor da Universidade.
     * 
     * <strong>NÃO USE ESSE CONSTRUTOR!!!</strong>
     */
    public Universidade(){
        gerador = new Random(SEMENTE);
        
        secretaria = new Secretaria();
        mesa = new Mesa();
        cursos = new HashMap<>();
        
        for(CodigoCurso codigo : CodigoCurso.values()){
            cursos.put(codigo, new Curso(codigo));
        }
        
        burocrata = new Burocrata(mesa, this);
    }
    
    /**
     * Executa uma simulação da universidade.
     * 
     * <strong>NÃO INVOQUE ESSE MÉTODO!!!</strong>
     */
    public void simular(){
        //repõe espaços vazios de processos despachados
        mesa.reporProcessosVazios();
        
        //percorre todos os cursos
        for(CodigoCurso codigo : CodigoCurso.values()){
            //chance de 20% de aparecerem de 1 a 29 novos documentos no monte de cada curso
            if(gerador.nextInt(0, 5) % 5 == 0){
                int quantidade = gerador.nextInt(1, 30);
                cursos.get(codigo).criarDocumentos(quantidade);
            }
        }
        
        //invoca as ações do burocrata
        burocrata.trabalhar();
        
        //verificação de inconsistências na quantidade de documentos
        int documentosNosMontes = 0;
        for(CodigoCurso codigo : CodigoCurso.values()){
            documentosNosMontes += cursos.get(codigo).contarDocumentosNoMonte();
        }
        if(secretaria.contarDocumentosDespachados() + secretaria.contarDocumentosPerdidos() + documentosNosMontes > Curso.getDocumentosCriados()){
            throw new RuntimeException("Documentos misteriosos apareceram nos processos! Podem ser duplicatas");
        }
    }
    
    /**
     * Despacha o processo para a secretaria da universidade.
     * <br><br>
     * Além de despachar o processo para verificação da secretaria acadêmica,
     * esse método vai remover o processo na mesa do burocrata substituindo
     * o objeto por <strong>null</strong>. Dessa forma, é importante que o
     * burocrata verifique se o processo em sua mesa é diferente de null antes
     * de invocar métodos a partir dele para evitar uma NullPointerException.
     * <br><br>
     * Um processo despachado não é substituído por um novo processo vazio
     * imediatamente. É necessário esperar que isso ocorra em um próximo ciclo
     * da simulação, então você deve sempre verificar se um processo existe
     * antes de tentar manipular ele.
     * <br><br>
     * Um processo não despachado permanece na mesa do burocrata 
     * indefinidamente.
     * 
     * @param processo a ser despachado
     */
    public void despachar(Processo processo){
        if(processo != null){
            mesa.removerProcessoDaMesa(mesa.verificarOrigem(processo));
            secretaria.despachar(processo, burocrata);
        }
    }
    
    /**
     * Remove um documento do monte de um curso especificado.
     * <br><br>
     * Esse método encontra e remove a primeira ocorrência do documento passado
     * como parâmetro do monte de um curso. A identificação do documento usa
     * o método <strong>equals</strong> para fazer a comparação, logo o
     * documento removido é o primeiro documento estruturalmente igual àquele
     * passado como parâmetro.
     * <br><br>
     * Em princípio, não devem existir documentos estruturalmente iguais no 
     * monte, mas isso não é garantido no simulador.
     * 
     * @param documento a ser removido
     * @param codigo do monte usado
     * @return true se o documento existia no monte, ou false caso contrário
     */
    public boolean removerDocumentoDoMonteDoCurso(Documento documento, CodigoCurso codigo){
        if(documento != null){
            return cursos.get(codigo).removerDocumento(documento);
        }
        return false;
    }
    
    /** Devolve um documento ao monte de um curso especificado.
     * <br><br>
     * Esse método tenta devolver um documento ao monte de um curso, mas não
     * verifica se ele já existe lá dentro. Dessa forma, você pode acabar
     * colocando o mesmo documento várias vezes no monte se invocar esse método
     * várias vezes, causando um problema difícil de detectar na simulação.
     * <br><br>
     * Se você tentar devolver um documento de um curso ao monte de outro curso,
     * ele é recusado e não é colocado lá e o método retorna false.
     * 
     * @param documento a ser devolvido
     * @param codigo do monte para devolução
     * @return true se o curso do documento é o mesmo curso do monte e se ele
     * foi devolvido, ou false caso contrário
     */
    public boolean devolverDocumentoParaMonteDoCurso(Documento documento, CodigoCurso codigo){
        if(documento == null || !documento.getCodigoCurso().equals(codigo)){
            return false;
        }
        cursos.get(codigo).devolverDocumento(documento);
        return true;
    }
    
    /**
     * Retorna um vetor contendo os documentos do monte do curso especificado.
     * <br><br>
     * O uso desse vetor deve ser cuidadoso, pois os elementos são
     * referências para os documentos que estão também no monte, ou seja, 
     * modificar o estado interno de um documento do vetor causa modificação no
     * mesmo documento do monte do curso. Por outro lado, alterar o vetor não 
     * causa efeito no monte do curso, ou seja, remover um elemento do vetor não 
     * modifica em nada os documentos no monte do curso.
     * <br><br>
     * Se você quiser remover um documento do monte do curso de verdade, precisa
     * usar o método <strong>removerDocumentoDoMonteDoCurso</strong> 
     * disponível na classe Universidade.
     * <br><br>
     * Se você quiser devolver um documento para o monte do curso, precisa
     * usar o método <strong>devolverDocumentoParaMonteDoCurso</strong> 
     * disponível na classe Universidade.
     * 
     * @param codigo da enumeração CodigoCurso
     * @return vetor de documentos sem qualquer ordem específica
     * @see professor.entidades.Universidade#removerDocumentoDoMonteDoCurso(estudantes.entidades.Documento, professor.entidades.CodigoCurso)
     * @see professor.entidades.Universidade#devolverDocumentoParaMonteDoCurso(estudantes.entidades.Documento, professor.entidades.CodigoCurso) 
     */
    public Documento[] pegarCopiaDoMonteDoCurso(CodigoCurso codigo){
        return cursos.get(codigo).pegarCopiaDoMonte();
    }
    
    /**
     * Retorna a quantidade de documentos no monte do curso especificado.
     * <br><br>
     * @param codigo da enumeração CodigoCurso
     * @return quantidade de documentos no monte do curso
     */
    public int contarDocumentosNoMonteDoCurso(CodigoCurso codigo){
        return cursos.get(codigo).contarDocumentosNoMonte();
    }
    
    /**
     * Retorna a quantidade de processos despachados pelo burocrata.
     * <br><br>
     * Esse método contabiliza tanto processos despachados com sucesso quanto
     * processos destruídos no despacho por ultrapassarem a capacidade máxima
     * de páginas nos documentos incluídos.
     * 
     * @return quantidade total de processos despachados
     */
    public int contarProcessosDespachados(){
        return secretaria.contarProcessosDespachados();
    }
    
    /**
     * Retorna a quantidade de documentos despachados pelo burocrata.
     * <br><br>
     * Esse método não conta documentos perdidos em processos destruídos no
     * despacho por ultrapassarem a capacidade máxima de páginas nos documentos
     * incluídos, mas conta documentos em processos que descumpriram uma ou
     * mais regras da universidade.
     * 
     * @return quantidade total de documentos despachados
     */
    public int contarDocumentosDespachados(){
        return secretaria.contarDocumentosDespachados();
    }
    
    /**
     * Retorna a quantidade de documentos perdidos em despachos fracassados.
     * <br><br>
     * Esse método conta apenas documentos perdidos em processos destruídos no
     * despacho por ultrapassarem a capacidade máxima de páginas nos documentos
     * incluídos.
     * 
     * @return quantidade total de documentos perdidos após despacho
     */
    public int contarDocumentosPerdidos(){
        return secretaria.contarDocumentosPerdidos();
    }
    
    /**
     * Retorna a quantidade de documentos que apareceram nos montes dos cursos.
     * <br><br>
     * Essa contagem inclui qualquer documento criado na simulação, não
     * importando sua localização ou seu destino (destruído, despachado, no
     * monte de um curso etc.).
     * 
     * @return quantidade total de documentos criados na simulação
     */
    public int contarDocumentosCriados(){
        return Curso.getDocumentosCriados();
    }
    
    /**
     * Retorna o estresse acumulado do burocrata.
     * <br><br>
     * @return estresse atual do burocrata
     */
    public int verificarEstresseDoBurocrata(){
        return burocrata.getEstresse();
    }
}