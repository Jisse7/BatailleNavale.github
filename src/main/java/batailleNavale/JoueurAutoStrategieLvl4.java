package batailleNavale;

public class JoueurAutoStrategieLvl4 extends JoueurAuto {
	boolean[][] casesPossibles; // une matrice qui indique les cases touchables (dont la valeur est true par defaut)
	protected GrilleNavale grille;
	private boolean regarderVoisine = false; // true si on a pas encore coulé le navire en cour
	private Coordonnee derniereCaseTouchee; // derniere case de bateau touchee
	private Coordonnee[] bateauEnCours;	// tableau de coordonnee du bateau en cour, remis à nul à chaque fois que on coule un bateau
	private int direction = 0;	// direction du bateau : 0 si inconnue, 1 si horizontal, 2 si vertical


	public JoueurAutoStrategieLvl4(GrilleNavale g, String nom) {
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

	public JoueurAutoStrategieLvl4(GrilleNavale g) {
		this(g,"NomJoueur");
	}

	@Override
	public Coordonnee choixAttaque() {
		int Ligne = 0, Colonne = 0;

		// Si on a une coordonne voisine a attaquer
		 if (regarderVoisine) {

			 	Ligne = derniereCaseTouchee.getLigne();
				Colonne = derniereCaseTouchee.getColonne();

				Ligne = chercherCaseATaper(Ligne, Colonne)[0];
				Colonne = chercherCaseATaper(Ligne, Colonne)[1];

				// La nouvelle ligne ou la nouvelle colonne est la même que la derniere case touchee : on a pas trouvé de cases
				if (Ligne == derniereCaseTouchee.getLigne() && Colonne == derniereCaseTouchee.getColonne()) {
					derniereCaseTouchee = bateauEnCours[0]; // on repart à la Première coordonnée

					Ligne = chercherCaseATaper(Ligne, Colonne)[0];
					Colonne = chercherCaseATaper(Ligne, Colonne)[1];
				}

		}

		 // si on a pas de coordonnée voisine à attaquer : au hasard
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

	private int[] chercherCaseATaper(int Ligne, int Colonne) {


		if(direction == 0 || direction == 1) {
			if (Colonne+1 < casesPossibles.length && casesPossibles[Ligne][Colonne+1])
				Colonne += 1;
			else if (Colonne-1 >= 0 && casesPossibles[Ligne][Colonne-1])
				Colonne -= 1;
			else
				direction = 2;
		}

		if (direction == 2) {
			if (Ligne+1 < casesPossibles.length && casesPossibles[Ligne+1][Colonne])
				Ligne += 1;
			else if (Ligne-1 >= 0 && casesPossibles[Ligne-1][Colonne])
				Ligne -= 1;
		}

		return new int[] {Ligne,Colonne};
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



		 if(bateauEnCours[0] != null && bateauEnCours[1] != null) {
			 if ( bateauEnCours[0].getLigne() == bateauEnCours[1].getLigne() )
				 direction = 1;
			 else
				 direction = 2;
		 } else {
			 direction = 0;
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
