/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plant_game;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author breco
 */
public class UnlockShopPanel extends JPanel implements Observer {

    private UnlockShop unlockShop;

    private JLabel unlockShopLabel;

    public UnlockShopPanel(UnlockShop unlockShop) {

        this.unlockShop = unlockShop;

        this.unlockShopLabel = new JLabel(this.unlockShop.toString());
        System.out.println(this.unlockShop.toString());
        this.add(unlockShopLabel);
    }

    @Override
    public void update(Observable o, Object arg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
