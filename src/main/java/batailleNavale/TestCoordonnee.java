package batailleNavale;

public abstract class TestCoordonnee {

	public static void testConstructeur1() {
		System.out.print("Test du constructeur Coordonne(int, int) --> ");
		int lig = (int) (Math.random() * 26);
		int col = (int) (Math.random() * 26);
		Coordonnee c = new Coordonnee(lig, col);
		if (c.getLigne() != lig || c.getColonne() != col)
			System.err.println("Initialisation erronée pour Coordonnee(" + lig + "," + col + ")");
		// levées d'exception
		try {
			lig = -1;
			c = new Coordonnee(lig, col);
			System.err.println("Exception non levée pour Coordonnee(" + lig + "," + col + ")");
		} catch (IllegalArgumentException e) {
		}
		try {
			lig = 26;
			c = new Coordonnee(lig, col);
			System.out.println("Exception non levée pour Coordonnee(" + lig + "," + col + ")");
		} catch (IllegalArgumentException e) {
		}
		try {
			lig = 0;
			col = -1;
			c = new Coordonnee(lig, col);
			System.err.println("Exception non levée pour Coordonnee(" + lig + "," + col + ")");
		} catch (IllegalArgumentException e) {
		}
		try {
			col = 26;
			c = new Coordonnee(lig, col);
			System.err.println("Exception non levée pour Coordonnee(" + lig + "," + col + ")");
		} catch (IllegalArgumentException e) {
		}
		System.out.println("Fin de test");
	}

	public static void testConstructeur2bis() {
		System.out.print("Test du constructeur Coordonne(String) --> ");

		for (int i = 0; i < 25; i++) {
			char col = (char) (i + 'A');
			int lig = i+1;

			String s = "" + col + lig;
			Coordonnee c = new Coordonnee(s);
			lig = i;

			if (c.getLigne() != lig || c.getColonne() != (col - 'A'))
				System.err.println("Initialisation erronée pour Coordonnee(" + lig + "," + col + ")");
			// levées d'exception
			try {
				lig = -1;
				c = new Coordonnee(lig, col);
				System.out.println("Exception non levée pour Coordonnee(" + lig + "," + col + ")");
			} catch (IllegalArgumentException e) {
			}
			try {
				lig = 26;
				c = new Coordonnee(lig, col);
				System.err.println("Exception non levée pour Coordonnee(" + lig + "," + col + ")");
			} catch (IllegalArgumentException e) {
			}
			try {
				lig = 0;
				col = 'ë';
				c = new Coordonnee(lig, col);
				System.err.println("Exception non levée pour Coordonnee(" + lig + "," + col + ")");
			} catch (IllegalArgumentException e) {
			}
			try {
				col = 26;
				c = new Coordonnee(lig, col);
				System.err.println("Exception non levée pour Coordonnee(" + lig + "," + col + ")");
			} catch (IllegalArgumentException e) {
			}

		}
		System.out.println("Fin de test");
	}

