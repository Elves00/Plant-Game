/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plant_game;

import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author breco
 */
public class FieldPanel extends JPanel {

    private JPanel field = new JPanel(new GridLayout(3, 3));
    private JLabel[][] fieldLabels = new JLabel[3][3];

}
