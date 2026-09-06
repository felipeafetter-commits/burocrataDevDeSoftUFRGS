package estudantes.entidades;

import professor.entidades.CodigoCurso;
import java.util.Arrays;
import java.util.Objects;

public class Ata extends Documento {
    private int numero;
    private String texto;
    private String[] presentes;

    public Ata(String criador, CodigoCurso codigoCurso, int paginas, int numero, String texto, String[] presentes) {
        super(criador, codigoCurso, paginas);
        this.numero = numero;
        this.texto = texto;
        this.presentes = presentes;
    }

    public int getNumero() {
        return numero;
    }

    public String getTexto() {
        return texto;
    }

    public String[] getPresentes() {
        return presentes;
    }

    @Override
    public boolean equals(Object o) { // comparar se dois objetos são logicamente iguais
        if (this == o) return true; // mesma instância na memória
        if (o == null || getClass() != o.getClass()) return false; 
        Ata ata = (Ata) o; // cast para acessar os atributos específicos da Ata
        return getPaginas() == ata.getPaginas() // compara tanto os campos próprios da Ata quanto os herdados de Documento
                && numero == ata.numero
                && Objects.equals(getCriador(), ata.getCriador())
                && getCodigoCurso() == ata.getCodigoCurso()
                && Objects.equals(texto, ata.texto)
                && Arrays.equals(presentes, ata.presentes); // Objects.equals evita NPE com strings nulas, Arrays.equals compara elemento a elemento do vetor
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(getCriador(), getCodigoCurso(), getPaginas(), numero, texto); // combina os campos herdados de Documento com os próprios da Ata
        result = 31 * result + Arrays.hashCode(presentes); // 31 espalha bem o hash; soma o hash do vetor de presentes
        return result;
    }
}