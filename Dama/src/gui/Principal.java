/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.Dimension;
import java.awt.Point;
import javax.swing.JFrame;

/**
 *
 * @author visitante
 */
public class Principal extends JFrame{
    public Principal(){
        super();
        initialize();
    }
    public void initialize(){
        setTitle("Dama");
        setVisible(true);
        setSize(new Dimension(600, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(new Point(0, 0));
        add(new Board());
    }
}
