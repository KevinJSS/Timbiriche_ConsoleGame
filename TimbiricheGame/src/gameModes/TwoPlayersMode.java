package gameModes;

import gameFunctionality.GameBoard;
import userInteraction.IOManager;
import players.Player;

/**
 * Using other methods and extending other classes (GameMode), this class
 * contains the methods necessary for two players mode to work
 *
 * @author Kevin Alvarado
 * @author Alina Rodriguez
 *
 * @version 15/11/2020
 */
public class TwoPlayersMode extends GameMode {

    private Player player2;

    public TwoPlayersMode(GameBoard board, Player player1, Player player2) {
        super(board, player1);
        player2.setPlayerSymbol(randomSymbol());
        this.player2 = player2;
    }

    /**
     * Method to print the player's information
     */
    @Override
    public void printPlayers() {
        IOManager.showMessage("\nJUGADORES\n"
                + getPlayer1().getPlayerSymbol() + ":\n" + getPlayer1() + "\n"
                + player2.getPlayerSymbol() + ":\n" + player2 + "\n");
    }

    /**
     * Method to get a header for the game board with the player's symbol, ID
     * and score
     *
     * @return the header string
     */
    @Override
    public String gameBoardHeader() {
        return super.gameBoardHeader()
                + "(" + player2.getGameScore() + ") " + player2.getPlayerID()
                + " [" + player2.getPlayerSymbol() + "]\n\n";
    }

    /**
     * Method to print the game board matrix, including the player's game score
     * and the player's turn
     */
    @Override
    public void printGameBoard() {
        IOManager.showMessage(gameBoardHeader() + getGameBoard().getBoard() + playersTurnSymbol() + " TURNO\n");
    }

    /**
     * This method adds a line to the board using other methods. First it finds
     * the position of the quadrant and the line to be added, makes sure that
     * the position is available, if it is, it adds the line. It also checks if
     * the quadrant is now full, in that case, the player's symbol is added on
     * the quadrant, increases their score and gives them another turn. If not,
     * it changes turns
     *
     */
    public void addLine() {
        GameBoard board = getGameBoard();
        String[] boardPositions = IOManager.askForBoardPositions();
        int[] numberPosition = board.findNumberPosition(boardPositions[0]);

        if (numberPosition != null) {
            int[] linePosition = board.findLinePosition(numberPosition, boardPositions[1]);

            if (linePosition != null) {
                if (board.isAvailable(linePosition)) {
                    board.addLine(numberPosition, linePosition);
                    int nearQuadrant = board.nearFullQuadrants(Integer.parseInt(boardPositions[0]));

                    if ((!board.fullQuadrant(numberPosition)) && (nearQuadrant == -1)) {
                        nextPlayer();

                    } else {
                        int tempScore = 0;
                        Player tempPlayer = getPlayerBySymbol(playersTurnSymbol());
                        
                        if (board.fullQuadrant(numberPosition)) {
                            board.setQuadrantSymbol(numberPosition, tempPlayer.getPlayerSymbol());
                            tempScore++;
                        }
                        if (nearQuadrant != -1) {
                            board.setQuadrantSymbol(board.findNumberPosition(String.valueOf(nearQuadrant)), tempPlayer.getPlayerSymbol());
                            tempScore++;
                        }
                        tempPlayer.increaseScore(tempScore);
                    }

                } else {
                    //Treat it with exceptions
                    IOManager.showMessage("\n" + INVALID_POSITION + "\n");
                }

            } else {
                //Treat it with exceptions
                IOManager.showMessage("\n" + INVALID_POSITION + "\n");
            }

        } else {
            //Treat it with exceptions
            IOManager.showMessage("\n" + INVALID_POSITION + "\n");
        }
    }

    /**
     * This method prints the result of the game, if a certain player wins, or
     * if it was a tie, depending on the score
     */
    @Override
    public void printGameResult() {
        Player winner = getWinner();

        if (winner != null) {
            winner.addNewVictory(1);
            IOManager.showMessage("\n*-*-| PARTIDA GANADA |-*-*\n"
                    + winner.getPlayerSymbol() + ":\n" + winner);
        } else {
            tiedGame();
            IOManager.showMessage("\n---*| EMPATE |*---\n"
                    + getPlayer1() + "\n\n" + player2);
        }
    }

    /**
     * Method to check wich player won a game by comparing their scores
     *
     * @return the player with the highest score or null for a tie
     */
    private Player getWinner() {
        Player player1 = getPlayer1();

        if (player1.getGameScore() > player2.getGameScore()) {
            return player1;
        }
        if (player2.getGameScore() > player1.getGameScore()) {
            return player2;
        }
        return null; //TIE
    }

    /**
     * Method to add half a point to each player's victories. It's used in case
     * of a tie
     */
    private void tiedGame() {
        getPlayer1().addNewVictory(0.5);
        player2.addNewVictory(0.5);
    }

    /**
     * Method to return the player comparing it with the symbol given
     *
     * @param symbol the player's symbol to compare
     * @return the player
     */
    private Player getPlayerBySymbol(String symbol) {
        return (getPlayer1().getPlayerSymbol().equals(symbol)) ? getPlayer1() : player2;
    }
}
