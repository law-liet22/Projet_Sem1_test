package models;

import utils.CSVUtils;
import java.io.*;
import java.util.*;

/**
 * Classe utilitaire pour lire et sauvegarder l'inventaire depuis/vers un fichier CSV
 */
public class LecteurCSV {

    /**
     * Lit un fichier CSV et crée une liste de produits
     * @param chemin Le chemin vers le fichier CSV à lire
     * @return Une liste de produits contenant tous les éléments du fichier
     */
    public static List<Produit> lireCSV(String chemin) {

        // Initialise une liste vide pour stocker les produits
        List<Produit> inventaire = new ArrayList<>();

        try {
            // Ouvre le fichier CSV
            File fichier = new File(chemin);
            Scanner scanner = new Scanner(fichier, "UTF-8");

            // Ignore la première ligne (en-tête)
            if (scanner.hasNextLine()) scanner.nextLine();

            // Parcourt toutes les lignes du fichier
            while (scanner.hasNextLine()) {

                // Récupère et nettoie la ligne
                String ligne = scanner.nextLine().trim();
                // Ignore les lignes vides
                if (ligne.isEmpty()) continue;

                // Parse la ligne CSV en tableau de chaînes
                String[] c = CSVUtils.parseCSVLine(ligne);
                // Vérifie que la ligne contient bien 7 colonnes
                if (c.length != 7) continue;

                // Crée un objet Produit avec les données de la ligne
                Produit p = new Produit(
                        Integer.parseInt(c[0]),    // ID
                        c[1],                       // Nom
                        c[2],                       // Référence
                        c[3],                       // Catégorie
                        Integer.parseInt(c[4]),    // Quantité
                        Double.parseDouble(c[5]),  // Prix unitaire
                        Integer.parseInt(c[6])     // Seuil de réapprovisionnement
                );

                // Ajoute le produit à la liste
                inventaire.add(p);
            }

            // Ferme le scanner
            scanner.close();

        } catch (FileNotFoundException e) {
            System.out.println("Fichier introuvable : " + chemin);
        }

        return inventaire;
    }

    /**
     * Sauvegarde une liste de produits dans un fichier CSV
     * (Méthode obsolète, préférer celle dans InventaireService)
     * @param chemin Le chemin où sauvegarder le fichier
     * @param inventaire La liste de produits à sauvegarder
     */
    public static void sauvegarderCSV(String chemin, List<Produit> inventaire) {

        try {
            // Crée un écrivain de fichier en UTF-8
            PrintWriter pw = new PrintWriter(new File(chemin), "UTF-8");

            // Écrit l'en-tête du CSV
            pw.println("id,nom,référence,catégorie,quantité,prixUnitaire,seuilRéapprovisionnement");

            // Écrit chaque produit (méthode à améliorer)
            for (Produit p : inventaire) {
                pw.println(
                        p.getId() + "," +
                        CSVUtils.escape(p.toString().split(" \\| ")[1].substring(5))
                );
            }

            // Ferme le fichier
            pw.close();

        } catch (Exception e) {
            System.out.println("Erreur lors de la sauvegarde : " + e.getMessage());
        }
    }
}
