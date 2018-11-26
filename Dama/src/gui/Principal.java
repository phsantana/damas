/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import javax.swing.JFrame;


/**
 *
 * @author Jean Yamada
 */
public class Principal extends JFrame{
    
    private Board board;

    
    public Principal() {
        super();
        initialize();
    }

    public void initialize() {
        setLayout(new BorderLayout());
        setTitle("Dama");
        setVisible(true);
        setSize(new Dimension(600, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(new Point(0, 0));
        board = new Board();

        new Thread(board).start();
        
        add(board, BorderLayout.CENTER);


    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    
    
}
