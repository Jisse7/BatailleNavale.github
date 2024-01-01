package batailleNavale;

import java.io.*;

public class Statistiques {
    private static final String CHEMIN_BUREAU = System.getProperty("user.home") + "/Desktop";
    private static final String FICHIER_STATISTIQUES = CHEMIN_BUREAU + "/statistiques2.txt";
    private int partiesGagnees;
    private int tirsReussis;
    private int tirsRates;
    private int partiesJouees;
    private int naviresDetruits;
    private int partiesPerdues;
    private int tirsReçus;

    // Constructeur
    public Statistiques() {
        lireFichier();
    }

    // Méthodes pour incrémenter les statistiques
    public void incrementerPartiesGagnees() {
        this.partiesGagnees++;
        sauvegarderFichier();
    }
    
    public void incrementerTirsRates() {
    	this.tirsRates++;
    	sauvegarderFichier();
    }

    public void incrementerPartiesJouees() {
        this.partiesJouees++;
        sauvegarderFichier();
    }

    public void incrementerNaviresDetruits() {
        this.naviresDetruits++;
        sauvegarderFichier();
    }

    public void incrementerTirsReçus() {
        this.tirsReçus++;
        sauvegarderFichier();
    }

    public void incrementerPartiesPerdues() {
        this.partiesPerdues++;
        sauvegarderFichier();
    }

    public void incrementerTirsReussis() {
        this.tirsReussis++;
        sauvegarderFichier();
    }
    

    public void setTirsRates(int tirsRates) {
        this.tirsRates = tirsRates;
    }

  

    public void setPartiesJouees(int partiesJouees) {
        this.partiesJouees = partiesJouees;
    }



    public void setNaviresDetruits(int naviresDetruits) {
        this.naviresDetruits = naviresDetruits;
    }
    
    public void setPartiesPerdues(int partiesPerdues) {
        this.partiesPerdues = partiesPerdues;
    }

    public void setTirsReçus(int tirsReçus) {
        this.tirsReçus = tirsReçus;
    }
    
    public int getPartiesGagnees() {
        return lireValeurDepuisFichier("Parties Gagnées");
    }

    public int getTirsReussis() {
        return lireValeurDepuisFichier("Tirs Réussis");
    }

    public int getTirsRates() {
        return lireValeurDepuisFichier("Tirs Ratés");
    }

    public int getPartiesJouees() {
        return lireValeurDepuisFichier("Parties Jouées");
    }

    public int getNaviresDetruits() {
        return lireValeurDepuisFichier("Navires Détruits");
    }

    public int getPartiesPerdues() {
        return lireValeurDepuisFichier("Parties Perdues");
    }

    public int getTirsReçus() {
        return lireValeurDepuisFichier("Tirs Reçus");
    }
    
    private int lireValeurDepuisFichier(String titre) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FICHIER_STATISTIQUES))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith(titre)) {
                    return Integer.parseInt(line.split(":")[1].trim());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0; // Valeur par défaut si la statistique n'est pas trouvée
    }


    // Lecture des statistiques à partir du fichier
private void lireFichier() {
    try (BufferedReader reader = new BufferedReader(new FileReader(FICHIER_STATISTIQUES))) {
        partiesGagnees = Integer.parseInt(reader.readLine().split(":")[1]);
        tirsReussis = Integer.parseInt(reader.readLine().split(":")[1]);
        tirsRates = Integer.parseInt(reader.readLine().split(":")[1]);
        partiesJouees = Integer.parseInt(reader.readLine().split(":")[1]);
        naviresDetruits = Integer.parseInt(reader.readLine().split(":")[1]);
        partiesPerdues = Integer.parseInt(reader.readLine().split(":")[1]);
        tirsReçus = Integer.parseInt(reader.readLine().split(":")[1]);
    } catch (IOException e) {
        e.printStackTrace();
    }
}


    // Sauvegarde des statistiques dans le fichier
public void sauvegarderFichier() {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(FICHIER_STATISTIQUES))) {
        writer.write("Parties Gagnées:" + (this.getPartiesGagnees()+ partiesGagnees) + "\n");
        writer.write("Tirs Réussis:" + (this.getTirsReussis()+ tirsReussis) + "\n");
        writer.write("Tirs Ratés:" + (this.getTirsRates()+ tirsRates) + "\n");
        writer.write("Parties Jouées:" + (this.getPartiesJouees()+ partiesJouees) + "\n");
        writer.write("Navires Détruits:" + (this.getNaviresDetruits()+ naviresDetruits) + "\n");
        writer.write("Parties Perdues:" + (this.getPartiesPerdues()+ partiesPerdues) + "\n");
        writer.write("Tirs Reçus:" + (this.getTirsReçus()+ tirsReçus) + "\n");
    } catch (IOException e) {
        e.printStackTrace();
    }
}

    
}