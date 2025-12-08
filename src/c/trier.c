// Inclusions des bibliothèques nécessaires
#include <stdio.h>      // Pour l'entrée/sortie (printf, fopen, etc.)
#include <stdlib.h>     // Pour malloc, free, atoi, qsort
#include <string.h>     // Pour strcmp (comparaison de chaînes)

// Définition des constantes
#define MAX_LIGNE 512   // Taille maximale d'une ligne du fichier CSV
#define MAX_CHAMP 128   // Taille maximale d'un champ (nom, référence, etc.)

/**
 * Structure représentant un produit
 * Contient tous les champs d'un produit de l'inventaire
 */
typedef struct 
{
    int id;                         // Identifiant unique
    char nom[MAX_CHAMP];            // Nom du produit
    char reference[MAX_CHAMP];      // Référence du produit
    char categorie[MAX_CHAMP];      // Catégorie du produit
    int quantite;                   // Quantité en stock
    double prixUnitaire;            // Prix unitaire
    int seuilReappro;              // Seuil de réapprovisionnement
} Produit;

// Variables globales
Produit* inventaire = NULL;  // Pointeur vers le tableau dynamique de produits
int nbProduits = 0;          // Nombre total de produits

/**
 * Charge l'inventaire depuis un fichier CSV
 * @param chemin Le chemin vers le fichier CSV
 * @return 1 si succès, 0 si erreur
 */
int chargerInventaire(const char* chemin)
{
    // Ouvre le fichier en lecture
    FILE* f = fopen(chemin, "r");

    // Vérifie que le fichier existe
    if(!f) return 0;

    char ligne[MAX_LIGNE];

    // Lit et ignore la première ligne (en-tête)
    fgets(ligne, MAX_LIGNE, f);

    // Premier passage : compte le nombre de produits
    while (fgets(ligne, MAX_LIGNE, f)) 
    {
        nbProduits++;
    }

    // Alloue la mémoire pour tous les produits
    inventaire = malloc(nbProduits * sizeof(Produit));

    // Retourne au début du fichier
    rewind(f);
    // Ignore à nouveau l'en-tête
    fgets(ligne, MAX_LIGNE, f);

    int i = 0;

    // Deuxième passage : lit chaque ligne et remplit le tableau
    while(fgets(ligne, MAX_LIGNE, f))
    {
        Produit p;

        // Parse la ligne CSV (format : id,nom,ref,cat,qte,prix,seuil)
        // %[^,] signifie "lire tout sauf la virgule"
        sscanf(ligne, "%d,%127[^,],%127[^,],%127[^,],%d,%lf,%d", 
               &p.id, p.nom, p.reference, p.categorie, 
               &p.quantite, &p.prixUnitaire, &p.seuilReappro);

        // Ajoute le produit au tableau
        inventaire[i++] = p;
    }

    // Ferme le fichier
    fclose(f);
    return 1; // Succès
}

// Variable globale pour le critère de tri
int critere = 0;

/**
 * Fonction de comparaison pour qsort
 * Compare deux produits selon le critère global
 * @param a Pointeur vers le premier produit
 * @param b Pointeur vers le second produit
 * @return <0 si a<b, 0 si a==b, >0 si a>b
 */
int comparer(const void* a, const void* b)
{
    // Cast des pointeurs void en pointeurs Produit
    const Produit* p1 = (const Produit*)a;
    const Produit* p2 = (const Produit*)b;

    // Comparaison selon le critère choisi
    switch (critere)
    {
        case 1: return p1->id - p2->id;                    // Tri par ID
        case 2: return strcmp(p1->nom, p2->nom);           // Tri par nom (alphabétique)
        case 3: return strcmp(p1->reference, p2->reference); // Tri par référence
        case 4: return strcmp(p1->categorie, p2->categorie); // Tri par catégorie
        case 5: return p1->quantite - p2->quantite;        // Tri par quantité
        case 6: return (p1->prixUnitaire > p2->prixUnitaire) - (p1->prixUnitaire < p2->prixUnitaire); // Tri par prix
        case 7: return (p1->seuilReappro - p2->seuilReappro); // Tri par seuil

    default:
        return 0; // Pas de tri si critère invalide
    }
}

/**
 * Sauvegarde l'inventaire trié dans un fichier texte
 * @param chemin Le chemin du fichier de sortie
 */
void sauvegarderTXT(const char* chemin)
{
    // Ouvre le fichier en écriture
    FILE* f = fopen(chemin, "w");

    // Vérifie que le fichier a pu être ouvert
    if (!f)
    {
        printf("Erreur : impossible d'écrire dans %s\n", chemin);
        return;
    }

    // Écrit l'en-tête du fichier
    fprintf(f,"===== INVENTAIRE TRIE =====\n\n");

    // Écrit chaque produit sur une ligne
    for (int i = 0; i < nbProduits; i++)
    {
        Produit p = inventaire[i];

        // Format : ID : x | Nom : y | ...
        fprintf(f, 
        "ID : %d | Nom : %s | Réf : %s | Catégorie : %s | Qté : %d | Prix : %.2f | Seuil : %d\n",
        p.id, p.nom, p.reference, p.categorie, p.quantite, p.prixUnitaire, p.seuilReappro);
    }

    // Ferme le fichier
    fclose(f);
}

/**
 * Programme principal
 * Trie l'inventaire selon un critère et sauvegarde le résultat
 */
int main(int argc, char** argv)
{
    // Vérifie qu'on a bien 3 arguments : programme, fichier CSV, critère
    if (argc < 3)
    {
        printf("Usage : %s inventaire.csv critère (1-7)\n", argv[0]);
        return 1; // Code d'erreur
    }

    // Charge l'inventaire depuis le fichier CSV (premier argument)
    if (!chargerInventaire(argv[1]))
    {
        printf("Erreur : impossible de lire le CSV.\n");
        return 1; // Code d'erreur
    }

    // Convertit le deuxième argument (chaîne) en entier
    critere = atoi(argv[2]);

    // Vérifie que le critère est valide (entre 1 et 7)
    if (critere < 1 || critere > 7)
    {
        printf("Critère invalide.\n");
        free(inventaire); // Libère la mémoire avant de quitter
        return 1; // Code d'erreur
    }

    // Trie le tableau avec qsort (tri rapide)
    // Paramètres : tableau, nombre d'éléments, taille d'un élément, fonction de comparaison
    qsort(inventaire, nbProduits, sizeof(Produit), comparer);

    // Sauvegarde le résultat trié dans un fichier texte
    sauvegarderTXT("out/data/inventaire_trie.txt");

    // Libère la mémoire allouée dynamiquement
    free(inventaire);
    return 0; // Succès
}