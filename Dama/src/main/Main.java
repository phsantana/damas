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

    private static Client c;

    private static Principal p;

    public static void main(String[] args) {
        c = new Client("localhost", 5000);
        new Thread(c).start();
        p = new Principal();
        p.setVisible(true);
    }

    public static Client getC() {
        return c;
    }

    public static void setC(Client c) {
        Main.c = c;
    }

    public static Principal getP() {
        return p;
    }

    public static void setP(Principal p) {
        Main.p = p;
    }
    
}
