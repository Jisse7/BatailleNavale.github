package batailleNavale;

import static batailleNavale.Joueur.A_L_EAU;
import static batailleNavale.Joueur.COULE;
import static batailleNavale.Joueur.GAMEOVER;
import static batailleNavale.Joueur.TOUCHE;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
 
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
 
import java.io.Serializable;

public class JoueurGraphiqueMulti extends JoueurAvecGrilleMulti{
    
    private Statistiques statistiques;
    
    private GrilleGraphiqueMulti grilleTirs;
    private JLabel infosTirs = new JLabel("Cliquez sur une case pour commencer à jouer");
    private JLabel infosDefense = new JLabel("......................................");



    String filepath = "victoire.wav";
    String boum = "boum.wav";
    String drop = "drop.wav";
    String metal = "metal.wav";
    String defeat = "defeat.wav";
    

    

    public JoueurGraphiqueMulti(GrilleNavaleGraphiqueMulti grilleDefense, GrilleGraphiqueMulti grilleTirs,
            String nom, String nomJ2) {

        super(grilleDefense, nom);
        this.grilleTirs = grilleTirs;
        grilleTirs.getParent().add(infosTirs);
        JPanel def = (JPanel) grilleTirs.getParent().getParent().getComponent(1);
        def.add(infosDefense);
     this.statistiques = new Statistiques();
    
    }
    
    	public JoueurGraphiqueMulti(GrilleNavaleGraphiqueMulti grilleDefense, GrilleGraphiqueMulti grilleTirs) {
		super(grilleDefense);
		this.grilleTirs = grilleTirs;
                this.statistiques = new Statistiques();
	}
 
      public String choixAttaqueThreaded(PrintWriter out){
        Coordonnee d = grilleTirs.getCoordonneeSelectionnee();
        String a = d.toString();
        out.println("tire en : " +a);
        System.out.println("taille de la coordonnee : " + a.length());
       return a;
    }
      
 //   public Coordonnee choixAttaque(PrintWriter out) {
//		Coordonnee c = grilleTirs.getCoordonneeSelectionnee();
//                envoyerAttaque(c, out);
//		return c;
//	}
    
  //  protected void envoyerAttaque(Coordonnee c, PrintWriter out) {
  //      System.out.println(getNom() +" tire : " +c);
        //on doit vérifier si l'envoi est du type lettre,chiffre
  //      out.println( c);
  //  }

    public int protego(Coordonnee c, PrintWriter out) {
        System.out.println("PROTEGO : Coordonnee de c :" + c);
        System.out.println("je suis utilisé ici dans JGM ligne 84");
        grille.recoitTir(c);

        if (grille.perdu()) {
            out.println(String.valueOf(JoueurMulti.GAMEOVER));
            return JoueurMulti.GAMEOVER;

        }
        if (grille.estCoule(c)) {
            out.println(String.valueOf(JoueurMulti.COULE));
            return JoueurMulti.COULE;
        }
        if (grille.estTouche(c)) {
            out.println(String.valueOf(JoueurMulti.TOUCHE));
            return JoueurMulti.TOUCHE;
        }
        if (grille.estALEau(c)) {
            out.println(String.valueOf(JoueurMulti.A_L_EAU));
            return JoueurMulti.A_L_EAU;
        }
        
        out.println(String.valueOf(JoueurMulti.A_L_EAU));
        return JoueurMulti.A_L_EAU;
    }


  //  protected void recevoirAttaque(Coordonnee c) {
 //       int etat = super.recevoirAttaque(c);
  //      retourAttaque(c, etat);
  //  }

    protected void retourDefense(Coordonnee c, int etat) {
        Color couleur = etat == A_L_EAU ? BatailleNavale1.aLeau : BatailleNavale1.touche;
        switch (etat) {
            case TOUCHE:
                statistiques.incrementerTirsReçus();
                infosDefense.setText("On a touché votre navire en " + c);
                break;
            case COULE:
            	
                infosDefense.setText("On a coulé votre  navire en " + c);
                break;
            case GAMEOVER:
                PlayMusic(this.defeat);
                infosDefense.setText("Vous avez perdu... ");
                statistiques.incrementerPartiesPerdues();
                JOptionPane.showMessageDialog(grilleTirs, "Vous avez perdu...");
                break;
            default:
            	statistiques.incrementerTirsRates();
                statistiques.incrementerTirsReçus();
                infosDefense.setText("Ouf, le tir en " + c + " est raté !");
        }
    }

    protected void retourAttaque(Coordonnee c, int etat) {
        
        Color couleur = etat == A_L_EAU ? BatailleNavale1.aLeau : BatailleNavale1.touche;
		grilleTirs.colorie(c, couleur);
        switch (etat) {
            case TOUCHE:
                PlayMusic(this.metal);
                infosTirs.setText("Vous avez touché un navire en " + c);
                statistiques.incrementerTirsReussis();
                break;
            case COULE:
                PlayMusic(this.boum);
                infosTirs.setText("Vous avez coulé un navire en " + c);
                statistiques.incrementerNaviresDetruits();
                break;
            case GAMEOVER:
                PlayMusic(this.filepath);
                infosTirs.setText("Vous avez gagné !!!");
                JOptionPane.showMessageDialog(grilleTirs, "Vous avez gagné!!!");
                statistiques.incrementerPartiesGagnees();
                statistiques.setPartiesJouees(statistiques.getPartiesPerdues()+statistiques.getPartiesGagnees());

            default:
                PlayMusic(this.drop);
                infosTirs.setText("Mince, le tir en " + c + " est raté");
                statistiques.incrementerTirsRates();
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
            System.out.println(e+"yo wtf");
        }
    }
}


