/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import control.ControlDama;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import threads.MyThreads;

/**
 *
 * @author visitante
 */
public class Board extends JPanel {

    private Square[][] board;
    private boolean clicked;

    public Board() {
        super();
        initialize();
    }

    private void initialize() {
        setLayout(new GridBagLayout());
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(600, 600));
        setVisible(true);
        clicked = false;

        int[][] matrix = ControlDama.getInstace().getMatrix();

        board = new Square[matrix.length][matrix.length];

        createBoard(matrix);
    }

    public void createBoard(int[][] matrix) {

        GridBagConstraints gdc = new GridBagConstraints();

        gdc.gridheight = 1;
        gdc.gridwidth = 1;

        gdc.insets = new Insets(1, 1, 1, 1);

        gdc.weightx = 0.2;
        gdc.weighty = 0.5;

        gdc.fill = GridBagConstraints.BOTH;

        gdc.anchor = GridBagConstraints.CENTER;

        gdc.ipadx = 30;
        gdc.ipady = 30;

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {

                Square square;

                if (matrix[i][j] != 0) {

                    switch (matrix[i][j]) {
                        case 1:
                            square = new Square(new Color(153, 76, 0), Color.WHITE);
                            break;
                        case 2:
                            square = new Square(new Color(153, 76, 0), Color.BLACK);
                            break;
                        default:
                            square = new Square(new Color(153, 76, 0));
                            break;
                    }
                } else {
                    square = new Square(new Color(255, 204, 153));
                }

                gdc.gridx = j;
                gdc.gridy = (matrix.length - 1) - i;

                board[i][j] = square;

                add(square, gdc);
            }
        }

        addNeighbor();
    }

    public void addNeighbor() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                Square square = board[i][j];
                int indice = 0;
                for (int k = -1; k < 2; k += 2) {
                    for (int l = -1; l < 2; l += 2) {
                        try {
                            square.getNeighbor()[indice] = board[i + k][j + l];
                        } catch (ArrayIndexOutOfBoundsException e) {
                            square.getNeighbor()[indice] = null;
                        }
                        indice++;
                    }
                }

            }
        }
    }

    public void updateBoard() {

        MyThreads mt = MyThreads.getInstace();

        synchronized (mt.getClientT()) {
            try {
                mt.getClientT().wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        ControlDama ct = ControlDama.getInstace();
        
        board = ct.getBoard();
        mt.getGui().repaint();

        /*
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                Square square = board[i][j];

                if (matrix[i][j] <= 0 && square.isIsPiece()) {
                    square.setIsPiece(false);
                }

                if (matrix[i][j] >= 1 && !square.isIsPiece()) {

                    square.setIsPiece(true);

                    if (matrix[i][j] == 1) {
                        square.setColorPiece(Color.WHITE);
                    } else {
                        square.setColorPiece(Color.BLACK);
                    }
                }
            }
        }
         */
    }

    public Square[][] getBoard() {
        return board;
    }

    public void setBoard(Square[][] board) {
        this.board = board;
    }

}
