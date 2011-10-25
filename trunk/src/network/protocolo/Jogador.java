/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package network.protocolo;

/**
 *
 * @author dolalima
 */
public class Jogador {
    
    private String nome;
    private boolean online;
    
    public Jogador(String nome, boolean online){
        this.nome = nome;
        this.online = online;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }
    
    
    
}
