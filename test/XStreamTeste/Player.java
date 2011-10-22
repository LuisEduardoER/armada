/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package XStreamTeste;

/**
 *
 * @author dolalima
 */
public class Player {
    
    private String nome;
    private String email;
    private int altuta;

    public int getAltuta() {
        return altuta;
    }

    public void setAltuta(int altuta) {
        this.altuta = altuta;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    

    Player(String nome, String email, int alt) {
        this.nome = nome;
        this.email = email;
        this.altuta = alt;
    }
    
}