	public static void testMethodes() {

		int lig = (int) (Math.random() * 26);
		int col = (int) (Math.random() * 26);

		Coordonnee c = new Coordonnee(1,1);
		Coordonnee d = new Coordonnee(1,1);
		Coordonnee e = new Coordonnee(lig,col);
		Coordonnee f = new Coordonnee(lig,col);
		Coordonnee g = new Coordonnee(4,5);
		Coordonnee h = new Coordonnee(3,2);


		System.out.print("Test des accesseurs --> ");

		if( c.getColonne() != 1 )
			System.err.println("Problème avec getColonne");
		if( c.getColonne() == 3 )
			System.err.println("Problème avec getColonne");
		if( e.getColonne() != col )
			System.err.println("Problème avec getColonne");

		if( c.getLigne() != 1 )
			System.err.println("Problème avec getLigne");
		if( c.getLigne() == 3 )
			System.err.println("Problème avec getLigne");
		if( e.getLigne() != lig )
			System.err.println("Problème avec getColonne");

	System.out.println("Fin de test");


		System.out.print("Test de toString() --> ");

			Coordonnee i = new Coordonnee("B2");

			if( !i.toString().equals("B2") )
				System.err.println("Problème avec le toString");
			if( !c.toString().equals("B2") )
				System.err.println("Problème avec le toString");
			if( c.toString().equals("G5") )
				System.err.println("Problème avec le toString");

		System.out.println("Fin de test");

		System.out.print("Test de equals() --> ");

			if( !g.equals(new Coordonnee(4,5)) )
				System.err.println("Problème avec equals");
			if( !c.equals(d) )
				System.err.println("Problème avec equals");
			if( !e.equals(f) )
				System.err.println("Problème avec equals");
			if( !e.equals(e) )
				System.err.println("Problème avec equals");
			if( !e.equals(new Coordonnee(lig,col)) )
				System.err.println("Problème avec equals");
			if( c.equals(e) )
				System.err.println("Problème avec equals");
			if( f.equals(c) )
				System.err.println("Problème avec equals");

		System.out.println("Fin de test");


		System.out.print("Test de voisine() --> ");

			if( g.voisine(new Coordonnee(4,5)))
				System.err.println("Problème avec voisine, Coordonnées égales");
			if( g.voisine(new Coordonnee(7,6)))
				System.err.println("Problème avec voisine");
			if( g.voisine(new Coordonnee(4,3)))
				System.err.println("Problème avec voisine");
			if( g.voisine(new Coordonnee(5,4)))
				System.err.println("Problème avec voisine");
			if( g.voisine(new Coordonnee(3,4)))
				System.err.println("Problème avec voisine");
			if( g.voisine(new Coordonnee(3,6)))
				System.err.println("Problème avec voisine");
			if( g.voisine(new Coordonnee(5,6)))
				System.err.println("Problème avec voisine");
			if( !g.voisine(new Coordonnee(3,5)))
				System.err.println("Problème avec voisine");
			if( !g.voisine(new Coordonnee(4,4)))
				System.err.println("Problème avec voisine");
			if( !g.voisine(new Coordonnee(4,6)))
				System.err.println("Problème avec voisine");
			if( !g.voisine(new Coordonnee(5,5)))
				System.err.println("Problème avec voisine");

			try {
				if( f.voisine(new Coordonnee(lig,col)))
					System.err.println("Problème avec voisine, Coordonnées égales");
				if( f.voisine(new Coordonnee(lig+1,col+1)))
					System.err.println("Problème avec voisine");
				if( f.voisine(new Coordonnee(lig-1,col-1)))
					System.err.println("Problème avec voisine");
				if( f.voisine(new Coordonnee(lig-1,col+1)))
					System.err.println("Problème avec voisine");
				if( f.voisine(new Coordonnee(lig+1,col-1)))
					System.err.println("Problème avec voisine");
				if( !f.voisine(new Coordonnee(lig-1,col)))
					System.err.println("Problème avec voisine");
				if( !f.voisine(new Coordonnee(lig,col+1)))
					System.err.println("Problème avec voisine");
				if( !f.voisine(new Coordonnee(lig+1,col)))
					System.err.println("Problème avec voisine");
				if( !f.voisine(new Coordonnee(lig,col-1)))
					System.err.println("Problème avec voisine");

			} catch (IllegalArgumentException ex) {
			}

		System.out.println("Fin de test");

		System.out.print("Test de compareTo() --> ");

			if( h.compareTo(new Coordonnee(3,2))!= 0)
				System.err.println("Problème avec compareTo");
			if( h.compareTo(new Coordonnee(3,3)) != -1)
				System.err.println("Problème avec compareTo");
			if( h.compareTo(new Coordonnee(4,2)) != -1)
				System.err.println("Problème avec compareTo");
			if( h.compareTo(new Coordonnee(4,3)) > 0)
				System.err.println("Problème avec compareTo");
			if( h.compareTo(new Coordonnee(3,1)) != 1)
				System.err.println("Problème avec compareTo");
			if( h.compareTo(new Coordonnee(2,2)) != 1)
				System.err.println("Problème avec compareTo");
			if( h.compareTo(new Coordonnee(2,1)) < 0)
				System.err.println("Problème avec compareTo");

			try {
				if( e.compareTo(new Coordonnee(lig,col)) != 0)
					System.err.println("Problème avec compareTo");
				if( e.compareTo(new Coordonnee(lig+1,col)) > 0)
					System.err.println("Problème avec compareTo");
				if( e.compareTo(new Coordonnee(lig,col+1)) > 0)
					System.err.println("Problème avec compareTo");
				if( e.compareTo(new Coordonnee(lig+1,col+1)) > 0)
					System.err.println("Problème avec compareTo");
				if( e.compareTo(new Coordonnee(lig-1,col)) < 0)
					System.err.println("Problème avec compareTo");
				if( e.compareTo(new Coordonnee(lig,col-1)) < 0)
					System.err.println("Problème avec compareTo");
				if( e.compareTo(new Coordonnee(lig-1,col-1)) < 0)
					System.err.println("Problème avec compareTo");
			} catch (IllegalArgumentException ex) {
			}
		System.out.println("Fin de test");

	}

	public static void main(String[] args) {
		testConstructeur1();
		testConstructeur2bis();
		testMethodes();
	}

}
