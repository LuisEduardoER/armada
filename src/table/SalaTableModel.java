/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package table;

import classes.Sala;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author arthur
 */
public class SalaTableModel extends AbstractTableModel {

    private final int COL_NOME = 0;
    private final int COL_STATUS = 1;
    private final int COL_CRIADOR = 2;
    private final int COL_TIPO_JOGO = 3;
    private final int COL_ENTRAR = 4;
    private List<Sala> salas;

    public SalaTableModel() {
        salas = new ArrayList<Sala>();
    }

    public SalaTableModel(List<Sala> lista) {
        this();
        salas.addAll(lista);
    }

    @Override
    public int getRowCount() {
        return salas.size();
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    @Override
    public String getColumnName(int column) {
        if(column == COL_NOME){
            return "Nome";
        } else if(column == COL_CRIADOR){
            return "Criador";
        } else if(column == COL_STATUS){
            return "Status";
        } else if(column == COL_TIPO_JOGO){
            return "Tipo Jogo";
        } else if(column == COL_ENTRAR){
            return "Entrar";
        }

        return "";
    }

    @Override
    public boolean isCellEditable(int rowIndex, int column) {
        if(column == COL_ENTRAR){
            return true;
        }

        return false;
    }

    @Override
    public Class<?> getColumnClass(int column) {
        return String.class;
    }

    @Override
    public Object getValueAt(int rowIndex, int column) {
        Sala s = salas.get(rowIndex);

        if(column == COL_NOME){
            return s.getNome();
        } else if(column == COL_CRIADOR){
            return s.getCriador();
        } else if(column == COL_STATUS){
            int status = s.getStatus();
            if(status == Sala.ESPERANDO) {
                return "Esperando";
            } else if(status == Sala.JOGANDO) {
                return "Jogando";
            }
        } else if(column == COL_TIPO_JOGO){
            return s.getTipoJogo();
        } else if(column == COL_ENTRAR){
            JButton botao = new JButton();
            botao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/entrar.png")));
            botao.setText("");
            return botao;
        }

        return "";
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int column) {
        Sala s = salas.get(rowIndex);

        if(column == COL_NOME){
            s.setNome(aValue.toString());
        } else if(column == COL_STATUS){
            s.setStatus((Integer) aValue);
        } else if(column == COL_CRIADOR){
            s.setCriador(aValue.toString());
        } else if(column == COL_TIPO_JOGO){
            s.setTipoJogo(aValue.toString());
        }

        fireTableDataChanged();
    }

    public void inserir(Sala p) {
        salas.add(p);
        fireTableDataChanged();
    }

    public void excluir(int pos) {
        salas.remove(pos);
        fireTableDataChanged();
    }

    public void excluir(Sala p) {
        salas.remove(p);
        fireTableDataChanged();
    }
}
