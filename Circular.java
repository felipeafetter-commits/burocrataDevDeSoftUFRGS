package estudantes.entidades;

import java.util.Arrays;
import java.util.Objects;

import professor.entidades.CodigoCurso;
import estudantes.entidades.Deliberacao;

public class Circular extends Deliberacao {
    private String Destinatarios[];

    public Circular(String criador, CodigoCurso codigoCurso, int paginas, String texto, String destinatarios[]) {
        super(criador, codigoCurso, paginas, texto);
        this.Destinatarios = destinatarios;
    }

    public String[] getDestinatarios() {
        return Destinatarios;
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        Circular outra = (Circular) obj;
        return Arrays.equals(this.Destinatarios, outra.Destinatarios);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), Arrays.hashCode(this.Destinatarios));
    }

}
