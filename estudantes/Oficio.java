package estudantes.entidades;

import professor.entidades.CodigoCurso;
import java.util.Objects;

public class Oficio extends Deliberacao {
    private String destinatario;

    public Oficio(String criador, CodigoCurso codigoCurso, int paginas, String texto, String destinatario) {
        super(criador, codigoCurso, paginas, texto);
        this.destinatario = destinatario;
    }

    public String getDestinatario() {
        return destinatario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Oficio oficio = (Oficio) o;
        return getPaginas() == oficio.getPaginas()
                && Objects.equals(getCriador(), oficio.getCriador())
                && getCodigoCurso() == oficio.getCodigoCurso()
                && Objects.equals(getTexto(), oficio.getTexto())
                && Objects.equals(destinatario, oficio.destinatario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCriador(), getCodigoCurso(), getPaginas(), getTexto(), destinatario);
    }
}