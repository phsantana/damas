/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connection;

import control.ControlDama;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import synchronize.Synchronize;

/**
 *
 * @author Jean Yamada
 */
public class Client implements Runnable {

    private Socket socket;
    private Integer player;
    private boolean turn;

    public Client(String address, int port) {
        try {
            socket = new Socket(InetAddress.getByName(address), port);
        } catch (UnknownHostException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        DataInputStream input = null;
        DataOutputStream out = null;
        int[][] board = null;
        ControlDama ctrld = ControlDama.getInstace();
        Synchronize instance = Synchronize.getInstance();
        try {
            input = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
        }

        try {
            player = input.readInt();

            board = new int[8][8];

            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    board[i][j] = input.readInt();
                }
            }

            instance.getClientInterfaceGui().put(board);

        } catch (InterruptedException | IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println(player);

        turn = player == 1;

        ctrld.setPlayer(player);
        ctrld.setTurn(turn);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        int endGame;
        
        do{

            if (turn) {
                try {

                    board = (int[][]) instance.getClientInterfaceGui().take();

                    for (int i = 0; i < 8; i++) {
                        for (int j = 0; j < 8; j++) {
                            out.writeInt(board[i][j]);
                        }
                    }

                    turn = false;
                    ctrld.setTurn(false);

                } catch (InterruptedException | IOException ex) {
                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                board = new int[8][8];

                try {
                    for (int i = 0; i < 8; i++) {
                        for (int j = 0; j < 8; j++) {
                            board[i][j] = input.readInt();
                        }
                    }

                    ctrld.setMatrix(board);
                    ctrld.updateBoard();

                    turn = true;
                    ctrld.setTurn(true);

                } catch (IOException ex) {
                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }while((endGame = checkEndGame(board)) == -1);
        
        if((endGame == 11 && player == 1) || (endGame == 22 && player == 2))
            JOptionPane.showMessageDialog(null,"Você Ganhou!");
        else JOptionPane.showMessageDialog(null,"Você Perdeu!");
            
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

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
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

}
