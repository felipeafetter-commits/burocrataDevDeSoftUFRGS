package estudantes.entidades;

import java.util.Arrays;
import java.util.Objects;
import estudantes.entidades.DocumentoAdministrativo;
import professor.entidades.CodigoCurso;

public class Norma extends DocumentoAdministrativo {

    private int numero;
    private boolean valido;
    private String texto;

    public Norma(String criador, CodigoCurso codigoCurso, int paginas, int numero, boolean valido, String texto) {
        super(criador, codigoCurso, paginas);
        this.numero = numero;
        this.valido = valido;
        this.texto = texto;
    }

    public int getNumero() {
        return numero;
    }

    public boolean getValido() {
        return valido;
    }

    public boolean isValido() {
        return valido;
    }

    public String getTexto() {
        return texto;
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
        return this.numero == outra.numero
                && this.valido == outra.valido
                && this.texto.equals(outra.texto)
                && this.getCriador().equals(outra.getCriador())
                && this.getCodigoCurso().equals(outra.getCodigoCurso())
                && this.getPaginas() == outra.getPaginas();
    }

    @Override
    public int hashCode() {
        return Objects.hash(numero, valido, texto, getCriador(), getCodigoCurso(), getPaginas());
    }

}
