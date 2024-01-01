package batailleNavale;

import java.io.Serializable;


public abstract class JoueurAvecGrilleMulti extends JoueurMulti implements Serializable{
	protected GrilleNavale grille;



	public JoueurAvecGrilleMulti(GrilleNavale g, String nom) {

		super(g.getTaille(), nom);
                System.out.println("JAGM instancié " +nom);
		this.grille = g;
	}
	public JoueurAvecGrilleMulti(GrilleNavale g) {

		super(g.getTaille());
                System.out.println("JAGM instancié pour" );
		this.grille = g;
	}

	@Override
	public int defendre(Coordonnee c) {
            System.out.println(c);
            System.out.println("je suis utilisé ici dans JAGM");
		grille.recoitTir(c);

		if(grille.perdu()){

			return JoueurMulti.GAMEOVER;

                }
		if (grille.estCoule(c)){

			return JoueurMulti.COULE;
                }
		if (grille.estTouche(c)){

                	return JoueurMulti.TOUCHE;
                }
		if (grille.estALEau(c)){

			return JoueurMulti.A_L_EAU;
                }


		return JoueurMulti.A_L_EAU;
	}






}
