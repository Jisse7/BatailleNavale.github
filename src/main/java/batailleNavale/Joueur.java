package batailleNavale;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public abstract class Joueur {
	public final static int TOUCHE = 1;
	public final static int COULE = 2;
	public final static int A_L_EAU = 3;
	public final static int GAMEOVER = 4;
	
	private Joueur adversaire;
	private int tailleGrille;
	private String nom;
	
	public Joueur(int tailleGrille, String nom) {
		if(tailleGrille < 1 || tailleGrille > 26)
			throw new IllegalArgumentException("La taille de la grille doit être comprise en 1 et 25");
		
		this.tailleGrille = tailleGrille;
		this.nom = nom;
	}
	
	public Joueur(int tailleGrille) {
		this(tailleGrille, "");
	}
	
	public int getTailleGrille() {
		return tailleGrille;
	}
	
	public String getNom() {
		return this.nom;
	}
	
	public void jouerAvec(Joueur j) {
		if(this.tailleGrille == j.getTailleGrille()) {
			this.adversaire = j;
			j.adversaire = this;
		}
		deroulementJeu(this, j);
	}
	
	private static void deroulementJeu(Joueur attaquant, Joueur defenseur) {
		int res = 0;
		
		while (res != GAMEOVER) {
			System.out.println("-- Tour de "+attaquant.getNom());
			if(attaquant instanceof JoueurTexte)
				((JoueurTexte) attaquant).montrerGrilleDefense();
		
			Coordonnee c = attaquant.choixAttaque();
			res = defenseur.defendre(c);
			attaquant.retourAttaque(c, res);
			defenseur.retourDefense(c, res);
			Joueur x = attaquant;
			attaquant = defenseur;
			defenseur = x;
		}
	}
        
        
        
       private static void deroulementJeuMultiReplayV2(Joueur att, Joueur def, File path) throws Exception {
    try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
        String lineTir;
        String lineReponse;
        int res = 0;

        while ((lineTir = reader.readLine()) != null && (lineReponse = reader.readLine()) != null) {
            String[] coordonneeTir = new String[2];
            coordonneeTir[0] = lineTir.substring(10, 11); // la coordonnee lettre
            coordonneeTir[1] = lineTir.substring(11); // la coordonnée chiffré
            String coordonneeTirString = coordonneeTir[0] + coordonneeTir[1];

            int resultatReponse = Integer.parseInt(lineReponse.substring(2)); //la réponse

            Coordonnee c = new Coordonnee(coordonneeTirString);
            res = resultatReponse;
            att.retourAttaque(c, res);
            def.retourDefense(c, res);
            Joueur x = att;
            att = def;
            def = x;

            if (res == GAMEOVER) break;
        }
    }
}


    public void jouerAvecMultiReplay(Joueur moi,File Path) throws Exception {
        System.out.println("jouerAvecMulti instancié");

        //	j.adversaire = this;
        deroulementJeuMultiReplayV2(this,moi, Path);
    }
	
	protected abstract void retourAttaque(Coordonnee c, int etat);
	protected abstract void retourDefense(Coordonnee c, int etat);
	public abstract Coordonnee choixAttaque();
	public abstract int defendre(Coordonnee c);

}
