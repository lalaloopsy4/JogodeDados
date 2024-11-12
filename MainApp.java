import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainApp extends JFrame {
    private JogoDados jogo = new JogoDados();
    private DatabaseHandler dbHandler;
    private JTextArea areaJogadores;
    private JTextField campoNome, campoAposta;
    private JLabel labelResultado;

    public MainApp() {
        dbHandler = new DatabaseHandler();
        setTitle("Jogo de Dados Personalizado");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Painel para entrada de jogador
        JPanel panelEntrada = new JPanel();
        panelEntrada.setLayout(new BoxLayout(panelEntrada, BoxLayout.Y_AXIS));
        panelEntrada.setBorder(BorderFactory.createTitledBorder("Adicionar Jogador"));
        panelEntrada.setBackground(new Color(230, 240, 255));

        campoNome = new JTextField(15);
        campoAposta = new JTextField(5);

        JLabel labelNome = new JLabel("Nome:");
        labelNome.setFont(new Font("Arial", Font.BOLD, 14));
        JLabel labelAposta = new JLabel("Aposta:");
        labelAposta.setFont(new Font("Arial", Font.BOLD, 14));

        JButton botaoAdicionar = new JButton("Adicionar Jogador");
        botaoAdicionar.setBackground(new Color(51, 153, 255));
        botaoAdicionar.setForeground(Color.WHITE);
        botaoAdicionar.setFont(new Font("Arial", Font.BOLD, 12));

        // Adiciona elementos ao painel de entrada
        panelEntrada.add(labelNome);
        panelEntrada.add(campoNome);
        panelEntrada.add(labelAposta);
        panelEntrada.add(campoAposta);
        panelEntrada.add(botaoAdicionar);

        // Painel para jogadores e resultados
        JPanel panelJogadores = new JPanel();
        panelJogadores.setLayout(new BorderLayout());
        panelJogadores.setBorder(BorderFactory.createTitledBorder("Jogadores"));
        areaJogadores = new JTextArea(10, 30);
        areaJogadores.setEditable(false);
        areaJogadores.setFont(new Font("Courier", Font.PLAIN, 12));
        panelJogadores.add(new JScrollPane(areaJogadores), BorderLayout.CENTER);

        // Painel do resultado
        JPanel panelResultado = new JPanel();
        panelResultado.setLayout(new BoxLayout(panelResultado, BoxLayout.Y_AXIS));
        panelResultado.setBorder(BorderFactory.createTitledBorder("Resultado"));
        panelResultado.setBackground(new Color(255, 240, 230));

        labelResultado = new JLabel("Resultado:");
        labelResultado.setFont(new Font("Arial", Font.BOLD, 16));
        labelResultado.setForeground(new Color(128, 0, 0));

        JButton botaoJogar = new JButton("Lançar Dados");
        botaoJogar.setBackground(new Color(255, 102, 102));
        botaoJogar.setForeground(Color.WHITE);
        botaoJogar.setFont(new Font("Arial", Font.BOLD, 12));

        panelResultado.add(labelResultado);
        panelResultado.add(Box.createVerticalStrut(10));  // Espaçamento
        panelResultado.add(botaoJogar);

        // Adiciona os painéis principais à interface
        add(panelEntrada, BorderLayout.WEST);
        add(panelJogadores, BorderLayout.CENTER);
        add(panelResultado, BorderLayout.EAST);

        // Adicionar jogador
        botaoAdicionar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String nome = campoNome.getText();
                    int aposta = Integer.parseInt(campoAposta.getText());
                    jogo.adicionarJogador(nome, aposta);
                    areaJogadores.append(nome + " apostou em " + aposta + "\n");
                    campoNome.setText("");
                    campoAposta.setText("");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        });

        // Lançar dados
        botaoJogar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Timer timer = new Timer(1000, new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        int[] dados = jogo.lancarDados();
                        int somaDados = dados[0] + dados[1];
                        labelResultado.setText("Soma dos Dados: " + somaDados);
                        String vencedor = jogo.verificarVencedor(somaDados);
                        dbHandler.salvarResultado(vencedor);
                        JOptionPane.showMessageDialog(null, vencedor);
                        dbHandler.exibirRanking(jogo.getListaJogadores());
                    }
                });
                timer.setRepeats(false);
                timer.start();
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainApp app = new MainApp();
            app.setVisible(true);
        });
    }
}
