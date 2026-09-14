package estudantes.entidades;

import professor.entidades.CodigoCurso;
import java.util.Objects;

public class Oficio extends Deliberacao {
    // definição atributo
    private String destinatario;

    // construtor
    public Oficio(String criador, CodigoCurso codigoCurso, int paginas, String texto, String destinatario) {
        super(criador, codigoCurso, paginas, texto);
        this.destinatario = destinatario;
    }

    // get
    public String getDestinatario() {
        return destinatario;
    }

    @Override
    public boolean equals(Object o) { // define o método para comparar se dois ofícios são iguais
        if (this == o) return true; // retorna true se forem a exata mesma instância na memória
        if (o == null || getClass() != o.getClass()) return false; // retorna false se o objeto for nulo ou de classes diferentes
        Oficio oficio = (Oficio) o; // converte o objeto genérico para o tipo Oficio (cast)
        return getPaginas() == oficio.getPaginas() // compara a quantidade de páginas dos dois ofícios
                && Objects.equals(getCriador(), oficio.getCriador()) // compara o criador
                && getCodigoCurso() == oficio.getCodigoCurso() // compara o código do curso
                && Objects.equals(getTexto(), oficio.getTexto()) // compara o texto interno
                && Objects.equals(destinatario, oficio.destinatario); // compara o destinatário exclusivo do ofício
    }

    @Override
    public int hashCode() { // define o método que gera a impressão digital numérica do objeto
        return Objects.hash(getCriador(), getCodigoCurso(), getPaginas(), getTexto(), destinatario); // calcula o hash combinando os mesmos atributos do equals
    }
}