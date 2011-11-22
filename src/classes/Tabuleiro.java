package classes;

import java.net.URL;
import java.util.ArrayList;

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

    public ArrayList<Integer[]> getCoordenadasNavio(int[] pos, Navio navio) {
        ArrayList<Integer[]> lista = new ArrayList<Integer[]>();

        if(navio != null){
            int x = pos[0];
            int y = pos[1];
            if(navio.getOrientacao() == Orientacao.HORIZONTAL && x + navio.getTamanho() > tamanho){
                x = tamanho - navio.getTamanho();
            } else if(navio.getOrientacao() == Orientacao.VERTICAL && y + navio.getTamanho() > tamanho){
                y = tamanho - navio.getTamanho();
            }

            for(int i = 0; i < navio.getTamanho(); i++){
                lista.add(new Integer[]{x, y});
                if(navio.getOrientacao() == Orientacao.HORIZONTAL){
                    x++;
                } else {
                    y++;
                }
            }
        }

        return lista;
    }

    public Navio getNavio(int pos[]) {
        Navio navio = null;
        ArrayList<Navio> ships = this.getNaviosPosicionados();

        for(int i = 0; i < ships.size(); i++){
            ArrayList<Integer[]> coords = getCoordenadasNavio(ships.get(i).getPos(), ships.get(i));
            for(int j = 0; j < coords.size(); j++){
                int x = coords.get(j)[0];
                int y = coords.get(j)[1];
                if(x == pos[0] && y == pos[1]){
                    navio = ships.get(i);
                    break;
                }
            }
        }

        return navio;
    }

    public ArrayList<Navio> getNaviosPosicionados() {
        ArrayList<Navio> ships = new ArrayList<Navio>();

        for(int i = 0; i < navios.length; i++){
            Navio navio = navios[i];

            if(navio.getPos() != null){
                ships.add(navio);
            }
        }

        return ships;
    }

    public int getQtdeNaviosSemPosicao() {
        int qtde = 0;

        for(int i = 0; i < navios.length; i++){
            Navio navio = navios[i];
            if(navio.getPos() == null){
                qtde++;
            }
        }

        return qtde;
    }

    public boolean coordenadasSeCruzam(ArrayList<Integer[]> lista1, ArrayList<Integer[]> lista2) {
        for(int i = 0; i < lista1.size(); i++){
            for(int j = 0; j < lista2.size(); j++){
                if(lista1.get(i)[0] == lista2.get(j)[0] && lista1.get(i)[1] == lista2.get(j)[1]){
                    return true;
                }
            }
        }

        return false;
    }

    public boolean coordenadasEstaoLivres(ArrayList<Integer[]> coordenadas) {
        ArrayList<Integer[]> lista = coordenadas;
        ArrayList<Navio> ships = this.getNaviosPosicionados();

        for(int i = 0; i < ships.size(); i++){
            Navio navio = ships.get(i);

            if(this.coordenadasSeCruzam(lista, navio.getCoordenadas())){
                return false;
            }
        }

        return true;
    }

    public boolean acertouNavio(int[] pos) {
        ArrayList<Navio> ships = getNaviosPosicionados();
        for(int i = 0; i < ships.size(); i++){
            Navio navio = ships.get(i);
            int x = navio.getPos()[0];
            int y = navio.getPos()[1];
            for(int j = 1; j <= navio.getTamanho(); j++){
                if(pos[0] == x && pos[1] == y){
                    return true;
                }

                if(navio.getOrientacao() == Orientacao.VERTICAL){
                    y++;
                } else {
                    x++;
                }
            }
        }

        return false;
    }

    public URL getCaminhoImagem(int[] pos) {
        ArrayList<Navio> ships = getNaviosPosicionados();
        for(int i = 0; i < ships.size(); i++){
            Navio navio = ships.get(i);

            String ori;
            if(navio.getOrientacao() == Orientacao.VERTICAL){
                ori = "v";
            } else {
                ori = "h";
            }
            int x = navio.getPos()[0];
            int y = navio.getPos()[1];
            for(int j = 1; j <= navio.getTamanho(); j++){
                if(pos[0] == x && pos[1] == y){
                    return getClass().getResource(navio.getCaminhoImagens() + ori + j + ".png");
                }

                if(navio.getOrientacao() == Orientacao.VERTICAL){
                    y++;
                } else {
                    x++;
                }
            }
        }

        return null;
    }
}
