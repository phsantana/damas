/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connection;

import control.ControlDama;
import gui.Square;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

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

        ControlDama ct = ControlDama.getInstace();
        try {
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());

            try {
                player = (Integer) inputStream.readObject();
            } catch (ClassNotFoundException e) {
            }
            while (!end) {
                if (turn) {

                    outputStream.writeObject(ct.getBoard());
                } else {
                    try {
                        ct.setBoard((Square[][]) inputStream.readObject());
                    } catch (ClassNotFoundException e) {
                    }
                    turn = true;

                }
                try {
                    end = (boolean) inputStream.readObject();
                } catch (ClassNotFoundException e) {
                }
            }
        } catch (IOException e) {
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
