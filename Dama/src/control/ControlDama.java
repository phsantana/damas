/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import connection.Client;

/**
 *
 * @author visitante
 */
public class ControlDama {
    private static ControlDama ctrlDama;
    private Client client;
    private Integer player;
    private int [][] matrix;
    
    public static ControlDama getInstace(){
        if(ctrlDama == null)
            ctrlDama = new ControlDama();
        return ctrlDama;
    }
    public ControlDama(){
        initialize();
    }
    
    public void initialize(){
        
        client = new Client("localhost", 10000);
        
        matrix = new int[][] {{1,0,1,0,1,0,1,0},{0,1,0,1,0,1,0,1},{1,0,1,0,1,0,1,0},
            {0,-1,0,-1,0,-1,0,-1},{-1,0,-1,0,-1,0,-1,0},
            {0,2,0,2,0,2,0,2,0,2},{2,0,2,0,2,0,2,0,2,0},{0,2,0,2,0,2,0,2,0,2}};
    }
    
    public int [][] getMatrix(){
        return matrix;
    }
    
}
