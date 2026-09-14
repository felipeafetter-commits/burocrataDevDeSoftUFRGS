package estudantes.entidades;

import professor.entidades.CodigoCurso;

public abstract class Deliberacao extends DocumentoAdministrativo {
    // definição de atributo
    private String texto;

    // construtor
    public Deliberacao(String criador, CodigoCurso codigoCurso, int paginas, String texto) {
        super(criador, codigoCurso, paginas);
        this.texto = texto;
    }

    // get
    public String getTexto() {
        return texto;
    }

    // obriga cada subclasse de Deliberação a definir sua própria regra para determinar quando dois documentos são idênticos
    @Override
    public abstract boolean equals(Object obj);

    // obriga cada subclasse de Deliberacao a gerar um hash consistente
    // para uso em coleções e comparações
    @Override
    public abstract int hashCode();
}