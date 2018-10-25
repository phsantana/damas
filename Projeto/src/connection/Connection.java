/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connection;

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
            ObjectInputStream objectInputStream1 = new ObjectInputStream(socket1.getInputStream());
            ObjectInputStream objectInputStream2 = new ObjectInputStream(socket2.getInputStream());
            ObjectOutputStream objectOutputStream1 = new ObjectOutputStream(socket1.getOutputStream());
            ObjectOutputStream objectOutputStream2 = new ObjectOutputStream(socket2.getOutputStream());

            Random random = new Random(System.currentTimeMillis());

            Integer player = random.nextInt(2)+1;
            
            objectOutputStream1.writeObject(player);
            objectOutputStream2.writeObject(player);
            
            
            
        } catch (IOException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }

        while (true) {

        }
    }

}
