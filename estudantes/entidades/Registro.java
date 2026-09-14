package estudantes.entidades;

import professor.entidades.CodigoCurso;
import java.util.Objects;

/**
 * classe abstrata que representa um registro academico especifico de um aluno
 * estende do documento academico para herdar atributos comuns aos documentos ,
 * adicionando dados de identificacao do estudante como nome e numero
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
        //verifica se o objeto comparado é da mesma classe e possui exatamente os mesmos dados herdados, a mesma matrícula e o mesmo nome de estudante
        if (this == o)return true; //ve se tem a mesma instancia de memoria
        if (o == null || getClass() != o.getClass())return false; //garante que o objeto nao e nulo e que ambos sao da mesma classe exata
        if(!super.equals(o))return false;
        Registro cast = (Registro) o; //casting
        return getMatricula() == cast.getMatricula()&& Objects.equals(getEstudante(), cast.getEstudante()); //compara os atributos se sao iguais
    }

    @Override
    //gera um numero de identificacao para o registro baseado nos dados herdados, nome e na matricula do estudante, usado para organizar o objeto em colecoes
    public int hashCode() {return Objects.hash(super.hashCode(), getEstudante(), getMatricula());}
}
