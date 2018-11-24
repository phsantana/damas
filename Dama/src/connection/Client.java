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
import synchronize.Synchronize;

/**
 *
 * @author jean
 */
public class Client implements Runnable {

    private Socket socket;
    private Integer player;
    private boolean turn, end;

    public Client(String address, int port) {
        try {
            socket = new Socket(InetAddress.getByName(address), port);
            end = false;
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
        Synchronize instance = Synchronize.getInstance();
        try {
            input = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
        }

        try {
            player = input.read();
        } catch (IOException e) {

            
        }
        System.out.println(player);

        turn = player == 1;

        try {
            instance.getClientInterfaceGui().put(player);
        } catch (InterruptedException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }

        ControlDama ctrld = ControlDama.getInstace();
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        while (!end) {
            int[][] board;
            if (turn) {
                try {

                    board = (int[][]) instance.getClientInterfaceGui().take();

                    for (int i = 0; i < 8; i++) {
                        for (int j = 0; j < 8; j++) {
                            out.write(board[i][j]);
                        }
                    }

                    turn = false;

                } catch (InterruptedException | IOException ex) {
                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                board = new int[8][8];

                try {
                    for (int i = 0; i < 8; i++) {
                        for (int j = 0; j < 8; j++) {
                            board[i][j] = input.read();
                        }
                    }

                    ctrld.setMatrix(board);
                    ctrld.updateBoard();

                    turn = true;
                } catch (IOException ex) {
                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }

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

    public boolean isEnd() {
        return end;
    }

    public void setEnd(boolean end) {
        this.end = end;
    }

}
