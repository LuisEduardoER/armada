package classes;

public class Cruzador extends Navio {
 
    public Cruzador() {
        super();
        this.nome = "Cruzador";
        this.tamanho = 2;
        this.caminhoImagens = "/images/ships/cruzador/";
    }
    
    public Cruzador(int[] pos){
        this();
        this.pos = pos;
    }
}
 
