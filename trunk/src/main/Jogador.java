package main;

import classes.Navio;
import classes.Tabuleiro;

public class Jogador {

    private String nome;
    private Navio[] navio;
    private Tabuleiro tabuleiro;

    public Jogador(String nome, Navio[] navio, Tabuleiro tabuleiro) {
        this.nome = nome;
        this.navio = navio;
        this.tabuleiro = tabuleiro;
    }
    

    public Navio[] getNavio() {
        return navio;
    }

    public void setNavio(Navio[] navio) {
        this.navio = navio;
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


    public void atacar() {
    }

    public void receberAtaque() {
    }
}
