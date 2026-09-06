package estudantes.entidades;

import professor.entidades.CodigoCurso;
import java.util.Objects;

public class Atestado extends Registro {
    private String decricao;
    private String categoria;

    public Atestado(String decricao, String categoria, String estudante, long matricula, String criador, CodigoCurso codigoCurso, int paginas, long autenticacao) {
        super(estudante, matricula, criador, codigoCurso, paginas, autenticacao);
        this.decricao = decricao;
        this.categoria = categoria;
    }

    public String getDescricao() {
        return decricao;
    }

    public String getCategoria() {
        return categoria;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)return true;
        if (o == null || getClass() != o.getClass())return false;
        if(!super.equals(o))return false;
        Atestado cast = (Atestado) o;
        return Objects.equals(getDescricao(), cast.getDescricao())&& Objects.equals(getCategoria(), cast.getCategoria());
    }

    @Override   
    public int hashCode() {
        return Objects.hash(getDescricao(), getCategoria());
    }
}
