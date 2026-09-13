package estudantes.entidades;

import professor.entidades.CodigoCurso;
import java.util.Objects;

/**
 * Classe abstrata que representa um registro academico especifico de um aluno.
 * Estende do documento academico para herdar atributos comuns aos documentos ,
 * adicionando dados de identificacao do estudante como nome e numero.
 */
public abstract class Registro extends DocumentoAcademico {
    private String estudante; //nome do estudante
    private long matricula; //numero de matricula do estudante

    //construtor 
    public Registro(String criador, CodigoCurso codigoCurso, int paginas, long autenticacao, String estudante, long matricula) {
        super(criador, codigoCurso, paginas, autenticacao);
        this.estudante = estudante;
        this.matricula = matricula;
    }

    //getters
    public String getEstudante() {
        return estudante;
    }

    public long getMatricula() {
        return matricula;
    }

    //metodos equals e hash code
    @Override
    public boolean equals(Object o) {
        //Verifica se o objeto comparado é da mesma classe e possui exatamente os mesmos dados herdados, a mesma matrícula e o mesmo nome de estudante
        if (this == o)return true; //ve se tem a mesma instancia de memoria
        if (o == null || getClass() != o.getClass())return false; //Garante que o objeto nao e nulo e que ambos sao da mesma classe exata
        if(!super.equals(o))return false;
        Registro cast = (Registro) o; //cast
        return getMatricula() == cast.getMatricula()&& Objects.equals(getEstudante(), cast.getEstudante());
    }

    @Override
    //Gera um numero de identificacao para o registro baseado no nome e na matricula do estudante, usado para organizar o objeto em colecoes
    public int hashCode() {return Objects.hash(super.hashCode(), getEstudante(), getMatricula());}
}
