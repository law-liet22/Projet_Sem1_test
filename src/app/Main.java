package app;
import models.Affichage;
import models.Menus;
import models.Terminal;
import models.VerifChoix;
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

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println();
            System.out.println("Appuyez sur Enter pour fermer le programme si celui-ci semble bloqué.");
            monInput.close();
        }));

        while (menu)
        {
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
                    while (menuModification)
                    {
                        Menus.afficherMenuModification();
                        Affichage.afficherSansLn("Entrez votre choix : ");
                        strChoix = monInput.nextLine();
                        Affichage.afficherAvecPointsSecondes(strChoix);

                        choix = VerifChoix.verifChoix(strChoix);
                    }
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