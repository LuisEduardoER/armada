package classes;

public class Couracado extends Navio {

    public Couracado() {
        super();
        this.nome = "Couraçado";
        this.tamanho = 3;
        this.caminhoImagens = "/images/ships/couracado/";
    }
    
    public Couracado(int[] pos){
        this();
        this.vivo= true;
        this.pos = pos;
    }
    
    public Couracado(int[] pos, Orientacao ori){
        this();
        this.pos = pos;
        this.orientacao = ori;
    }
}
 
