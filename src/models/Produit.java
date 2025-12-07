package models;

public class Produit {

    private int id;
    private String nom;
    private String reference;
    private String categorie;
    private int quantite;
    private double prixUnitaire;
    private int seuilReappro;

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

    public int getId() { return id; }
    public String getNom() { return nom; }
    public String getReference() { return reference; }
    public String getCategorie() { return categorie; }
    public int getQuantite() { return quantite; }
    public double getPrixUnitaire() { return prixUnitaire; }
    public int getSeuilReappro() { return seuilReappro; }

    public void setNom(String nom) { this.nom = nom; }
    public void setReference(String reference) { this.reference = reference; }
    public void setCategorie(String categorie) { this.categorie = categorie; }
    public void setQuantite(int quantite) { this.quantite = quantite; }
    public void setPrixUnitaire(double prixUnitaire) { this.prixUnitaire = prixUnitaire; }
    public void setSeuilReappro(int seuilReappro) { this.seuilReappro = seuilReappro; }

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
