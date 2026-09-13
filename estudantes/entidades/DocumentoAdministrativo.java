package estudantes.entidades;

import professor.entidades.CodigoCurso;

public abstract class DocumentoAdministrativo extends Documento {

  public DocumentoAdministrativo(String criador, CodigoCurso codigoCurso, int paginas) {
    super(criador, codigoCurso, paginas); // Classe filha de DOCUMENTO e herda seus atributos 
  }

  @Override
  public abstract boolean equals(Object obj); // Como é uma classe abstrata, ela não implementa método algum

  @Override
  public abstract int hashCode(); // idem

}
