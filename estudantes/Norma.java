package estudantes.entidades;

import java.util.Arrays;
import java.util.Objects;
import estudantes.entidades.DocumentoAdministrativo;
import professor.entidades.CodigoCurso;

public class Norma extends DocumentoAdministrativo {

    private int Numero;
    private boolean Valido;
    private String Texto;

    public Norma(String criador, CodigoCurso codigoCurso, int paginas, int numero, boolean valido, String texto) {
        super(criador, codigoCurso, paginas);
        this.Numero = numero;
        this.Valido = valido;
        this.Texto = texto;
    }

    public int getNumero() {
        return Numero;
    }

    public boolean getValido() {
        return Valido;
    }

    public String getTexto() {
        return Texto;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Norma outra = (Norma) obj;
        return this.Numero == outra.Numero
                && this.Valido == outra.Valido
                && this.Texto.equals(outra.Texto)
                && this.getCriador().equals(outra.getCriador())
                && this.getCodigoCurso().equals(outra.getCodigoCurso())
                && this.getPaginas() == outra.getPaginas();
    }

    @Override
    public int hashCode() {
        return Objects.hash(Numero, Valido, Texto, getCriador(), getCodigoCurso(), getPaginas());
    }

}
