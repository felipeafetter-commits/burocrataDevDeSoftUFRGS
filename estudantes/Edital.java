package estudantes.entidades;

import professor.entidades.CodigoCurso;
import java.util.Objects;
import java.util.Arrays;

public class Edital extends Norma {

    private String Destinatarios[];

    public Edital(String criador, CodigoCurso codigoCurso, int paginas, int numero, boolean valido, String texto,
            String destinatarios[]) {
        super(criador, codigoCurso, paginas, numero, valido, texto);
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
        Edital outro = (Edital) obj;
        return Arrays.equals(this.Destinatarios, outro.Destinatarios);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), Arrays.hashCode(this.Destinatarios));
    }

}
