package services;

import models.Produit;
import models.LecteurCSV;
import utils.CSVUtils;
import java.io.PrintWriter;

import java.util.List;

public class InventaireService {

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
    public void ajouterProduit(Produit p) {
        inventaire.add(p);
        sauvegarder();
    }

    // Modification
    public boolean modifierProduit(int id, Produit modif) {
        Produit p = trouverParId(id);
        if (p == null) return false;

        p.setNom(modif.getNom());
        p.setReference(modif.getReference());
        p.setCategorie(modif.getCategorie());
        p.setQuantite(modif.getQuantite());
        p.setPrixUnitaire(modif.getPrixUnitaire());
        p.setSeuilReappro(modif.getSeuilReappro());

        sauvegarder();
        return true;
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
