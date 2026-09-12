package estudantes.entidades;

import professor.entidades.CodigoCurso;
import java.util.Arrays;
import java.util.Objects;


public class Plano extends DocumentoAcademico {
    private String responsavel;
    private String[] planejamento;

    public Plano(String criador, CodigoCurso codigoCurso, int paginas, long autenticacao, String responsavel, String[] planejamento) {
        super(criador, codigoCurso, paginas, autenticacao);
        this.responsavel = responsavel;
        this.planejamento = planejamento;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public String[] getPlanejamento() {
        return planejamento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)return true;
        if (o == null || getClass() != o.getClass())return false;
        if(!super.equals(o))return false;
        Plano cast = (Plano) o;
        return Objects.equals(getResponsavel(), cast.getResponsavel())&& Arrays.equals(getPlanejamento(), cast.getPlanejamento());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(getResponsavel());
        result = 31 * result + Arrays.hashCode(getPlanejamento());
        return result;
    }
    
}
