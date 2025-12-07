package app;
import models.Affichage;
import models.Attendre;
import models.Menus;
import models.Terminal;
import models.VerifChoix;
import services.InventaireService;
import utils.DataPath;
import models.Produit;
import java.util.Scanner;
import java.util.HashMap;

public class Main 
{
    public static void main(String[] args)
    {
        Scanner monInput = new Scanner(System.in);
        boolean menu = true;
        HashMap<String, Integer> valeursMenuP = Menus.getValeursMenuP();
        HashMap<String, Integer> valeursMenuM = Menus.getValeursMenuM();
        String dataPath = DataPath.getDataPath();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println();
            System.out.println("Appuyez sur Enter pour fermer le programme si celui-ci semble bloqué.");
        }));

        while (menu)
        {
            InventaireService service = new InventaireService(dataPath);
            boolean menuModification = true;
            Menus.afficherMenuPrincipal();

            Affichage.afficherLn("\n");
            Affichage.afficherSansLn("Entrez votre choix : ");
            String strChoix = monInput.nextLine();
            Terminal.effacerTerminal();
            
            int choix = VerifChoix.verifChoix(strChoix);

            if (VerifChoix.verifChoix(strChoix, valeursMenuP) == -1 || VerifChoix.verifChoix(strChoix, valeursMenuP) == -2)
            {
                Terminal.effacerTerminal();
                Affichage.afficherAvecPointsSecondes("Veuillez entrer un nombre entier non négatif valide et/ou qui se trouve dans la liste des choix");
            }

            else
            {
                int sortie = valeursMenuP.get("Sortie");
                int afficher = valeursMenuP.get("Afficher");
                int modifier = valeursMenuP.get("Modifier");
                int trier = valeursMenuP.get("Trier");

                if (choix == sortie)
                {
                    monInput.close();
                    return;
                    
                }

                else if (choix == afficher)
                {
                    Terminal.effacerTerminal();
                    // LecteurCSV.afficherInventaire(inventaire);
                    for (Produit p : service.getInventaire())
                        System.out.println(p);
                    Affichage.afficherSansLn("\nAppuyez sur entrer pour continuer...");
                    monInput.nextLine();
                }

                else if (choix == modifier)
                {
                    while (menuModification)
                    {
                        Menus.afficherMenuModification();
                        Affichage.afficherLn("\n");
                        Affichage.afficherSansLn("Entrez votre choix : ");
                        strChoix = monInput.nextLine();

                        choix = VerifChoix.verifChoix(strChoix);

                        if (VerifChoix.verifChoix(strChoix, valeursMenuM) == -1 || VerifChoix.verifChoix(strChoix, valeursMenuM) == -2)
                        {
                            Terminal.effacerTerminal();
                            Affichage.afficherAvecPointsSecondes("Veuillez entrer un nombre entier non négatif valide et/ou qui se trouve dans la liste des choix");
                        }

                        else
                        {
                            int ajouter = valeursMenuM.get("Ajouter");
                            int modif = valeursMenuM.get("Modifier");
                            int supprimer = valeursMenuM.get("Supprimer");
                            int retour = valeursMenuM.get("Retour");

                            if (choix == ajouter)
                            {
                                Affichage.afficherAvecPointsSecondes(service.ajouterProduit());
                                Attendre.attendreInt(1);
                            }

                            else if (choix == modif)
                            {
                                Affichage.afficherAvecPointsSecondes(service.modifierProduit());

                                
                            }

                            else if (choix == supprimer)
                            {
                                Affichage.afficherSansLn("ID à supprimer : ");
                                String idS = monInput.nextLine();
                                int idSupp = Integer.parseInt(idS.trim());

                                boolean monBool = service.supprimerProduit(idSupp);

                                if (!monBool)
                                {
                                    Affichage.afficherAvecPointsSecondes("ID introuvable");
                                }
                                else
                                {
                                    Affichage.afficherAvecPointsSecondes("Objet supprimé");
                                }
                            }

                            else if (choix == retour)
                            {
                                menuModification = false;
                            }
                        }
                    }
                }

                else if (choix == trier)
                {
                    Affichage.afficherAvecPointsSecondes(service.lancerTri());
                }

                else
                {
                    Affichage.afficherAvecPointsSecondes("Le choix que vous avez entré n'est actuellement pas disponible.");
                }
            
            }
        }

        monInput.close();
    }
}