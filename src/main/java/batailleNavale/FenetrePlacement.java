package batailleNavale;

import java.awt.EventQueue;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class FenetrePlacement extends JFrame {

	private JPanel contentPane;
	private GrilleGraphique grillePlacement;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					FenetrePlacement frame = new FenetrePlacement();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FenetrePlacement() {
		setTitle("Placement des Navires");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 495);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);

		JPanel panelMain = new JPanel();
		contentPane.add(panelMain);
		panelMain.setLayout(new BoxLayout(panelMain, BoxLayout.Y_AXIS));

		JLabel lblPlacement = new JLabel("Placez vos navires sur la grille");
		lblPlacement.setHorizontalAlignment(SwingConstants.CENTER);
		panelMain.add(lblPlacement);

		grillePlacement = new GrilleGraphique(10);
		panelMain.add(grillePlacement);

		JButton btnNewButton = new JButton("Démarrer une partie");
		panelMain.add(btnNewButton);
	}

}
