package batailleNavale;

import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.GridLayout;
import  java.io.Serializable;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class FenetreJoueur extends JFrame implements Serializable{

	private JPanel contentPane;
	private GrilleGraphique grilleTirs;
	private GrilleNavaleGraphique grilleDefense;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					FenetreJoueur frame = new FenetreJoueur();
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
	public FenetreJoueur() {
		this("Nom du joueur", 10);
	}

	public FenetreJoueur(String nom, int taille) {
		this(nom,taille,new int[] {1,2,3,4});
	}

	public FenetreJoueur(String nom, int taille, int[] placement) {
		setTitle("Centre naval de "+ nom);
		this.grilleTirs = new GrilleGraphique(taille);
		this.grilleDefense = new GrilleNavaleGraphique(taille);

		placerNavires(placement);

		//grilleDefense.ajouteNavire(new Navire(new Coordonnee("B2"),3,false));
		initialize();
	}

	public void placerNavires() {
		grilleDefense.placementAuto(new int[] {1,2,3,4});
	}

	public void placerNavires(int[] placement) {
		try {
			grilleDefense.placementAuto(placement);
		} catch (Exception e) {
			System.out.println("Pb avec placement de Fenetre joueur");
			e.printStackTrace();
		}
	}

	private void initialize() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//this.setBounds(470, 200, 850, 501);
		//this.setMinimumSize(new Dimension(grilleDefense.getTaille()*75,grilleDefense.getTaille()*40));
		//this.setIconImage(Toolkit.getDefaultToolkit().getImage(BatailleNavale.class.getResource("/resources/icon.png")));

		if(grilleDefense.getTaille() <= 8)
			this.setBounds(470, 200, 800, 400);
		else
			this.setBounds(20, 200, grilleDefense.getTaille()*85, grilleDefense.getTaille()*50);

		this.contentPane = new JPanel();
		this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setContentPane(contentPane);
		this.contentPane.setLayout(new GridLayout(1, 0, 0, 0));
		contentPane.setBackground(BatailleNavale.bgColor);

		// Créer la zone de tir et la zone de défense
		JPanel zoneTir = new JPanel();
		this.getContentPane().add(zoneTir);
		zoneTir.setBorder(BorderFactory.createTitledBorder("Zone de tir"));
		zoneTir.setFont(BatailleNavale.pannelFont);
		zoneTir.setBackground(BatailleNavale.bgColor);
		grilleTirs.setBackground(BatailleNavale.bgColor);
		zoneTir.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
		zoneTir.add(grilleTirs);


		JPanel zoneDefense = new JPanel();
		this.getContentPane().add(zoneDefense);
		zoneDefense.setBorder(BorderFactory.createTitledBorder("Zone de defense"));
		zoneDefense.setFont(BatailleNavale.pannelFont);
		zoneDefense.setBackground(BatailleNavale.bgColor);
		grilleDefense.getGrilleGraphique().setBackground(BatailleNavale.bgColor);
		zoneDefense.add(grilleDefense.getGrilleGraphique());


		//zoneDefense.add();

	}

	public GrilleGraphique getGrilleTirs() {
		return this.grilleTirs;
	}
	public GrilleNavaleGraphique getGrilleDefense() {
		return this.grilleDefense;
	}

}
