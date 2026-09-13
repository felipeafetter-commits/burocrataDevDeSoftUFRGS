package estudantes.entidades;

import professor.entidades.CodigoCurso;
import java.util.Objects;

public class Portaria extends Norma {

    private int anoInicio;

    public Portaria(String criador, CodigoCurso codigoCurso, int paginas, int numero, boolean valido, String texto,
            int anoinicio) {

        super(criador, codigoCurso, paginas, numero, valido, texto); // Classe filha de Norma e herda seus atributod
        this.anoInicio = anoinicio;
    }

    public int getAnoInicio() { // método para retornar o estado do atributo privado anoInicio
        return anoInicio;
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) { //Usa método já implementado em Norma
            return false;
        }
        Portaria outra = (Portaria) obj; // Se for atendido, faz o Casting com portaria
        return this.anoInicio == outra.anoInicio; // Compara se possuem o mesmo estado do atributo anoInicio, confirmando que se tratam da mesma portaria
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), anoInicio); // Cria código Hash, dois objetos iguais pelo eqauls terão mesmo código
    }

}