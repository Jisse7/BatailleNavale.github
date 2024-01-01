package batailleNavale;

public class JoueurAutoStrategieLvl1 extends JoueurAuto {
	boolean[][] casesPossibles; // une matrice qui indique les cases touchables (dont la valeur est true)
	protected GrilleNavale grille;

	public JoueurAutoStrategieLvl1(GrilleNavale g, String nom) {
		super(g, nom);
		this.grille=g;
		casesPossibles = new boolean[g.getTaille()][g.getTaille()];
		for (int i = 0; i < casesPossibles.length; i++)
			for (int j = 0; j < casesPossibles.length; j++)
				casesPossibles[i][j] = true;
	}

	public JoueurAutoStrategieLvl1(GrilleNavale g) {
		this(g,"NomJoueur");
	}

	@Override
	public Coordonnee choixAttaque() {
		int randomLigne, randomColonne;
		do {
			randomLigne = (int) (Math.random() * (this.getTailleGrille()));
			randomColonne = (int) (Math.random() * (this.getTailleGrille()));
		} while (!casesPossibles[randomLigne][randomColonne]);

		casesPossibles[randomLigne][randomColonne] = false;
		Coordonnee coordonneeAttaquee = new Coordonnee(randomLigne, randomColonne);
		return coordonneeAttaquee;
	}

}
