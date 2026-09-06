package estudantes.entidades;

import professor.entidades.CodigoCurso;

public abstract class DocumentoAdministrativo extends Documento {

  public DocumentoAdministrativo(String criador, CodigoCurso codigoCurso, int paginas) {
    super(criador, codigoCurso, paginas);
  }

  @Override
  public abstract boolean equals(Object obj);

  @Override
  public abstract int hashCode();

}
