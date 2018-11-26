/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jean Yamada
 */
public class Connection implements Runnable {

    private Socket socket1, socket2;

    public Connection(Socket socket1, Socket socket2) {
        this.socket1 = socket1;
        this.socket2 = socket2;
    }

    @Override
    public void run() {

        DataInputStream input = null;
        DataOutputStream out = null;
        int turn = 0;
        int[][] board;

        try {
            out = new DataOutputStream(socket1.getOutputStream());
            out.writeInt(1);
            board = createMatrix(1);
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    out.writeInt(board[i][j]);
                }
            }

            out = new DataOutputStream(socket2.getOutputStream());
            out.writeInt(2);

            board = rotacionarMatrizHorario(board);
            board = rotacionarMatrizHorario(board);

            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    out.writeInt(board[i][j]);
                }
            }

        } catch (IOException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }

        board = new int[8][8];
        turn = 1;
        int loose = 0;

        do {
            try {

                if (turn == 1) {
                    input = new DataInputStream(socket1.getInputStream());
                    out = new DataOutputStream(socket2.getOutputStream());
                    turn = 2;
                } else {
                    input = new DataInputStream(socket2.getInputStream());
                    out = new DataOutputStream(socket1.getOutputStream());
                    turn = 1;
                }

                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        board[i][j] = input.readInt();
                    }
                }

                board = rotacionarMatrizHorario(board);
                board = rotacionarMatrizHorario(board);

                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        out.writeInt(board[i][j]);
                    }
                }

            } catch (IOException e) {
            }

        } while ((loose = checkEndGame(board)) == -1);

        System.out.println("acabaou " + loose);
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

    public int checkEndGame(int[][] board) {
        int contWhite = 0, contBlack = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {

                if (board[i][j] == 1 || board[i][j] == 3) {
                    contWhite++;
                } else if (board[i][j] == 2 || board[i][j] == 4) {
                    contBlack++;
                }

            }
        }
        if (contBlack == 0) {
            return 11;
        }
        if (contWhite == 0) {
            return 22;
        }
        return -1;
    }

    public int[][] createMatrix(int player) {
        int[][] matrix;
        if (player == 1) {
            matrix = new int[][]{{1, 0, 1, 0, 1, 0, 1, 0}, {0, 1, 0, 1, 0, 1, 0, 1}, {1, 0, 1, 0, 1, 0, 1, 0},
            {0, -1, 0, -1, 0, -1, 0, -1}, {-1, 0, -1, 0, -1, 0, -1, 0},
            {0, 2, 0, 2, 0, 2, 0, 2, 0, 2}, {2, 0, 2, 0, 2, 0, 2, 0, 2, 0}, {0, 2, 0, 2, 0, 2, 0, 2, 0, 2}};
        } else {
            matrix = new int[][]{{0, 2, 0, 2, 0, 2, 0, 2, 0, 2}, {2, 0, 2, 0, 2, 0, 2, 0, 2, 0}, {0, 2, 0, 2, 0, 2, 0, 2, 0, 2},
            {-1, 0, -1, 0, -1, 0, -1, 0}, {0, -1, 0, -1, 0, -1, 0, -1},
            {1, 0, 1, 0, 1, 0, 1, 0}, {0, 1, 0, 1, 0, 1, 0, 1}, {1, 0, 1, 0, 1, 0, 1, 0}};
        }
        return matrix;
    }
}
