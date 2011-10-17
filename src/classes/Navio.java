package classes;

public abstract class Navio {
    
    public static final String VIVO = "vivo.png";
    public static final String MORTO = "morto.png";

    protected String nome;
    protected int[] pos;
    protected int tamanho;
    protected String caminhoImagens;
    protected Orientacao orientacao;
    protected boolean vivo;

    public Navio() {
        this.pos = null;
        this.tamanho = 10;
        this.orientacao = Orientacao.HORIZONTAL;
        this.vivo= true;
    }

    public Navio(String nome, int tamanho, String caminhoImagens) {
        this.nome = nome;
        this.tamanho = tamanho;
        this.caminhoImagens = caminhoImagens;
    }

    public Navio(int[] pos, int tamanho, String caminhoImagens, Orientacao orientacao, boolean vivo) {
        this.pos = pos;
        this.tamanho = tamanho;
        this.caminhoImagens = caminhoImagens;
        this.orientacao = orientacao;
        this.vivo = vivo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    
    public String getCaminhoImagens() {
        return caminhoImagens;
    }

    public void setCaminhoImagens(String caminhoImagens) {
        this.caminhoImagens = caminhoImagens;
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

    public boolean isVivo() {
        return vivo;
    }

    public void setVivo(boolean vivo) {
        this.vivo = vivo;
    }    
}
