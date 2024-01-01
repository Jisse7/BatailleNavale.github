package batailleNavale;

class GrilleNavale {
	private Navire[] navires;
	private int nbNavires;
	private int taille;
	private Coordonnee[] tirsRecus;
	private int nbTirsRecus;

	public GrilleNavale(int taille, int[] taillesNavires) {

		int max = taillesNavires[0];
		for (int i = 1; i < taillesNavires.length; i++) {

			if (taillesNavires[i] > taillesNavires[i - 1])
				max = taillesNavires[i];
		}

		if (taille > 26 || taille < 2)
			throw new IllegalArgumentException("La taille de la grille depasse limite (2 < taille <= 26)");
		else if (taille < max)
			throw new IllegalArgumentException( "La taille de grille doit être supérieure ou égale à la taille maximale des tailles de navires");
		else
			this.taille = taille;

		boolean TN = true;
		for (int i = 0; i < taillesNavires.length; i++) {
			if ((taillesNavires[i] < 1) && (taillesNavires[i] > 5))
				TN = false;
		}
		if (TN == false) {
			throw new IllegalArgumentException("La taille d'un navire doit être entre 1 et 5");
		}

		 nbNavires = 0;
		 navires = new Navire[taillesNavires.length];
		 nbTirsRecus = 0;
		 tirsRecus = new Coordonnee[taille * taille];
		 placementAuto(taillesNavires);
	}

	public GrilleNavale(int taille, int nbNavires) {
		if (taille > 26 || taille <= 0)
			throw new IllegalArgumentException("la taille depasse les limite: 0 < taille < 26");
		this.taille = taille;
		if (nbNavires <= 0)
			throw new IllegalArgumentException("il faut plus de 0 navire");
		this.nbNavires = 0;

		nbTirsRecus = 0;
		navires = new Navire[nbNavires];
		tirsRecus = new Coordonnee[taille * taille];
	}

	public GrilleNavale(int taille) {
		this(taille, taille/2);
	}
	
	public GrilleNavale() {
		this(10, new int[] {1,2,2,3,4} );
	}
	
	public int getTaille() {
		return taille;
	}
	
	public String toString() {
		StringBuffer grilleBf = new StringBuffer();
		
		/////////// Créer la grille de base
		
		// Mettre la première ligne de lettres
		grilleBf.append("  "); // espace vide pour alignement
		for(int i = 0; i < taille; i++) 
			grilleBf.append(""+(char) ('A'+ i)+"");
		
		// Ajouter les autres lignes 
		int countLigne = 1; // counteur 
		for(int i = 0; i < taille; i++) {
			grilleBf.append("\n");
			
			// Ajouter un espace avant les nombres à 1 chiffre pour aligner, et mettre le n° de ligne
			if(countLigne < 10) 
				grilleBf.append(" "+countLigne);
			else 
				grilleBf.append(countLigne);
			
			// Ajouter les points
			for(int j = 0; j < taille; j++) {
				grilleBf.append(".");
			}
			countLigne +=1;
		}
		
		int nextLigne = taille+3; // taille + (saut de ligne + entete de ligne de 2 caractères) 
		
		///// Ajouter les tirs effectués
			for (int i = 0; i < this.tirsRecus.length; i++) {
				if(this.tirsRecus[i] != null) {
					int caseTir = nextLigne+ this.tirsRecus[i].getColonne()+2 + (nextLigne * this.tirsRecus[i].getLigne());// recalculer la case en évitant les entêtes,espaces et retour ligne	
					grilleBf.setCharAt(caseTir, 'o');
				} else {
					break;
				}
			}
		
		////// Ajouter les navires en fonction de la grille de Navire
		for (int i = 0; i < this.navires.length; i++) {
			if(this.navires[i] != null) {
				int ld = this.navires[i].getDebut().getLigne();	// ld = ligne debut
				int cd = this.navires[i].getDebut().getColonne()+2;	// cd = colonne debut, +2 pour entete + démarre a 0
								
				for (int u = ld; u <= this.navires[i].getFin().getLigne(); u++) {
					for (int v = cd; v <= this.navires[i].getFin().getColonne()+2; v++) {
						int caseNav = nextLigne+ v + (nextLigne * u);// recalculer la case en évitant les entêtes,espaces et retour ligne	

						if(this.navires[i].estTouche(new Coordonnee(u,(v-2))))
							grilleBf.setCharAt(caseNav, 'x');
						else
							grilleBf.setCharAt(caseNav, '#');
					}
				}
				
			}
		}
			
		String grille = grilleBf+"";
		return grille;	
	}
	
	public void placementAuto(int[] taillesNavires) {
		taillesNavires = ajusterTailleNavires(taillesNavires);		
		// parcourir la taille navire pour placer aléatoirement
		for (int i = 0; i < taillesNavires.length; i++) {	
			Navire n = randomNavire(taillesNavires[i]);
			while (!this.ajouteNavire(n)) 
				n = randomNavire(taillesNavires[i]);
			
		}
	}
	
