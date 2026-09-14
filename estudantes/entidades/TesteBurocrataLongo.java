package estudantes.entidades;

import professor.entidades.Universidade;

public class TesteBurocrataLongo {
    public static void main(String[] args) {
        for (int rodada = 1; rodada <= 5; rodada++) {
            System.out.println("=== Rodada " + rodada + " ===");
            Universidade universidade = new Universidade();
            
            try {
                for (int i = 1; i <= 2000; i++) {
                    universidade.simular();
                }
                
                int criados = universidade.contarDocumentosCriados();
                int despachados = universidade.contarDocumentosDespachados();
                int perdidos = universidade.contarDocumentosPerdidos();
                int processos = universidade.contarProcessosDespachados();
                int estresse = universidade.verificarEstresseDoBurocrata();

                int totalNosMontes = 0;
                for (professor.entidades.CodigoCurso curso : professor.entidades.CodigoCurso.values()) {
                    totalNosMontes += universidade.contarDocumentosNoMonteDoCurso(curso);
                }
                
                double eficiencia = 0.0;
                if (processos > 0 && criados > 0) {
                    eficiencia = ((double) despachados / processos) * ((double) despachados / criados) / (estresse + 1);
                }
                
                System.out.printf("Criados: %d | Despachados: %d | Perdidos: %d | Processos: %d | Estresse: %d | Eficiência: %.4f%n",
                    criados, despachados, perdidos, processos, estresse, eficiencia);
                    
            } catch (Exception e) {
                System.out.println("QUEBROU na rodada " + rodada + ": " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}