package services;

import models.Produit;
import models.Affichage;
import models.InterractionCSV;
import models.LecteurCSV;
import utils.CSVUtils;
import java.io.PrintWriter;
import java.util.Scanner;
import utils.DataPath;

import java.util.List;

public class InventaireService {
    private String dataPath = DataPath.getDataPath();
    private Scanner monInput = new Scanner(System.in);
    private List<Produit> inventaire;
    private String cheminCSV;

    public InventaireService(String cheminCSV) {
        this.cheminCSV = cheminCSV;
        this.inventaire = LecteurCSV.lireCSV(cheminCSV);
    }

    public List<Produit> getInventaire() {
        return inventaire;
    }

    // Trouve un produit par ID
    public Produit trouverParId(int id) {
        for (Produit p : inventaire) {
            if (p.getId() == id) return p;
        }
        return null;
    }

    // Ajout
    public String ajouterProduit() {
        InventaireService service = new InventaireService(this.dataPath);
        Affichage.afficherSansLn("Nom : ");
        String nom = monInput .nextLine();
        if (nom.isBlank() || nom == "\0" || nom == "\n")
        {
            return "Veuillez entrer un nom";
        }

        Affichage.afficherSansLn("Référence : ");
        String ref = monInput.nextLine();
        if (ref.isBlank() || ref == "\0" || ref == "\n")
        {
            return "Veuillez entrer une référence";
        }

        Affichage.afficherSansLn("Catégorie : ");
        String cat = monInput.nextLine();
        if (cat.isBlank() || cat == "\0" ||  cat == "\n")
        {
            return "Veuillez entrer une catégorie";
        }

        Affichage.afficherSansLn("Quantité : ");
        String qteS = monInput.nextLine();
        if (!qteS.isBlank() || qteS != "\n" || qteS != "\0")
        {
            try
            {
                int nQte = Integer.parseInt(qteS.trim());

                if (nQte < 0) return "Quantité invalide (doit être supérieure à 0)";

                Affichage.afficherSansLn("Prix : ");
                String prixS = monInput.nextLine();
                if (!prixS.isBlank() || prixS != "\0" || prixS != "\n")
                {
                    try
                    {
                        double nPrix = Double.parseDouble(prixS.trim());

                        if (nPrix < 0.0) return "Prix invalide (doit être supérieur à 0.0)";

                        Affichage.afficherSansLn("Seuil : ");
                        String seuilS = monInput.nextLine();
                        if (!seuilS.isBlank() || seuilS != "\0" || seuilS != "\n")
                        {
                            try
                            {
                                int nSeuil = Integer.parseInt(seuilS.trim());

                                if (nSeuil < 0) return "Seuil invalide (doit être supérieure ou égal à 0";

                                int id = service.getInventaire().size()+1;

                                Produit p = new Produit(id, nom, ref, cat, nQte, nPrix, nSeuil);

                                inventaire.add(p);
                                sauvegarder();
                            }

                            catch (NumberFormatException e)
                            {
                                return "Seuil invalide (doit être un entier)";
                            }
                        }
                        else
                        {
                            return "Veuillez entrer un seuil";
                        }
                    }
                    catch (NumberFormatException e)
                    {
                        return "Prix invalide (doit être un nombre)";
                    }
                }
                else
                {
                    return "Veuillez entrer un prix";
                }
            }
            catch (NumberFormatException e)
            {
                return "Quantité invalide (doit être un entier)";
            }
        }
        else
        {
            return "Veuillez entrer une quantité";
        }

        return "Objet ajouté";
    }

