/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.Main;
import moviment.Moviment;

/**
 *
 * @author jean
 */
public class Client implements Runnable {

    private Socket socket;
    private Integer player;
    private boolean turn, end;
    private Moviment moviment;

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
        try {
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());

            player = (Integer) inputStream.readObject();

            synchronized (this) {
                while (!end) {
                    if (turn) {

                        synchronized (Main.principal) {
                            try {
                                Main.principal.wait();
                            } catch (InterruptedException ex) {
                                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                            }

                            outputStream.writeObject(moviment);
                        }
                    } else {
                        moviment = (Moviment) inputStream.readObject();
                        turn = true;
                        notify();
                    }
                    end = (boolean) inputStream.readObject();
                    notify();
                }
            }
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
