 package batailleNavale;

import java.awt.Color;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class JoueurGraphique extends JoueurAvecGrille {
	private GrilleGraphique grilleTirs;
	private JLabel infosTirs = new JLabel("Cliquez sur une case pour commencer à jouer");
	private JLabel infosDefense = new JLabel("......................................");

	String filepath = "victoire.wav";
	String boum = "boum.wav";
	String drop = "drop.wav";
	String metal = "metal.wav";
	String defeat = "defeat.wav";

	public JoueurGraphique(GrilleNavaleGraphique grilleDefense, GrilleGraphique grilleTirs, String nom) {
		super(grilleDefense, nom);
		this.grilleTirs = grilleTirs;
		grilleTirs.getParent().add(infosTirs);
		JPanel def = (JPanel) grilleTirs.getParent().getParent().getComponent(1);
		def.add(infosDefense);
	}

	public JoueurGraphique(GrilleNavaleGraphique grilleDefense, GrilleGraphique grilleTirs) {
		super(grilleDefense);
		this.grilleTirs = grilleTirs;
	}

	@Override
	public Coordonnee choixAttaque() {
		Coordonnee c = grilleTirs.getCoordonneeSelectionnee();
		return c;
	}

	@Override
	protected void retourDefense(Coordonnee c, int etat) {
		Color couleur = etat == A_L_EAU ? BatailleNavale.aLeau : BatailleNavale.touche;
		switch (etat) {
		case TOUCHE:

			infosDefense.setText("On a touché votre navire en " + c);
//JOptionPane.showMessageDialog(grilleTirs, "On a touché votre navire en " + c);
			break;
		case COULE:

			infosDefense.setText("On a coulé votre un navire en " + c);
//JOptionPane.showMessageDialog(grilleTirs, "On a coulé votre un navire en " + c);
			break;
		case GAMEOVER:
			PlayMusic(this.defeat);
			infosDefense.setText("Vous avez perdu... ");
			JOptionPane.showMessageDialog(grilleTirs, "Vous avez perdu...");
			break;
		default:
			infosDefense.setText("Ouf, le tir en " + c + " est raté !");
		}
	}

	@Override
	protected void retourAttaque(Coordonnee c, int etat) {
		Color couleur = etat == A_L_EAU ? BatailleNavale.aLeau : BatailleNavale.touche;
		grilleTirs.colorie(c, couleur);
		switch (etat) {
		case TOUCHE:
			PlayMusic(this.metal);
			infosTirs.setText("Vous avez touché un navire en " + c);
//JOptionPane.showMessageDialog(grilleTirs, "Vous avez touché un navire en " + c);
			break;
		case COULE:
			PlayMusic(this.boum);
			infosTirs.setText("Vous avez coulé un navire en " + c);
//JOptionPane.showMessageDialog(grilleTirs, "Vous avez coulé un navire en " + c);
			break;
		case GAMEOVER:
			PlayMusic(this.filepath);
			infosTirs.setText("Vous avez gagné !!!");
			JOptionPane.showMessageDialog(grilleTirs, "Vous avez gagné!!!");
		default:
			PlayMusic(this.drop);
			infosTirs.setText("Mince, le tir en " + c + " est raté");
		}
	}

	public static void PlayMusic(String location) {
		try {
			File musicPath = new File(location);

			if (musicPath.exists()) {
				AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
				Clip clip = AudioSystem.getClip();
				clip.open(audioInput);
				clip.start();
			}

			else {
				System.out.println("can't find file");
			}

		} catch (Exception e) {
			System.out.println(e);
		}

	}
}

