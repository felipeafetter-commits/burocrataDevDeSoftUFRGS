package professor.gui;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import javax.swing.Timer;

import professor.entidades.CodigoCurso;
import professor.entidades.Universidade;

/**
 * Classe principal responsável pela interface gráfica do simulador.
 * <br><br>
 * Controla a execução da simulação, atualização de dados e renderização dos
 * estados do burocrata e dos cursos.
 * <br><br>
 * <strong>Não mexa aqui!!!</strong>
 * 
 * @author Matheus Ciocca
 * @author André Medeiros
 * @version 2.0
 */

public class SimuladorGUI extends JFrame {

    private static SimuladorGUI instancia;
    private static Universidade universidade;
    private static TimerTask tarefaSimular;
    private static TimerTask tarefaAtualizarGUI;
    private static TimerTask tarefaControle;

    private int execucoes = 0;
    private final int TEMPO_DE_EXECUCAO = 120; // em segundos
    private boolean burocrataPunido = false;
    private String statusBurocrata = "Calmo";

    // Labels principais
    private JLabel labelTempo;
    private JLabel labelEstresse;
    private JLabel labelProcessosDespachados;
    private JLabel labelDocumentosDespachados;
    private JLabel labelDocumentosPerdidos;
    private JLabel labelDocumentosCriados;
    private JLabel labelBurocrataInfo;
    private JLabel burocrataIcon;

    private JProgressBar barraEstresse;

    // Painel de cursos
    private final Map<CodigoCurso, JLabel> cursoLabels = new EnumMap<>(CodigoCurso.class);
    private final Map<CodigoCurso, Integer> valoresAnteriores = new EnumMap<>(CodigoCurso.class);

    // Cores e fontes
    private final Color COR_FUNDO = new Color(245, 247, 252);
    private final Color COR_TOPO = new Color(66, 133, 244);
    private final Color COR_PAINEL_BUROCRATA = new Color(230, 236, 245);
    private final Font FONTE_TITULO = new Font("Segoe UI", Font.BOLD, 15);

    // ===================== CONSTRUTOR =====================

    /**
     * Construtor privado que inicializa a universidade e configura os componentes
     * da interface.
     */
    private SimuladorGUI() {
        universidade = new Universidade();
        initComponents();
    }

    /**
     * Retorna a instância única (Singleton) da classe SimuladorGUI.
     *
     * @return instância única da interface gráfica
     */
    public static SimuladorGUI getInstancia() {
        if (instancia == null) {
            instancia = new SimuladorGUI();
        }
        return instancia;
    }

    // ===================== MÉTODOS DE INTERFACE =====================

    /**
     * Inicializa todos os componentes visuais da janela principal, incluindo
     * painéis, ícones, rótulos e barra de progresso.
     */
    private void initComponents() {
        setTitle("Simulador do Burocrata");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1250, 720);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(COR_FUNDO);

        // ===================== PAINEL SUPERIOR =====================
        JPanel topPanel = new JPanel(new GridLayout(2, 3, 10, 10));
        topPanel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        topPanel.setBackground(COR_TOPO);

        labelTempo = criarLabelResumo("src/professor/gui/imagens/relogio.png", "Tempo: 0s");
        labelEstresse = criarLabelResumo("src/professor/gui/imagens/estresse.png", "Estresse: 0");
        labelProcessosDespachados = criarLabelResumo("src/professor/gui/imagens/processos.png", "Processos: 0");
        labelDocumentosDespachados = criarLabelResumo("src/professor/gui/imagens/documentos.png", "Despachados: 0");
        labelDocumentosPerdidos = criarLabelResumo("src/professor/gui/imagens/perdidos.png", "Perdidos: 0");
        labelDocumentosCriados = criarLabelResumo("src/professor/gui/imagens/criados.png", "Criados: 0");

        topPanel.add(labelTempo);
        topPanel.add(labelEstresse);
        topPanel.add(labelProcessosDespachados);
        topPanel.add(labelDocumentosDespachados);
        topPanel.add(labelDocumentosPerdidos);
        topPanel.add(labelDocumentosCriados);

