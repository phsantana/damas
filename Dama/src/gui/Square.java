/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import control.ControlDama;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import synchronize.Synchronize;

/**
 *
 * @author Jean Yamada
 */
public class Square extends JPanel implements MouseListener {

    private Color colorSquare, colorPiece;
    private boolean isPiece, isDama;
    private boolean clicked;
    private Square[] neighbor;

    public Square() {
        super();
        this.colorSquare = Color.WHITE;
        initialize();
    }

    public Square(Color colorSquare) {
        super();
        this.colorSquare = colorSquare;
        isPiece = false;
        initialize();
    }

    public Square(Color colorSquare, Color colorPiece) {
        super();
        this.colorSquare = colorSquare;
        this.colorPiece = colorPiece;
        isPiece = true;
        initialize();
    }

    private void initialize() {
        isDama = false;
        setPreferredSize(new Dimension(50, 50));
        setLayout(new BorderLayout());
        setVisible(true);
        clicked = false;
        addMouseListener(this);
        neighbor = new Square[4];
    }

    @Override
    public void paintComponent(Graphics g) {
        fillSquare(g);
        if (isPiece) {
            fillPiece(g);
        }
        g.dispose();
    }

    public void fillSquare(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        Dimension d = getSize();
        g2d.setColor(colorSquare);
        g2d.fillRect(0, 0, d.width, d.height);
    }

    public void fillPiece(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        Dimension d = getSize();

        g2d.setColor(colorPiece);
        g2d.fillOval(5, 5, d.width - 10, d.height - 10);

        if(isDama){
            g2d.setColor(Color.YELLOW);
            g2d.fillOval(15, 15, d.width - 30, d.height - 30);
        }
        if (clicked) {
            g2d.setColor(Color.ORANGE);
            g2d.drawOval(5, 5, d.width - 10, d.height - 10);

        }

    }

    public Color getColorSquare() {
        return colorSquare;
    }

    public void setColorSquare(Color colorSquare) {
        this.colorSquare = colorSquare;
    }

    public Color getColorPiece() {
        return colorPiece;
    }

    public void setColorPiece(Color colorPiece) {
        this.colorPiece = colorPiece;
    }

    public boolean isIsPiece() {
        return isPiece;
    }

    public void setIsPiece(boolean isPiece) {
        this.isPiece = isPiece;
    }

    public boolean isIsDama() {
        return isDama;
    }

    public void setIsDama(boolean isDama) {
        this.isDama = isDama;
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        try {

            ControlDama ctrd = ControlDama.getInstace();

            if (ctrd.isTurn()) {

                    Synchronize instance = Synchronize.getInstance();

                    setClicked(true);
                    instance.getMouseEvent().put(this);
                
            }
            repaint();

        } catch (InterruptedException ex) {
            Logger.getLogger(Square.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void mousePressed(MouseEvent me) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent me) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent me) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean isClicked() {
        return clicked;
    }

    public void setClicked(boolean clicked) {
        this.clicked = clicked;
    }

    public Square[] getNeighbor() {
        return neighbor;
    }

    public void setNeighbor(Square[] vizinho) {
        this.neighbor = vizinho;
    }

}
