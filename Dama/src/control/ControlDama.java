/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import gui.Square;
import moviment.Moviment;

/**
 *
 * @author visitante
 */
public class ControlDama {

    private static ControlDama ctrlDama;
    private Moviment moviment;
    private Square[][] board;
    private int[][] matrix;

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
        matrix = new int[][]{{1, 0, 1, 0, 1, 0, 1, 0}, {0, 1, 0, 1, 0, 1, 0, 1}, {1, 0, 1, 0, 1, 0, 1, 0},
        {0, -1, 0, -1, 0, -1, 0, -1}, {-1, 0, -1, 0, -1, 0, -1, 0},
        {0, 2, 0, 2, 0, 2, 0, 2, 0, 2}, {2, 0, 2, 0, 2, 0, 2, 0, 2, 0}, {0, 2, 0, 2, 0, 2, 0, 2, 0, 2}};
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

    public Moviment getMoviment() {
        return moviment;
    }

    public void setMoviment(Moviment moviment) {
        this.moviment = moviment;
    }

    public Square[][] getBoard() {
        return board;
    }

    public void setBoard(Square[][] board) {
        this.board = board;
    }

}
