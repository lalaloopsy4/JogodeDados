public class Jogador {
    private String nomeJogador;
    private int valorApostado;

    public Jogador(String nomeJogador, int valorApostado) {
        this.nomeJogador = nomeJogador;
        this.valorApostado = valorApostado;
    }

    public String getNomeJogador() {
        return nomeJogador;
    }

    public int getValorApostado() {
        return valorApostado;
    }
}
