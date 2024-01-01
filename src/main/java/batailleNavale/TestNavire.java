package batailleNavale;

public abstract class TestNavire {

	public static void main(String[] args) {

		Navire N = new Navire(new Coordonnee("B2"), 3, false);
		Navire M = new Navire(new Coordonnee("B3"), 2, true);



		System.out.println(N);
		System.out.println(M);

		System.out.print("Test du constructeur  --> ");
			try {
				Navire Np = new Navire(new Coordonnee("é33"),3,true);
				System.err.println("Exception non levée quand coordonnée incorrecte");
			} catch(IllegalArgumentException ex) {
			}
			try {
				Navire Np = new Navire(new Coordonnee("A3"),0,true);
				System.err.println("Exception non levée quand longeur = 0");
			} catch(IllegalArgumentException ex) {
			}

			try {
				Navire Np = new Navire(new Coordonnee("A0"),26,true);
				System.err.println("Exception non levée quand longeur > 25");
			} catch(IllegalArgumentException ex) {
			}

			try {
				Navire Np = new Navire(new Coordonnee("A23"),5,true);
				System.err.println("Exception non levée quand debut + longeur > 25");
			} catch(IllegalArgumentException ex) {
			}
		System.out.println("Fin de test");

		System.out.print("Test des accesseurs --> ");
		//INDICES colonne commencent par 0
		//INDICES lignes commencent par 0

		System.out.println("Pour "+N+" : Col : "+N.getDebut().getColonne()+" + Ligne : "+N.getDebut().getLigne());
		//
			if (N.getDebut().getColonne()!=1)
				System.err.println("pb avec get getDebut > Colonne");
			if (N.getDebut().getLigne()!=1)
				System.err.println("pb avec get getDebut > Ligne");
			if (M.getDebut().getColonne()!=1)
				System.err.println("pb avec get getDebut > Ligne");
			if (M.getDebut().getLigne()!=2)
				System.err.println("pb avec get getDebut > Colonne");

		System.out.println("Fin de test accesseurs");




		System.out.print("Test de ToString --> ");
		//output sous le format "Navire(B1,4, horizontal)"
			String nav=(M.toString().substring(0, 7));
			String debut=(M.toString().substring(7,9));
			String longeur=(M.toString().substring(9,13)); //on verifie aussi les vrigules+espaces
			String pos=(M.toString().substring(13,M.toString().length()-1));
			if (! nav.equals("Navire("))
				System.err.println("pb avec le output 'Navire'");
			if (! debut.equals(M.getDebut().toString()))
				System.err.println("pb avec la position de debut'");
			if (! longeur.equals(",2, ")) //condition peut etre changer pour utiliser getdebut/getfin
				System.err.println("pb avec la longeur de bateau '");
			if (! (pos.equals("horizontal") || pos.equals("vertical")))//faire la verification avec estVertical?
				System.err.println("pb avec vertical/horizontal'");

		System.out.println("Fin de test de ToString");




		System.out.print("Test de contient() --> ");
		Navire k = new Navire(new Coordonnee("A3"),4,true);

			if (!k.contient(new Coordonnee("A3"))) {
				System.err.println("pb ne contient pas A3");
			}
			if (!k.contient(new Coordonnee("A4"))) {
				System.err.println("pb ne contient pas  A4");
			}
			if (!k.contient(new Coordonnee("A5"))) {
				System.err.println("pb ne contient pas  A5");
			}
			if (!k.contient(new Coordonnee("A6"))) {
				System.err.println("pb ne contient pas  A6");
			}

			if (k.contient(new Coordonnee("A2"))) {
				System.err.println("pb contient A2");
			}
			if (k.contient(new Coordonnee("A7"))) {
				System.err.println("pb contient A7");
			}
			if (k.contient(new Coordonnee("B4"))) {
				System.err.println("pb contient B4");
			}

		System.out.println("Fin de test contient");




		System.out.print("Test de touche --> ");
			Navire P = new Navire(new Coordonnee("F2"), 2, false);


			if (! M.touche(N))
				System.err.print("Pb avec touche ");
			if (P.touche(N))
				System.err.print("Pb avec touche");

		System.out.println("Fin de test de touche");


		System.out.print("Test de chevauche --> ");
			Navire Q = new Navire(new Coordonnee("C2"), 2, false);

			if (M.chevauche(N))
				System.err.print("Pb avec chevauche ");
			if (! Q.chevauche(N))
				System.err.print("Pb avec chevauche");
		System.out.println("Fin de test de chevauche");


		System.out.print("Test de recoitTir --> ");
		Coordonnee C = new Coordonnee("B2");
		Coordonnee D = new Coordonnee("D2");

		if (!N.recoitTir(C))
			System.err.print("Pb avec recoit tir");
		if (M.recoitTir(C))
			System.err.print("Pb avec recoit tir");
		if (P.recoitTir(C))
			System.err.print("Pb avec recoit tir");
		if (!Q.recoitTir(D))
			System.err.print("Pb avec recoit tir");

		System.out.println("Fin de test de recoitTir");




		System.out.print("Test de esTouche() --> ");

		Navire NavIntacte =  new Navire(new Coordonnee("J12"), 2, false);


		if (NavIntacte.estTouche())
			System.err.print("Pb avec estTouche()");

		if (M.estTouche())
			System.err.print("Pb avec estTouche()");
		if (!N.estTouche())
			System.err.print("Pb avec estTouche()");
		if (P.estTouche())
			System.err.print("Pb avec estTouche()");
		if (!Q.estTouche())
			System.err.print("Pb avec estTouche()");
		System.out.println("Fin de test de esTouche()");





		System.out.print("Test de test esTouche(avec cc) --> ");
		//Etant donne que les seuls navire touches sont N ET Q avec C les coordonees C et D respectivement, les 2 premiers vont retourner true.
		//les autres vont retourner false.


		if (! Q.estTouche(D))
			System.err.print("Pb avec estTouche(c)");
		if (! N.estTouche(C))
			System.err.print("Pb avec estTouche(c)");

		if (NavIntacte.estTouche(C))
			System.err.print("Pb avec estTouche(c)");
		if (NavIntacte.estTouche(D))
			System.err.print("Pb avec estTouche(c)");

		if (M.estTouche(C))
			System.err.print("Pb avec estTouche(c)");
		if (M.estTouche(D))
			System.err.print("Pb avec estTouche(c)");

		if (N.estTouche(D))
			System.err.print("Pb avec estTouche(c)");

		if (P.estTouche(C))
			System.err.print("Pb avec estTouche(c)");
		if (P.estTouche(D))
			System.err.print("Pb avec estTouche(c)");

		System.out.println("Fin de test de esTouche(avec cc)");






		System.out.print("Test de estCoule(avec cc) --> ");
		//on va couler le bateau NavCoulee
		//P, NavIntacte est toujours ok.
		Navire NavCoulee =  new Navire(new Coordonnee("M12"), 2, true);
		Coordonnee F  = new Coordonnee("M12");
		Coordonnee G  = new Coordonnee("M13");

		NavCoulee.recoitTir(F);
		NavCoulee.recoitTir(G);

		if (! NavCoulee.estCoule())
			System.err.print("Pb avec estCoulee");

		if (P.estCoule())
			System.err.print("Pb avec estCoulee");

		if (NavIntacte.estCoule())
			System.err.print("Pb avec estCoulee");

		System.out.println("Fin de test de estCoule(avec cc)");



	}


	}


