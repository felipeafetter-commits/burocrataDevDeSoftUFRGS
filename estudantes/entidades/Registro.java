package estudantes.entidades;

import professor.entidades.CodigoCurso;
import java.util.Objects;

public abstract class Registro extends DocumentoAcademico {
    private String estudante;
    private long matricula;

    public Registro(String estudante, long matricula, String criador, CodigoCurso codigoCurso, int paginas, long autenticacao) {
        super(criador, codigoCurso, paginas, autenticacao);
        this.estudante = estudante;
        this.matricula = matricula;
    }

    public String getEstudante() {
        return estudante;
    }

    public long getMatricula() {
        return matricula;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)return true;
        if (o == null || getClass() != o.getClass())return false;
        Registro cast = (Registro) o;
        return getMatricula() == cast.getMatricula()&& Objects.equals(getEstudante(), cast.getEstudante());
    }

    @Override
    public int hashCode() {return Objects.hash(getEstudante(), getMatricula());}
}
