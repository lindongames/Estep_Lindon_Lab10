import java.util.Scanner;

/**
 * SafeInput: reusable, bulletproof console input methods.
 * Methods are public static so they can be called from any other class,
 * e.g. int rowMove = SafeInput.getRangedInt(in, "Enter row", 1, 3);
 */
public class SafeInput
{
    /**
     * Prompts the user until they enter an integer within [low, high].
     *
     * @param console Scanner to read from (typically System.in)
     * @param prompt  message shown to the user (range is appended automatically)
     * @param low     lowest acceptable value (inclusive)
     * @param high    highest acceptable value (inclusive)
     * @return an int between low and high inclusive
     */
    public static int getRangedInt(Scanner console, String prompt, int low, int high)
    {
        int retVal = 0;
        String trash = "";
        boolean done = false;

        do
        {
            System.out.print("\n" + prompt + " [" + low + "-" + high + "]: ");
            if (console.hasNextInt())
            {
                retVal = console.nextInt();
                console.nextLine(); // clear the newline from the input buffer
                if (retVal >= low && retVal <= high)
                {
                    done = true;
                }
                else
                {
                    System.out.println("You must enter a value in the range [" + low + "-" + high + "]. You entered: " + retVal);
                }
            }
            else
            {
                trash = console.nextLine();
                System.out.println("You must enter a whole number, not: " + trash);
            }
        } while (!done);

        return retVal;
    }

    /**
     * Prompts the user for a yes/no answer until they enter Y or N (any case).
     *
     * @param console Scanner to read from (typically System.in)
     * @param prompt  message shown to the user ("[Y/N]" is appended automatically)
     * @return true for yes, false for no
     */
    public static boolean getYNConfirm(Scanner console, String prompt)
    {
        String response = "";
        boolean retVal = false;
        boolean done = false;

        do
        {
            System.out.print("\n" + prompt + " [Y/N]: ");
            response = console.nextLine().trim();
            if (response.equalsIgnoreCase("Y"))
            {
                retVal = true;
                done = true;
            }
            else if (response.equalsIgnoreCase("N"))
            {
                retVal = false;
                done = true;
            }
            else
            {
                System.out.println("You must enter Y or N. You entered: " + response);
            }
        } while (!done);

        return retVal;
    }
}
