package batailleNavale;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class CompteARebours {

    private int secondsRemaining;
    private Timer timer;
    private JLabel timerLabel;

    public CompteARebours(int seconds, JLabel label) {
        this.secondsRemaining = seconds;
        this.timerLabel = label;

        // Crée un timer qui décrémente le temps chaque seconde
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (secondsRemaining > 0) {
                    timerLabel.setText(Integer.toString(secondsRemaining));
                    secondsRemaining--;
                } else {
                    timerLabel.setText("Le moins fort PERD!");
                    timer.stop();  // Arrête le timer une fois le compte à rebours terminé
                }
            }
        });
    }

    public void startCountdown() {
        timer.start();  // Démarre le timer
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("Compte à Rebours Swing");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(200, 150);

                JLabel timerLabel = new JLabel();
                frame.add(timerLabel);

                CompteARebours countdown = new CompteARebours(3, timerLabel);
                countdown.startCountdown();

                frame.setVisible(true);
            }
        });
    }
}
