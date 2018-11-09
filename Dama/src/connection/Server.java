/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jean
 */
public class Server {
    private ServerSocket serverSocket;
   
    public Server(){
        try {
            serverSocket = new ServerSocket(10000);
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void waitConnection(){
        while(true){
            try {
                
                Socket p1 = serverSocket.accept();
                System.out.println("player 1 connected");
                Socket p2 = serverSocket.accept();
                System.out.println("player 2 connected");
                
                Connection connection = new Connection(p1, p2);
                
                new Thread(connection).start();
                
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static void main(String [] args){
        Server server = new Server();
        server.waitConnection();
    }
    
}
