package classes;

import java.awt.Image;

public abstract class Navio {

    protected int[] pos;
    protected int tamanho;
    protected Image[] modelo;
    protected Orientacao orientacao;

    public Navio() {
        this.pos = null;
        this.tamanho = 10;
        this.modelo = null;
        this.orientacao = Orientacao.HORIZONTAL;
    }


    public Navio(int[] pos, int tamanho, Image[] modelo, Orientacao orientacao) {
        this.pos = pos;
        this.tamanho = tamanho;
        this.modelo = modelo;
        this.orientacao = orientacao;
    }

    
    public Image[] getModelo() {
        return modelo;
    }

    public void setModelo(Image[] modelo) {
        this.modelo = modelo;
    }


    public Orientacao getOrientacao() {
        return orientacao;
    }

    public void setOrientacao(Orientacao orientacao) {
        this.orientacao = orientacao;
    }


    public int[] getPos() {
        return pos;
    }

    public void setPos(int[] pos) {
        this.pos = pos;
    }


    public int getTamanho() {
        return tamanho;
    }

    public void setTamanho(int tamanho) {
        this.tamanho = tamanho;
    }
}
