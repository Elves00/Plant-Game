/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plant_game;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author breco
 */
public class ViewHighScorePanel extends JPanel {

    private JButton advance;
    private JScrollPane highScoreScroll;
    private JList<Score> highScores = new JList();

    public ViewHighScorePanel() {
        super();
        this.setLayout(new BorderLayout());

        //Create scroll bar for highscore jlist.
        this.highScoreScroll = new JScrollPane(this.getHighScores());
        this.add(this.highScoreScroll, BorderLayout.CENTER);

    }

    public ViewHighScorePanel(JButton advance) {
        super();
        this.setLayout(new BorderLayout());
        this.advance = advance;
        //Create scroll bar for highscore jlist.
        this.highScoreScroll = new JScrollPane(this.getHighScores());
        this.add(this.highScoreScroll, BorderLayout.CENTER);
        this.add(this.advance, BorderLayout.SOUTH);

    }

    /**
     * Updates the high score display with a new set of scores.
     *
     * @param score a JList containing all scores.
     */
    public void updateScore(String[] names, int[] scores) {
        //Remove the previous high score scorll from the view.
        this.remove(this.getHighScoreScroll());

        OrderedList<Score> highscores = new OrderedList();

        //High scores only cares about 20 values.
        for (int i = 0; i < 20; i++) {
            //if there are defualt values stored in both arrays don't add.
            if (!(names[i] == null && scores[i] == 0)) {
                highscores.add(new Score(names[i], scores[i]));
            }

        }

        //Create a new jlist using the orderlist
        this.highScores = new JList<>(highscores.toArray());

        highScoreScroll = new JScrollPane(highScores);

        //add the jlist to the panel.
        this.add(highScoreScroll, BorderLayout.CENTER);
    }

    /**
     * Adds actionListener to all buttons within the plant game
     *
     * @param actionListener
     */
    public void addActionListener(ActionListener actionListener) {
        this.getAdvance().addActionListener(actionListener);
    }

    /**
     * @return the advance
     */
    public JButton getAdvance() {
        return advance;
    }

    /**
     * @return the highScoreScroll
     */
    public JScrollPane getHighScoreScroll() {
        return highScoreScroll;
    }

    /**
     * @return the highScores
     */
    public JList<Score> getHighScores() {
        return highScores;
    }

    /**
     * @param highScoreScroll the highScoreScroll to set
     */
    public void setHighScoreScroll(JScrollPane highScoreScroll) {
        this.highScoreScroll = highScoreScroll;
    }

    /**
     * @param highScores the highScores to set
     */
    public void setHighScores(JList<Score> highScores) {
        this.highScores = highScores;
    }

}
