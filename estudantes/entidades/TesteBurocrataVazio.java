package estudantes.entidades;

import professor.entidades.Universidade;

public class TesteBurocrataVazio {
    public static void main(String[] args) {
        Universidade universidade = new Universidade();
        
        System.out.println("Rodando 1 única simulação (pode ou não criar documentos)...");
        try {
            universidade.simular(); // só 1 chamada
            System.out.println("OK — não quebrou mesmo com poucos/nenhum documento.");
            System.out.println("Documentos criados: " + universidade.contarDocumentosCriados());
        } catch (Exception e) {
            System.out.println("ERRO: " + e.getMessage());
            e.printStackTrace();
        }
    }
}