package models;

import utils.CSVUtils;
import java.io.*;
import java.util.*;

public class LecteurCSV {

    public static List<Produit> lireCSV(String chemin) {

        List<Produit> inventaire = new ArrayList<>();

        try {
            File fichier = new File(chemin);
            Scanner scanner = new Scanner(fichier, "UTF-8");

            if (scanner.hasNextLine()) scanner.nextLine(); // ignore header

            while (scanner.hasNextLine()) {

                String ligne = scanner.nextLine().trim();
                if (ligne.isEmpty()) continue;

                String[] c = CSVUtils.parseCSVLine(ligne);
                if (c.length != 7) continue;

                Produit p = new Produit(
                        Integer.parseInt(c[0]),
                        c[1],
                        c[2],
                        c[3],
                        Integer.parseInt(c[4]),
                        Double.parseDouble(c[5]),
                        Integer.parseInt(c[6])
                );

                inventaire.add(p);
            }

            scanner.close();

        } catch (FileNotFoundException e) {
            System.out.println("Fichier introuvable : " + chemin);
        }

        return inventaire;
    }

    public static void sauvegarderCSV(String chemin, List<Produit> inventaire) {

        try {
            PrintWriter pw = new PrintWriter(new File(chemin), "UTF-8");

            pw.println("id,nom,référence,catégorie,quantité,prixUnitaire,seuilRéapprovisionnement");

            for (Produit p : inventaire) {
                pw.println(
                        p.getId() + "," +
                        CSVUtils.escape(p.toString().split(" \\| ")[1].substring(5)) // on va corriger ça dans InventaireService
                );
            }

            pw.close();

        } catch (Exception e) {
            System.out.println("Erreur lors de la sauvegarde : " + e.getMessage());
        }
    }
}
