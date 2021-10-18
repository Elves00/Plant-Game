/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plant_game;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * A panel holding the content to be displayed once the new game button in the
 * start menu has been pressed.
 *
 * Main components are a title,text field and submision button.
 *
 * @author breco
 */
public class ViewNewGame extends JPanel {

    private JTextField username = new JTextField(20);
    private JPanel newGameSouth = new JPanel();
    private JLabel enterUsername = new JLabel("Enter your username", SwingConstants.CENTER);

    private Font fancy = new Font("verdana", Font.BOLD | Font.ITALIC, 28);
    private JButton submit = new JButton("Submit");

    public ViewNewGame() {
        this.setLayout(new BorderLayout());
        this.enterUsername.setFont(fancy);

        this.newGameSouth.add(this.username);
        this.newGameSouth.add(this.submit);

        add(this.enterUsername, BorderLayout.CENTER);
        add(this.newGameSouth, BorderLayout.SOUTH);

    }

    /**
     * Adds action listener to the submit button to allow for submision of
     * username
     *
     * @param actionListener
     */
    public void addActionListener(ActionListener actionListener) {
        this.getSubmit().addActionListener(actionListener);
    }

    /**
     * @return the username
     */
    public JTextField getUsername() {
        return username;
    }

    /**
     * @return the submit
     */
    public JButton getSubmit() {
        return submit;
    }
}
