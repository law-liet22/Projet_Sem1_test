package app;
import models.Affichage;
import models.Attendre;
import models.Menus;
import models.Terminal;
import models.VerifChoix;
import services.InventaireService;
import services.TrieService;
import utils.DataPath;
import models.Produit;
import java.util.Scanner;
import java.util.HashMap;

/**
 * Classe principale du programme de gestion d'inventaire
 * Gère le menu principal et les interactions utilisateur
 */
public class Main 
{
    /**
     * Point d'entrée du programme
     */
    public static void main(String[] args)
    {
        // Initialisation du scanner pour lire les entrées utilisateur
        Scanner monInput = new Scanner(System.in);
        // Variable pour contrôler la boucle du menu principal
        boolean menu = true;
        // Récupération des valeurs du menu principal
        HashMap<String, Integer> valeursMenuP = Menus.getValeursMenuP();
        // Récupération des valeurs du menu modification
        HashMap<String, Integer> valeursMenuM = Menus.getValeursMenuM();
        // Récupération du chemin vers le fichier de données
        String dataPath = DataPath.getDataPath();

        // Hook pour afficher un message lors de la fermeture du programme
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println();
            System.out.println("Appuyez sur Enter pour fermer le programme si celui-ci semble bloqué.");
        }));

        // Boucle principale du menu
        while (menu)
        {
            // Crée une nouvelle instance du service d'inventaire
            InventaireService service = new InventaireService(dataPath);
            // Variable pour contrôler la boucle du menu modification
            boolean menuModification = true;
            // Affiche le menu principal
            Menus.afficherMenuPrincipal();

            // Demande le choix de l'utilisateur
            Affichage.afficherLn("\n");
            Affichage.afficherSansLn("Entrez votre choix : ");
            String strChoix = monInput.nextLine();
            Terminal.effacerTerminal();
            
            // Convertit la chaîne en entier
            int choix = VerifChoix.verifChoix(strChoix);

            // Vérifie si le choix est valide (entier valide ET dans le menu)
            if (VerifChoix.verifChoix(strChoix, valeursMenuP) == -1 || VerifChoix.verifChoix(strChoix, valeursMenuP) == -2)
            {
                // Choix invalide : affiche un message d'erreur
                Terminal.effacerTerminal();
                Affichage.afficherAvecPointsSecondes("Veuillez entrer un nombre entier non négatif valide et/ou qui se trouve dans la liste des choix");
            }

            else
            {
                // Récupère les valeurs des options du menu
                int sortie = valeursMenuP.get("Sortie");
                int afficher = valeursMenuP.get("Afficher");
                int modifier = valeursMenuP.get("Modifier");
                int trier = valeursMenuP.get("Trier");

                // === Option : Sortie du programme ===
                if (choix == sortie)
                {
                    // Ferme le scanner et termine le programme
                    monInput.close();
                    return;
                }

                // === Option : Afficher l'inventaire ===
                else if (choix == afficher)
                {
                    Terminal.effacerTerminal();
                    // Parcourt et affiche chaque produit de l'inventaire
                    for (Produit p : service.getInventaire())
                        System.out.println(p);
                    // Attend que l'utilisateur appuie sur Entrée
                    Affichage.afficherSansLn("\nAppuyez sur entrer pour continuer...");
                    monInput.nextLine();
                }

                // === Option : Modifier l'inventaire ===
                else if (choix == modifier)
                {
                    // Boucle du sous-menu modification
                    while (menuModification)
                    {
                        // Affiche le menu des modifications
                        Menus.afficherMenuModification();
                        Affichage.afficherLn("\n");
                        Affichage.afficherSansLn("Entrez votre choix : ");
                        strChoix = monInput.nextLine();

                        // Convertit le choix en entier
                        choix = VerifChoix.verifChoix(strChoix);

                        // Vérifie la validité du choix
                        if (VerifChoix.verifChoix(strChoix, valeursMenuM) == -1 || VerifChoix.verifChoix(strChoix, valeursMenuM) == -2)
                        {
                            Terminal.effacerTerminal();
                            Affichage.afficherAvecPointsSecondes("Veuillez entrer un nombre entier non négatif valide et/ou qui se trouve dans la liste des choix");
                        }

                        else
                        {
                            try
                            {
                                // Récupère les valeurs des options du menu modification
                                int ajouter = valeursMenuM.get("Ajouter");
                                int modif = valeursMenuM.get("Modifier");
                                int supprimer = valeursMenuM.get("Supprimer");
                                int retour = valeursMenuM.get("Retour");

                                // === Sous-option : Ajouter un produit ===
                                if (choix == ajouter)
                                {
                                    // Appelle la méthode d'ajout et affiche le résultat
                                    Affichage.afficherAvecPointsSecondes(service.ajouterProduit());
                                    Attendre.attendreInt(1);
                                }

                                // === Sous-option : Modifier un produit ===
                                else if (choix == modif)
                                {
                                    // Appelle la méthode de modification et affiche le résultat
                                    Affichage.afficherAvecPointsSecondes(service.modifierProduit());
                                }

                                // === Sous-option : Supprimer un produit ===
                                else if (choix == supprimer)
                                {
                                    // Demande l'ID à supprimer
                                    Affichage.afficherSansLn("ID à supprimer : ");
                                    String idS = monInput.nextLine();
                                    int idSupp = Integer.parseInt(idS.trim());

                                    // Tente de supprimer le produit
                                    boolean monBool = service.supprimerProduit(idSupp);

                                    // Affiche le résultat de la suppression
                                    if (!monBool)
                                    {
                                        Affichage.afficherAvecPointsSecondes("ID introuvable");
                                    }
                                    else
                                    {
                                        Affichage.afficherAvecPointsSecondes("Objet supprimé");
                                    }
                                }

                                // === Sous-option : Retour au menu principal ===
                                else if (choix == retour)
                                {
                                    menuModification = false;
                                }
                            }
                            catch (NumberFormatException e)
                            {
                                // Gère les erreurs de conversion de nombres
                                Affichage.afficherAvecPointsSecondes("Veuillez entrer un entier");
                            }

                        }
                    }
                }

                // === Option : Trier l'inventaire ===
                else if (choix == trier)
                {
                    // Lance le processus de tri via le programme C
                    int tri = service.lancerTri();
                    
                    // Vérifie le code de retour du tri
                    if (tri == 1)
                    {
                        // Tri réussi : affiche le fichier trié
                        Affichage.afficherAvecPointsSecondes("Trié avec succès");
                        TrieService.afficherFichierTrie("/home/mat/Bureau/L3/POO_Algo/Projet_Sem1_test/out/data/inventaire_trie.txt");
                        Affichage.afficherSansLn("\nAppuyez sur entrer pour continuer...");
                        monInput.nextLine();
                    }
                    else if (tri == -1)
                    {
                        // Erreur d'exécution du programme C
                        Affichage.afficherAvecPointsSecondes("Erreur lors du triage");
                    }
                    else if (tri == -2)
                    {
                        // Critère de tri invalide
                        Affichage.afficherAvecPointsSecondes("Veuillez entrer un critère valide");
                    }
                    else
                    {
                        // Autre erreur : affiche le code d'erreur
                        Affichage.afficherAvecPointsSecondes("Erreur : " + Integer.toString(tri));
                    }
                }

                else
                {
                    // Choix non implémenté
                    Affichage.afficherAvecPointsSecondes("Le choix que vous avez entré n'est actuellement pas disponible.");
                }
            
            }
        }

        // Ferme le scanner à la fin du programme
        monInput.close();
    }
}