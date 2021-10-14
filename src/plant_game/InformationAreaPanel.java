/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plant_game;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * A display used to present information from the plant game info table.
 *
 * @author breco
 */
public class InformationAreaPanel extends JPanel {

    private final JScrollPane infoScroller;
    private final JTextArea infoArea = new JTextArea();

    public InformationAreaPanel() {
        super();
        this.setLayout(new BorderLayout());
        //Disables user from editing text.
        this.infoArea.setEditable(false);
        //Set up info scroller
        this.infoScroller = new JScrollPane(this.infoArea);
        this.add(this.infoScroller);
    }

    /**
     * Updates information displayed on the info area panel.
     *
     * @param infoArray
     */
    public void updateInformationDisplay(String... infoArray) {
        String toDisplay = "";
        for (String infoArray1 : infoArray) {
            toDisplay += infoArray1 + "\n";
        }
        this.infoArea.setText(toDisplay);

    }

}
