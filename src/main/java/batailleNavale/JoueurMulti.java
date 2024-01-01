package batailleNavale;

import javax.swing.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

import java.util.concurrent.CountDownLatch;

public abstract class JoueurMulti implements Serializable {

    private static final long serialVersionUID = 1L;
    public final static int TOUCHE = 1;
    public final static int COULE = 2;
    public final static int A_L_EAU = 3;
    public final static int GAMEOVER = 4;

    private JoueurMulti adversaire;
    private int tailleGrille;
    private String nom;
    private String nomJ2;

    public JoueurMulti(int tailleGrille, String nom) {
        if (tailleGrille < 1 || tailleGrille > 26) {
            throw new IllegalArgumentException("La taille de la grille doit être comprise en 1 et 25");
        }

        this.tailleGrille = tailleGrille;
        this.nom = nom;

    }

    public JoueurMulti(int tailleGrille) {
        this(tailleGrille, "");
    }

    public int getTailleGrille() {
        return tailleGrille;
    }

    public String getNom() {
        return this.nom;
    }

  public int getResThreaded(BufferedReader inPlsGod) throws Exception {
        System.out.println("getResThreaded en attente");
   
            String a = inPlsGod.readLine();
            if (a.length()==1) {
                int d = Integer.parseInt(a);
                // est necessairement de taille 1 : puisuqe la plage de valeur va de 0 à 4  etc. NORMALEMENT
                System.out.println("getResThreaded terminé");
                return d;
            }

      
        System.out.println("ALERTE PROBLEME L:58");
        return 0;
    }

    public String getCoordonneeThreaded(BufferedReader inPlsGod) throws IOException {

        System.out.println("getCoordonneeThreaded en attente");
        String a = inPlsGod.readLine();
        if (a.startsWith("tire en : ") || a.length() == 3 || a.length() == 2) {
            System.out.println("on est la");
            String c = a.substring(10);
            a = c;
        }
        return a;
        //   }
        // System.out.println("ALERTE PROBLEME L:100");

        // return "A10";
    }

    private static void deroulementJeuMultiV2(JoueurMulti moi, String nomJ2) throws Exception {

        try (Socket socketPls = new Socket("localhost", 4040)) {
            PrintWriter outPlsGod = new PrintWriter(socketPls.getOutputStream(), true);
            BufferedReader inPlsGod = new BufferedReader(new InputStreamReader(socketPls.getInputStream()));

            boolean attaque;

            if (moi.getNom().compareTo(nomJ2) < 0) {
                //selon l'ordre lexicographique des joueurs 
                System.out.println(moi.getNom() + " commence");
                attaque = true;
                System.out.println(attaque);
            } else {
                System.out.println(nomJ2 + " commence");
                attaque = false;
                System.out.println(attaque);
            }

            int res = 0;

            //   Coordonnee a;
            while (res != GAMEOVER) {

                if (attaque == true) {
                    System.out.println("Attaquant");
                    Coordonnee c = new Coordonnee(moi.choixAttaqueThreaded(outPlsGod));
                    //  Coordonnee c = new Coordonnee(inPlsGod.readLine());
                    System.out.println(c);

                    res = moi.getResThreaded(inPlsGod);
  
                    System.out.println("nouvelle valeur de : " + res);
                    //attend res : la réponse du Joueur2 et son protego
                    moi.retourAttaque(c, res);
                    attaque = false;

                } else {
                    System.out.println("Defenseur");


                        Coordonnee d = new Coordonnee(moi.getCoordonneeThreaded(inPlsGod));
                        System.out.println(d);
                        res = moi.protego(d, outPlsGod);
                        moi.retourDefense(d, res);

                        attaque = true;
                  

                }

            }
        }

    }

    public void jouerAvecMulti(String nomJ2) throws Exception {
        System.out.println("jouerAvecMulti instancié");

        this.nomJ2 = nomJ2;
        //	j.adversaire = this;

        deroulementJeuMultiV2(this, nomJ2);
    }

    private static void deroulementJeu(Joueur attaquant, Joueur defenseur) {
        int res = 0;

        while (res != GAMEOVER) {
            System.out.println("-- Tour de " + attaquant.getNom());
            //if(attaquant instanceof JoueurTexteMulti)
            //	((JoueurTexteMulti) attaquant).montrerGrilleDefense();

            Coordonnee c = attaquant.choixAttaque();
            res = defenseur.defendre(c);
            attaquant.retourAttaque(c, res);
            defenseur.retourDefense(c, res);
            Joueur x = attaquant;
            attaquant = defenseur;
            defenseur = x;

        }
    }

    protected abstract void retourAttaque(Coordonnee c, int etat);

    protected abstract void retourDefense(Coordonnee c, int etat);

    // public abstract Coordonnee choixAttaque(PrintWriter out);
    public abstract String choixAttaqueThreaded(PrintWriter out);

    public abstract int defendre(Coordonnee c);

    public abstract int protego(Coordonnee c, PrintWriter out);

}
