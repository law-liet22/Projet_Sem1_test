#ifndef INVENTAIRE_H
#define INVENTAIRE_H

#define MAX_LIGNES 500
#define MAX_CHAMP 128

// Structure représentant une ligne du CSV
typedef struct {
    int id;
    char nom[MAX_CHAMP];
    char reference[MAX_CHAMP];
    char categorie[MAX_CHAMP];
    int quantite;
    float prixUnitaire;
    int seuilReappro;
} Item;

// Fonctions
int chargerCSV(const char *chemin, Item *tab, int max);
void sauvegarderTXT(const char *chemin, Item *tab, int n);
void trier(Item *tab, int n, const char *critere);
int ajouter(Item *tab, int n, const char *ligneCSV);
int supprimer(Item *tab, int n, int id, const char *reference);

#endif
