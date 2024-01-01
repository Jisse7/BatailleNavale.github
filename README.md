Jeu de Bataille navale réalisé en groupe.

Le Jeu comporte les fonctionnalités suivantes : 

-Joueur contre Joueur.
-Joueur contre IA.
-Joueur contre Joueur en réseau. 
     - Chat textuel intégré.
-Système de suivi statistique.


Pour executer le programme :
- démarrer les deux serveurs en premiers (ServerBTN et Server JEU).
- Si vous jouez seul : Run une instance de BatailleNavale1
- Si vous jouez en multijoueur sur le même pc pour tester le programme : Run une autre instance de BatailleNavale1 sur un autre workspace. Pensez à créer PREALABLEMENT deux fichiers .txt sur le bureau au nom de Statistiques et Statistiques2.
- Dans le class Statistiques de la deuxième instance :
 modifier l'attribut : private static final String FICHIER_STATISTIQUES = CHEMIN_BUREAU + "/statistiques.txt";
 en : private static final String FICHIER_STATISTIQUES = CHEMIN_BUREAU + "/statistiques2.txt";
      → ce sont les bases de données.

Voici à quoi ressemble une partie avec 2 instances de BatailleNavale1 lancées sur le même pc : 
![BTN](https://github.com/Jisse7/BatailleNavale.github/assets/105201176/c300481b-6995-4c82-85b8-60788d8b0b20)
