package estudantes.entidades;

import professor.entidades.CodigoCurso;
import java.util.Objects;

public class Diploma extends Certificado {
    // definição atributos
    private String habilitacao;

    // construtor
    public Diploma(String criador, CodigoCurso codigoCurso, int paginas, long autenticacao,
                   String estudante, long matricula, String descricao, String habilitacao) {
        super(criador, codigoCurso, paginas, autenticacao, estudante, matricula, descricao);
        this.habilitacao = habilitacao;
    }

    // get
    public String getHabilitacao() {
        return habilitacao;
    }

    @Override
    public boolean equals(Object o) { // define o método para comparar se dois diplomas são iguais
        if (this == o) return true; // retorna true se forem a exata mesma instância na memória
        if (o == null || getClass() != o.getClass()) return false; // retorna false se o objeto for nulo ou de classes diferentes
        Diploma diploma = (Diploma) o; // converte o objeto genérico para o tipo Diploma (cast)
        return super.equals(diploma) && Objects.equals(habilitacao, diploma.habilitacao); // compara os atributos herdados via super.equals() e a habilitação exclusiva

    }

    @Override
    public int hashCode() { // define o método que gera a impressão digital numérica do objeto
        return Objects.hash(super.hashCode(), habilitacao); // calcula o hash combinando o hash da classe pai com o atributo habilitação
    }
}