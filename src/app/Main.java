package app;
// import models.Affichage;
// import models.MenuPrincipal;
// import models.Terminal;
// import models.VerifChoix;
import models.*;
import java.util.Scanner;

import java.util.HashMap;

public class Main 
{
    public static void main(String[] args)
    {
        Scanner monInput = new Scanner(System.in);
        boolean menu = true;
        HashMap<String, Integer> valeursMenu = MenuPrincipal.getValeursMenu();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println();
            System.out.println("Appuyez sur Enter pour fermer le programme si celui-ci semble bloqué.");
            monInput.close();
        }));

        while (menu)
        {
            MenuPrincipal.afficherMenu();

            Affichage.afficherLn("\n");
            Affichage.afficherSansLn("Entrez votre choix : ");
            String strChoix = monInput.nextLine();
            
            int choix = VerifChoix.recupEtVerifChoix(strChoix);

            if (choix == -1)
            {
                Terminal.effacerTerminal();
                Affichage.afficherAvecPointsSecondes("Veuillez entrer un nombre entier non négatif valide");
            }

            else
            {
                int sortie = valeursMenu.get("Sortie");
                int afficher = valeursMenu.get("Afficher");
                int modifier = valeursMenu.get("Modifier");

                if (VerifChoix.choixDansValeursMenu(valeursMenu, choix) == 1)
                {
                    if (choix == sortie)
                    {
                        monInput.close();
                        return;
                        
                    }

                    else if (choix == afficher)
                    {

                    }

                    else if (choix == modifier)
                    {

                    }

                    else
                    {
                        Terminal.effacerTerminal();
                        Affichage.afficherAvecPointsSecondes("Le choix que vous avez entré n'est actuellement pas disponible.");
                    }
                }

                else
                {
                    Terminal.effacerTerminal();
                    Affichage.afficherAvecPointsSecondes("Veuillez entrer un nombre présent dans le menu");
                }
                
            }
        }
        monInput.close();
    }
}