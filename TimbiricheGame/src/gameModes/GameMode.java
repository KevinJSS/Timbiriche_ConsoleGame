package gameModes;

import gameFunctionality.GameBoard;
import gameFunctionality.GameFunctions;
import players.Player;

/**
 * An abstract class with atributes and methods to be used later on for the game
 * logic
 *
 * @author Kevin Alvarado
 * @author Alina Rodriguez
 *
 * @version 15/11/2020
 */
public abstract class GameMode implements GameFunctions {

    protected static final String INVALID_POSITION = "---* POSICI\u00D3N INV\u00C1LIDA *---";
    private static final String[] SYMBOLS = {"J1", "J2"};
    private static int playersTurn;
    private GameBoard board;
    private Player player1;

    public GameMode(GameBoard board, Player player1) {
        player1.setPlayerSymbol(randomSymbol());
        this.board = board;
        this.player1 = player1;
    }

    public Player getPlayer1() {
        return player1;
    }

    public GameBoard getGameBoard() {
        return board;
    }

    public int getPlayersTurn() {
        return playersTurn;
    }

    /**
     * Increase the player's turn in order to get to the next player
     */
    protected void nextPlayer() {
        playersTurn++;
    }

    /**
     * Method to randomly get a player symbol, either P1 or P2
     *
     * @return the random symbol
     */
    protected String randomSymbol() {
        int randomNumber = (int) (Math.floor(Math.random() * 2));
        String symbol = SYMBOLS[randomNumber];
        SYMBOLS[randomNumber] = null;

        if (symbol != null) {
            return symbol;
        } else {
            return randomSymbol();
        }
    }

    /**
     * Method to get a header for the game board with the player's symbol, ID
     * and score
     *
     * @return the header string
     */
    protected String gameBoardHeader() {
        return "\nTABLERO\n"
                + "[" + player1.getPlayerSymbol() + "] " + player1.getPlayerID() + " (" + player1.getGameScore() + ") : ";
    }

    /**
     * Method to get the player's symbol depending on the turn, if it's odd or
     * not
     *
     * @return the player's symbol
     */
    protected String playersTurnSymbol() {
        return ((playersTurn % 2) == 0) ? "J1" : "J2";
    }
    
    /**
     * Method to check if the game board is completed, and if that's the case,
     * the game is finished
     *
     * @return boolean, if the game is finished it returns true, else it returns
     * false
     */
    @Override
    public boolean wonGame() {
        return board.fullBoard();
    }
}
