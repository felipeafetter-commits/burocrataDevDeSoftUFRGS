package professor.entidades;

/**
 * Classe que representa a mesa do burocrata com os processos para organizar.
 * <br><br>
 * <strong>Não mexa aqui!!!</strong>
 * 
 * @author Jean Cheiran
 */
public class Mesa {
    
    private Processo[] processos;
    
    protected Mesa(){
        processos = new Processo[5];
        
        for(int i = 0; i < 5; i++){
            processos[i] = new Processo();
        }
    }
    
    /**
     * Retorna o processo cujo número é passado como parâmetro.
     * <br><br>
     * Há cinco processos na mesa e seus índices vão de 0 a 4. Destaca-se que
     * é importante verificar se esse método retornou um objeto do tipo
     * Processo e não <strong>null</strong> antes de tentar manipular os
     * documentos do processo.
     * 
     * @param numero do processo desejado
     * @return processo especificado, ou null se não houver processo aberto
     * naquela posição
     */
    public Processo getProcesso(int numero){
        return processos[numero];
    }
    
    /**
     * Retorna um vetor contendo os processos na mesa.
     * <br><br>
     * O uso desse vetor deve ser cuidadoso, pois os elementos são
     * referências diretas para os processos que estão também na mesa, ou seja, 
     * modificar o estado interno de um processo causa modificação no processo 
     * na mesa. Ainda, alterar o vetor causa efeito na mesa, ou seja, trocar um
     * elemento do vetor por outro ou apagar um elemento modifica também o 
     * vetor da mesa.
     * @return vetor de processos na mesma ordem que estão na mesa
     */
    public Processo[] getProcessos(){
        return processos;
    }
    
    protected int verificarOrigem(Processo processo){
        for(int i = 0; i < 5; i++){
            if(processos[i] == processo){
                return i;
            }
        }
        return -1;
    }
    
    protected void reporProcessosVazios(){
        for(int i = 0; i < 5; i++){
            if(processos[i] == null){
                processos[i] = new Processo();
            }
        }
    }
    
    protected void removerProcessoDaMesa(int posicao){
        processos[posicao] = null;
    }
}