	public Navire randomNavire(int taille) {
		int Rlig = (int) (Math.random() * this.taille)-1;
		int Rcol = (int) (Math.random() * this.taille)-1;
		boolean estVert = (Math.random() < 0.5) ? true : false; 
		
		Navire n;
		try {
			n = new Navire(new Coordonnee(Rlig,Rcol),taille,estVert);
		} catch (Exception e) {
			n = randomNavire(taille);
		}
		return n;
	}
	
	private int[] ajusterTailleNavires(int[] taillesNavires) {
		int[] NewTaillesNavires = new int[taillesNavires.length];
		// vérifier que on ne tombe pas sur une longueur de 0, si oui raccourcir le tableau
		for (int i = 0; i < taillesNavires.length; i++) {
			if ( taillesNavires[i] == 0) {
				this.nbNavires = i;
				NewTaillesNavires = new int[i];
				for (int j = 0; j < NewTaillesNavires.length; j++) {
					NewTaillesNavires[j] = taillesNavires[j];
				}
				taillesNavires = NewTaillesNavires;
			}
		}
		return NewTaillesNavires;
	}
	
	private boolean estDansTirsRecus(Coordonnee c) {
		for (int i = 0; i < this.tirsRecus.length; i++) {
			if(this.tirsRecus[i] != null && this.tirsRecus[i].equals(c))
				return true;
		}
		return false;
	}

	private boolean ajouteDansTirsRecus(Coordonnee c) {
		for (int i = 0; i < this.tirsRecus.length; i++) {		
			if (!this.estDansGrille(c))
				return false;
			if (this.tirsRecus[i] != null && this.estDansTirsRecus(c)) // si la coordonnee est dans tir recus
				return false;
			if( this.tirsRecus[i] == null) { // la coordonnée n'est pas encore dans tirs reçu
				this.tirsRecus[i] = c;
				this.nbTirsRecus += 1;
				return true;
			}
		}
		return false;
	}


	public boolean recoitTir(Coordonnee c) {
		if (ajouteDansTirsRecus(c)) {
			// tir ne figure pas dans tirsRecus[] et un des navires contient la coordonnee c
			for (int i = 0; i < navires.length; i++) {
				if(navires[i] != null)
					if(navires[i].recoitTir(c))// un des navire est touché par le tir
						return true;
			}
		}
		return false;
	}
	

	public boolean ajouteNavire(Navire n) {	
		// vérifier que le nombre de navire max n'ai pas été atteint (le dernier emplacement n'est pas null)
		if (this.navires[this.navires.length-1] != null) {
			Navire[] NewNavires = new Navire[this.navires.length+1];
			for (int i = 0; i < this.navires.length; i++) {
				NewNavires[i] = this.navires[i];
			}
			this.navires = NewNavires;
		}
		
		// Vérifier que chaque navire n'est pas touché ni chevauché par n
		for (int i = 0; i < this.navires.length; i++) {
			if(this.navires[i] != null && (this.navires[i].chevauche(n) || this.navires[i].touche(n)))
				return false;
		}
		
		if( n.getFin().getColonne() >= taille || n.getFin().getLigne() >= taille)
			return false;
		
		// Ajouter le navire dans la liste de navires
		for (int i = 0; i < this.navires.length; i++) {
			if(this.navires[i] == null) {// ajouter à un emplacement vide
				this.navires[i] = n;
				this.nbNavires += 1;
				break;
			}
		}
		return true;
	}
	
	public boolean estDansGrille(Coordonnee c) {
		return ((c.getLigne()) >= 0 && (c.getColonne()) >= 0 && (c.getLigne()) < this.taille && (c.getColonne()) < this.taille);
	}
	
	public boolean estTouche(Coordonnee c) {
		boolean var = false;
		for (int i = 0; i < this.navires.length; i++) {
			if (this.navires[i] != null && this.navires[i].contient(c) && estDansTirsRecus(c)) {
				var = true;
			}
		}
		return var;
	}
	
	public boolean estCoule(Coordonnee c) {
		boolean couler = false;
		for (int i = 0; i < this.navires.length; i++) {
			if (this.navires[i] != null && this.navires[i].estCoule() && this.navires[i].estTouche(c)) {
				couler = true;
				break;
			}
		}
		return couler;
	}


	public boolean estALEau(Coordonnee c) {
		return (estDansTirsRecus(c) && !estTouche(c));
	}
	
	public boolean perdu() {
		boolean perdre = true;
		for (int i = 0; i < navires.length; i++) {
			if (navires[i] != null && !navires[i].estCoule()) {
				perdre = false;
				break;
			} else {
				perdre = true;
			}
		}
		return perdre;
	}
	
//	
//	public boolean perdu() {
//		boolean perdre = true;
//		for (int i = 0; i < nbNavires; i++) {
//			if (!navires[i].estCoule()) {
//				perdre = false;
//				break;
//			}
//		}
//		return perdre;
//	}
}