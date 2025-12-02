package models;

public class Produit {
    private String nom;
    private String reference;
    private String categorie;
    private int quantite;
    private double prixUnitaire;
    private int seuilReappro;

    public Produit(String nom, String reference, String categorie, int quantite, double prixUnitaire, int seuilReappro)
    {
        this.nom = nom;
        this.reference = reference;
        this.categorie = categorie;
        this.quantite = quantite;
        this.prixUnitaire = prixUnitaire;
        this.seuilReappro = seuilReappro;
    }

    public String getNom()
    {
        return nom;
    }

    public String getReference()
    {
        return reference;
    }

    public String getCategorie()
    {
        return categorie;
    }

    public int getQuantite()
    {
        return quantite;
    }

    public double getPrixUnitaire()
    {
        return prixUnitaire;
    }

    public int getSeuilReappro()
    {
        return seuilReappro;
    }
}
