package estudantes.entidades;

import professor.entidades.CodigoCurso;
import java.util.Objects;

/**
 * Classe que representa um atestado de um estudante.
 * Estende de Registro para herdar os atributos de identificacao do aluno,
 * adicionando a descricao detalhada e a categoria do atestado.
 */
public class Atestado extends Registro {
    private String descricao; //descricao detalhada do que esta sendo atestado
    private String categoria; //categoria do atestado (ex: saude, comparecimento, etc.)

    //construtor
    public Atestado(String criador, CodigoCurso codigoCurso, int paginas, long autenticacao, String estudante, long matricula, String descricao, String categoria) {
        super(criador, codigoCurso, paginas, autenticacao, estudante, matricula); 
        this.descricao = descricao;
        this.categoria = categoria;
    }

    //getters
    public String getDescricao() {
        return descricao;
    }

    public String getCategoria() {
        return categoria;
    }

    //metodos equals e hash code
    @Override
    public boolean equals(Object o) {
        //Verifica se o objeto comparado é da mesma classe e possui exatamente os mesmos dados herdados, a mesma descricao e a mesma categoria
        if (this == o) return true; //ve se tem a mesma instancia de memoria
        if (o == null || getClass() != o.getClass()) return false; //Garante que o objeto nao e nulo e que ambos sao da mesma classe exata
        if (!super.equals(o)) return false; //Usa a logica do pai para checar os dados herdados
        
        Atestado cast = (Atestado) o; //cast
        return Objects.equals(getDescricao(), cast.getDescricao()) && Objects.equals(getCategoria(), cast.getCategoria());//compara os atributos se sao iguais
    }

    @Override   
    //Gera um numero de identificacao para o atestado baseado nos dados herdados, na descricao e na categoria, usado para organizar o objeto em colecoes
    public int hashCode() {
        return Objects.hash(super.hashCode(), getDescricao(), getCategoria());
    }
}