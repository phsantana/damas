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
    public static Principal principal;
    public static Client client;
    
    public static void main(String [] args){
        principal = new Principal();
        client = new Client("localhost", 10000);
    }
}