//
//package batailleNavale;
//
//import java.awt.Color;
//
//import javax.swing.JLabel;
//import javax.swing.JOptionPane;
//import javax.swing.JPanel;
//
//import java.io.File;
//import javax.sound.sampled.AudioInputStream;
//import javax.sound.sampled.AudioSystem;
//import javax.sound.sampled.Clip;
//
//public class JoueurGraphique extends JoueurAvecGrille {
//	private GrilleGraphique grilleTirs;
//	private JLabel infosTirs = new JLabel("Cliquez sur une case pour commencer à jouer");
//	private JLabel infosDefense = new JLabel("......................................");
//
//	String filepath = "victoire.wav";
//	String boum = "boum.wav";
//	String drop = "drop.wav";
//	String metal = "metal.wav";
//
//	public JoueurGraphique(GrilleNavaleGraphique grilleDefense, GrilleGraphique grilleTirs, String nom) {
//		super(grilleDefense, nom);
//		this.grilleTirs = grilleTirs;
//		grilleTirs.getParent().add(infosTirs);
//		JPanel def = (JPanel) grilleTirs.getParent().getParent().getComponent(1);
//		def.add(infosDefense);
//	}
//
//	public JoueurGraphique(GrilleNavaleGraphique grilleDefense, GrilleGraphique grilleTirs) {
//		super(grilleDefense);
//		this.grilleTirs = grilleTirs;
//	}
//
//	public Coordonnee choixAttaque() {
//		Coordonnee c = grilleTirs.getCoordonneeSelectionnee();
//		return c;
//	}
//
//	protected void retourDefense(Coordonnee c, int etat) {
//		Color couleur = etat == A_L_EAU ?  BatailleNavale.aLeau :  BatailleNavale.touche;
//		switch (etat) {
//		case TOUCHE:
//
//			infosDefense.setText("On a touché votre navire en " + c);
////JOptionPane.showMessageDialog(grilleTirs, "On a touché votre navire en " + c);
//			break;
//		case COULE:
//
//			infosDefense.setText("On a coulé votre un navire en " + c);
////JOptionPane.showMessageDialog(grilleTirs, "On a coulé votre un navire en " + c);
//			break;
//		case GAMEOVER:
//			infosDefense.setText("Vous avez perdu... ");
//			JOptionPane.showMessageDialog(grilleTirs, "Vous avez perdu...");
//			break;
//		default:
//			infosDefense.setText("Ouf, le tir en " + c + " est raté !");
//		}
//	}
//
//	protected void retourAttaque(Coordonnee c, int etat) {
//		Color couleur = etat == A_L_EAU ?  BatailleNavale.aLeau :  BatailleNavale.touche;
//		grilleTirs.colorie(c, couleur);
//		switch (etat) {
//		case TOUCHE:
//			PlayMusic(this.metal);
//			infosTirs.setText("Vous avez touché un navire en " + c);
////JOptionPane.showMessageDialog(grilleTirs, "Vous avez touché un navire en " + c);
//			break;
//		case COULE:
//			PlayMusic(this.boum);
//			infosTirs.setText("Vous avez coulé un navire en " + c);
////JOptionPane.showMessageDialog(grilleTirs, "Vous avez coulé un navire en " + c);
//			break;
//		case GAMEOVER:
//			PlayMusic(this.filepath);
//			infosTirs.setText("Vous avez gagné !!!");
//			JOptionPane.showMessageDialog(grilleTirs, "Vous avez gagné!!!");
//		default:
//			PlayMusic(this.drop);
//			infosTirs.setText("Mince, le tir en " + c + " est raté");
//		}
//	}
//
//	public static void PlayMusic(String location) {
//		try {
//			File musicPath = new File(location);
//
//			if (musicPath.exists()) {
//				AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
//				Clip clip = AudioSystem.getClip();
//				clip.open(audioInput);
//				clip.start();
//			}
//
//			else {
//				System.out.println("can't find file");
//			}
//
//		} catch (Exception e) {
//			System.out.println(e);
//		}
//
//	}
//}

//package batailleNavale;
//
//import java.awt.Color;
//
//import javax.swing.JLabel;
//import javax.swing.JOptionPane;
//import javax.swing.JPanel;
//
//public class JoueurGraphique extends JoueurAvecGrille {
//	private GrilleGraphique grilleTirs;
//	private JLabel infosTirs = new JLabel("Cliquez sur une case pour commencer à jouer");
//	private JLabel infosDefense = new JLabel("......................................");
//
//	public JoueurGraphique(GrilleNavaleGraphique grilleDefense, GrilleGraphique grilleTirs, String nom) {
//		super(grilleDefense, nom);
//		this.grilleTirs = grilleTirs;
//		grilleTirs.getParent().add(infosTirs);
//		JPanel def = (JPanel) grilleTirs.getParent().getParent().getComponent(1);
//		def.add(infosDefense);
//	}
//
//	public JoueurGraphique(GrilleNavaleGraphique grilleDefense, GrilleGraphique grilleTirs) {
//		super(grilleDefense);
//		this.grilleTirs = grilleTirs;
//	}
//
//	public Coordonnee choixAttaque() {
//		Coordonnee c = grilleTirs.getCoordonneeSelectionnee();
//		return c;
//	}
//
//	protected void retourDefense(Coordonnee c, int etat) {
//		Color couleur = etat == A_L_EAU ? Color.RED : Color.BLUE;
//		switch (etat) {
//		case TOUCHE:
//			infosDefense.setText("On a touché votre navire en " + c);
//			//JOptionPane.showMessageDialog(grilleTirs, "On a touché votre navire en " + c);
//			break;
//		case COULE:
//			infosDefense.setText("On a coulé votre un navire en " + c);
//			//JOptionPane.showMessageDialog(grilleTirs, "On a coulé votre un navire en " + c);
//			break;
//		case GAMEOVER:
//			infosDefense.setText("Vous avez perdu... ");
//			JOptionPane.showMessageDialog(grilleTirs, "Vous avez perdu...");
//			break;
//		default :
//			infosDefense.setText("Ouf, le tir en "+c+" est raté !");
//		}
//	}
//
//	protected void retourAttaque(Coordonnee c, int etat) {
//		Color couleur = etat == A_L_EAU ? Color.BLUE : Color.RED;
//		grilleTirs.colorie(c, couleur);
//		switch (etat) {
//		case TOUCHE:
//			infosTirs.setText("Vous avez touché un navire en " + c);
//			//JOptionPane.showMessageDialog(grilleTirs, "Vous avez touché un navire en " + c);
//			break;
//		case COULE:
//			infosTirs.setText("Vous avez coulé un navire en " + c);
//			//JOptionPane.showMessageDialog(grilleTirs, "Vous avez coulé un navire en " + c);
//			break;
//		case GAMEOVER:
//			infosTirs.setText("Vous avez gagné !!!");
//			JOptionPane.showMessageDialog(grilleTirs, "Vous avez gagné!!!");
//		default:
//			infosTirs.setText("Mince, le tir en "+c+" est raté");
//		}
//	}
//}