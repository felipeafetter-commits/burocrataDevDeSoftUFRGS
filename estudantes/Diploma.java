package estudantes.entidades;

import professor.entidades.CodigoCurso;
import java.util.Objects;

public class Diploma extends Certificado {
    private String habilitacao;

    public Diploma(String criador, CodigoCurso codigoCurso, int paginas, long autenticacao,
                   String estudante, long matricula, String descricao, String habilitacao) {
        super(criador, codigoCurso, paginas, autenticacao, estudante, matricula, descricao);
        this.habilitacao = habilitacao;
    }

    public String getHabilitacao() {
        return habilitacao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Diploma diploma = (Diploma) o;
        return super.equals(diploma) && Objects.equals(habilitacao, diploma.habilitacao); // super.equals() aqui roda com "this" e "diploma" sendo ambos Diploma em tempo de
        // execução, então getClass() dentro de Certificado.equals() também bate (Diploma == Diploma)

    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), habilitacao);
    }
}