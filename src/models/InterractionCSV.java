package models;

import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class InterractionCSV {

    public static void executerC(String... args)
    {
        try
        {
            List<String> commande = new ArrayList<>();

            // Programme C compilé
            commande.add("out/C/gestionInventaire");

            // Ajoute tous les arguments
            for (String s : args) {
                commande.add(s);
            }

            ProcessBuilder pb = new ProcessBuilder(commande);
            pb.inheritIO(); // Optionnel : pour afficher la sortie du C dans la console Java

            Process p = pb.start();
            p.waitFor();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void afficherInventaire(String path)
    {
        File f = new File(path);

        try (Scanner reader = new Scanner(f))
        {
            while (reader.hasNextLine())
            {
                String data = reader.nextLine();
                Affichage.afficherLn(data);
            }
        }
        catch (FileNotFoundException e)
        {
            Affichage.afficherLn("Une erreur est survenue.");
            e.printStackTrace();
        }
    }

    public static void demanderEtAjouterProduit()
    {
        Scanner sc = new Scanner(System.in);
        StringBuilder ligneCSV = new StringBuilder();

        Affichage.afficherSansLn("Nom : ");
        ligneCSV.append(sc.nextLine()).append(",");

        Affichage.afficherSansLn("Référence : ");
        ligneCSV.append(sc.nextLine()).append(",");

        Affichage.afficherSansLn("Catégorie : ");
        ligneCSV.append(sc.nextLine()).append(",");

        Affichage.afficherSansLn("Quantité : ");
        ligneCSV.append(sc.nextLine()).append(",");

        Affichage.afficherSansLn("Prix unitaire : ");
        ligneCSV.append(sc.nextLine()).append(",");

        Affichage.afficherSansLn("Seuil de réapprovisionnement : ");
        ligneCSV.append(sc.nextLine());

        executerC("ajouter", ligneCSV.toString());
        Affichage.afficherLn("Produit ajouté !");
    }
}
