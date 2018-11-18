/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import gui.Square;
import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;
import synchronize.Synchronize;

/**
 *
 * @author visitante
 */
public class ControlDama {

    private static ControlDama ctrlDama;
    private Integer player;
    private boolean turn;
    private int[][] matrix;
    private Square[][] board;

    public static ControlDama getInstace() {
        if (ctrlDama == null) {
            ctrlDama = new ControlDama();
        }
        return ctrlDama;
    }

    public ControlDama() {
        initialize();

    }

    public void initialize() {
        Synchronize instance = Synchronize.getInstance();

        try {
            player = (Integer) instance.getClientInterfaceGui().take();
        } catch (InterruptedException ex) {
            Logger.getLogger(ControlDama.class.getName()).log(Level.SEVERE, null, ex);
        }

        matrix = new int[][]{{1, 0, 1, 0, 1, 0, 1, 0}, {0, 1, 0, 1, 0, 1, 0, 1}, {1, 0, 1, 0, 1, 0, 1, 0},
        {0, -1, 0, -1, 0, -1, 0, -1}, {-1, 0, -1, 0, -1, 0, -1, 0},
        {0, 2, 0, 2, 0, 2, 0, 2, 0, 2}, {2, 0, 2, 0, 2, 0, 2, 0, 2, 0}, {0, 2, 0, 2, 0, 2, 0, 2, 0, 2}};
        turn = true;

        if (player == 2) {
            matrix = rotacionarMatrizHorario(matrix);
            matrix = rotacionarMatrizHorario(matrix);
            turn = false;
        }

    }

    public void updateBoard() {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {

                Square s = board[i][j];

                if (matrix[i][j] != 0) {

                    switch (matrix[i][j]) {
                        case 1:
                            s.setIsPiece(true);
                            s.setColorPiece(Color.WHITE);
                            break;
                        case 2:
                            s.setIsPiece(true);
                            s.setColorPiece(Color.BLACK);
                            break;
                        case 3:
                            s.setIsPiece(true);
                            s.setIsDama(true);
                            s.setColorPiece(Color.WHITE);
                            break;
                        case 4:
                            s.setIsPiece(true);
                            s.setIsDama(true);
                            s.setColorPiece(Color.BLACK);
                            break;
                        default:
                            s.setIsPiece(false);
                            break;
                    }
                }
            }
        }
    }

    public void updateMatrix() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {

                Square square = board[i][j];

                if (square.isIsPiece()) {

                    if (square.getColorPiece() == Color.BLACK) {
                        if (square.isIsDama()) {
                            matrix[i][j] = 4;
                        } else {
                            matrix[i][j] = 2;
                        }
                    } else {
                        if (square.isIsDama()) {
                            matrix[i][j] = 3;
                        } else {
                            matrix[i][j] = 1;
                        }
                    }
                } else {
                    Color black = new Color(153, 76, 0);

                    if (square.getColorSquare() == black) {
                        matrix[i][j] = -1;
                    } else {
                        matrix[i][j] = 0;
                    }
                }

            }
        }
    }

    public int[][] rotacionarMatrizHorario(int[][] matriz) {
        int largura = matriz.length;
        int altura = matriz[0].length;
        int[][] ret = new int[altura][largura];
        for (int i = 0; i < altura; i++) {
            for (int j = 0; j < largura; j++) {
                ret[i][j] = matriz[largura - j - 1][i];
            }
        }
        return ret;
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public static ControlDama getCtrlDama() {
        return ctrlDama;
    }

    public static void setCtrlDama(ControlDama ctrlDama) {
        ControlDama.ctrlDama = ctrlDama;
    }

    public Integer getPlayer() {
        return player;
    }

    public void setPlayer(Integer player) {
        this.player = player;
    }

    public boolean isTurn() {
        return turn;
    }

    public void setTurn(boolean turn) {
        this.turn = turn;
    }

    public void setMatrix(int[][] matrix) {
        this.matrix = matrix;
    }

    public Square[][] getBoard() {
        return board;
    }

    public void setBoard(Square[][] board) {
        this.board = board;
    }

}
