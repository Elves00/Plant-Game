/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plant_game;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

/**
 * The field panel is a panel consisting of 9 JLabels which are used to
 * represent the plant game field.
 *
 * @author breco
 */
public class ViewField extends JPanel {

    //Label array.
    private final JLabel[][] fieldLabels = new JLabel[3][3];

    private Border blueLine = BorderFactory.createLineBorder(Color.blue);
    private Border yellowLine = BorderFactory.createLineBorder(Color.yellow);
    private Border mixedLine = BorderFactory.createCompoundBorder(blueLine, yellowLine);

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
     * Updates the JLabels displaying field information.
     *
     * Updates the displayed field to correctly display the contents of the
     * models field and sets a border to indicate if a plant is watered and if a
     * plant is pollinated.
     *
     * @param plants
     * @param water
     * @param pollin
     */
    public void updateField(String[][] plants, Boolean[][] water, Boolean[][] pollin) {

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                //Updates defaultField labels to display the correct plant
                this.getFieldLabels()[i][j].setText(plants[i][j]);
                //If a plant is watered and pollinated display a mixed line
                if (water[i][j] && pollin[i][j]) {
                    this.getFieldLabels()[i][j].setBorder(mixedLine);
                } else if (water[i][j]) {
                    this.getFieldLabels()[i][j].setBorder(blueLine);
                } else if (pollin[i][j]) {

                    this.getFieldLabels()[i][j].setBorder(yellowLine);
                } else {
                    //A plant has no border if no condition is met.
                    this.getFieldLabels()[i][j].setBorder(null);
                }
            }
        }
    }

    /**
     * Adds mouseListener to all field labels.
     *
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
