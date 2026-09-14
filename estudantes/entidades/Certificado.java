package estudantes.entidades;

import professor.entidades.CodigoCurso;
import java.util.Objects;

public class Certificado extends Registro {
    // definição atributo
    private String descricao;

    // construtor
    public Certificado(String criador, CodigoCurso codigoCurso, int paginas, long autenticacao,
            String estudante, long matricula, String descricao) {
        super(criador, codigoCurso, paginas, autenticacao, estudante, matricula);
        this.descricao = descricao;
    }

    // get
    public String getDescricao() {
        return descricao;
    }

    @Override
    public boolean equals(Object o) { // define o método para comparar se dois certificados são iguais
        if (this == o) return true; // retorna true se forem a exata mesma instância na memória
        if (o == null || getClass() != o.getClass()) return false; // retorna false se o objeto for nulo ou de classes diferentes
        Certificado that = (Certificado) o; // converte o objeto genérico para o tipo Certificado (cast)
        return getPaginas() == that.getPaginas() // compara a quantidade de páginas
                && getAutenticacao() == that.getAutenticacao() // compara o código/número de autenticação
                && getMatricula() == that.getMatricula() // compara a matrícula associada
                && Objects.equals(getCriador(), that.getCriador()) // compara o criador
                && getCodigoCurso() == that.getCodigoCurso() // compara o código do curso
                && Objects.equals(getEstudante(), that.getEstudante()) // compara o estudante
                && Objects.equals(descricao, that.descricao); // compara a descrição exclusiva do certificado
    }

    @Override
    public int hashCode() { // define o método que gera a impressão digital numérica do objeto
        return Objects.hash(getCriador(), getCodigoCurso(), getPaginas(), getAutenticacao(), // calcula o hash combinando o primeiro bloco de atributos
                getEstudante(), getMatricula(), descricao); // completa o cálculo do hash combinando os atributos restantes
    }
}