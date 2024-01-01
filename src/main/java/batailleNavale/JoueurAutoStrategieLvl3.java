package batailleNavale;

public class JoueurAutoStrategieLvl3 extends JoueurAuto {
	boolean[][] casesPossibles; // une matrice qui indique les cases touchables (dont la valeur est true)
	protected GrilleNavale grille;
	private boolean regarderVoisine = false;
	private Coordonnee derniereCaseTouchee;
	private Coordonnee[] bateauEnCours;

	public JoueurAutoStrategieLvl3(GrilleNavale g, String nom) {
		super(g, nom);
		this.grille=g;
		casesPossibles = new boolean[g.getTaille()][g.getTaille()];
		for (int i = 0; i < casesPossibles.length; i++)
			for (int j = 0; j < casesPossibles.length; j++)
				casesPossibles[i][j] = true;

		bateauEnCours = new Coordonnee[8];
		for (int j = 0; j < bateauEnCours.length; j++)
			bateauEnCours[j] = null;
	}

	public JoueurAutoStrategieLvl3(GrilleNavale g) {
		this(g,"NomJoueur");
	}

	@Override
	public Coordonnee choixAttaque() {
		int Ligne = 0, Colonne = 0;

		// Si il n'y a pas de coordonne voisine a attaquer
		 if (regarderVoisine) {

				//System.out.println("Dernieree case : "+derniereCaseTouchee);
				Ligne = derniereCaseTouchee.getLigne();
				Colonne = derniereCaseTouchee.getColonne();

				if (Ligne+1 < casesPossibles.length && casesPossibles[Ligne+1][Colonne])
					Ligne += 1;
				else if (Ligne-1 >= 0 && casesPossibles[Ligne-1][Colonne])
					Ligne -= 1;
				else if (Colonne+1 < casesPossibles.length && casesPossibles[Ligne][Colonne+1])
					Colonne += 1;
				else if (Colonne-1 >= 0 && casesPossibles[Ligne][Colonne-1])
					Colonne -= 1;

				System.out.println(Ligne+" "+Colonne);

				if (Ligne == derniereCaseTouchee.getLigne() && Colonne == derniereCaseTouchee.getColonne()) {
					derniereCaseTouchee = bateauEnCours[0];

					Ligne = derniereCaseTouchee.getLigne();
					Colonne = derniereCaseTouchee.getColonne();

					if (Ligne+1 < casesPossibles.length && casesPossibles[Ligne+1][Colonne])
						Ligne += 1;
					else if (Ligne-1 >= 0 && casesPossibles[Ligne-1][Colonne])
						Ligne -= 1;
					else if (Colonne+1 < casesPossibles.length && casesPossibles[Ligne][Colonne+1])
						Colonne += 1;
					else if (Colonne-1 >= 0 && casesPossibles[Ligne][Colonne-1])
						Colonne -= 1;
				}



		}

		 if (!regarderVoisine) {
			do {
				Ligne = (int) (Math.random() * (this.getTailleGrille()));
				Colonne = (int) (Math.random() * (this.getTailleGrille()));
			} while (!casesPossibles[Ligne][Colonne]);
		 }

		casesPossibles[Ligne][Colonne] = false;
		Coordonnee coordonneeAttaquee = new Coordonnee(Ligne, Colonne);
		return coordonneeAttaquee;
	}

	@Override
	protected void retourAttaque(Coordonnee c, int etat) {
		String resultat = "";

		if (etat == Joueur.TOUCHE) {
			resultat = "Vous avez touché un navire ";
			regarderVoisine = true;
			ajouterDansBateauEnCours(c);
			derniereCaseTouchee = c;
		} else if (etat == Joueur.COULE) {
			resultat = "Vous avez coulé un navire ";
			regarderVoisine = false;
			ajouterDansBateauEnCours(c);
			setVoisineBateauFalse();
			viderBateauEnCours();
		} else if (etat == Joueur.A_L_EAU) {
			resultat = "Dommage, c'est a l'eau";
		} else if (etat == Joueur.GAMEOVER) {
			resultat = "Vous avez gagné \\n ***tin tin tin tin tin tin tiinnnnnnn***";
			System.out.println("Attaque en " + c + ": " + resultat);
		}

		//System.out.println("Attaque en " + c + ": " + resultat);
	}

	@Override
	protected void retourDefense(Coordonnee c, int etat) {
		String resultat = "";

		if (etat == Joueur.TOUCHE) {
			resultat = "Le navire a été touché";
		} else if (etat == Joueur.COULE) {
			resultat = "Le navire a coulé";
		} else if (etat == Joueur.A_L_EAU) {
			resultat = "C'est à l'eau !";
		} else if (etat == Joueur.GAMEOVER) {
			resultat = "Vous avez perdu \\n **** bruit de violon ***";
			System.out.println("Attaque de l'ennemi en " + c + ": " + resultat);
		}

		//System.out.println("Attaque de l'ennemi en " + c + ": " + resultat);
	}

	private void setVoisineBateauFalse() {
		for (Coordonnee bateauEnCour : bateauEnCours) {
			if (bateauEnCour != null) {
				setVoisineFalse(bateauEnCour);
			}
		}
	}

	private void setVoisineFalse(Coordonnee c) {
		int ligne = c.getLigne();
		int colonne = c.getColonne();

		setFalse(ligne-1,colonne);
		setFalse(ligne,colonne+1);
		setFalse(ligne+1,colonne);
		setFalse(ligne,colonne-1);
	}

	private void viderBateauEnCours() {
		for(int i = 0; i < bateauEnCours.length; i++)
			bateauEnCours[i] = null;
	}

	private void ajouterDansBateauEnCours(Coordonnee c) {
		for(int i = 0; i < bateauEnCours.length; i++)
			if (bateauEnCours[i] == null) {
				bateauEnCours[i] = c;
				break;
			}
	}

	private void setFalse(int l, int c) {
		if (l < grille.getTaille() &&  c < grille.getTaille() && l >= 0 && c >= 0)
			casesPossibles[l][c] = false;
	}
}
