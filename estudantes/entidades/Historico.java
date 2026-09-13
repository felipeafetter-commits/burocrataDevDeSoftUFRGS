package estudantes.entidades;

import java.util.Arrays;
import professor.entidades.CodigoCurso;
import java.util.Objects;

/**
 * Classe que representa o historico academico de um estudante.
 * Estende de Registro para herdar os atributos de identificacao do aluno,
 * adicionando o coeficiente de rendimento e os componentes curriculares cursados.
 */
public class Historico extends Registro {
    private double coeficiente; //coeficiente de rendimento nota media do estudante
    private String[] componentes; //vetor contendo os  disciplinas cursadas

    //construtor
    public Historico(String criador, CodigoCurso codigoCurso, int paginas, long autenticacao, String estudante, long matricula, double coeficiente, String[] componentes) {
        super(criador, codigoCurso, paginas, autenticacao, estudante, matricula);
        this.coeficiente = coeficiente;
        this.componentes = componentes;
    }

    //getters
    public double getCoeficiente() {
        return coeficiente;
    }

    public String[] getComponentes() {
        return componentes;
    }

    //metodos equals e hash code
    @Override
    public boolean equals(Object o) {
        //Verifica se o objeto comparado é da mesma classe e possui exatamente os mesmos dados herdados, o mesmo coeficiente e os mesmos componentes
        if (this == o) return true; //ve se tem a mesma instancia de memoria
        if (o == null || getClass() != o.getClass()) return false; //Garante que o objeto nao e nulo e que ambos sao da mesma classe exata
        if (!super.equals(o)) return false; //Usa a logica do pai para checar os dados herdados
        
        Historico cast = (Historico) o; //cast
        return Double.compare(cast.getCoeficiente(), getCoeficiente()) == 0 && Arrays.equals(getComponentes(), cast.getComponentes());
    }

    @Override
    //Gera um numero de identificacao para o historico baseado nos dados herdados, no coeficiente e nos componentes, usado para organizar o objeto em colecoes
    public int hashCode() {
        int result = Objects.hash(super.hashCode(), getCoeficiente());
        result = 31 * result + Arrays.hashCode(getComponentes());
        return result;
    }
}