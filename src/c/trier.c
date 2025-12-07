#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define MAX_LIGNE 512
#define MAX_CHAMP 128

typedef struct 
{
    int id;
    char nom[MAX_CHAMP];
    char reference[MAX_CHAMP];
    char categorie[MAX_CHAMP];
    int quantite;
    double prixUnitaire;
    int seuilReappro;
} Produit;

Produit* inventaire = NULL;
int nbProduits = 0;

// Lecture CSV
int chargerInventaire(const char* chemin)
{
    FILE* f = fopen(chemin, "r");

    if(!f) return 0;

    char ligne[MAX_LIGNE];

    // Lecture de l'entête
    fgets(ligne, MAX_LIGNE, f);

    // Premier passage : compter nbLignes
    while (fgets(ligne, MAX_LIGNE, f)) 
    {
        nbProduits++;
    }

    inventaire = malloc(nbProduits * sizeof(Produit));

    rewind(f);
    fgets(ligne, MAX_LIGNE, f); // Sauter l'entête

    int i = 0;

    while(fgets(ligne, MAX_LIGNE, f))
    {
        Produit p;

        sscanf(ligne, "%d,%127[^,],%127[^,],%127[^,],%d,%lf,%d", &p.id, p.nom, p.reference, p.categorie, &p.quantite, &p.prixUnitaire, &p.seuilReappro);

        inventaire[i++] = p;
    }

    fclose(f);
    return 1;
}

// Tri dynamique
int critere = 0;

int comparer(const void* a, const void* b)
{
    const Produit* p1 = (const Produit*)a;
    const Produit* p2 = (const Produit*)b;

    switch (critere)
    {
        case 1: return p1->id - p2->id;
        case 2: return strcmp(p1->nom, p2->nom);
        case 3: return strcmp(p1->reference, p2->reference);
        case 4: return strcmp(p1->categorie, p2->categorie);
        case 5: return p1->quantite - p2->quantite;
        case 6: return (p1->prixUnitaire > p2->prixUnitaire) - (p1->prixUnitaire < p2->prixUnitaire);
        case 7: return (p1->seuilReappro - p2->seuilReappro);

    default:
        return 0;
    }
}

// Sauvegarder dans fichier .txt

void sauvegarderTXT(const char* chemin)
{
    FILE* f = fopen(chemin, "w");

    if (!f)
    {
        printf("Erreur : impossible d'écrire dans %s\n", chemin);
        return;
    }

    fprintf(f,"===== INVENTAIRE TRIE =====\n\n");

    for (int i = 0; i < nbProduits; i++)
    {
        Produit p = inventaire[i];

        fprintf(f, 
        "ID : %d | Nom : %s | Réf : %s | Catégorie : %s | Qté : %d | Prix : %.2f | Seuil : %d\n",
    p.id, p.nom, p.reference, p.categorie, p.quantite, p.prixUnitaire, p.seuilReappro);
    }

    fclose(f);
}

// Prog principal

int main(int argc, char** argv)
{
    if (argc < 3)
    {
        printf("Usage : %s inventaire.csv critère (1-7)\n", argv[0]);
        return 1;
    }

    if (!chargerInventaire(argv[1]))
    {
        printf("Erreur : impossible de lire le CSV.\n");
        return 1;
    }

    critere = atoi(argv[2]);

    if (critere < 1 || critere > 7)
    {
        printf("Critère invalide.\n");
        free(inventaire);
        return 1;
    }

    qsort(inventaire, nbProduits, sizeof(Produit), comparer);

    sauvegarderTXT("out/data/inventaire_trie.txt");

    free(inventaire);
    return 0;
}