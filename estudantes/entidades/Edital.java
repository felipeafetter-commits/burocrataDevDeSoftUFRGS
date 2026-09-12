package estudantes.entidades;

import professor.entidades.CodigoCurso;
import java.util.Objects;
import java.util.Arrays;

public class Edital extends Norma {

    private String responsaveis[];

    public Edital(String criador, CodigoCurso codigoCurso, int paginas, int numero, boolean valido, String texto,
            String responsaveis[]) {
        super(criador, codigoCurso, paginas, numero, valido, texto);
        this.responsaveis = responsaveis;

    }

    public String[] getResponsaveis() {
        return responsaveis;

    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        Edital outro = (Edital) obj;
        return Arrays.equals(this.responsaveis, outro.responsaveis);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), Arrays.hashCode(this.responsaveis));
    }

}
