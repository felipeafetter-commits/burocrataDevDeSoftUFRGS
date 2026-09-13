package estudantes.entidades;

import professor.entidades.CodigoCurso;
import java.util.Objects;
import java.util.Arrays;

public class Edital extends Norma {

    private String responsaveis[];

    public Edital(String criador, CodigoCurso codigoCurso, int paginas, int numero, boolean valido, String texto,
            String responsaveis[]) {
        super(criador, codigoCurso, paginas, numero, valido, texto); // Classe filha de Norma e herda todos os seus atributos
        this.responsaveis = responsaveis;

    }

    public String[] getResponsaveis() { // Metodo que retorna estados do atributo privado responsaveis
        return responsaveis;

    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) { // Caso válido pelo método herdado de Norma, faz o casting para Edital
            return false;
        }
        Edital outro = (Edital) obj;
        return Arrays.equals(this.responsaveis, outro.responsaveis); // Confirma que são o mesmo edital através dessa comparação final
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), Arrays.hashCode(this.responsaveis)); // Gera Hashcode, dois objetos referentes ao mesmo edital terão o mesmo hascode
    }

}
