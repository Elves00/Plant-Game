/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plant_game;

import java.awt.GridLayout;
import java.awt.event.MouseListener;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * The field panel is a panel consisting of 9 JLabels which are used to
 * represent the plant game field.
 *
 * @author breco
 */
public class ViewField extends JPanel {

    //Label array.
    private final JLabel[][] fieldLabels = new JLabel[3][3];

    public ViewField() {
        super();
        //Sets up a grid layout to arrange the labels.
        this.setLayout(new GridLayout(3, 3));
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                //Sets labels to start with default name
                this.fieldLabels[i][j] = new JLabel("", SwingConstants.CENTER);
                this.add(this.fieldLabels[i][j]);
            }
        }

    }

    /**
     * Adds mouseListener to all field labels.
     * @param mouseListener
     */
    @Override
    public void addMouseListener(MouseListener mouseListener) {
        //Listeners for the field panels
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                getFieldLabels()[i][j].addMouseListener(mouseListener);
            }
        }
    }

    /**
     * @return the fieldLabels
     */
    public JLabel[][] getFieldLabels() {
        return fieldLabels;
    }

}
