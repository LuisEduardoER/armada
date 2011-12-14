package classes;

public class PortaAvioes extends Navio {
 
    public PortaAvioes() {
        super();
        this.nome = "Porta-Aviões";
        this.tamanho = 5;
        this.caminhoImagens = "/images/ships/porta-avioes/";
    }
    
    public PortaAvioes(int[] pos){
        this();
        this.pos = pos;
    }
    
    public PortaAvioes(int[] pos, Orientacao ori){
        this();
        this.pos = pos;
        this.orientacao = ori;
    }
}
 
