/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sistema.model;

import br.com.sistema.view.Frmmenu;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;

/**
 *
 * @author Tampelini
 */
public class Utilitarios {

    //metodo limparCampos
    public void LimpaTela(JPanel container) {
        Component components[] = container.getComponents();
        for (Component component : components) {
            if (component instanceof JSplitPane) {
                System.out.println("-> " + ((JTextField) ((JSplitPane) component).getLeftComponent()).getText());
                ((JTextField) ((JSplitPane) component).getLeftComponent()).setText(null);
            }
            
            if (component instanceof JTextField) {
                ((JTextField) component).setText(null);
            }
        }
    }
    
    // metodo para verificar se os campos estão limpos
    public boolean verificaLimpo(JPanel container) {
        boolean bool = true;
        Component components[] = container.getComponents();
        for (Component component : components) {
            System.out.println("->" + component.getName());
            if (component instanceof JTextField && component.getName() != null && (component.getName().equals("cod"))) {
                if (((JTextField) component).getText().isEmpty()) {
                    bool = false;
                    break;
                }
            }
        }
        return bool;
    }
    
    public int[] cpfInt(String cpf) {

        final String regex = "\\W";
        final String subst = "";
        
        int[] digitos = new int [12];
        
        String result = cpf.replaceAll(regex, subst);
        
        String [] numero = result.split("");
        
        for (int i = 0; i < result.length(); i++) {
            digitos [i] = Integer.parseInt(numero[i]);
        }

        return digitos;
    }

    public int validaCpf(String cpf) {

        int[] nums = cpfInt(cpf);

        int num1 = ((nums[0] * 10) + (nums[1] * 9) + (nums[2] * 8) + (nums[3] * 7) + (nums[4] * 6) + (nums[5] * 5) + (nums[6] * 4) + (nums[7] * 3) + (nums[8] * 2)) % 11;

        if (num1 >= 2) {
            num1 = 11 - num1;
        } else {
            num1 = 0;
        }

        int num2 = ((nums[0] * 11) + (nums[1] * 10) + (nums[2] * 9) + (nums[3] * 8) + (nums[4] * 7) + (nums[5] * 6) + (nums[6] * 5) + (nums[7] * 4) + (nums[8] * 3) + (num1 * 2)) % 11;

        if (num2 >= 2) {
            num2 = 11 - num2;
        } else {
            num2 = 0;
        }

        if (num1 == nums[9] && num2 == nums[10]) {
            return 0;
        } else {
            return 1;
        }
    }

    //Metodo para adicionar imagem de fundo JDesktopPane
//    public void adicionaImagem() {
//
//        ImageIcon icon = new ImageIcon(Frmmenu.class.getResource("br.com.projeto.images/fundo.jpg"));
//        Image img = icon.getImage();
//
//        JDesktopPane painel = new JDesktopPane() {
//            public void paintComponent(Graphics g) {
//                g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
//            }
//
//        }
                
                }
