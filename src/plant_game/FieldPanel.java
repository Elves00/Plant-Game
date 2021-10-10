/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plant_game;

import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author breco
 */
public class FieldPanel {

    private JPanel field = new JPanel(new GridLayout(3, 3));
    private JLabel[][] fieldLabels = new JLabel[3][3];

    public FieldPanel() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                this.fieldLabels[i][j] = new JLabel("", SwingConstants.CENTER);
                this.field.add(this.fieldLabels[i][j]);
            }
        }
    }

    public void addActionListener(ActionListener actionListener) {

    }

    public void addMouseListener(MouseListener mouseListener) {
          //Listeners for the field panels
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                getFieldLabels()[i][j].addMouseListener(mouseListener);
            }
        }
    }

    /**
     * @return the field
     */
    public JPanel getField() {
        return field;
    }

    /**
     * @return the fieldLabels
     */
    public JLabel[][] getFieldLabels() {
        return fieldLabels;
    }
}