        add(topPanel, BorderLayout.NORTH);

        // ===================== PAINEL CENTRAL =====================
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(COR_FUNDO);

        JPanel cursosPanel = new JPanel(new GridLayout(3, 4, 20, 20));
        cursosPanel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        cursosPanel.setBackground(COR_FUNDO);

        adicionarCurso(cursosPanel, CodigoCurso.GRADUACAO_BIOTECNOLOGIA, "BIOTEC");
        adicionarCurso(cursosPanel, CodigoCurso.GRADUACAO_CIENCIA_DA_COMPUTACAO, "CIC");
        adicionarCurso(cursosPanel, CodigoCurso.GRADUACAO_CIENCIA_DE_DADOS, "CID");
        adicionarCurso(cursosPanel, CodigoCurso.GRADUACAO_CIENCIA_E_TECNOLOGIA, "CITEC");
        adicionarCurso(cursosPanel, CodigoCurso.GRADUACAO_ENGENHARIA_DE_AUTOMACAO, "EA");
        adicionarCurso(cursosPanel, CodigoCurso.GRADUACAO_ENGENHARIA_DE_COMPUTACAO, "ECP");
        adicionarCurso(cursosPanel, CodigoCurso.GRADUACAO_ENGENHARIA_ELETRICA, "EE");
        adicionarCurso(cursosPanel, CodigoCurso.POS_GRADUACAO_COMPUTACAO, "PPGC");
        adicionarCurso(cursosPanel, CodigoCurso.POS_GRADUACAO_ENGENHARIA_ELETRICA, "PPGEE");
        adicionarCurso(cursosPanel, CodigoCurso.POS_GRADUACAO_MICROELETRONICA, "PGMICRO");

        centerPanel.add(cursosPanel, BorderLayout.CENTER);

        // ===================== PAINEL DO BUROCRATA =====================
        JPanel burocrataPanel = new JPanel();
        burocrataPanel.setLayout(new BoxLayout(burocrataPanel, BoxLayout.Y_AXIS));
        burocrataPanel.setBorder(BorderFactory.createEmptyBorder(25, 20, 25, 20));
        burocrataPanel.setBackground(COR_PAINEL_BUROCRATA);
        burocrataPanel.setPreferredSize(new Dimension(220, 0));

        burocrataIcon = new JLabel();
        burocrataIcon.setAlignmentX(Component.CENTER_ALIGNMENT);
        burocrataIcon
                .setIcon(new ImageIcon(
                        carregarImagemRedimensionada("src/professor/gui/imagens/estados-burocrata/calmo.png", 180,
                                180)));

        labelBurocrataInfo = new JLabel(
                "<html><center>Burocrata<br>Status: " + statusBurocrata
                        + "<br>Estresse: 0<br>Processos: 0</center></html>",
                SwingConstants.CENTER);
        labelBurocrataInfo.setFont(FONTE_TITULO);
        labelBurocrataInfo.setAlignmentX(Component.CENTER_ALIGNMENT);

        // barra de estresse
        barraEstresse = new JProgressBar(0, 100);
        barraEstresse.setStringPainted(true);
        barraEstresse.setPreferredSize(new Dimension(180, 25));
        barraEstresse.setForeground(new Color(255, 80, 80));
        barraEstresse.setBackground(new Color(240, 240, 240));
        barraEstresse.setAlignmentX(Component.CENTER_ALIGNMENT);
        barraEstresse.setValue(0);
        barraEstresse.setString("Estresse do Burocrata");

        burocrataPanel.add(burocrataIcon);
        burocrataPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        burocrataPanel.add(labelBurocrataInfo);
        burocrataPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        burocrataPanel.add(barraEstresse);

        centerPanel.add(burocrataPanel, BorderLayout.EAST);
        add(centerPanel, BorderLayout.CENTER);

