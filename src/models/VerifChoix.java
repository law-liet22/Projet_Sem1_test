package models;
import java.util.HashMap;

public class VerifChoix {

    public static int choixDansValeursMenu(HashMap<String, Integer> monHash, int choix)
    {
        for (int i : monHash.values())
        {
            if (choix == i)
            {
                return 99;
            }
        }
        return -1;
    }

    public static int verifChoix(String input)
    {
        try {
        // Attempt to parse the input string to an integer
        int intInput = Integer.parseInt(input);
        return intInput;
        
        } catch (NumberFormatException e) {
            // If parsing fails, the input is not a valid integer
            return -1;
        }
    }

    public static int verifChoix(String input, HashMap<String, Integer> monHash)
    {
        try {
        // Attempt to parse the input string to an integer
        int intInput = Integer.parseInt(input);
        if (choixDansValeursMenu(monHash, intInput) == 99)
        {
            return intInput;
        }
        else
        {
            return -2;
        }
        
        } catch (NumberFormatException e) {
            // If parsing fails, the input is not a valid integer
            return -1;
        }
    }
}
