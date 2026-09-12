package estudantes.entidades;

import professor.entidades.Universidade;

public class TesteBurocrata {
    public static void main(String[] args) {
        Universidade universidade = new Universidade();
        
        for (int i = 1; i <= 100; i++) {
            universidade.simular();
        }
        
        int docsDespachados = universidade.contarDocumentosDespachados();
        int procsDespachados = universidade.contarProcessosDespachados();
        int docsCriados = universidade.contarDocumentosCriados();
        int estresse = universidade.verificarEstresseDoBurocrata();
        
        double eficiencia = 0.0;
        if (procsDespachados > 0 && docsCriados > 0) {
            double termo1 = (double) docsDespachados / procsDespachados;
            double termo2 = (double) docsDespachados / docsCriados;
            eficiencia = (termo1 * termo2) / (estresse + 1);
        }
        
        System.out.println("Processos despachados: " + procsDespachados);
        System.out.println("Documentos despachados: " + docsDespachados);
        System.out.println("Documentos criados: " + docsCriados);
        System.out.println("Estresse do burocrata: " + estresse);
        System.out.println("Eficiência: " + eficiencia);
    }
}