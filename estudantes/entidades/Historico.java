package estudantes.entidades;

import java.util.Arrays;
import professor.entidades.CodigoCurso;
import java.util.Objects;

public class Historico extends Registro {
    private double coeficiente;
    private String[] componentes;

    public Historico(String criador, CodigoCurso codigoCurso, int paginas, long autenticacao, String estudante, long matricula, double coeficiente, String[] componentes) {
        super(criador, codigoCurso, paginas, autenticacao, estudante, matricula); // ← trocar aqui
        this.coeficiente = coeficiente;
        this.componentes = componentes;
    }

    public double getCoeficiente() {
        return coeficiente;
    }

    public String[] getComponentes() {
        return componentes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)return true;
        if (o == null || getClass() != o.getClass())return false;
        if(!super.equals(o))return false;
        Historico cast = (Historico) o;
        return getCoeficiente() == cast.getCoeficiente()&& Arrays.equals(getComponentes(), cast.getComponentes());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(super.hashCode(), getCoeficiente());
        result = 31 * result + Arrays.hashCode(getComponentes());
        return result;
    }
}
