package models;
import java.util.HashMap;

/**
 * Classe utilitaire pour valider les choix de l'utilisateur dans les menus
 */
public class VerifChoix {

    /**
     * Vérifie si un choix existe dans les valeurs d'un menu
     * @param monHash Le dictionnaire contenant les valeurs valides du menu
     * @param choix Le choix à vérifier
     * @return 99 si le choix est valide, -1 sinon
     */
    public static int choixDansValeursMenu(HashMap<String, Integer> monHash, int choix)
    {
        // Parcourt toutes les valeurs du menu
        for (int i : monHash.values())
        {
            // Si le choix correspond à une valeur valide
            if (choix == i)
            {
                return 99; // Code de retour pour un choix valide
            }
        }
        return -1; // Le choix n'est pas dans le menu
    }

    /**
     * Vérifie si une chaîne peut être convertie en entier
     * @param input La chaîne à vérifier
     * @return L'entier converti ou -1 si la conversion échoue
     */
    public static int verifChoix(String input)
    {
        try {
            // Tente de convertir la chaîne en entier
            int intInput = Integer.parseInt(input);
            return intInput;
        
        } catch (NumberFormatException e) {
            // Si la conversion échoue, l'entrée n'est pas un entier valide
            return -1;
        }
    }

    /**
     * Vérifie si une chaîne est un entier valide ET fait partie des choix du menu
     * @param input La chaîne à vérifier
     * @param monHash Le dictionnaire des valeurs valides du menu
     * @return L'entier si valide et dans le menu, -1 si pas un entier, -2 si pas dans le menu
     */
    public static int verifChoix(String input, HashMap<String, Integer> monHash)
    {
        try {
            // Tente de convertir la chaîne en entier
            int intInput = Integer.parseInt(input);
            
            // Vérifie si cet entier est dans les options du menu
            if (choixDansValeursMenu(monHash, intInput) == 99)
            {
                return intInput; // Choix valide
            }
            else
            {
                return -2; // Entier valide mais pas dans le menu
            }
        
        } catch (NumberFormatException e) {
            // Si la conversion échoue, l'entrée n'est pas un entier valide
            return -1;
        }
    }
}
