package batailleNavale;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;


public class BatailleNavale {

	private JFrame frmBatailleNavale;
	private Joueur joueur1, joueur2;
	private int tailleGrille;
	private JTextField textFieldTailleGrille;
	private JTextField textFieldNomJ1;
	private JTextField textFieldNomJ2;
//This class is used to create a multiple-exclusion scope for a set of buttons. Creating a set of buttons with the same ButtonGroup object means that turning "on" one of those buttons turns off all other buttons in the group.
	private final ButtonGroup buttonGroup_Joueur1 = new ButtonGroup();
	private final ButtonGroup buttonGroup_Joueur2 = new ButtonGroup();

	private JRadioButton rdbtnJ1Graphique;
	private JRadioButton rdbtnJ1Texte;
	private JRadioButton rdbtnJ1Auto;
	private JRadioButton rdbtnJ2Graphique;
	private JRadioButton rdbtnJ2Texte;
	private JRadioButton rdbtnJ2Auto;

	String filepath ="bo1.wav";
	static AudioInputStream audioInput;
	static InputStream musicPath;
	static Clip clip;

	protected static final Font textFont = new Font("Fira Sans",0,12);
	protected static final Font pannelFont = new Font("Fira Sans",1,12);
	protected static final Font buttonFont = new Font("Fira Sans",1,13);

	protected static final Color bgColor = new Color(255,255,255);
	protected static final Color gridColor = new Color(211,217,234);
	protected static final Color FontColor = new Color(41,79,107);
	protected static final Color caseColor = new Color(245,246,251);

