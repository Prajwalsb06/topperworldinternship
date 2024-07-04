import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import java.util.Random;

public class Game {
    private int minNum = 1;
    private int maxNum = 100;
    private int answer;
    private int attempts = 0;

    private JFrame frame;
    private JTextField guessField;
    private JLabel resultLabel;

    public Game() {
        // Initialize the random number
        answer = new Random().nextInt(maxNum - minNum + 1) + minNum;

        frame = new JFrame("Number Guessing Game");
        guessField = new JTextField(10);
        JButton guessButton = new JButton("Guess");
        resultLabel = new JLabel();

        frame.setLayout(new FlowLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 150);

        frame.add(new JLabel("Guess a number between " + minNum + " and " + maxNum + ":"));
        frame.add(guessField);
        frame.add(guessButton);
        frame.add(resultLabel);

        guessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (attempts < 5) {
                    checkGuess();
                } else {
                    resultLabel.setText("You failed. The answer was " + answer);
                }
            }
        });
        frame.setVisible(true);
    }

    private void checkGuess() {
        try {
            int guess = Integer.parseInt(guessField.getText());
            attempts++;

            if (guess < answer) {
                resultLabel.setText("Too low! Try again.");
            } else if (guess > answer) {
                resultLabel.setText("Too high! Try again.");
            } else {
                resultLabel.setText("Correct! The answer was " + answer + ". It took you " + attempts + " attempts.");
            }
        } catch (NumberFormatException ex) {
            resultLabel.setText("Please enter a valid number.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Game());
    }
}

