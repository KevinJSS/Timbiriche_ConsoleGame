package userInteraction;

/**
 * The IOManager class is in charge of collecting the information given by the
 * user (input info) and also prints the app's system messages (output info).
 *
 * @author Kevin Alvarado
 * @author Alina Rodriguez
 *
 * @version 15/11/2020
 */
import java.util.Scanner;

public class IOManager {

    private static final Scanner reader = new Scanner(System.in);

    /**
     * Print the given information through the System class to be shown by the
     * method to the user.
     *
     * @param message is a given message by the user or app's system.
     */
    public static void showMessage(String message) {
        System.out.println(message);
    }

    /**
     * Reads the String text written by the user in the keyboard, treated as an
     * input information.
     *
     * @param message is a given message by the user or app's system.
     * @return the string text entered by the user.
     */
    public static String requestString(String message) {
        showMessage(message);
        return reader.next();
    }

    /**
     * Reads the String text written by the user in the keyboard, and extracts
     * the first character on the text.
     *
     * @param message is a given message by the user or app's system.
     * @return the selected character on the entered text.
     */
    public static char requestChar(String message) {
        showMessage(message);
        return reader.next().charAt(0);
    }

    /**
     * Reads an integer number entered by the user through the keyboard.
     *
     * @param message is a given message by the user or app's system.
     * @return the entered integer by the user.
     */
    public static int requestInt(String message) {
        showMessage(message);
        return reader.nextInt();
    }

    /**
     * Method to ask for a position in the board to add a line on it
     * 
     * @return the position wanted
     */
    public static String[] askForBoardPositions() {
        String num = requestString("> Indique el n\u00FAmero del cuadrante:");
        String lineDir = requestString("> Indique la direcci\u00F3n en la que desea colocar la linea:"
                + "\nU : Arriba"
                + "\nD : Abajo"
                + "\nL : Izquierda"
                + "\nR : Derecha\n");
        return new String[]{num, lineDir};
    }
}
