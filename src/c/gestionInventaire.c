#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

#define MAX_LIGNE 512
#define MAX_PRODUITS 10000

typedef struct {
    int id;
    char nom[128];
    char reference[128];
    char categorie[128];
    int quantite;
    float prixUnitaire;
    int seuilReappro;
} Produit;

// Lecture CSV
int chargerCSV(const char *fichier, Produit *tab) {
    FILE *fp = fopen(fichier, "r");
    if (!fp) {
        fprintf(stderr, "Erreur : impossible d'ouvrir %s\n", fichier);
        return -1;
    }
    char ligne[MAX_LIGNE];
    int index = 0;

    if (!fgets(ligne, MAX_LIGNE, fp)) {
        fclose(fp);
        return 0;
    }
    // on ignore header

    while (fgets(ligne, MAX_LIGNE, fp)) {
        // retirer \n
        ligne[strcspn(ligne, "\r\n")] = '\0';
        Produit p;
        char *token;

        token = strtok(ligne, ",");
        if (!token) continue;
        p.id = atoi(token);

        token = strtok(NULL, ","); if (!token) continue;
        strcpy(p.nom, token);

        token = strtok(NULL, ","); if (!token) continue;
        strcpy(p.reference, token);

        token = strtok(NULL, ","); if (!token) continue;
        strcpy(p.categorie, token);

        token = strtok(NULL, ","); if (!token) continue;
        p.quantite = atoi(token);

        token = strtok(NULL, ","); if (!token) continue;
        p.prixUnitaire = atof(token);

        token = strtok(NULL, ","); if (!token) continue;
        p.seuilReappro = atoi(token);

        tab[index++] = p;
        if (index >= MAX_PRODUITS) break;
    }

    fclose(fp);
    return index;
}

// Comparateurs pour qsort
int cmp_id(const void *a, const void *b) {
    return ((Produit *)a)->id - ((Produit *)b)->id;
}
int cmp_nom(const void *a, const void *b) {
    return strcmp(((Produit *)a)->nom, ((Produit *)b)->nom);
}
int cmp_reference(const void *a, const void *b) {
    return strcmp(((Produit *)a)->reference, ((Produit *)b)->reference);
}
int cmp_categorie(const void *a, const void *b) {
    return strcmp(((Produit *)a)->categorie, ((Produit *)b)->categorie);
}
int cmp_quantite(const void *a, const void *b) {
    return ((Produit *)a)->quantite - ((Produit *)b)->quantite;
}
int cmp_prix(const void *a, const void *b) {
    float diff = ((Produit *)a)->prixUnitaire - ((Produit *)b)->prixUnitaire;
    if (diff < 0) return -1;
    if (diff > 0) return 1;
    return 0;
}
int cmp_seuil(const void *a, const void *b) {
    return ((Produit *)a)->seuilReappro - ((Produit *)b)->seuilReappro;
}

// Tri
void trier(Produit *tab, int taille, int critere) {
    switch (critere) {
        case 1: qsort(tab, taille, sizeof(Produit), cmp_id); break;
        case 2: qsort(tab, taille, sizeof(Produit), cmp_nom); break;
        case 3: qsort(tab, taille, sizeof(Produit), cmp_reference); break;
        case 4: qsort(tab, taille, sizeof(Produit), cmp_categorie); break;
        case 5: qsort(tab, taille, sizeof(Produit), cmp_quantite); break;
        case 6: qsort(tab, taille, sizeof(Produit), cmp_prix); break;
        case 7: qsort(tab, taille, sizeof(Produit), cmp_seuil); break;
        default: break;
    }
}

// Ajout
int ajouter(Produit *tab, int taille,
            const char *nom,
            const char *ref,
            const char *categorie,
            int quantite,
            float prix,
            int seuil) {
    int maxId = 0;
    for (int i = 0; i < taille; i++) {
        if (tab[i].id > maxId) maxId = tab[i].id;
    }
    Produit p;
    p.id = maxId + 1;
    strncpy(p.nom, nom, 127);
    p.nom[127] = '\0';
    strncpy(p.reference, ref, 127);
    p.reference[127] = '\0';
    strncpy(p.categorie, categorie, 127);
    p.categorie[127] = '\0';
    p.quantite = quantite;
    p.prixUnitaire = prix;
    p.seuilReappro = seuil;
    tab[taille] = p;
    return taille + 1;
}

// Suppression (id ou reference)
int supprimer(Produit *tab, int taille, const char *crit) {
    int estID = 1;
    for (int i = 0; crit[i] != '\0'; i++) {
        if (!isdigit((unsigned char)crit[i])) {
            estID = 0; break;
        }
    }
    for (int i = 0; i < taille; i++) {
        if ((estID && tab[i].id == atoi(crit)) ||
            (!estID && strcmp(tab[i].reference, crit) == 0)) {
            for (int j = i; j < taille - 1; j++) {
                tab[j] = tab[j + 1];
            }
            return taille - 1;
        }
    }
    return taille;
}

// Sauvegarde TXT
int sauverTXT(const char *fichier, Produit *tab, int taille) {
    FILE *fp = fopen(fichier, "w");
    if (!fp) {
        fprintf(stderr, "Erreur écriture %s\n", fichier);
        return 0;
    }
    for (int i = 0; i < taille; i++) {
        fprintf(fp,
            "%d,%s,%s,%s,%d,%.2f,%d\n",
            tab[i].id,
            tab[i].nom,
            tab[i].reference,
            tab[i].categorie,
            tab[i].quantite,
            tab[i].prixUnitaire,
            tab[i].seuilReappro
        );
    }
    fclose(fp);
    return 1;
}

// MAIN
int main(int argc, char *argv[]) {
    if (argc < 4) {
        fprintf(stderr, "Usage: %s <input.csv> <output.txt> <op> [args]\n", argv[0]);
        return 1;
    }
    const char *in = argv[1];
    const char *out = argv[2];
    int op = atoi(argv[3]);

    Produit tab[MAX_PRODUITS];
    int n = chargerCSV(in, tab);
    if (n < 0) return 1;

    if (op == 0) {
        // tri : 4ᵉ argument = critère 1..7
        int crit = atoi(argv[4]);
        trier(tab, n, crit);
    }
    else if (op == 1) {
        // ajout : args : nom, ref, categorie, quantite, prix, seuil
        if (argc != 10) {
            fprintf(stderr, "Args manquants pour ajout\n");
            return 1;
        }
        n = ajouter(tab, n,
                    argv[4], argv[5], argv[6],
                    atoi(argv[7]), atof(argv[8]), atoi(argv[9]));
    }
    else if (op == 2) {
        // suppression : arg 4 = id ou reference
        if (argc != 5) {
            fprintf(stderr, "Args manquants pour suppression\n");
            return 1;
        }
        n = supprimer(tab, n, argv[4]);
    }
    else {
        fprintf(stderr, "Opération inconnue\n");
        return 1;
    }

    if (!sauverTXT(out, tab, n)) return 1;

    return 0;
}
