package classes;

public class Tabuleiro {

    private int tamanho;
    private Casa[] casas;

    public Tabuleiro(int tamanho, Casa[] casas) {
        this.tamanho = tamanho;
        this.casas = casas;
    }

    public int getTamanho() {
        return tamanho;
    }

    public void setTamanho(int tamanho) {
        this.tamanho = tamanho;
    }
    

    public Casa[] getCasas() {
        return casas;
    }

    public void setCasas(Casa[] casas) {
        this.casas = casas;
    }
}
