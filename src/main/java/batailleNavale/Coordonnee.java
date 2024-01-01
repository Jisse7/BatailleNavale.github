package batailleNavale;

public class Coordonnee  implements Comparable<Coordonnee> {

	private int ligne;
	private int colonne;


	public Coordonnee(int ligne, int colonne) {
		//la verification si ligne est colonne sont entre 0 et 25
		if (ligne < 0 || colonne < 0 || ligne > 25 || colonne > 25 )
			throw new IllegalArgumentException("Le nombre de lignes et de colonnes ne peut pas être inférieur à 0 ou supérieur à 25");

		this.ligne=ligne;
		this.colonne=colonne;
	}

	public Coordonnee(String s) {

		if (s.length() > 3)
			throw new IllegalArgumentException("La coordonnée ne peut être que de 3 caractères max");

		char lettreCol=s.toUpperCase().charAt(0);//pour avoir le numero de ligne de C5
		//System.out.print(lettreCol);
		int numCol = lettreCol - 'A'; //ca retourne un int - subtracting the values char of lettreCol et A
		//System.out.println(numCol);
		if(numCol < 0 || numCol > 25)
			throw new IllegalArgumentException("La colonne ne peut être qu'une lettre de A à Z majuscule");


		if (! ((s.charAt(1)>0) && (s.charAt(1)>25)))
			throw new IllegalArgumentException("Numero de ligne incorrecte. La coordonnée doit etre ecrit dans le format [A-Z][1-26]");


		int numLigne=Integer.parseInt(s.substring(1))-1; //numero de la ligne
//		System.out.println(numLigne);


		if(numLigne < 0 || numLigne > 25)
			throw new IllegalArgumentException("Le nombre de lignes ne peut pas excéder 26 ou être inférieure a 1");

		this.colonne=numCol;
		this.ligne=numLigne;
//		System.out.println(this.colonne);
//		System.out.println(this.ligne);
	}

	@Override
	public String toString() {
		return ""+(char)('A'+this.colonne)+(this.ligne+1); //"" empty string pour que ca retourne le tout en string
	}
	public int getLigne() {
		return this.ligne;
	}
	public int getColonne() {
		return this.colonne;
	}

	@Override
	public boolean equals(Object obj) {
		Coordonnee c= (Coordonnee)obj;
		if (c.getLigne()==this.getLigne() && c.getColonne()==this.getColonne())
			return true;
		else
			return false;

	}

	public boolean voisine(Coordonnee c) {
		if (!this.equals(c))
			if (this.colonne==(c.colonne)  && ( this.ligne==(c.ligne+1) || this.ligne==(c.ligne-1)))
				return true;
			if (this.ligne==(c.ligne) && (this.colonne==(c.colonne+1) || this.colonne==(c.colonne-1)))
				return true;
		return false;
		//	return ((this.colonne==(c.colonne)  && ( this.ligne==(c.ligne+1) || this.ligne==(c.ligne-1)) ) || (this.ligne==(c.ligne) && (this.colonne==(c.colonne+1) || this.colonne==(c.colonne-1)) ) );
	}


	@Override
	public int compareTo(Coordonnee c) {
		int compareLigne=this.ligne-c.ligne;

		if (compareLigne!=0)//meme ligne, diff colonne
			return compareLigne;

		int compareCol=this.colonne-c.colonne;
		if (compareCol!=0)
			return compareCol;
		else
			return compareLigne; //va etre la meme position - 0
	}


}