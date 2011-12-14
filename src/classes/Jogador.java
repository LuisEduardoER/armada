package classes;

import java.util.ArrayList;

public class Jogador {

    private String nome;
    private Tabuleiro tabuleiro;
    
    private TipoJogador tipo;
    
    private ArrayList<Integer[]> tiros;
    
    private boolean preparado;
    private boolean comecaJogando;
    
    
    public Jogador(){
        this.nome = "Luciano";
        
        Navio[] navios = new Navio[] {
            new PortaAvioes(new int[]{11,1}, Orientacao.VERTICAL), 
            new Destroyer(new int[]{3,2}, Orientacao.VERTICAL),
            new Destroyer(new int[]{2,0}, Orientacao.HORIZONTAL),
            new Couracado(new int[]{0,4}, Orientacao.HORIZONTAL),
            new Couracado(new int[]{4,10}, Orientacao.VERTICAL),
            new Cruzador(new int[]{6,2}, Orientacao.HORIZONTAL),
            new Cruzador(new int[]{1,7}, Orientacao.HORIZONTAL),
            new Cruzador(new int[]{10,8}, Orientacao.VERTICAL)
        };
        
        this.tabuleiro = new Tabuleiro(13, navios);
        this.tipo = TipoJogador.ADVERSARIO;
        this.tiros = new ArrayList();
    }
    

    public Jogador(TipoJogador tipo) {
        this.nome = "Zezé di Camargo";
        
        /*Navio[] navios = new Navio[] {
            new PortaAvioes(new int[]{1,1}), 
            new Destroyer(new int[]{2,2}),
            new Destroyer(new int[]{3,3}),
            new Couracado(new int[]{4,4}),
            new Couracado(new int[]{5,5}),
            new Cruzador(new int[]{6,6}),
            new Cruzador(new int[]{7,7}),
            new Cruzador(new int[]{8,8})
        };
        
        this.tabuleiro = new Tabuleiro(13, navios);
        this.tipo = TipoJogador.LOCAL;
        this.tiros = new ArrayList();*/
        
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
        this.tiros = new ArrayList();
    }

        
    public Jogador(String nome, Navio[] navios, Tabuleiro tabuleiro, TipoJogador tipo) {
        this.nome = nome;
        this.tabuleiro = tabuleiro;
        this.tipo = tipo;
        this.tiros = new ArrayList();
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

    public ArrayList<Integer[]> getTiros() {
        return tiros;
    }

    public void setTiros(ArrayList<Integer[]> tiros) {
        this.tiros = tiros;
    }
}
