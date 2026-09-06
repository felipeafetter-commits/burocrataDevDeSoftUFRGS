package estudantes.entidades;

import professor.entidades.CodigoCurso;
import java.util.Objects;

public abstract class Documento Academico extends Documento {
    private long autenticacao;

    public class DocumentoAcademico(String criador, CodigoCurso codigoCurso, int paginas, long autenticacao) {
        super(getCriador(), getCodigoCurso(), getPaginas());
        this.autenticacao = autenticacao;
    }

    public long getAutenticacao() {
        return autenticacao;
    }

    @Override
    public boolean equals(Object o){
        if this == o) return true;
        if (o == null) || getclass() != o.getClass()) return false;
        DocumentoAcademico cast = (DocumentoAcademico) o;
        return getPaginas() == cast.getPaginas()
                && getAutenticacao() == cast.getAutenticacao()
                && Objects.equals(getCriador(), cast.getCriador())
                && getCodigoCurso() == cast.getCodigoCurso();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCriador(), getCodigoCurso(), getPaginas(), getAutenticacao());
    }
}


