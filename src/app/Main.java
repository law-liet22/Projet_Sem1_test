package app;
import models.MenuPrincipal;
import services.VerifChoix;
import java.util.Scanner;
import java.lang.Thread;
import java.util.HashMap;

public class Main 
{

    public static void afficherLn(String str)
    {
        System.out.println(str);
    }

    public static void afficherSansLn(String str)
    {
        System.out.print(str);
    }

    public static void attendre(int s)
    {
        int ms = s * 1000;
        try
        {
            Thread.sleep(ms);
        }
        catch (InterruptedException e)
        {
            Thread.currentThread().interrupt();
        }
    }
    public static void main(String[] args)
    {
        Scanner monInput = new Scanner(System.in);
        boolean menu = true;
        HashMap<String, Integer> valeursMenu = MenuPrincipal.getValeursMenu();

        while (menu)
        {
            MenuPrincipal.afficherMenu();

            afficherLn("\n");
            afficherSansLn("Entrez votre choix : ");
            String strChoix = monInput.nextLine();
            
            int choix = VerifChoix.recupEtVerifChoix(strChoix);

            if (choix == -1)
            {
                afficherLn("Veuillez entrer un nombre valide.");
            }

            else
            {
                
                if (VerifChoix.choixDansValeursMenu(valeursMenu, choix) == 1)
                {
                    afficherLn("Choix est dans le menu.");
                    attendre(3);
                }
                else
                {
                    afficherLn("Veuillez entrer un nombre présent dans le menu.");
                    attendre(3);
                }
                
            }
        }

        monInput.close();
    }
}