package batailleNavale;

public class Navire {
	private Coordonnee debut; // Coordonnée de début du navire,
	private Coordonnee fin; // Coordonnée de fin du navire,
	private Coordonnee[] partiesTouchees; // Tableau qui stocke les coordonnées des parties touchées du navire. Taille = longueur du navire
	private int nbTouchees; // Nombres de parties touchées du navire

	// TODO Constructeur
	public Navire(Coordonnee debut, int longueur, boolean estVertical) {
		this.debut = debut;


		if(longueur < 1 || longueur > 25)
			throw new IllegalArgumentException("la longueur doit être >= 1 et <= 25");

		if( (estVertical && (this.debut.getLigne()+longueur) > 24) || (!estVertical && (this.debut.getColonne()+longueur) > 24) )
			throw new IllegalArgumentException("Le navire sort de la taille max de la grille");

		// Calculer la coordonnée de fin en fonction de la longueur
		if(estVertical)
			this.fin = new Coordonnee(debut.getLigne()+longueur-1, debut.getColonne());
		else
			this.fin = new Coordonnee(debut.getLigne(), debut.getColonne()+longueur-1);

		this.partiesTouchees = new Coordonnee[longueur]; // Taille = longueur du navire
		this.nbTouchees = 0;
	}

	//////////// TODO Accesseurs

	public Coordonnee getDebut() {
		return this.debut;
	}

	public Coordonnee getFin() {
		return this.fin;
	}

	public Coordonnee[] getPartiesTouchees() {
		return this.partiesTouchees;
	}


	//////////// TODO Méthodes public
	// toString : renvoie une chaîne sous la forme "Navire(B3, 3, vertical)" (début, longueur, vertical/horizontal)
	@Override
	public String toString() {
		if (this.estVertical())  // Vérifier si le navire est vertical
			return "Navire("+this.debut.toString()+","+(fin.getLigne()-debut.getLigne()+1)+", vertical)";
		else
			return "Navire("+this.debut.toString()+","+(fin.getColonne()-debut.getColonne()+1)+", horizontal)";
	}

	// Contient : retourne true si c est compris dans l'intervalle début - fin du navire.
	public boolean contient(Coordonnee c) {
		return (this.getDebut().getColonne() <= c.getColonne() && this.getFin().getColonne() >= c.getColonne() && this.getDebut().getLigne() <= c.getLigne() && this.getFin().getLigne() >= c.getLigne());
	}

	// Touche : Retourne true uniquement si le navire n touche le navire this (et pas de chevauchement)
	public boolean touche(Navire n) {
		// Si sur une ligne adjacente et sur même colonne & pas de chevauchement
		if(n.getFin().getLigne()+1 >= this.getDebut().getLigne() && this.getFin().getLigne()+1 >= n.getDebut().getLigne())
			if(n.getFin().getColonne() >= this.getDebut().getColonne() && this.getFin().getColonne() >= n.getDebut().getColonne())
				if ( !this.chevauche(n) )
					return true;

		// Si sur une colonne adjacente et sur même ligne & pas de chevauchement
		if(n.getFin().getColonne()+1 >= this.getDebut().getColonne() && this.getFin().getColonne()+1 >= n.getDebut().getColonne())
			if(n.getFin().getLigne() >= this.getDebut().getLigne() && this.getFin().getLigne() >= n.getDebut().getLigne())
				if ( !this.chevauche(n) )
					return true;

		return false;
	}

	// Chevauche : retourne true si this chevauche n
	public boolean chevauche(Navire n) {
		// Vérifier le chevauchement sur les lignes
		if(n.getFin().getLigne() >= this.getDebut().getLigne() && this.getFin().getLigne() >= n.getDebut().getLigne())
			if(n.getFin().getColonne() >= this.getDebut().getColonne() && this.getFin().getColonne() >= n.getDebut().getColonne()) 	// Vérifier le chevauchement sur les lignes
				return true;
		return false;
	}

	// Recoit tir : renvoitVrai si la coordonnée C est dans le navire this, et change les attributs
	public boolean recoitTir(Coordonnee c) {
		if (this.contient(c)) {
			if (!this.estTouche(c)) {
				nbTouchees+=1;
				for (int i = 0; i < this.partiesTouchees.length; i++) {
						if(this.partiesTouchees[i] == null) {
							this.partiesTouchees[i] = c;
							break;
						}
				}

			}
			return true; // Si on touche une partie déjà touchée on renvoie quand même true
		}
		return false;
	}

	// estTouche : renvoie true si le navire this a déjà été touché
	public boolean estTouche() {
		for (Coordonnee partiesTouchee : this.partiesTouchees) {
			if(partiesTouchee != null)
				return true;
		}
		return false;
	}

	// estTouche : renvoie true si le navire this a été touché a la coordonnée c
	public boolean estTouche(Coordonnee c) {
		for (Coordonnee partiesTouchee : this.partiesTouchees) {
			if(partiesTouchee != null && partiesTouchee.equals(c))
				return true;
		}
		return false;
	}

	public boolean estCoule() {
		return nbTouchees == this.partiesTouchees.length;
	}

	////////////TODO Méthodes private
	private boolean estVertical() {
		return (debut.getLigne() - fin.getLigne() != 0);
	}


}
