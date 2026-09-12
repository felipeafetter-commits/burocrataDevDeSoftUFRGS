package professor.entidades;

import estudantes.entidades.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

/**
 * Classe que representa um curso com seu monte de documentos.
 * <br><br>
 * <strong>Não mexa aqui!!!</strong>
 * 
 * @author Jean Cheiran
 */
public class Curso {
    
    private final String[] PRENOMES = { "Alexandre", "André", "Antonio", "Álvaro", "Carla", "Claudio", "Claudio", "Dante", "Edson", "Érika", "Fernanda", "João", "João", "Jacob", "Leandro", "Leila", "Lisandro", "Luciana", "Luciano", "Luigi", "Luís", "Manuel", "Marcelo", "Marcelo", "Marcus", "Renato", "Rosa", "Sergio", "Sérgio", "Valter", "Viviane" };
    private final String[] SOBRENOMES = { "Carissimi", "Reis", "Filho", "Moreira", "Freitas", "Geyer", "Jung", "Barone", "Junior", "Cota", "Kastensmidt", "Netto", "Comba", "Scharcanski", "Wives", "Ribeiro", "Granville", "Nedel", "Gaspary", "Carro", "Lamb", "Neto", "Pimenta", "Walter", "Ritt", "Ribas", "Vicari", "Bampi", "Cechin", "Roesler", "Moreira" };
    private final String[] COMPONENTES = { "ALGORITMOS E PROGRAMAÇÃO", "CÁLCULO E GEOMETRIA ANALÍTICA I","INTRODUÇÃO À CIÊNCIA DA COMPUTAÇÃO","LÓGICA PARA COMPUTAÇÃO","PENSAMENTO COMPUTACIONAL N","ARQUITETURA DE COMPUTADORES","CÁLCULO E GEOMETRIA ANALÍTICA II","ESTRUTURAS DE DADOS","MATEMÁTICA DISCRETA", "PROBABILIDADE E ESTATÍSTICA","TESTE E VERIFICAÇÃO DE SOFTWARE","BANCOS DE DADOS","DESENVOLVIMENTO DE SOFTWARE","PROJETO DE CIRCUITOS DIGITAIS", "PROJETO E ANÁLISE DE ALGORITMOS I","TEORIA DA COMPUTAÇÃO I","ÁLGEBRA LINEAR I","ENGENHARIA DE SOFTWARE","INTERAÇÃO HUMANO-COMPUTADOR E EXPERIÊNCIA DO USUÁRIO","ORGANIZAÇÃO DE COMPUTADORES","PROJETO E ANÁLISE DE ALGORITMOS II","TEORIA DA COMPUTAÇÃO II","FÍSICA I","INTRODUÇÃO À ENGENHARIA DE COMPUTAÇÃO","FÍSICA GERAL - ELETROMAGNETISMO","EQUAÇÕES DIFERENCIAIS II","FÍSICA III","CIRCUITOS ELÉTRICOS I","MATEMÁTICA APLICADA II","AVALIAÇÃO DE DESEMPENHO","CIRCUITOS ELÉTRICOS II" };
    
    private static int documentosCriados = 0; 
    
    private Random gerador;
    
    private CodigoCurso codigo;
    private LinkedList<Documento> monte;
    
    protected Curso(CodigoCurso codigo){
        this.codigo = codigo;
        this.gerador = new Random(codigo.hashCode() + Universidade.SEMENTE);
        this.monte = new LinkedList<>();
    }
    
