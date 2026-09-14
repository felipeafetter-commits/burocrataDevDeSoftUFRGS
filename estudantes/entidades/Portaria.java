package estudantes.entidades;

import professor.entidades.CodigoCurso;
import java.util.Objects;

public class Portaria extends Norma {

    private int anoInicio;

    public Portaria(String criador, CodigoCurso codigoCurso, int paginas, int numero, boolean valido, String texto,
            int anoinicio) {

        super(criador, codigoCurso, paginas, numero, valido, texto); // classe filha de Norma e herda seus atributos
        this.anoInicio = anoinicio;
    }

    public int getAnoInicio() { // método para retornar o estado do atributo privado anoInicio
        return anoInicio;
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) { //usa método já implementado em Norma
            return false;
        }
        Portaria outra = (Portaria) obj; // se for atendido, faz o casting com portaria
        return this.anoInicio == outra.anoInicio; // compara se possuem o mesmo estado do atributo anoInicio, confirmando que se tratam da mesma portaria
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), anoInicio); // cria código Hash, dois objetos iguais pelo equals terão mesmo código
    }

}