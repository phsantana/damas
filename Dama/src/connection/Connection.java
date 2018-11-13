/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connection;

import gui.Square;
import java.awt.Color;
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
        try {

            ObjectInputStream objectInputStream1;
            ObjectInputStream objectInputStream2;
            ObjectOutputStream objectOutputStream1;
            ObjectOutputStream objectOutputStream2;
            
            objectInputStream1 = new ObjectInputStream(socket1.getInputStream());
            /*
            Random random = new Random(System.currentTimeMillis());

            Integer player1 = random.nextInt(2) + 1;

            Integer player2;

            if (player1 == 1) {
                player2 = 2;
                objectInputStream1 = new ObjectInputStream(socket2.getInputStream());
                objectInputStream2 = new ObjectInputStream(socket1.getInputStream());

                objectOutputStream1 = new ObjectOutputStream(socket2.getOutputStream());
                objectOutputStream2 = new ObjectOutputStream(socket1.getOutputStream());
            } else {
                player2 = 1;
                objectInputStream1 = new ObjectInputStream(socket1.getInputStream());
                objectInputStream2 = new ObjectInputStream(socket2.getInputStream());

                objectOutputStream1 = new ObjectOutputStream(socket1.getOutputStream());
                objectOutputStream2 = new ObjectOutputStream(socket2.getOutputStream());

            }

            objectOutputStream1.writeObject(player1);
            objectOutputStream2.writeObject(player2);

            try {
                Square[][] board;
                do {
                    board = (Square[][]) objectInputStream1.readObject();

                    if (checkEndGame(board) != -1) {
                        objectOutputStream1.writeObject(false);
                    } else {
                        objectOutputStream1.writeObject(true);
                    }

                    objectOutputStream2.writeObject(board);

                    if (checkEndGame(board) != -1) {
                        objectOutputStream2.writeObject(false);
                    } else {
                        objectOutputStream2.writeObject(true);
                    }

                    board = (Square[][]) objectInputStream2.readObject();

                    if (checkEndGame(board) != -1) {
                        objectOutputStream2.writeObject(false);
                    } else {
                        objectOutputStream2.writeObject(true);
                    }

                    objectOutputStream1.writeObject(board);

                    if (checkEndGame(board) != -1) {
                        objectOutputStream1.writeObject(false);
                    } else {
                        objectOutputStream1.writeObject(true);
                    }
                } while (checkEndGame(board) == -1);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
            }
            */
        } catch (IOException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public int checkEndGame(Square[][] board) {
        int contWhite = 0, contBlack = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j].isIsPiece()) {
                    if (board[i][j].getColorPiece() == Color.WHITE) {
                        contWhite++;
                    } else {
                        contBlack++;
                    }
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
