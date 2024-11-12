import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class JogoDados {
    private List<Jogador> listaJogadores = new ArrayList<>();
    private Random random = new Random();

    public void adicionarJogador(String nome, int aposta) throws Exception {
        if (listaJogadores.size() >= 11) {
            throw new Exception("Número máximo de jogadores é 11");
        }
        if (aposta < 2 || aposta > 12) {
            throw new Exception("Aposta deve estar entre 2 e 12");
        }

        for (Jogador jogador : listaJogadores) {
            if (jogador.getValorApostado() == aposta) {
                throw new Exception("Valor de aposta já escolhido");
            }
        }

        listaJogadores.add(new Jogador(nome, aposta));
    }

    public int[] lancarDados() {
        return new int[] { random.nextInt(6) + 1, random.nextInt(6) + 1 };
    }

    public String verificarVencedor(int somaDados) {
        for (Jogador jogador : listaJogadores) {
            if (jogador.getValorApostado() == somaDados) {
                return "Ganhador: " + jogador.getNomeJogador();
            }
        }
        return "Nenhum jogador acertou. O computador venceu!";
    }

    public List<Jogador> getListaJogadores() {
        return listaJogadores;
    }
}
