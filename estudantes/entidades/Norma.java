package estudantes.entidades;

import java.util.Arrays;
import java.util.Objects;
import estudantes.entidades.DocumentoAdministrativo;
import professor.entidades.CodigoCurso;

public class Norma extends DocumentoAdministrativo {

    private int numero;
    private boolean valido;
    private String texto;

    public Norma(String criador, CodigoCurso codigoCurso, int paginas, int numero, boolean valido, String texto) {
        super(criador, codigoCurso, paginas); // Classe filha de DOCUMENTO ADMINISTRATIVO e herda seus atributos
        this.numero = numero;
        this.valido = valido;
        this.texto = texto;
    }

    public int getNumero() { // método para retornar o estado do atributo privado numero
        return numero;
    }

    public boolean getValido() { // método para retornar o estado do atributo privado valido
        return valido;
    }

    public boolean isValido() { //// método para retornar o estado do atributo privado valido
        return valido;
    }

    public String getTexto() { // método para retornar o estado do atributo privado Texto
        return texto;
    }

    @Override
    public boolean equals(Object obj) { // método que compara dois objetos e determina se são duas Normas
        if (this == obj) {
            return true; // se forem exatamente o mesmo objeto, retorna true
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false; // se for vazio ou uma de uma classe diferente, retorna false
        }
        Norma outra = (Norma) obj; // faz o casting do objeto tipo obj para Norma, já que os testes anteriores comprovam que tratasse de uma Norma
        return this.numero == outra.numero // testa condição por condição para saber se tratam-se da mesma Norma, mas em objetos diferentes
                && this.valido == outra.valido
                && this.texto.equals(outra.texto)
                && this.getCriador().equals(outra.getCriador())
                && this.getCodigoCurso().equals(outra.getCodigoCurso())
                && this.getPaginas() == outra.getPaginas();
    }

    @Override
    public int hashCode() {
        return Objects.hash(numero, valido, texto, getCriador(), getCodigoCurso(), getPaginas()); // Gera o hashCode da Norma a partir de seus campos, dois objetos iguais pelo equals gerarão o mesmo hash 
    }

}
