package classes;

public class Destroyer extends Navio {
    
    public Destroyer() {
        super();
        this.nome = "Destroyer";
        this.tamanho = 4;
        this.caminhoImagens = "/images/ships/destroyer/";
    }
    
    public Destroyer(int[] pos){
        this();
        this.pos = pos;
    }
}
 
