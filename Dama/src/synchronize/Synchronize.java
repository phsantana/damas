/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package synchronize;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 *
 * @author Jean Yamada
 */
public class Synchronize {
    private static Synchronize synchronize;
    private BlockingQueue mouseEvent;
    private BlockingQueue clientInterfaceGui;
    
    public Synchronize(){
        mouseEvent = new ArrayBlockingQueue(1);
        clientInterfaceGui = new ArrayBlockingQueue(1);
    }
    public static Synchronize getInstance(){
        if(synchronize == null)
            synchronize = new Synchronize();
        return synchronize;
    }

    public BlockingQueue getMouseEvent() {
        return mouseEvent;
    }

    public void setMouseEvent(BlockingQueue mouseEvent) {
        this.mouseEvent = mouseEvent;
    }

    public BlockingQueue getClientInterfaceGui() {
        return clientInterfaceGui;
    }

    public void setClientInterfaceGui(BlockingQueue clientInterfaceGui) {
        this.clientInterfaceGui = clientInterfaceGui;
    }
    
    
}
