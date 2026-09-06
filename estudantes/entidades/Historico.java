package estudantes.entidades;

import java.util.Arrays;
import professor.entidades.CodigoCurso;
import java.util.Objects;;

public class Historico extends Registro {
    private double coeficiente;
    private String[] componentes;

    public Historico(double coeficiente, String[] componentes, String estudante, long matricula, String criador, CodigoCurso codigoCurso, int paginas, long autenticacao) {
        super(estudante, matricula, criador, codigoCurso, paginas, autenticacao);
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
        int result = Objects.hash(getCoeficiente());
        result = 31 * result + Arrays.hashCode(getComponentes());
        return result;
    }
}
