package classes;

public class Tabuleiro {

    private int tamanho;
    private Casa[][] casas;
    private Navio[] navios;

    public Tabuleiro(int tamanho, Navio[] navios) {
        this.tamanho = tamanho;
        this.navios = navios;
        this.casas = new Casa[tamanho][tamanho];
        
        for(int i = 0; i < tamanho; i++){            
            for(int j = 0; j < tamanho; j++){
                casas[i][j] = new Casa();
            }
        }
    }

    public Navio[] getNavios() {
        return navios;
    }

    public void setNavios(Navio[] navios) {
        this.navios = navios;
    }

    public int getTamanho() {
        return tamanho;
    }

    public void setTamanho(int tamanho) {
        this.tamanho = tamanho;
    }
    

    public Casa[][] getCasas() {
        return casas;
    }

    public void setCasas(Casa[][] casas) {
        this.casas = casas;
    }
}
