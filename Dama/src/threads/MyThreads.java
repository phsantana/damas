/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threads;

import connection.Client;
import gui.Principal;

/**
 *
 * @author jean
 */
public class MyThreads {

    private Thread clientT, guiT;
    private Client client;
    private Principal gui;
    private static MyThreads myThreads;

    public static MyThreads getInstace() {
        if (myThreads == null) {
            myThreads = new MyThreads();
        }
        return myThreads;
    }

    public MyThreads() {
        client = new Client("localhost", 10000);
        gui = new Principal();
        clientT = new Thread(client);
        clientT.start();
        guiT = new Thread(gui);
        guiT.start();
    }

    public Thread getClientT() {
        return clientT;
    }

    public void setClientT(Thread clientT) {
        this.clientT = clientT;
    }

    public Thread getGuiT() {
        return guiT;
    }

    public void setGuiT(Thread guiT) {
        this.guiT = guiT;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Principal getGui() {
        return gui;
    }

    public void setGui(Principal gui) {
        this.gui = gui;
    }

}
