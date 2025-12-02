#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "gestionInventaire.h"

// Lit tout le CSV en mémoire
int chargerCSV(const char *chemin, Item *tab, int max)
{
    FILE *f = fopen(chemin, "r");
    if (!f) return -1;

    char buffer[512];
    int i = 0;

    fgets(buffer, 512, f);

    while (fgets(buffer, 512, f) && i < max) {
        sscanf(buffer, "%d,%[^,],%[^,],%[^,],%d,%f,%d",
               &tab[i].id,
               tab[i].nom,
               tab[i].reference,
               tab[i].categorie,
               &tab[i].quantite,
               &tab[i].prixUnitaire,
               &tab[i].seuilReappro);
        i++;
    }

    fclose(f);
    return i;
}

// Sauvegarde données triées dans TXT
// void sauvegarderTXT(const char *chemin, Item *tab, int n)
// {
//     FILE *f = fopen(chemin, "w");
//     if (!f) return;

//     for (int i = 0; i < n; i++) {
//         fprintf(f,
//             "%d | %s | %s | %s | %d | %.2f | %d\n",
//             tab[i].id,
//             tab[i].nom,
//             tab[i].reference,
//             tab[i].categorie,
//             tab[i].quantite,
//             tab[i].prixUnitaire,
//             tab[i].seuilReappro
//         );
//     }

//     fclose(f);
// }

// Sauvegarde données dans TXT avec entêtes et mise en forme agréable
void sauvegarderTXT(const char *chemin, Item *tab, int n)
{
    FILE *f = fopen(chemin, "w");
    if (!f) return;

    // Écriture des en-têtes
    fprintf(f, "%-5s | %-20s | %-12s | %-15s | %-8s | %-12s | %-8s\n",
            "ID", "Nom", "Référence", "Catégorie", "Quantité", "PrixUnitaire", "Seuil");
    fprintf(f, "-------------------------------------------------------------------------------\n");

    // Écriture des données
    for (int i = 0; i < n; i++) {
        fprintf(f, "%-5d | %-20s | %-12s | %-15s | %-8d | %-12.2f | %-8d\n",
                tab[i].id,
                tab[i].nom,
                tab[i].reference,
                tab[i].categorie,
                tab[i].quantite,
                tab[i].prixUnitaire,
                tab[i].seuilReappro
        );
    }

    fclose(f);
}


// Comparateurs
int cmp(const void *a, const void *b, const char *critere)
{
    const Item *x = a;
    const Item *y = b;

    if (strcmp(critere, "id") == 0)
        return x->id - y->id;
    if (strcmp(critere, "nom") == 0)
        return strcmp(x->nom, y->nom);
    if (strcmp(critere, "reference") == 0)
        return strcmp(x->reference, y->reference);
    if (strcmp(critere, "categorie") == 0)
        return strcmp(x->categorie, y->categorie);
    if (strcmp(critere, "quantite") == 0)
        return x->quantite - y->quantite;
    if (strcmp(critere, "prixUnitaire") == 0)
        return (x->prixUnitaire > y->prixUnitaire) - (x->prixUnitaire < y->prixUnitaire);
    if (strcmp(critere, "seuilReappro") == 0)
        return x->seuilReappro - y->seuilReappro;

    return 0;
}

char critere_global[64];

int wrapper_cmp(const void *a, const void *b)
{
    return cmp(a, b, critere_global);
}

void trier(Item *tab, int n, const char *critere)
{
    strcpy(critere_global, critere);
    qsort(tab, n, sizeof(Item), wrapper_cmp);
}

// Ajout (ID auto-incrémenté)
// int ajouter(Item *tab, int n, const char *ligneCSV)
// {
//     int maxId = 0;
//     for (int i = 0; i < n; i++)
//         if (tab[i].id > maxId) maxId = tab[i].id;

//     tab[n].id = maxId + 1;

//     sscanf(ligneCSV, "%[^,],%[^,],%[^,],%d,%f,%d",
//            tab[n].nom,
//            tab[n].reference,
//            tab[n].categorie,
//            &tab[n].quantite,
//            &tab[n].prixUnitaire,
//            &tab[n].seuilReappro);

//     return n + 1;
// }

int ajouter(Item *tab, int n, const char *ligneCSV)
{
    int maxId = 0;
    for (int i = 0; i < n; i++)
        if (tab[i].id > maxId) maxId = tab[i].id;

    tab[n].id = maxId + 1;

    char copie[256];
    strcpy(copie, ligneCSV);

    char *token = strtok(copie, ",");
    strcpy(tab[n].nom, token);

    token = strtok(NULL, ",");
    strcpy(tab[n].reference, token);

    token = strtok(NULL, ",");
    strcpy(tab[n].categorie, token);

    token = strtok(NULL, ",");
    tab[n].quantite = atoi(token);

    token = strtok(NULL, ",");
    tab[n].prixUnitaire = atof(token);

    token = strtok(NULL, ",");
    tab[n].seuilReappro = atoi(token);

    return n + 1;
}


// Suppression d'une ligne par ID ou référence
int supprimer(Item *tab, int n, int id, const char *reference)
{
    int j = 0;
    for (int i = 0; i < n; i++) {
        if ((id != -1 && tab[i].id == id) ||
            (reference != NULL && strcmp(tab[i].reference, reference) == 0))
            continue;
        tab[j++] = tab[i];
    }
    return j;
}

// Programme principal (appelé depuis Java)
int main(int argc, char **argv)
{
    printf("Ligne reçue pour ajout : '%s'\n", argv[2]);

    Item tab[MAX_LIGNES];
    int n = chargerCSV("src/data/inventaire.csv", tab, MAX_LIGNES);
    if (n <= 0) return 1;

    if (strcmp(argv[1], "afficher") == 0) {
        sauvegarderTXT("out/C/output.txt", tab, n);
    }
    else if (strcmp(argv[1], "trier") == 0) {
        trier(tab, n, argv[2]);
        sauvegarderTXT("out/C/output.txt", tab, n);
    }
    else if (strcmp(argv[1], "ajouter") == 0) {
        char ligne[256];
        strcpy(ligne, argv[2]);
        n = ajouter(tab, n, ligne);
        sauvegarderTXT("out/C/output.txt", tab, n);
    }
    else if (strcmp(argv[1], "supprimer") == 0) {
        int id = atoi(argv[2]);
        const char *ref = (strcmp(argv[2], "-1") == 0 ? argv[3] : NULL);
        n = supprimer(tab, n, id, ref);
        sauvegarderTXT("out/C/output.txt", tab, n);
    }

    return 0;
}
