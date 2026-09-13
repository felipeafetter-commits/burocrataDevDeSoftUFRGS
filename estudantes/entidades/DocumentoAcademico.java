package estudantes.entidades;

import professor.entidades.CodigoCurso;
import java.util.Objects;

/**
 * Classe abstrata que representa um documento academico de forma geral.
 * Estende da classe base Documento para herdar atributos comuns,
 * adicionando o numero de autenticacao especifico para documentos academicos.
 */
public abstract class DocumentoAcademico extends Documento {
    private long autenticacao; //numero de autenticacao do documento

    //construtor
    public DocumentoAcademico(String criador, CodigoCurso codigoCurso, int paginas, long autenticacao) {
        super(criador, codigoCurso, paginas);
        this.autenticacao = autenticacao;
    }

    //getters
    public long getAutenticacao() {
        return autenticacao;
    }

    //metodos equals e hash code
    @Override
    public boolean equals(Object o) {
        //Verifica se o objeto comparado é da mesma classe e possui os mesmos dados herdados e a mesma autenticacao
        if (this == o) return true; //ve se tem a mesma instancia de memoria
        if (o == null || getClass() != o.getClass()) return false; //Garante que o objeto nao e nulo e que ambos sao da mesma classe exata
        
        DocumentoAcademico cast = (DocumentoAcademico) o; //cast
        
        //Compara os atributos da classe base (paginas, criador, codigoCurso) junto com a autenticacao desta classe
        return getPaginas() == cast.getPaginas()
                && getAutenticacao() == cast.getAutenticacao()
                && Objects.equals(getCriador(), cast.getCriador())
                && getCodigoCurso() == cast.getCodigoCurso();
    }

    @Override
    //Gera um numero de identificacao para o documento academico baseado nos dados herdados e na autenticacao, usado para organizar o objeto em colecoes
    public int hashCode() {
        return Objects.hash(getCriador(), getCodigoCurso(), getPaginas(), getAutenticacao());
    }
}