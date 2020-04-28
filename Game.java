/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.1, Apr 2017
 */

// imports necessary libraries for Java swing
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Game Main class that specifies the frame and widgets of the GUI
 */
public class Game implements Runnable {
    public void run() {
        // NOTE : recall that the 'final' keyword notes immutability even for local variables.

        // Top-level frame in which game components live
        // Be sure to change "TOP LEVEL FRAME" to the name of your game
        final JFrame frame = new JFrame("Snake Game");
        frame.setLocation(400, 400);

        // Status panel
        final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.SOUTH);
        final JLabel status = new JLabel("Running... | ");
        final JLabel score = new JLabel("Score = 0");
        status_panel.add(status);
        status_panel.add(score);
        final JTextArea instructions = new JTextArea("INSTRUCTIONS WINDOW: "
                + "Begin by pressing any arrow key. "
                + "The objective of the game is to become as large as you can by "
                + "eating the yellow circles without "
                + "crashing into yourself or the walls. "
                + "The snake will be controlled by the arrow keys, and "
                + "the direction of the key is the direction your "
                + "snake will travel. Good luck!");
        instructions.setLineWrap(true);
        instructions.setWrapStyleWord(true);
        instructions.setEditable(false);
        frame.add(instructions, BorderLayout.LINE_START);
        final JTextArea ai = new JTextArea("Bonus: Press the AI button in order to "
                + "get an AI to play this game. \n \n"
                + "Note: The food will teleport once the game starts "
                + "to prevent starting in an advantageous direction");
        ai.setLineWrap(true);
        ai.setWrapStyleWord(true);
        ai.setEditable(false);
        frame.add(ai, BorderLayout.LINE_END);
        // Main playing area
        final Board board = new Board(status, score);
        frame.add(board, BorderLayout.CENTER);

        // Reset button
        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.NORTH);

        // Note here that when we add an action listener to the reset button, we define it as an
        // anonymous inner class that is an instance of ActionListener with its actionPerformed()
        // method overridden. When the button is pressed, actionPerformed() will be called.
        final JButton reset = new JButton("Reset");
        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                board.reset();
            }
        });
        control_panel.add(reset);
        final JButton aiMode = new JButton("AI");
        aiMode.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                board.flipAI();
            }
        });
        control_panel.add(aiMode);

        // Put the frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Start game
        board.reset();
    }

    /**
     * Main method run to start and run the game. Initializes the GUI elements specified in Game and
     * runs it. IMPORTANT: Do NOT delete! You MUST include this in your final submission.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Game());
    }
}