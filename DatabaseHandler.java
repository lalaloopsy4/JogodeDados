import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class DatabaseHandler {

    private static final String ARQUIVO_CSV = "resultados.csv";

    public void salvarResultado(String resultado) {
        try (FileWriter writer = new FileWriter(ARQUIVO_CSV, true)) {
            writer.append(resultado).append("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void exibirRanking(List<Jogador> jogadores) {
        System.out.println("Ranking dos jogadores:");
        for (Jogador jogador : jogadores) {
            System.out.println(jogador.getNomeJogador() + " apostou em " + jogador.getValorApostado());
        }
    }
}
