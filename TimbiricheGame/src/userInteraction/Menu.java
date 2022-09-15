package userInteraction;

import players.BaseComputer;
import gameModes.ComputerMode;
import gameFunctionality.GameBoard;
import gameFunctionality.GameFunctions;
import players.HardComputer;
import players.MediumComputer;
import players.Player;
import gameModes.TwoPlayersMode;

/**
 * This class contains the menu, controls interactions with the user and it's
 * the way to play the game using recourses from other classes
 *
 * @author Kevin Alvarado
 * @author Alina Rodriguez
 *
 * @version 15/11/2020
 */
public class Menu {

    /**
     * This method contains the menu. Using methods from other classes it runs
     * the game, and controls interactions with the user
     */
    public static void showMenu() {
        boolean remain = false;
        boolean stay = false;
        int choice;

        /* GAME ENTRY */
        IOManager.showMessage("****************************************\n"
                + "\tBienvenido a Timbiriche\n"
                + "\n\t Creadores del juego:"
                + "\n\t - Alina Rodriguez"
                + "\n\t - Kevin Alvarado\n"
                + "****************************************");

        /* GAME SETTINGS */
        IOManager.showMessage("\nINSTRUCCIONES"
                + "\nIndique las dimensiones del tablero de juego (M\u00EDnimo 2*2 y M\u00E1ximo 10*10)\n");
        int rowSize = IOManager.requestInt("Indique el n\u00FAmero de filas:");
        int columnSize = IOManager.requestInt("Indique el n\u00FAmero de columnas:");
        GameBoard board = new GameBoard(rowSize, columnSize);
        GameFunctions game = null;

        do {
            choice = IOManager.requestInt("\nINSTRUCCIONES"
                    + "\nIndique la modalidad de juego: "
                    + "\n1) Dos jugadores"
                    + "\n2) VS Computadora");

            switch (choice) {
                case 1:
                    game = new TwoPlayersMode(board, new Player("Aquiles", 3), new Player("Bailo", 2));
                    remain = false;
                    break;

                case 2:
                    do {
                        choice = IOManager.requestInt("Indique el nivel de dificultad de la computadora:"
                                + "\n1) F\u00E1cil"
                                + "\n2) Medio"
                                + "\n3) Dif\u00EDcil");

                        switch (choice) {

                            case 1:
                                game = new ComputerMode(board, new Player("Armando", 5), new BaseComputer(board));
                                stay = false;
                                break;

                            case 2:
                                game = new ComputerMode(board, new Player("Esteban", 3), new MediumComputer(board));
                                stay = false;
                                break;

                            case 3:
                                game = new ComputerMode(board, new Player("Quito", 7), new HardComputer(board));
                                stay = false;
                                break;

                            default:
                                IOManager.showMessage("\nOpci\u00F3n incorrecta, intente nuevamente");
                                stay = true;
                        }

                    } while (stay);

                    remain = false;
                    break;

                default:
                    IOManager.showMessage("\nOpci\u00F3n incorrecta, intente nuevamente");
                    remain = true;
            }

        } while (remain);

        /* GAME PROCESS */
        game.printPlayers();
        remain = true;

        do {
            game.printGameBoard();

            if (game.wonGame()) {
                game.printGameResult();
                remain = false;
            } else {
                game.addLine();
            }

        } while (remain);
    }
}
