package estudantes.entidades;

import java.util.Arrays;
import java.util.Objects;
import professor.entidades.CodigoCurso;

public class Circular extends Deliberacao {
    private String[] destinatarios;

    public Circular(String criador, CodigoCurso codigoCurso, int paginas, String texto, String destinatarios[]) {
        super(criador, codigoCurso, paginas, texto); // Classe filha de Deliberação, herdando os seus atributos
        this.destinatarios = destinatarios;
    }

    public String[] getDestinatarios() { //Metodo que retorna estado de destinatarios
        return destinatarios;
    }

    @Override // implementação do metodo de comparação de objetos, equals
    public boolean equals(Object obj) { 
        if (this == obj) return true; // se forem exatamente o mesmo objeto, retorna true e encerra o método
        if (obj == null || getClass() != obj.getClass()) return false; //se for null ou de outra classe, retorna false e encerra o método
        Circular outra = (Circular) obj; // Confirmando que não é o mesmo, null ou de outra classe, faz o casting para circular
        return getPaginas() == outra.getPaginas() // compara atributo por atributo para determinar se se tratam de uma mesma circular
                && Objects.equals(getCriador(), outra.getCriador())
                && getCodigoCurso() == outra.getCodigoCurso()
                && Objects.equals(getTexto(), outra.getTexto())
                && Arrays.equals(this.destinatarios, outra.destinatarios);
    }

    @Override
    public int hashCode() { // método para criar um código hash para o objeto, mesmo objetos verificados pelo equals recebem o mesmo código
        int result = Objects.hash(getCriador(), getCodigoCurso(), getPaginas(), getTexto());
        result = 31 * result + Arrays.hashCode(this.destinatarios);
        return result;
    }
}