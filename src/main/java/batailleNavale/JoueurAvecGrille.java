package batailleNavale;

public abstract class JoueurAvecGrille extends Joueur {
	protected GrilleNavale grille;

	public JoueurAvecGrille(GrilleNavale g, String nom) {
		super(g.getTaille(), nom);
		this.grille = g;
	}
	public JoueurAvecGrille(GrilleNavale g) {
		super(g.getTaille());
		this.grille = g;
	}

	@Override
	public int defendre(Coordonnee c) {
		grille.recoitTir(c);

		if(grille.perdu())
			return Joueur.GAMEOVER;

		if (grille.estCoule(c))
			return Joueur.COULE;

		if (grille.estTouche(c))
			return Joueur.TOUCHE;

		if (grille.estALEau(c))
			return Joueur.A_L_EAU;


		return Joueur.A_L_EAU;
	}






}
