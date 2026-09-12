package estudantes.entidades;

import professor.entidades.CodigoCurso;
import java.util.Objects;

public class Atestado extends Registro {
    private String descricao;
    private String categoria;

    public Atestado(String criador, CodigoCurso codigoCurso, int paginas, long autenticacao, String estudante, long matricula, String descricao, String categoria) {
        super(criador, codigoCurso, paginas, autenticacao, estudante, matricula); // ← ordem nova
        this.descricao = descricao;
        this.categoria = categoria;
    }

    public String getDescricao() {
        return descricao;
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
        return Objects.hash(super.hashCode(), getDescricao(), getCategoria());
    }
}
