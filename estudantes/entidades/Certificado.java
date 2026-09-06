package estudantes.entidades;

import professor.entidades.CodigoCurso;
import java.util.Objects;

public class Certificado extends Registro {
    private String descricao;

    public Certificado(String criador, CodigoCurso codigoCurso, int paginas, long autenticacao,
                        String estudante, long matricula, String descricao) {
        super(estudante, matricula, criador, codigoCurso, paginas, autenticacao);
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Certificado that = (Certificado) o;
        return getPaginas() == that.getPaginas()
                && getAutenticacao() == that.getAutenticacao()
                && getMatricula() == that.getMatricula()
                && Objects.equals(getCriador(), that.getCriador())
                && getCodigoCurso() == that.getCodigoCurso()
                && Objects.equals(getEstudante(), that.getEstudante())
                && Objects.equals(descricao, that.descricao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCriador(), getCodigoCurso(), getPaginas(), getAutenticacao(),
                getEstudante(), getMatricula(), descricao);
    }
}