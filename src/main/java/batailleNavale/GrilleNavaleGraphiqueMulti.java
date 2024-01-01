package batailleNavale;

import java.awt.Color;

public class GrilleNavaleGraphiqueMulti extends GrilleNavale {
	private GrilleGraphiqueMulti grille;

	public GrilleNavaleGraphiqueMulti(int taille) {
		//si la taille de grille<5 on pourra avoir que 2 navire possible sur la grille
		//si la taille >=5 le nombre de navire sera egale a (taille/5)+2)
		//on prend 5 car: 1< nombre navire <=5
		// ((taille / 5) == 0 ? 2 : taille / 5 + 2)
		super(taille, taille);
		grille = new GrilleGraphiqueMulti(taille);
		this.grille.colorie(new Coordonnee(0,0),new Coordonnee(taille-1,taille-1), BatailleNavale.bgColor);
		grille.setClicActive(false);
	}


	// TODO Faire ce constructeur
//	public GrilleNavaleGraphique(int taille, int[] tailleNavire) {
//		//si la taille de grille<5 on pourra avoir que 2 navire possible sur la grille
//		//si la taille >=5 le nombre de navire sera egale a (taille/5)+2)
//		//on prend 5 car: 1< nombre navire <=5
//				super(taille, tailleNavire);
//				grille = new GrilleGraphique(taille);
//				this.grille.colorie(new Coordonnee(0,0),new Coordonnee(taille-1,taille-1), new Color(220,220,220));
//				grille.setClicActive(false);
//
//			}

	public GrilleGraphiqueMulti getGrilleGraphique() {
		return this.grille;
	}

	public void colorierTouteGrille(Color c) {
		this.grille.colorie(new Coordonnee(0,0),new Coordonnee(this.getTaille()-1,this.getTaille()-1), c);
	}



// voir la méthode colorie dans GrilleGraphique
	@Override
	public boolean ajouteNavire(Navire n) {
		if (super.ajouteNavire(n)) {
			this.grille.colorie(n.getDebut(), n.getFin(), BatailleNavale.navire);
			return true;
		}
		return false;
	}

//voir la méthode colorie dans GrilleGraphique
	@Override
	public boolean recoitTir(Coordonnee c) {
		if (super.recoitTir(c)) {
			this.grille.colorie(c, BatailleNavale.touche);
			return true;
		} else {

			if (this.estTouche(c) || super.estCoule(c)) { // super.estTouche(c) || super.estCoule(c) //super.recoitTir(c)
				this.grille.colorie(c, BatailleNavale.touche);
				return true;
			}

			this.grille.colorie(c, BatailleNavale.aLeau);
			return false;
		}
		//}
	}
}
