package estudantes.entidades;

import professor.entidades.CodigoCurso;
import java.util.Objects;

public class Portaria extends Norma {

    private int anoInicio;

    public Portaria(String criador, CodigoCurso codigoCurso, int paginas, int numero, boolean valido, String texto,
            int anoinicio) {

        super(criador, codigoCurso, paginas, numero, valido, texto);
        this.anoInicio = anoinicio;
    }

    public int getAnoInicio() {
        return anoInicio;
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        Portaria outra = (Portaria) obj;
        return this.anoInicio == outra.anoInicio;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), anoInicio);
    }

}