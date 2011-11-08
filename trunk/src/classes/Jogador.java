package classes;

public class Jogador {

    private String nome;
    private Tabuleiro tabuleiro;
    
    private TipoJogador tipo;
    
    private boolean preparado;
    private boolean comecaJogando;
    

    public Jogador(TipoJogador tipo) {
        this.nome = "Zé";
        
        Navio[] navios = new Navio[] {
            new PortaAvioes(), 
            new Destroyer(),
            new Destroyer(),
            new Couracado(),
            new Couracado(),
            new Cruzador(),
            new Cruzador(),
            new Cruzador()
    };
        
        this.tabuleiro = new Tabuleiro(13, navios);
        this.tipo = tipo;
    }

        
    public Jogador(String nome, Navio[] navios, Tabuleiro tabuleiro, TipoJogador tipo) {
        this.nome = nome;
        this.tabuleiro = tabuleiro;
        this.tipo = tipo;
        this.preparado = false;
        this.comecaJogando = false;
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


    public Tabuleiro getTabuleiro() {
        return tabuleiro;
    }

    public void setTabuleiro(Tabuleiro tabuleiro) {
        this.tabuleiro = tabuleiro;
    }

    public boolean isPreparado() {
        return preparado;
    }

    public void setPreparado(boolean preparado) {
        this.preparado = preparado;
    }

    public boolean comecaJogando() {
        return comecaJogando;
    }

    public void setComecaJogando(boolean comecaJogando) {
        this.comecaJogando = comecaJogando;
    }

    public TipoJogador getTipo() {
        return tipo;
    }

    public void setTipo(TipoJogador tipo) {
        this.tipo = tipo;
    }
}
