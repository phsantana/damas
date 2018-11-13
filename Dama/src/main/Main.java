/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import connection.Client;
import gui.Principal;





/**
 *
 * @author visitante
 */
public class Main {

    
    public static void main(String [] args){
        Principal p = new Principal();
        p.setVisible(true);
        Client c = new Client("localhost", 5000);
        new Thread(c).start();
    }
}
