package estudantes.entidades;

import java.util.Arrays;
import java.util.Objects;
import professor.entidades.CodigoCurso;

public class Circular extends Deliberacao {
    private String[] destinatarios;

    public Circular(String criador, CodigoCurso codigoCurso, int paginas, String texto, String destinatarios[]) {
        super(criador, codigoCurso, paginas, texto);
        this.destinatarios = destinatarios;
    }

    public String[] getDestinatarios() {
        return destinatarios;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Circular outra = (Circular) obj;
        return getPaginas() == outra.getPaginas()
                && Objects.equals(getCriador(), outra.getCriador())
                && getCodigoCurso() == outra.getCodigoCurso()
                && Objects.equals(getTexto(), outra.getTexto())
                && Arrays.equals(this.destinatarios, outra.destinatarios);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(getCriador(), getCodigoCurso(), getPaginas(), getTexto());
        result = 31 * result + Arrays.hashCode(this.destinatarios);
        return result;
    }
}