package estudantes.entidades;

import professor.entidades.CodigoCurso;
import java.util.Arrays;
import java.util.Objects;

/**
 * Classe que representa um plano academico.
 * Estende de DocumentoAcademico para herdar atributos comuns,
 * adicionando o responsavel e as etapas do planejamento.
 */
public class Plano extends DocumentoAcademico {
    private String responsavel; //nome do responsavel pelo plano
    private String[] planejamento; //vetor contendo as etapas do planejamento

    //construtor
    public Plano(String criador, CodigoCurso codigoCurso, int paginas, long autenticacao, String responsavel, String[] planejamento) {
        super(criador, codigoCurso, paginas, autenticacao);
        this.responsavel = responsavel;
        this.planejamento = planejamento;
    }

    //getters
    public String getResponsavel() {
        return responsavel;
    }

    public String[] getPlanejamento() {
        return planejamento;
    }

    //metodos equals e hash code
    @Override
    public boolean equals(Object o) {
        //Verifica se o objeto comparado é da mesma classe e possui exatamente os mesmos dados herdados, o mesmo responsavel e o mesmo planejamento
        if (this == o) return true; //ve se tem a mesma instancia de memoria
        if (o == null || getClass() != o.getClass()) return false; //Garante que o objeto nao e nulo e que ambos sao da mesma classe exata
        if (!super.equals(o)) return false; //Usa a logica do pai para checar os dados herdados
        
        Plano cast = (Plano) o; //cast
        return Objects.equals(getResponsavel(), cast.getResponsavel()) && Arrays.equals(getPlanejamento(), cast.getPlanejamento());
    }

    @Override
    //Gera um numero de identificacao para o plano baseado no responsavel e no planejamento, usado para organizar o objeto em colecoes
    public int hashCode() {
        int result = Objects.hash(getResponsavel());
        result = 31 * result + Arrays.hashCode(getPlanejamento());
        return result;
    }
}