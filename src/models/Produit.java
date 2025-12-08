package models;

/**
 * Classe représentant un produit dans l'inventaire
 */
public class Produit {

    // Identifiant unique du produit
    private int id;
    // Nom du produit
    private String nom;
    // Référence du produit
    private String reference;
    // Catégorie à laquelle appartient le produit
    private String categorie;
    // Quantité en stock
    private int quantite;
    // Prix unitaire du produit
    private double prixUnitaire;
    // Seuil de réapprovisionnement (quantité minimale)
    private int seuilReappro;

    /**
     * Constructeur de la classe Produit
     * Initialise tous les attributs d'un produit
     */
    public Produit(int id, String nom, String reference, String categorie,
                   int quantite, double prixUnitaire, int seuilReappro) {
        this.id = id;
        this.nom = nom;
        this.reference = reference;
        this.categorie = categorie;
        this.quantite = quantite;
        this.prixUnitaire = prixUnitaire;
        this.seuilReappro = seuilReappro;
    }

    // Getters : méthodes pour accéder aux attributs privés
    public int getId() { return id; }
    public String getNom() { return nom; }
    public String getReference() { return reference; }
    public String getCategorie() { return categorie; }
    public int getQuantite() { return quantite; }
    public double getPrixUnitaire() { return prixUnitaire; }
    public int getSeuilReappro() { return seuilReappro; }

    // Setters : méthodes pour modifier les attributs privés
    public void setNom(String nom) { this.nom = nom; }
    public void setReference(String reference) { this.reference = reference; }
    public void setCategorie(String categorie) { this.categorie = categorie; }
    public void setQuantite(int quantite) { this.quantite = quantite; }
    public void setPrixUnitaire(double prixUnitaire) { this.prixUnitaire = prixUnitaire; }
    public void setSeuilReappro(int seuilReappro) { this.seuilReappro = seuilReappro; }

    /**
     * Convertit l'objet Produit en chaîne de caractères lisible
     * @return Représentation textuelle du produit avec tous ses attributs
     */
    @Override
    public String toString() {
        return "ID: " + id +
               " | Nom: " + nom +
               " | Référence: " + reference +
               " | Catégorie: " + categorie +
               " | Quantité: " + quantite +
               " | Prix Unitaire: " + prixUnitaire +
               " | Seuil Réappro: " + seuilReappro;
    }
}
