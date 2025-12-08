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

/**
 * Service gérant toutes les opérations sur l'inventaire
 * Permet d'ajouter, modifier, supprimer et trier les produits
 */
public class InventaireService {
    // Chemin vers le fichier de données
    private String dataPath = DataPath.getDataPath();
    // Scanner pour lire les entrées utilisateur
    private Scanner monInput = new Scanner(System.in);
    // Liste des produits en mémoire
    private List<Produit> inventaire;
    // Chemin du fichier CSV
    private String cheminCSV;

    /**
     * Constructeur du service
     * Charge automatiquement l'inventaire depuis le fichier CSV
     * @param cheminCSV Le chemin vers le fichier CSV
     */
    public InventaireService(String cheminCSV) {
        this.cheminCSV = cheminCSV;
        this.inventaire = LecteurCSV.lireCSV(cheminCSV);
    }

    /**
     * Retourne la liste complète de l'inventaire
     * @return La liste des produits
     */
    public List<Produit> getInventaire() {
        return inventaire;
    }

    /**
     * Recherche un produit par son identifiant
     * @param id L'identifiant du produit à chercher
     * @return Le produit trouvé, ou null si inexistant
     */
    public Produit trouverParId(int id) {
        // Parcourt tous les produits
        for (Produit p : inventaire) {
            if (p.getId() == id) return p;
        }
        return null; // Produit non trouvé
    }

