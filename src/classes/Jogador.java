package classes;

public class Jogador {

    private String nome;
    private Navio[] navios;
    private Tabuleiro tabuleiro;

    public Jogador() {
        this.nome = "Zé";
        this.navios = new Navio[] {
            new PortaAvioes(), 
            new Destroyer(),
            new Destroyer(),
            new Couracado(),
            new Couracado(),
            new Cruzador(),
            new Cruzador(),
            new Cruzador()
    };
        
        this.tabuleiro = new Tabuleiro(12);
    }

        
    public Jogador(String nome, Navio[] navios, Tabuleiro tabuleiro) {
        this.nome = nome;
        this.navios = navios;
        this.tabuleiro = tabuleiro;
    }
    

    public Navio[] getNavios() {
        return navios;
    }

    public void setNavios(Navio[] navios) {
        this.navios = navios;
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
}