    protected void criarDocumentos(int quantidade){
        for(int i = 0; i < quantidade; i++){
            Documento doc = null;
            
            int tipoDeDocumento = gerador.nextInt(0, 11);
            
            switch(tipoDeDocumento){
                case 0:
                    doc = new Norma(gerarNome(), this.codigo, gerador.nextInt(1, 150), gerador.nextInt(1, 1000), gerador.nextBoolean(), "Lorem ipsum");
                    break;
                case 1:
                    doc = new Portaria(gerarNome(), this.codigo, gerador.nextInt(1, 250), gerador.nextInt(1, 1000), gerador.nextBoolean(), "Lorem ipsum", gerador.nextInt(2000, 2025));
                    break;
                case 2:
                    {
                        int quantidadeDePessoas = gerador.nextInt(1, 5);
                        String responsaveis[] = new String[quantidadeDePessoas];
                        for(int j = 0; j < quantidadeDePessoas; j++){
                            responsaveis[j] = gerarNome();
                        }
                        doc = new Edital(gerarNome(), this.codigo, gerador.nextInt(1, 250), gerador.nextInt(1, 1000), gerador.nextBoolean(), "Lorem ipsum", responsaveis);
                    }
                    break;
                case 3:
                    {
                        int quantidadeDePessoas = gerador.nextInt(1, 5);
                        String destinatarios[] = new String[quantidadeDePessoas];
                        for(int j = 0; j < quantidadeDePessoas; j++){
                            destinatarios[j] = gerarNome();
                        }
                        doc = new Circular(gerarNome(), this.codigo, gerador.nextInt(1, 5), "Lorem ipsum", destinatarios);
                    }
                    break;
                case 4:
                    doc = new Oficio(gerarNome(), this.codigo, gerador.nextInt(1, 5), "Lorem ipsum", gerarNome());
                    break;
                case 5:
                    {
                        int quantidadeDePessoas = gerador.nextInt(1, 50);
                        String presentes[] = new String[quantidadeDePessoas];
                        for(int j = 0; j < quantidadeDePessoas; j++){
                            presentes[j] = gerarNome();
                        }
                        doc = new Ata(gerarNome(), this.codigo, gerador.nextInt(1, 150), gerador.nextInt(1, 100), "Lorem ipsum", presentes);
                    }
                    break;
                case 6:
                    doc = new Certificado(gerarNome(), this.codigo, gerador.nextInt(1, 2), gerador.nextLong(Long.MAX_VALUE), gerarNome(), gerador.nextLong(Long.MAX_VALUE), "Lorem Ipsum");
                    break;
                case 7:
                    doc = new Diploma(gerarNome(), this.codigo, gerador.nextInt(1, 2), gerador.nextLong(Long.MAX_VALUE), gerarNome(), gerador.nextLong(Long.MAX_VALUE), "Lorem Ipsum", "Bacharelado "+this.codigo);
                    break;
                case 8:
                    {
                        int quantidadeDeComponentes = gerador.nextInt(1, 10);
                        String componentes[] = new String[quantidadeDeComponentes];
                        for(int j = 0; j < quantidadeDeComponentes; j++){
                            componentes[j] = escolherComponente();
                        }
                        doc = new Historico(gerarNome(), this.codigo, gerador.nextInt(1, 2), gerador.nextLong(Long.MAX_VALUE), gerarNome(), gerador.nextLong(Long.MAX_VALUE), gerador.nextDouble(100), componentes);
                    }
                    break;
                case 9:
                    doc = new Atestado(gerarNome(), this.codigo, gerador.nextInt(1, 2), gerador.nextLong(Long.MAX_VALUE), gerarNome(), gerador.nextLong(Long.MAX_VALUE), "Lorem Ipsum", "Categoria " + gerador.nextInt(1, 5));
                    break;
                case 10:
                    {
                        int quantidadeDeAtividades = gerador.nextInt(1, 30);
                        String atividades[] = new String[quantidadeDeAtividades];
                        for(int j = 0; j < quantidadeDeAtividades; j++){
                            atividades[j] = "Atividade " + (j+1);
                        }
                        doc = new Plano(gerarNome(), this.codigo, gerador.nextInt(1, 2), gerador.nextLong(Long.MAX_VALUE), gerarNome(), atividades);
                    }
                    break;
                default: 
                    throw new RuntimeException("Tipo de documento inválido");
            }
            
            documentosCriados++;
            monte.push(doc);
        }
    }
    
    protected int contarDocumentosNoMonte(){
        return monte.size();
    }
    
    protected boolean removerDocumento(Documento documento){
        return monte.remove(documento);
    }
    
    protected void devolverDocumento(Documento documento){
        monte.push(documento);
    }
    
    protected Documento[] pegarCopiaDoMonte(){
        return Arrays.copyOf(monte.toArray(), monte.size(), Documento[].class);
    }
    
    protected static int getDocumentosCriados(){
        return documentosCriados;
    }
    
    private String gerarNome(){
        return PRENOMES[gerador.nextInt(PRENOMES.length)] + " " + SOBRENOMES[gerador.nextInt(SOBRENOMES.length)];
    }
    
    private String escolherComponente(){
        return COMPONENTES[gerador.nextInt(COMPONENTES.length)];
    }
}