    /**
     * Ajoute un nouveau produit à l'inventaire
     * Demande toutes les informations à l'utilisateur
     * @return Un message indiquant le succès ou l'échec de l'ajout
     */
    public String ajouterProduit() {
        // Crée un service pour obtenir le dernier ID
        InventaireService service = new InventaireService(this.dataPath);
        
        // === Saisie du nom ===
        Affichage.afficherSansLn("Nom : ");
        String nom = monInput .nextLine();
        // Vérifie que le nom n'est pas vide
        if (nom.isBlank() || nom == "\0" || nom == "\n")
        {
            return "Veuillez entrer un nom";
        }

        // === Saisie de la référence ===
        Affichage.afficherSansLn("Référence : ");
        String ref = monInput.nextLine();
        if (ref.isBlank() || ref == "\0" || ref == "\n")
        {
            return "Veuillez entrer une référence";
        }

        // === Saisie de la catégorie ===
        Affichage.afficherSansLn("Catégorie : ");
        String cat = monInput.nextLine();
        if (cat.isBlank() || cat == "\0" ||  cat == "\n")
        {
            return "Veuillez entrer une catégorie";
        }

        // === Saisie de la quantité ===
        Affichage.afficherSansLn("Quantité : ");
        String qteS = monInput.nextLine();
        if (!qteS.isBlank() || qteS != "\n" || qteS != "\0")
        {
            try
            {
                // Convertit la quantité en entier
                int nQte = Integer.parseInt(qteS.trim());

                // Vérifie que la quantité est positive
                if (nQte < 0) return "Quantité invalide (doit être supérieure à 0)";

                // === Saisie du prix ===
                Affichage.afficherSansLn("Prix : ");
                String prixS = monInput.nextLine();
                if (!prixS.isBlank() || prixS != "\0" || prixS != "\n")
                {
                    try
                    {
                        // Convertit le prix en nombre décimal
                        double nPrix = Double.parseDouble(prixS.trim());

                        // Vérifie que le prix est positif
                        if (nPrix < 0.0) return "Prix invalide (doit être supérieur à 0.0)";

                        // === Saisie du seuil de réapprovisionnement ===
                        Affichage.afficherSansLn("Seuil : ");
                        String seuilS = monInput.nextLine();
                        if (!seuilS.isBlank() || seuilS != "\0" || seuilS != "\n")
                        {
                            try
                            {
                                // Convertit le seuil en entier
                                int nSeuil = Integer.parseInt(seuilS.trim());

                                // Vérifie que le seuil est positif ou nul
                                if (nSeuil < 0) return "Seuil invalide (doit être supérieure ou égal à 0";

                                // Génère le nouvel ID (dernier ID + 1)
                                int id = service.getInventaire().size()+1;

                                // Crée le nouveau produit
                                Produit p = new Produit(id, nom, ref, cat, nQte, nPrix, nSeuil);

                                // Ajoute le produit à l'inventaire
                                inventaire.add(p);
                                // Sauvegarde dans le fichier CSV
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

    /**
     * Modifie un produit existant dans l'inventaire
     * Demande l'ID puis propose de modifier chaque champ
     * Les champs laissés vides conservent leur valeur actuelle
     * @return Un message indiquant le succès ou l'échec de la modification
     */
    public String modifierProduit() {

        try 
        {
            // === Saisie de l'ID du produit à modifier ===
            Affichage.afficherSansLn("ID du produit à modifier : ");
            String idS = monInput.nextLine();

            int id;
            try
            {
                // Convertit l'ID en entier
                id = Integer.parseInt(idS);
            }
            catch (NumberFormatException e)
            {
                return "ID invalide (doit être un entier";
            }

            // Recherche le produit
            Produit p = trouverParId(id);
            if (p == null)
            {
                return "ID introuvable";
            }

            // === Saisie des nouveaux champs (optionnels) ===
            // Affiche la valeur actuelle entre crochets
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

            // === Application des modifications si les champs ne sont pas vides ===
            
            // Modifie le nom si une nouvelle valeur est fournie
            if (!nNom.isBlank())
            {
                p.setNom(nNom);
            }

            // Modifie la référence si une nouvelle valeur est fournie
            if (!nRef.isBlank())
            {
                p.setReference(nRef);
            }

            // Modifie la catégorie si une nouvelle valeur est fournie
            if (!nCat.isBlank())
            {
                p.setCategorie(nCat);
            }

            // Modifie la quantité si une nouvelle valeur est fournie
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

            // Modifie le prix si une nouvelle valeur est fournie
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

            // Modifie le seuil si une nouvelle valeur est fournie
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

            // Sauvegarde les modifications dans le fichier
            sauvegarder();

            return "Produit modifié (ID = " + id + ")";
        }
        finally
        {
        }
    }

    /**
     * Supprime un produit de l'inventaire par son ID
     * @param id L'identifiant du produit à supprimer
     * @return true si suppression réussie, false si produit introuvable
     */
    public boolean supprimerProduit(int id) {
        // Recherche le produit
        Produit p = trouverParId(id);
        if (p == null) return false; // Produit non trouvé

        // Retire le produit de la liste
        inventaire.remove(p);
        // Sauvegarde les modifications
        sauvegarder();
        return true;
    }

    /**
     * Sauvegarde l'inventaire complet dans le fichier CSV
     * Écrit l'en-tête puis tous les produits ligne par ligne
     */
    private void sauvegarder() {
        try {
            // Crée un écrivain de fichier en UTF-8
            PrintWriter pw = new PrintWriter(cheminCSV, "UTF-8");

            // Écrit l'en-tête du CSV
            pw.println("id,nom,référence,catégorie,quantité,prixUnitaire,seuilRéapprovisionnement");

            // Écrit chaque produit sur une ligne
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

            // Ferme le fichier
            pw.close();

        } catch (Exception e) {
            System.out.println("Erreur de sauvegarde : " + e);
        }
    }

    /**
     * Lance le processus de tri de l'inventaire
     * Demande le critère de tri à l'utilisateur puis appelle le programme C
     * @return 1 si succès, -2 si critère invalide, autre code en cas d'erreur
     */
    public int lancerTri()
    {
        // Affiche les critères de tri disponibles
        Affichage.afficherLn("Choisir critère de tri : ");
        Affichage.afficherLn("1 = id");
        Affichage.afficherLn("2 = nom");
        Affichage.afficherLn("3 = référence");
        Affichage.afficherLn("4 = catégorie");
        Affichage.afficherLn("5 = quantité");
        Affichage.afficherLn("6 = prix unitaire");
        Affichage.afficherLn("7 = seul réapprovisionnement");
        Affichage.afficherSansLn("> ");
        try
        {
            // Récupère le choix de l'utilisateur
            int critere = Integer.parseInt(monInput.nextLine());

            // Vérifie que le critère est dans la plage valide
            if (critere < 1 || critere > 7)
            {
                return -2; // Critère invalide
            }

            // Crée l'interface avec le programme C
            InterractionCSV inter = new InterractionCSV("/home/mat/Bureau/L3/POO_Algo/Projet_Sem1_test/out/C/trier");

            // Exécute le tri et retourne le résultat
            return inter.executerTri(dataPath, critere);
        }
        catch (NumberFormatException e)
        {
            return -3; // Critère invalide
        }
    }
}
