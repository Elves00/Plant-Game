/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plant_game;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author breco
 */
public class PlayerPanel extends JPanel implements Observer, MouseListener {

    //Model
    private Player player;

    //Panels
    private JPanel southPanel;
    private JPanel northPanel;

    //Plant grid
    private JLabel[][] field;

    //Image
    private JLabel image;

    //Actions
    private Boolean plant;

    private Boolean pick;

    private Boolean water;

    public PlayerPanel(Player player) {
        super(new BorderLayout());

        this.player = player;
        plant = false;
        pick = false;
        water = false;

        //Panels
        this.southPanel = new JPanel();
        this.northPanel = new JPanel(new GridLayout(3, 3));

        this.field = new JLabel[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                this.field[i][j] = new JLabel("" + this.player.getField().getPlant(i, j).toString());
            }
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                this.northPanel.add(this.field[i][j]);
            }
        }

        player.addObserver(this);

        this.image = new JLabel(player.toString());
        this.southPanel.add(image);
        this.northPanel.addMouseListener(this);

        this.add(northPanel, BorderLayout.NORTH);
        this.add(southPanel, BorderLayout.SOUTH);

    }

    @Override
    public void update(Observable o, Object arg) {

        //ARG REPRESENTS THE OBJECT BEING PASSED INTO OBSERVABLE 
        //We could chuck observable on player and use this as a big thing
        if (arg.equals(this.player.getDay())) {
            System.out.println("N");
        }
        this.image.setText(player.toString());

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                this.field[i][j].setText("" + this.player.getField().getPlant(j, j));
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mousePressed(MouseEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        System.out.println("Pressed");
    }

    @Override
    public void mouseReleased(MouseEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
