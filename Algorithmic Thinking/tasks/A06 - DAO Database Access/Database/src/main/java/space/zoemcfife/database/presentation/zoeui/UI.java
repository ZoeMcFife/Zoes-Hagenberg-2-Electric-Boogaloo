package space.zoemcfife.database.presentation.zoeui;

public final class UI
{
    public static final String RESET  = "\u001B[0m";

    public static final String RED    = "\u001B[31m";
    public static final String GREEN  = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE   = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN   = "\u001B[36m";
    public static final String GRAY  = "\u001B[37m";

    private UI()
    {

    }

    public static void printGray(String message)
    {
        IO.print(GRAY + message + RESET);
    }

    public static void printlnGray(String message)
    {
        IO.println(GRAY + message + RESET);
    }

    public static void printRed(String message)
    {
        IO.print(RED + message + RESET);
    }

    public static void printlnRed(String message)
    {
        IO.println(RED + message + RESET);
    }

    public static void printGreen(String message)
    {
        IO.print(GREEN + message + RESET);
    }

    public static void printlnGreen(String message)
    {
        IO.println(GREEN + message + RESET);
    }

    public static void printYellow(String message)
    {
        IO.print(YELLOW + message + RESET);
    }
    public static void printlnYellow(String message)
    {
        IO.println(YELLOW + message + RESET);
    }

    public static void printBlue(String message)
    {
        IO.print(BLUE + message + RESET);
    }

    public static void printlnBlue(String message)
    {
        IO.println(BLUE + message + RESET);
    }

    public static void printCyan(String message)
    {
        IO.print(CYAN + message + RESET);
    }

    public static void printlnCyan(String message)
    {
        IO.println(CYAN + message + RESET);
    }

    public static void printPurple(String message)
    {
        IO.print(PURPLE + message + RESET);
    }

    public static void printlnPurple(String message)
    {
        IO.println(PURPLE + message + RESET);
    }

    /**
     * Gets a single character input from the player.
     * Continues to prompt until valid input is provided.
     *
     * @return The validated character input
     */
    public static char getCharInput()
    {
        while (true)
        {
            printCyan("> ");

            String input = IO.readln();

            if (input.length() == 1)
            {
                return input.toLowerCase().charAt(0);
            }

            printlnRed("Invalid input. Please enter a single character.");
        }
    }

    /**
     * Gets a single character input from the player, filtered by valid characters.
     * Continues to prompt until valid input is provided.
     *
     * @param validChars The array of valid characters
     * @return The validated character input
     */
    public static char getFilteredCharInput(char... validChars)
    {
        while (true)
        {
            char input = getCharInput();

            for (char validChar : validChars)
            {
                if (input == validChar)
                {
                    return input;
                }
            }

            printlnRed("Invalid input. Please enter one of the following characters: " + String.valueOf(validChars));
        }
    }

    /**
     * Gets an integer input from the player within a specified range.
     * Continues to prompt until valid input is provided.
     *
     * @param min The minimum allowed value (inclusive)
     * @param max The maximum allowed value (inclusive)
     * @return The validated integer input
     */
    public static int getIntInput(int min, int max)
    {
        while (true)
        {
            try
            {
                printCyan("> ");

                int value = Integer.parseInt(IO.readln());

                if (value >= min && value <= max)
                {
                    return value;
                }
            }
            catch (NumberFormatException ignored)
            {

            }

            printlnRed("Invalid input. Enter a number between " + min + " and " + max + ".");
        }
    }

    /**
     * Gets a yes/no response from the player.
     * Accepts 'y', 'yes', 'n', or 'no' (case insensitive).
     *
     * @param prompt The question to ask the player
     * @return true for yes, false for no
     */
    public static boolean getYesNoInput(String prompt)
    {
        while (true)
        {
            IO.print(prompt + " (y/n): ");
            String input = IO.readln().trim().toLowerCase();

            if (input.equals("y") || input.equals("yes"))
            {
                return true;
            }
            else if (input.equals("n") || input.equals("no"))
            {
                return false;
            }

            printlnRed("Invalid input. Please enter 'y' or 'n'.");
        }
    }

    /**
     * Gets a string input from the player with a maximum length.
     * Continues to prompt until valid input is provided.
     *
     * @param prompt    The prompt message to display
     * @param maxLength The maximum allowed length of the input
     * @return The validated string input
     */
    public static String getStringInput(String prompt, int maxLength)
    {
        return getStringInput(prompt, 0, maxLength);
    }

    /**
     * Gets a string input from the player with minimum and maximum length restrictions.
     * Continues to prompt until valid input is provided.
     *
     * @param prompt    The prompt message to display
     * @param minLength The minimum allowed length of the input
     * @param maxLength The maximum allowed length of the input
     * @return The validated string input
     */
    public static String getStringInput(String prompt, int minLength, int maxLength)
    {
        while (true)
        {
            IO.println(prompt + ": ");
            String input = IO.readln().trim();;

            if (!input.isEmpty() && input.length() <= maxLength && input.length() >= minLength)
            {
                return input;
            }

            if (input.length() > maxLength)
            {
                printlnRed("Input exceeds maximum length of " + maxLength + " characters. Please try again.");
            }
            else if (input.length() < minLength)
            {
                printlnRed("Input must be at least " + minLength + " characters long. Please try again.");
            }
            else
            {
                printlnRed("Input cannot be empty. Please try again.");
            }
        }
    }

    /**
     * Gets a string input from the player without length restriction.
     *
     * @param prompt The prompt message to display
     * @return The validated string input
     */
    public static String getStringInput(String prompt)
    {
        return getStringInput(prompt, Integer.MAX_VALUE);
    }

    // Thank you autocomplete for like suggesting so many of these... i surely will need them....

    public static void printSeparatorLine()
    {
        IO.println("--------------------------------------------------");
    }

    public static void printDoubleSeparatorLine()
    {
        IO.println("==================================================");
    }

    public static void printAsteriskSeparatorLine()
    {
        IO.println("**************************************************");
    }

    public static void printHashSeparatorLine()
    {
        IO.println("##################################################");
    }

    public static void printBlankSeparatorLine()
    {
        IO.println();
    }

    /**
     * Pauses execution until the player presses Enter.
     */
    public static void waitForEnterKey()
    {
        IO.readln("Press Enter to continue...");
    }

    /**
     * Pauses execution for a short duration based on GameManager.DELAY_SHORT.
     */
    public static void delayShort()
    {
        delay(1);
    }

    /**
     * Pauses execution for a medium duration based on GameManager.DELAY_MEDIUM.
     */
    public static void delayMedium()
    {
        delay(2);
    }

    /**
     * Pauses execution for a long duration based on GameManager.DELAY_LONG.
     */
    public static void delayLong()
    {
        delay(3);
    }

    /**
     * Pauses execution for a specified duration.
     *
     * @param milliseconds The number of milliseconds to delay
     */
    private static void delay(int milliseconds)
    {
        try
        {
            Thread.sleep(milliseconds);
        }
        catch (InterruptedException e)
        {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Clears the console screen by printing blank lines.
     */
    public static void clearScreen()
    {
        for (int i = 0; i < 50; i++)
        {
            System.out.println();
        }
    }

    /**
     * Exits the game with a humorous message.
     */
    public static void exit()
    {
        clearScreen();
        printlnRed("Your computer will blow up in 3 seconds...");
        System.exit(0);
    }
}