	protected static final Color aLeau = new Color(42,58,144);
	protected static final Color touche = new Color(234,40,57);
	protected static final Color navire = new Color(0,165,81);
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					BatailleNavale window = new BatailleNavale();
					window.frmBatailleNavale.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public BatailleNavale() {
		initialize();
		//initMusic(this.filepath);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmBatailleNavale = new JFrame();
		//frmBatailleNavale.setIconImage(Toolkit.getDefaultToolkit().getImage(BatailleNavale.class.getResource("\"C:\\Users\\User\\Documents\\NetBeansProjects\\BatailleNavale\\src\\icon.png\"")));
		frmBatailleNavale.setTitle("Bataille Navale");
		frmBatailleNavale.setBounds(200, 200, 275, 360);
		frmBatailleNavale.setMinimumSize(new Dimension(275, 445));
		frmBatailleNavale.setMaximumSize(new Dimension(450,450));
		frmBatailleNavale.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmBatailleNavale.getContentPane().setLayout(new BorderLayout(0, 0));
		frmBatailleNavale.setBackground(bgColor);
	//	frmBatailleNavale.setResizable(false);


		JPanel panelContent = new JPanel();
		frmBatailleNavale.getContentPane().add(panelContent, BorderLayout.NORTH);
		panelContent.setLayout(new BoxLayout(panelContent, BoxLayout.Y_AXIS));
		panelContent.setBorder(new EmptyBorder(10, 10, 10, 10));
		panelContent.setBackground(bgColor);

		/////////////// PANEL de la taille de la grille
		JPanel panelTailleGrille = new JPanel();
		panelContent.add(panelTailleGrille);
		panelTailleGrille.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		panelTailleGrille.setBackground(bgColor);

		JLabel lblTailleGrille = new JLabel("Taille de la grille : ");
		lblTailleGrille.setHorizontalAlignment(SwingConstants.LEFT);
		panelTailleGrille.add(lblTailleGrille);

		textFieldTailleGrille = new JTextField();
		textFieldTailleGrille.setColumns(8);
		textFieldTailleGrille.setBackground(caseColor);
		lblTailleGrille.setLabelFor(textFieldTailleGrille);
		lblTailleGrille.setFont(textFont);
		textFieldTailleGrille.setText("10");
		panelTailleGrille.add(textFieldTailleGrille);

		/////////////// PANEL du Joueur 1
		JPanel panelJ1 = new JPanel();
		panelContent.add(panelJ1);
		panelJ1.setLayout(new GridLayout(4, 0, 0, 0));
		panelJ1.setBorder(BorderFactory.createTitledBorder("Joueur 1"));
		panelJ1.setFont(pannelFont);
		panelJ1.setBackground(bgColor);

		JPanel panelNomJ1 = new JPanel();
		FlowLayout fl_panelNomJ1 = (FlowLayout) panelNomJ1.getLayout();
		fl_panelNomJ1.setAlignment(FlowLayout.LEFT);
		panelJ1.add(panelNomJ1);

		JLabel lblJ1 = new JLabel("Nom : ");
		panelNomJ1.add(lblJ1);

		textFieldNomJ1 = new JTextField();
		textFieldNomJ1.setBackground(caseColor);

		lblJ1.setLabelFor(textFieldNomJ1);
		lblJ1.setFont(textFont);
		textFieldNomJ1.setText("Rick");
		textFieldNomJ1.setColumns(15);
		panelNomJ1.add(textFieldNomJ1);
		panelNomJ1.setBackground(bgColor);

		rdbtnJ1Graphique = new JRadioButton("Joueur Graphique");
		panelJ1.add(rdbtnJ1Graphique);
		rdbtnJ1Graphique.setSelected(true);
		buttonGroup_Joueur1.add(rdbtnJ1Graphique);
		rdbtnJ1Graphique.setFont(textFont);


		rdbtnJ1Texte = new JRadioButton("Joueur Texte");
		panelJ1.add(rdbtnJ1Texte);
		buttonGroup_Joueur1.add(rdbtnJ1Texte);
		rdbtnJ1Texte.setFont(textFont);

		rdbtnJ1Auto = new JRadioButton("Joueur Auto");
		panelJ1.add(rdbtnJ1Auto);
		buttonGroup_Joueur1.add(rdbtnJ1Auto);
		rdbtnJ1Auto.setFont(textFont);

		rdbtnJ1Graphique.setBackground(bgColor);
		rdbtnJ1Texte.setBackground(bgColor);
		rdbtnJ1Auto.setBackground(bgColor);

		/////////////// PANEL du Joueur 2
		JPanel panelJ2 = new JPanel();
		panelContent.add(panelJ2);
		panelJ2.setLayout(new GridLayout(4, 0, 0, 0));
		panelJ2.setBorder(BorderFactory.createTitledBorder("Joueur 2"));
		panelJ2.setFont(pannelFont);
		panelJ2.setBackground(bgColor);

		JPanel panelNomJ2 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panelNomJ2.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		panelJ2.add(panelNomJ2);

		JLabel lblJ2 = new JLabel("Nom : ");
		lblJ2.setFont(textFont);
		panelNomJ2.add(lblJ2);
		panelNomJ2.setBackground(bgColor);


		textFieldNomJ2 = new JTextField();
		lblJ2.setLabelFor(textFieldNomJ2);
		textFieldNomJ2.setText("Morty");
		textFieldNomJ2.setColumns(15);
		textFieldNomJ2.setBackground(caseColor);

		panelNomJ2.add(textFieldNomJ2);
		panelNomJ2.setBackground(bgColor);

		rdbtnJ2Graphique = new JRadioButton("Joueur Graphique");
		panelJ2.add(rdbtnJ2Graphique);
		buttonGroup_Joueur2.add(rdbtnJ2Graphique);
		rdbtnJ2Graphique.setFont(textFont);

		rdbtnJ2Texte = new JRadioButton("Joueur Texte");
		panelJ2.add(rdbtnJ2Texte);
		buttonGroup_Joueur2.add(rdbtnJ2Texte);
		rdbtnJ2Texte.setFont(textFont);

		rdbtnJ2Auto = new JRadioButton("Joueur Auto");
		panelJ2.add(rdbtnJ2Auto);
		rdbtnJ2Auto.setSelected(true);
		buttonGroup_Joueur2.add(rdbtnJ2Auto);
		rdbtnJ2Auto.setFont(textFont);

		rdbtnJ2Graphique.setBackground(bgColor);
		rdbtnJ2Texte.setBackground(bgColor);
		rdbtnJ2Auto.setBackground(bgColor);

		/////////////// PANEL du/des boutons de lancements
		JPanel panelBtn = new JPanel();
		panelContent.add(panelBtn);
		panelBtn.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		panelBtn.setBackground(bgColor);

		JButton btnDemarrerPartie = new JButton("Lancer la partie");
		btnDemarrerPartie.setFont(buttonFont);
		btnDemarrerPartie.setBackground(gridColor);
		btnDemarrerPartie.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// click sur lancer partie:
				demarrerPartie();
			}
		});
		btnDemarrerPartie.setVerticalAlignment(SwingConstants.BOTTOM);
		panelBtn.add(btnDemarrerPartie);

		JPanel panelEmptySpace = new JPanel();
		frmBatailleNavale.getContentPane().add(panelEmptySpace, BorderLayout.CENTER);
		panelEmptySpace.setBackground(bgColor);
	}

	public static void playMusic() {
		try {
			if (musicPath.available() > 0) {
				clip = AudioSystem.getClip();
				clip.open(audioInput);
				clip.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}

	public static void pauseMusic() {
		clip.stop();
		clip.close();
	}

	public static void initMusic(String location) {
		try {
			musicPath = BatailleNavale.class.getResourceAsStream("/resources/"+location);
			audioInput = AudioSystem.getAudioInputStream(musicPath);

			// playMusic();
		} catch (Exception e) {
			System.err.println("can't find file");
		}
	}

	private int[] genererTableauNavires() {
		int[] tableauNaviresAuto = new int[15];
		if (tailleGrille <= 3) {
			tableauNaviresAuto[0] = 2;
			return tableauNaviresAuto;
		}

		if (tailleGrille <= 5) {
			tableauNaviresAuto[0] = 2;
			tableauNaviresAuto[1] = 2;
			return tableauNaviresAuto;
		}

		for(int i = 1; i < tailleGrille/2; i++) {
			if (i <= 5)
				tableauNaviresAuto[i-1] = i;
			else
				tableauNaviresAuto[i-1] = i-5;
		}
		return tableauNaviresAuto;

	}

	private void demarrerPartie() {
		boolean ready = true;

		try {
			tailleGrille = Integer.parseInt(textFieldTailleGrille.getText());

			if(tailleGrille <= 2) {
				JOptionPane.showMessageDialog(frmBatailleNavale, "La taille de la grille doit être minimum 3");
				textFieldTailleGrille.setText("10");
				ready = false;
			}

		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(frmBatailleNavale, "La valeur fournie dans tailleGrille n'est pas un nombre");
			textFieldTailleGrille.setText("10");
			ready = false;
		}


		String nomJ1 = textFieldNomJ1.getText();
		String nomJ2 = textFieldNomJ2.getText();

		 if(ready) {
			int[] tableauNaviresAuto = genererTableauNavires();

			// Essayer de créer les joueurs
			try {
				if (rdbtnJ1Graphique.isSelected()) {
					FenetreJoueur fj1 = new FenetreJoueur(nomJ1,tailleGrille,tableauNaviresAuto);
					joueur1 = new JoueurGraphique(fj1.getGrilleDefense(),fj1.getGrilleTirs(), nomJ1);
					fj1.setVisible(true);
				} else if (rdbtnJ1Texte.isSelected())
					joueur1 = new JoueurTexte(new GrilleNavale(tailleGrille,tableauNaviresAuto),nomJ1);
				else if (rdbtnJ1Auto.isSelected())
					joueur1 = new JoueurAutoStrategieLvl1(new GrilleNavale(tailleGrille,tableauNaviresAuto),nomJ1);


				if (rdbtnJ2Graphique.isSelected()) {
					FenetreJoueur fj2 = new FenetreJoueur(nomJ2,tailleGrille,tableauNaviresAuto);
					fj2.setBounds(880, 200, 850, 501);
					joueur2 = new JoueurGraphique(fj2.getGrilleDefense(),fj2.getGrilleTirs(), nomJ2);
					fj2.setVisible(true);
				} else if (rdbtnJ2Texte.isSelected())
					joueur2 = new JoueurTexte(new GrilleNavale(tailleGrille,tableauNaviresAuto),nomJ2);
				else if (rdbtnJ2Auto.isSelected())
					joueur2 = new JoueurAutoStrategieLvl4(new GrilleNavale(tailleGrille,tableauNaviresAuto),nomJ2);
			}  catch (IllegalArgumentException e) {
				JOptionPane.showMessageDialog(frmBatailleNavale, "La taille de la grille doit être entre 2 et 26");
				textFieldTailleGrille.setText("10");
				ready = false;
			}

			if (ready) {
			//	pauseMusic();
				new Thread() {
					@Override
					public void run() {
						joueur1.jouerAvec(joueur2);
					}
				}.start();
			}
		 }
	}

}
