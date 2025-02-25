import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RockPaperScissorsFrame extends JFrame {
    private final JTextArea resultsArea;
    private final JTextField playerWinsField, computerWinsField, tiesField;
    private final List<String> playerMoveHistory = new ArrayList<>();
    private int playerWins = 0, computerWins = 0, ties = 0;
    private final Random random = new Random();
    private final String[] moves = {"Rock", "Paper", "Scissors"};

    public RockPaperScissorsFrame() {
        setTitle("Rock Paper Scissors Game");
        setLayout(new BorderLayout());


        JPanel controlPanel = new JPanel();
        controlPanel.setBorder(new TitledBorder("Choose Your Move"));

        for (String move : moves) {
            JButton button = new JButton(new ImageIcon("src/images/" + move + ".png"));
            button.setActionCommand(move);
            button.addActionListener(new MoveListener());
            controlPanel.add(button);
        }

        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener(e -> System.exit(0));
        controlPanel.add(quitButton);
        add(controlPanel, BorderLayout.NORTH);


        JPanel statsPanel = new JPanel(new GridLayout(3, 2));
        statsPanel.setBorder(new TitledBorder("Stats"));
        statsPanel.add(new JLabel("Player Wins:"));
        playerWinsField = new JTextField("0");
        playerWinsField.setEditable(false);
        statsPanel.add(playerWinsField);

        statsPanel.add(new JLabel("Computer Wins:"));
        computerWinsField = new JTextField("0");
        computerWinsField.setEditable(false);
        statsPanel.add(computerWinsField);

        statsPanel.add(new JLabel("Ties:"));
        tiesField = new JTextField("0");
        tiesField.setEditable(false);
        statsPanel.add(tiesField);

        add(statsPanel, BorderLayout.CENTER);


        resultsArea = new JTextArea(10, 30);
        resultsArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultsArea);
        scrollPane.setBorder(new TitledBorder("Game History"));
        add(scrollPane, BorderLayout.SOUTH);

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private class MoveListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String playerMove = e.getActionCommand();
            playerMoveHistory.add(playerMove);

            String computerMove = moves[random.nextInt(moves.length)];


            String result = determineResult(playerMove, computerMove);
            updateStats();
            resultsArea.append(result + "\n");
        }

        private String determineResult(String playerMove, String computerMove) {
            if (playerMove.equals(computerMove)) {
                ties++;
                return "Tie! Both chose " + playerMove;
            } else if ((playerMove.equals("Rock") && computerMove.equals("Scissors")) ||
                    (playerMove.equals("Paper") && computerMove.equals("Rock")) ||
                    (playerMove.equals("Scissors") && computerMove.equals("Paper"))) {
                playerWins++;
                return "Player wins! " + playerMove + " beats " + computerMove;
            } else {
                computerWins++;
                return "Computer wins! " + computerMove + " beats " + playerMove;
            }
        }

        private void updateStats() {
            playerWinsField.setText(String.valueOf(playerWins));
            computerWinsField.setText(String.valueOf(computerWins));
            tiesField.setText(String.valueOf(ties));
        }
    }
}
