/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.sistema.model;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author diana
 */
public class Pintar_tabela extends DefaultTableCellRenderer {
public Pintar_tabela (){}

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column); //To change body of generated methods, choose Tools | Templates.
    
    Color background = Color.WHITE;
    
    Color foreground = Color.BLACK;
    Object objeto = table.getValueAt(row, 6);
    
        try {
            String pagamento = objeto.toString();
            
            if (pagamento == "Não pago") {
                background = Color.RED;
            }
            else {
                background = Color.GREEN;
            }
        
            
        } catch (Exception e) {
        }
    
        if(isSelected){
            background = Color.GRAY;
        }
        
    label.setBackground(background); 
    label.setForeground(foreground);
    return label;
    
    }
    
}
