/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jean
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

        Random random = new Random(System.currentTimeMillis());

        Integer player1 = random.nextInt(2) + 1;

        Integer player2;

        if (player1 == 1) {
            player2 = 2;
            turn = 1;

        } else {
            player2 = 1;
            turn = 2;

        }

        try {
            out = new DataOutputStream(socket1.getOutputStream());
            out.write(player1);

            out = new DataOutputStream(socket2.getOutputStream());
            out.write(player2);


        } catch (IOException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }

        int[][] board = new int[8][8];
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
                        board[i][j] = input.read();
                    }
                }

                board = rotacionarMatrizHorario(board);
                board = rotacionarMatrizHorario(board);

                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        out.writeInt(board[i][j]);
                    }
                }
                out.close();
            } catch (IOException e) {
            }

        } while (checkEndGame(board) == -1);

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

                if (board[i][j] == 1) {
                    contWhite++;
                } else if (board[i][j] == 2) {
                    contBlack++;
                }

            }
        }
        if (contBlack == 0) {
            return 1;
        }
        if (contWhite == 0) {
            return 2;
        }
        return -1;
    }

}
