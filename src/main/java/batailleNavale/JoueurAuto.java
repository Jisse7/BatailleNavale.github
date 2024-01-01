package batailleNavale;

public class JoueurAuto extends JoueurAvecGrille {

	public JoueurAuto(GrilleNavale g, String nom) {
		super(g, nom);
	}

	public JoueurAuto(GrilleNavale g) {
		super(g);

	}

	@Override
	protected void retourAttaque(Coordonnee c, int etat) {
		String resultat = "";

		if (etat == Joueur.TOUCHE) {
			resultat = "Vous avez touché un navire ";
		} else if (etat == Joueur.COULE) {
			resultat = "Vous avez coulé un navire ";
		} else if (etat == Joueur.A_L_EAU) {
			resultat = "Dommage, c'est a l'eau";
		} else if (etat == Joueur.GAMEOVER) {
			resultat = "Vous avez gagné \\n ***tin tin tin tin tin tin tiinnnnnnn***";
		}

		System.out.println("Attaque en " + c + ": " + resultat);

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
		}

		System.out.println("Attaque de l'ennemi en " + c + ": " + resultat);
	}

	// TODO mettre une stratégie où on regarde si on a pas déjà tapé la coordonnée
	@Override
	public Coordonnee choixAttaque() {
		Coordonnee coordonneeAttaquee;
		int randomLigne = (int) (Math.random() * (this.getTailleGrille()));
		int randomColonne = (int) (Math.random() * (this.getTailleGrille()));
		coordonneeAttaquee = new Coordonnee(randomLigne, randomColonne);
		return coordonneeAttaquee;
	}

	///////////////////////

//	public static void main(String[] args) {
//		int[] longueursBateaux = {2,1};
//		GrilleNavale g = new GrilleNavale(8,longueursBateaux);
//		System.out.println(g.toString());
//
//		JoueurAuto att = new JoueurAuto(g,"Pedro");
//		JoueurAuto def = new JoueurAuto(g,"Martha");
//
//
//		deroulementJeu(att, def); // mettre deroulement jeu en protected pour test
//
//	}

}