        comecarSimulacao();
    }

    /**
     * Obtém e redimensiona uma imagem conforme largura e altura especificadas.
     *
     * @param caminho caminho da imagem
     * @param largura largura desejada
     * @param altura  altura desejada
     * @return imagem redimensionada
     */
    private Image carregarImagemRedimensionada(String caminho, int largura, int altura) {
        ImageIcon icon = new ImageIcon(caminho);
        return icon.getImage().getScaledInstance(largura, altura, Image.SCALE_SMOOTH);
    }

    /**
     * Cria um JLabel com ícone e texto formatados para o painel superior.
     *
     * @param caminhoIcone caminho do ícone
     * @param texto        texto exibido no rótulo
     * @return JLabel configurado
     */
    private JLabel criarLabelResumo(String caminhoIcone, String texto) {
        JLabel label = new JLabel(texto, SwingConstants.CENTER);
        label.setIcon(new ImageIcon(carregarImagemRedimensionada(caminhoIcone, 25, 25)));
        label.setFont(new Font("Segoe UI", Font.BOLD, 15));
        label.setForeground(Color.WHITE);
        return label;
    }

    /**
     * Adiciona um curso e seu contador de documentos ao painel central.
     *
     * @param panel  painel onde o curso será adicionado
     * @param codigo código do curso
     * @param nome   nome curto exibido
     */
    private void adicionarCurso(JPanel panel, CodigoCurso codigo, String nome) {
        JPanel box = new JPanel(new BorderLayout());
        box.setBackground(Color.WHITE);
        box.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
                BorderFactory.createEmptyBorder(12, 12, 12, 12)));
        box.setPreferredSize(new Dimension(200, 110));

        JLabel titulo = new JLabel(nome, SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titulo.setForeground(new Color(50, 50, 60));

        JLabel valor = new JLabel("0 documentos", SwingConstants.CENTER);
        valor.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        valor.setForeground(new Color(100, 100, 110));

        box.add(titulo, BorderLayout.NORTH);
        box.add(valor, BorderLayout.CENTER);

        panel.add(box);
        cursoLabels.put(codigo, valor);
        valoresAnteriores.put(codigo, 0);
    }

    /**
     * Aplica uma pequena animação visual quando o valor de um curso é atualizado.
     *
     * @param label rótulo a ser animado
     */
    private void animarMudanca(JLabel label) {
        Color original = label.getForeground();
        label.setForeground(new Color(25, 150, 75)); // verde temporário
        new java.util.Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(() -> label.setForeground(original));
            }
        }, 400);
    }

    /**
     * Atualiza a imagem e o status do burocrata de acordo com o nível de estresse.
     * Caso o limite seja atingido, uma punição é acionada.
     *
     * @param estresse nível atual de estresse
     */
    private void atualizarImagemBurocrata(int estresse) {
        String caminhoImagem;
        /*
         * estados:
         * calmo -> estresse = 0
         * preocupado -> 0 < estresse <= 25
         * levemente estressado -> 25 < estresse <= 50
         * estressado -> 50 < estresse <= 75
         * muito estressado -> 75 < estresse <= 100
         * absurdamente estressado -> 100 < estresse <= 1.000
         * vertiginosamente estressado -> estresse > 10.000
         */

        if (estresse < 1) {
            caminhoImagem = "src/professor/gui/imagens/estados-burocrata/calmo.png";
            statusBurocrata = "Calmo";
        } else if (estresse < 25) { // valor padrão = 25
            caminhoImagem = "src/professor/gui/imagens/estados-burocrata/preocupado.png";
            statusBurocrata = "Preocupado";
        } else if (estresse < 50) { // valor padrão = 50
            caminhoImagem = "src/professor/gui/imagens/estados-burocrata/levemente-estressado.png";
            statusBurocrata = "Levemente Estressado";
        } else if (estresse < 75) { // valor padrão = 75
            caminhoImagem = "src/professor/gui/imagens/estados-burocrata/estressado.png";
            statusBurocrata = "Estressado";
        } else if (estresse < 100) { // valor padrão = 100
            caminhoImagem = "src/professor/gui/imagens/estados-burocrata/muito-estressado.png";
            statusBurocrata = "Muito Estressado";
        } else if (estresse < 1000) { // valor padrão = 1.000
            caminhoImagem = "src/professor/gui/imagens/estados-burocrata/absurdamente-estressado.png";
            statusBurocrata = "Absurdamente Estressado";
        } else {
            caminhoImagem = "src/professor/gui/imagens/estados-burocrata/vertiginosamente-estressado.png";
            statusBurocrata = "Vertiginosamente Estressado";

            if (!burocrataPunido) {
                burocrataPunido = true;

                JOptionPane optionPane = new JOptionPane(
                        "O burocrata atingiu o nível máximo de estresse!\nPreparando punição...",
                        JOptionPane.WARNING_MESSAGE,
                        JOptionPane.DEFAULT_OPTION);

                JDialog dialog = optionPane.createDialog(this, "🔥 Colapso Emocional Detectado!");
                dialog.setModal(false);
                dialog.setVisible(true);
            }

        }

        Image novaImagem = carregarImagemRedimensionada(caminhoImagem, 180, 180);
        burocrataIcon.setIcon(new ImageIcon(novaImagem));
    }

    // ===================== CONTROLE DA SIMULAÇÃO =====================

    /**
     * Inicia a simulação, programando tarefas periódicas para controle, simulação
     * e atualização da interface.
     */
    private void comecarSimulacao() {
        tarefaSimular = new TimerTask() {
            @Override
            public void run() {
                simular();
            }
        };

        tarefaAtualizarGUI = new TimerTask() {
            @Override
            public void run() {
                atualizarInterface();
            }
        };

        tarefaControle = new TimerTask() {
            @Override
            public void run() {
                controlarTempo();
            }
        };

        new java.util.Timer().schedule(tarefaControle, 3000, 1000);
        new java.util.Timer().schedule(tarefaSimular, 3000, 50);
        new java.util.Timer().schedule(tarefaAtualizarGUI, 4000, 1000);
    }

    /**
     * Executa um ciclo de simulação na universidade.
     */
    private void simular() {
        universidade.simular();
    }

    /**
     * Atualiza todos os elementos visuais da interface conforme os valores atuais
     * da simulação.
     */
    private void atualizarInterface() {
        SwingUtilities.invokeLater(() -> {
            int estresse = universidade.verificarEstresseDoBurocrata();
            labelTempo.setText("Tempo: " + execucoes + "s");
            labelEstresse.setText("Estresse: " + estresse);
            labelProcessosDespachados.setText("Processos: " + universidade.contarProcessosDespachados());
            labelDocumentosDespachados.setText("Despachados: " + universidade.contarDocumentosDespachados());
            labelDocumentosPerdidos.setText("Perdidos: " + universidade.contarDocumentosPerdidos());
            labelDocumentosCriados.setText("Criados: " + universidade.contarDocumentosCriados());

            // atualiza imagem do burocrata
            atualizarImagemBurocrata(estresse);

            // Atualiza barra de estresse do burocrata
            barraEstresse.setValue(Math.min(estresse, 100));

            // Troca a cor da barra conforme o nível
            if (estresse < 40)
                barraEstresse.setForeground(new Color(80, 180, 80));
            else if (estresse < 70)
                barraEstresse.setForeground(new Color(255, 200, 80));
            else
                barraEstresse.setForeground(new Color(255, 80, 80));

            labelBurocrataInfo.setText("<html><center>Burocrata<br>Status: " + statusBurocrata + "<br>Estresse: " +
                    estresse +
                    "<br>Processos: " + universidade.contarProcessosDespachados() + "</center></html>");

            for (Map.Entry<CodigoCurso, JLabel> entry : cursoLabels.entrySet()) {
                int qtd = universidade.contarDocumentosNoMonteDoCurso(entry.getKey());
                JLabel label = entry.getValue();

                if (qtd != valoresAnteriores.get(entry.getKey())) {
                    animarMudanca(label);
                    valoresAnteriores.put(entry.getKey(), qtd);
                }
                label.setText(qtd + " documentos");
            }
        });
    }

    /**
     * Controla o tempo de execução da simulação e, ao final, calcula a eficiência
     * geral do algoritmo.
     */
    private void controlarTempo() {
        if (execucoes == TEMPO_DE_EXECUCAO) {
            pararSimulacao();

            // cálculo da eficiência
            double eficiencia = 1.0 *
                    (universidade.contarDocumentosDespachados() / (double) universidade.contarProcessosDespachados()) *
                    (universidade.contarDocumentosDespachados() / (double) universidade.contarDocumentosCriados()) /
                    Math.sqrt(universidade.verificarEstresseDoBurocrata() + 1);

            // Exibe janela com o resultado
            SwingUtilities.invokeLater(() -> {
                mostrarJanelaEficiencia(eficiencia);
            });

        } else {
            execucoes++;
        }
    }

    /**
     * Exibe uma janela modal com o resultado final da simulação, mostrando a
     * eficiência do algoritmo de despacho.
     *
     * @param eficiencia valor calculado de eficiência
     */
    private void mostrarJanelaEficiencia(double eficiencia) {
        JDialog dialog = new JDialog(this, "Resultados da Simulação", true);
        dialog.setSize(420, 300);
        dialog.setLocationRelativeTo(this);
        dialog.setUndecorated(false);
        dialog.getContentPane().setBackground(new Color(245, 247, 252));
        dialog.setLayout(new BorderLayout(10, 10));

        // Ícone ou emoji no topo
        JLabel icone = new JLabel(
                new ImageIcon(carregarImagemRedimensionada("src/professor/gui/imagens/processos.png", 25, 25)),
                SwingConstants.CENTER);

        icone.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 40));
        icone.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));

        // Título
        JLabel titulo = new JLabel("Eficiência do Algoritmo", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI Semibold", Font.BOLD, 20));
        titulo.setForeground(new Color(40, 60, 90));
        titulo.setBorder(BorderFactory.createEmptyBorder(5, 0, 10, 0));

        // Painel central
        JPanel painel = new JPanel();
        painel.setBackground(Color.WHITE);
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 225, 235), 1, true),
                BorderFactory.createEmptyBorder(25, 20, 25, 20)));

        JLabel eficienciaLabel = new JLabel("Eficiência: 0.0000", SwingConstants.CENTER);
        eficienciaLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        eficienciaLabel.setForeground(new Color(50, 70, 100));
        eficienciaLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        painel.add(eficienciaLabel);

        // Botão "Fechar"
        JButton fechar = new JButton("Fechar");
        fechar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        fechar.setBackground(new Color(66, 133, 244));
        fechar.setForeground(Color.WHITE);
        fechar.setFocusPainted(false);
        fechar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        fechar.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        fechar.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Efeito hover
        fechar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                fechar.setBackground(new Color(52, 103, 192));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                fechar.setBackground(new Color(66, 133, 244));
            }
        });

        fechar.addActionListener(e -> dialog.dispose());

        // Animação de contagem da eficiência
        Timer timer = new Timer(15, null);
        final double[] atual = { 0.0 };
        timer.addActionListener(e -> {
            if (atual[0] < eficiencia) {
                atual[0] += (eficiencia / 60); // velocidade da contagem
                eficienciaLabel.setText(String.format("Eficiência: %.4f", Math.min(atual[0], eficiencia)));
            } else {
                timer.stop();
            }
        });
        timer.start();

        // Montagem da janela
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(dialog.getContentPane().getBackground());
        topPanel.add(icone, BorderLayout.NORTH);
        topPanel.add(titulo, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(dialog.getContentPane().getBackground());
        bottomPanel.add(fechar);

        dialog.add(topPanel, BorderLayout.NORTH);
        dialog.add(painel, BorderLayout.CENTER);
        dialog.add(bottomPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    /**
     * Interrompe a execução da simulação, cancelando todas as tarefas ativas.
     */
    private void pararSimulacao() {
        tarefaControle.cancel();
        tarefaSimular.cancel();
        tarefaAtualizarGUI.cancel();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SimuladorGUI simulador = SimuladorGUI.getInstancia();
            simulador.setVisible(true);
        });
    }
}
