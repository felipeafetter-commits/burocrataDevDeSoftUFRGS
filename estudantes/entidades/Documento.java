/**
 * @author Otávio Zucchetti Dalla Costa (616320), Felipe Burmann Machado (602624), Felipe Fetter (609593)
 */

package estudantes.entidades;

import professor.entidades.CodigoCurso;

public abstract class Documento {
    // definição de atributos
    private String criador;
    private CodigoCurso codigoCurso;
    private int paginas;

    // construtor
    public Documento(String criador, CodigoCurso codigoCurso, int paginas) {
        this.criador = criador;
        this.codigoCurso = codigoCurso;
        this.paginas = paginas;
    }

    // getters
    public String getCriador() {
        return criador;
    }

    public CodigoCurso getCodigoCurso() {
        return codigoCurso;
    }

    public int getPaginas() {
        return paginas;
    }

    // obriga cada subclasse de Documento a definir sua própria regra para determinar quando dois documentos são idênticos
    @Override
    public abstract boolean equals(Object obj);

    // obriga cada subclasse a gerar um hash consistente para uso em coleções
    // e comparações, sem garantir unicidade absoluta entre objetos distintos
    @Override
    public abstract int hashCode();
}