    // Modification
    public String modifierProduit() {

        try 
        {
            Affichage.afficherSansLn("ID du produit à modifier : ");
            String idS = monInput.nextLine();

            int id;
            try
            {
                id = Integer.parseInt(idS);
            }
            catch (NumberFormatException e)
            {
                return "ID invalide (doit être un entier";
            }

            Produit p = trouverParId(id);
            if (p == null)
            {
                return "ID introuvable";
            }

            Affichage.afficherSansLn("Nom [" + p.getNom() + "] : ");
            String nNom = monInput.nextLine();

            Affichage.afficherSansLn("Référence [" + p.getReference() + "] : ");
            String nRef = monInput.nextLine();

            Affichage.afficherSansLn("Catégorie [" + p.getCategorie() + "] : ");
            String nCat = monInput.nextLine();

            Affichage.afficherSansLn("Quantité [" + p.getQuantite() + "] : ");
            String qteS = monInput.nextLine();

            Affichage.afficherSansLn("Prix unitaire [" + p.getPrixUnitaire() + "] : ");
            String prixS = monInput.nextLine();

            Affichage.afficherSansLn("Seuil de réapprovisionnement [" + p.getSeuilReappro() + "] : ");
            String seuilS = monInput.nextLine();

            if (!nNom.isBlank())
            {
                p.setNom(nNom);
            }

            if (!nRef.isBlank())
            {
                p.setReference(nRef);
            }

            if (!nCat.isBlank())
            {
                p.setCategorie(nCat);
            }

            if (!qteS.isBlank())
            {
                try
                {
                    int nQte = Integer.parseInt(qteS.trim());

                    if (nQte < 0) return "Quantité invalide (doit être supérieure à 0)";

                    p.setQuantite(nQte);
                }
                catch (NumberFormatException e)
                {
                    return "Quantité invalide (doit être un entier)";
                }
            }

            if (!prixS.isBlank())
            {
                try
                {
                    double nPrix = Double.parseDouble(prixS.trim());

                    if (nPrix < 0.0) return "Prix invalide (doit être supérieur à 0.0)";
                    
                    p.setPrixUnitaire(nPrix);
                }
                catch (NumberFormatException e)
                {
                    return "Prix invalide (doit être un nombre)";
                }
            }

            if (!seuilS.isBlank())
            {
                try
                {
                    int nSeuil = Integer.parseInt(seuilS.trim());

                    if (nSeuil < 0) return "Seuil invalide (doit être supérieure ou égal à 0";

                    p.setSeuilReappro(nSeuil);
                }

                catch (NumberFormatException e)
                {
                    return "Seuil invalide (doit être un entier)";
                }
            }

            sauvegarder();

            return "Produit modifié (ID = " + id + ")";
        }
        finally
        {
        }
    }

    // Suppression
    public boolean supprimerProduit(int id) {
        Produit p = trouverParId(id);
        if (p == null) return false;

        inventaire.remove(p);
        sauvegarder();
        return true;
    }

    // Sauvegarde correcte du CSV
    private void sauvegarder() {
        try {
            PrintWriter pw = new PrintWriter(cheminCSV, "UTF-8");

            pw.println("id,nom,référence,catégorie,quantité,prixUnitaire,seuilRéapprovisionnement");

            for (Produit p : inventaire) {
                pw.println(
                        p.getId() + "," +
                        CSVUtils.escape(p.getNom()) + "," +
                        CSVUtils.escape(p.getReference()) + "," +
                        CSVUtils.escape(p.getCategorie()) + "," +
                        p.getQuantite() + "," +
                        p.getPrixUnitaire() + "," +
                        p.getSeuilReappro()
                );
            }

            pw.close();

        } catch (Exception e) {
            System.out.println("Erreur de sauvegarde : " + e);
        }
    }

    public String lancerTri()
    {
        Affichage.afficherLn("Choisir critère de tri : ");
        Affichage.afficherLn("1 = id");
        Affichage.afficherLn("2 = nom");
        Affichage.afficherLn("3 = référence");
        Affichage.afficherLn("4 = catégorie");
        Affichage.afficherLn("5 = quantité");
        Affichage.afficherLn("6 = prix unitaire");
        Affichage.afficherLn("7 = seul réapprovisionnement");
        Affichage.afficherSansLn("> ");

        int critere = Integer.parseInt(monInput.nextLine());

        if (critere < 1 || critere > 7)
        {
            return "Critère invalide.";
        }

        InterractionCSV inter = new InterractionCSV("/home/mat/Bureau/L3/POO_Algo/Projet_Sem1_test/out/C/trier");

        return inter.executerTri(dataPath, critere);
    }
}
