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
import synchronize.Synchronize;

/**
 *
 * @author Jean Yamada
 */
public class Board extends JPanel implements Runnable {
    
    private Square[][] board;
    
    public Board() {
        super();
        initialize();
    }
    
    private void initialize() {
        setLayout(new GridBagLayout());
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(600, 600));
        setVisible(true);
        Synchronize instance = Synchronize.getInstance();
        
        int[][] matrix = null;
        
        try {
            matrix = (int[][]) instance.getClientInterfaceGui().take();
        } catch (InterruptedException ex) {
            Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        board = new Square[matrix.length][matrix.length];
        
        createBoard(matrix);
        
        ControlDama ctrld = ControlDama.getInstace();
        ctrld.setMatrix(matrix);
        ctrld.setBoard(board);
        
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
    
    public void updateBoardMouse() {
        Square org = null, dst = null;
        
        Synchronize instance = Synchronize.getInstance();
        ControlDama ctrld = ControlDama.getInstace();
        
        while (true) {
            try {
                org = (Square) instance.getMouseEvent().take();
                dst = (Square) instance.getMouseEvent().take();
                
                if (org.isIsPiece() && checkePlayer(org)) {
                    if (!org.isIsDama() && !dst.isIsPiece()) {
                        
                        Square current = checkNeighbor(org, dst);
                        
                        if (current != null) {
                            current.setColorPiece(org.getColorPiece());
                            current.setIsPiece(true);
                            org.setIsPiece(false);
                            
                            if (checkDama(current)) {
                                current.setIsDama(true);
                            }
                            
                            ctrld.updateMatrix();
                            instance.getClientInterfaceGui().put(ctrld.getMatrix());
                            
                        } else {
                            current = checkNeighborInCommon(org, dst);
                            
                            if (current != null) {
                                
                                if (current.isIsPiece()) {
                                    current.setIsPiece(false);
                                    current.setIsDama(false);
                                    org.setIsPiece(false);
                                    dst.setIsPiece(true);
                                    dst.setColorPiece(org.getColorPiece());
                                    
                                    if (checkDama(current)) {
                                        current.setIsDama(true);
                                    }
                                    ctrld.updateMatrix();
                                    instance.getClientInterfaceGui().put(ctrld.getMatrix());
                                }
                            }
                        }
                        
                    } else if (org.isIsDama() && !dst.isIsPiece()) {
                        Square crt = checkNeighborDama(org, dst);
                        
                        if (crt != null) {
                            if (crt.isIsPiece()) {
                                dst.setIsPiece(true);
                                dst.setIsDama(true);
                                dst.setColorPiece(org.getColorPiece());
                                crt.setIsPiece(false);
                                crt.setIsDama(false);
                                org.setIsDama(false);
                                org.setIsPiece(false);
                                
                                ctrld.updateMatrix();
                                instance.getClientInterfaceGui().put(ctrld.getMatrix());
                            } else {
                                dst.setIsDama(true);
                                dst.setIsPiece(true);
                                dst.setColorPiece(org.getColorPiece());
                                org.setIsDama(false);
                                org.setIsPiece(false);
                                
                                ctrld.updateMatrix();
                                instance.getClientInterfaceGui().put(ctrld.getMatrix());
                            }
                        }
                    }
                }
                org.setClicked(false);
                dst.setClicked(false);
                
                repaint();
                
            } catch (InterruptedException e) {
            }
            
        }
        
    }
    
    public boolean checkDama(Square s) {
        return s.getNeighbor()[2] == null && s.getNeighbor()[3] == null;
    }
    
    public Square checkNeighborDama(Square x, Square y) {
        for (int i = 0; i < x.getNeighbor().length; i++) {
            Square crt = x.getNeighbor()[i];
            if (crt != null) {
                do {
                    if (crt.isIsPiece()) {
                        if (crt.getNeighbor()[i] == y) {
                            return crt;
                        } else {
                            break;
                        }
                    }
                    if (crt == y) {
                        return y;
                    } else {
                        crt = crt.getNeighbor()[i];
                    }
                    
                } while (crt != null);
            }
        }
        return null;
    }
    
    public Square checkNeighbor(Square x, Square y) {
        for (int i = 2; i < x.getNeighbor().length; i++) {
            Square crt = x.getNeighbor()[i];
            if (crt == y) {
                return y;
            }
        }
        return null;
    }
    
    public Square checkNeighborInCommon(Square x, Square y) {
        for (Square a : x.getNeighbor()) {
            for (Square b : y.getNeighbor()) {
                if (a == b) {
                    return b;
                }
            }
        }
        return null;
    }
    
    public Square[][] getBoard() {
        return board;
    }
    
    public void setBoard(Square[][] board) {
        this.board = board;
    }
    
    @Override
    public void run() {
        updateBoardMouse();
    }
    
    public boolean checkePlayer(Square s) {
        ControlDama ctrld = ControlDama.getInstace();
        
        if (ctrld.getPlayer() == 1 && s.getColorPiece() == Color.WHITE) {
            return true;
        }
        return ctrld.getPlayer() == 2 && s.getColorPiece() == Color.BLACK;
    }
}
