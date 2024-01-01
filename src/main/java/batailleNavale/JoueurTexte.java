package batailleNavale;

import java.util.Scanner;

public class JoueurTexte extends JoueurAvecGrille {
	private Scanner sc;

	// Construteurs
	public JoueurTexte(GrilleNavale g, String nom) {
		super(g, nom);
		this.sc = new Scanner(System.in);
	}

	public JoueurTexte(GrilleNavale g) {
		super(g);
		this.sc = new Scanner(System.in);
	}

	// Methodes héritées
	@Override
	protected void retourAttaque(Coordonnee c, int etat) {
		switch(etat){
			case Joueur.TOUCHE :
				System.out.println("Bam !! Vous avez touché un navire en "+c.toString()+" !");
				break;
			case Joueur.COULE :
				System.out.println("Vous avez coulé un navire en "+c.toString()+" !");
				break;
			case Joueur.A_L_EAU :
				System.out.println("Vous avez raté votre tir en "+c.toString()+"...");
				break;
			case Joueur.GAMEOVER :
				System.out.println("YEAH ! Vous avez gagné le jeu ! (ﾉ◕ヮ◕)ﾉ*:･ﾟ✧");
				break;
			case 5 :
				System.out.println("Vous avez déjà tapé dans cette case, dommage !");
				break;
		}

	}

	@Override
	protected void retourDefense(Coordonnee c, int etat) {
		switch(etat){
		case Joueur.TOUCHE :
			System.out.println("Arg ! On vous a touché en "+c.toString()+" :(");
			break;
		case Joueur.COULE :
			System.out.println("Noooon !! On a coulé votre navire en "+c.toString()+" *mélodiedeviolion*");
			break;
		case Joueur.A_L_EAU  :
			System.out.println("Ouf, Le tir de votre adversaire en "+c.toString()+" est dans l'eau   !");
			break;
		case Joueur.GAMEOVER :
			System.out.println("Oh non ! Vous avez perdu le jeu... ಥ_ಥ");
			break;
		case 5 :
			System.out.println("Vous avez déjà tapé dans cette case, dommage !");
			break;
	}

	}


	// TODO est ce qu'on doit s'occuper de vérifier si la coordonnée fait partie de la grille
	@Override
	public Coordonnee choixAttaque() {
		System.out.println("Choissisez une coordonnée à attaquer (ex : B2) :");
		String Coord = this.sc.next();

		Coordonnee c;
		try {
			c = new Coordonnee(Coord);

			while( !super.grille.estDansGrille(c) ) {
				System.out.println("La coordonnée n'est pas dans la grille, veuillez en choissir une autre :");
				Coord = this.sc.next();
				c = new Coordonnee(Coord);
			}

			return c;
		} catch (IllegalArgumentException e) {
			System.out.println("La coordonnée n'est pas correcte ou pas dans la grille");
			c = choixAttaque();
			return c;
		}

	}


	public void montrerGrilleDefense() {
		System.out.println(	"--- Vos Navires ---\n"+super.grille.toString());
	}

//	///////////////////////
//
//	public static void main(String[] args) {
//		int[] longueursBateaux = {2,1};
//		GrilleNavale g = new GrilleNavale(8,longueursBateaux);
//		System.out.println(g.toString());
//
//		JoueurTexte att = new JoueurTexte(g,"Pedro");
//		JoueurTexte def = new JoueurTexte(g,"Martha");
//
//
//		deroulementJeu(att, def); // mettre deroulement jeu en protected pour test
//
//	}

}
