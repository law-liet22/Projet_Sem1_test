package services;

import models.Produit;
import models.Affichage;
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
    public void ajouterProduit() {
        InventaireService service = new InventaireService(this.dataPath);
        Affichage.afficherSansLn("Nom : ");
        String nom = monInput .nextLine();

        Affichage.afficherSansLn("Référence : ");
        String ref = monInput.nextLine();

        Affichage.afficherSansLn("Catégorie : ");
        String cat = monInput.nextLine();

        Affichage.afficherSansLn("Quantité : ");
        String qteS = monInput.nextLine();
        int qte = Integer.parseInt(qteS);

        Affichage.afficherSansLn("Prix : ");
        String prixS = monInput.nextLine();
        double prix = new Double(prixS).doubleValue();

        Affichage.afficherSansLn("Seuil : ");
        String seuilS = monInput.nextLine();
        int seuil = Integer.parseInt(seuilS);

        int id = service.getInventaire().size()+1;

        Produit p = new Produit(id, nom, ref, cat, qte, prix, seuil);

        inventaire.add(p);
        sauvegarder();
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

        // InventaireService service = new InventaireService(this.dataPath);

        // Affichage.afficherSansLn("ID du produit à modifier : ");
        // String idModifS = monInput.nextLine();

        // int idModif = Integer.parseInt(idModifS);
        // Produit existant = service.trouverParId(idModif);
        // if (existant == null)
        // {
        //     return "ID introuvable";
        // }

        // Affichage.afficherSansLn("Nom : ");
        // String nNom = monInput .nextLine();

        // Affichage.afficherSansLn("Référence : ");
        // String nRef = monInput.nextLine();

        // Affichage.afficherSansLn("Catégorie : ");
        // String nCat = monInput.nextLine();

        // Affichage.afficherSansLn("Quantité : ");
        // String qteS = monInput.nextLine();
        // int nQte = Integer.parseInt(qteS);

        // Affichage.afficherSansLn("Prix : ");
        // String prixS = monInput.nextLine();
        // double nPrix = new Double(prixS).doubleValue();

        // Affichage.afficherSansLn("Seuil : ");
        // String seuilS = monInput.nextLine();
        // int nSeuil = Integer.parseInt(seuilS);

        // Produit p = new Produit(idModif, nNom, nRef, nCat, nQte, nPrix, nSeuil);

        // p.setNom(nNom);
        // p.setReference(nRef);
        // p.setCategorie(nCat);
        // p.setQuantite(nQte);
        // p.setPrixUnitaire(nPrix);
        // p.setSeuilReappro(nSeuil);

        // sauvegarder();
        // return "Produit modifié";
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
}
