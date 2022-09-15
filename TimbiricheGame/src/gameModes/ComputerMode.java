package gameModes;

import players.BaseComputer;
import gameFunctionality.GameBoard;
import userInteraction.IOManager;
import players.Player;

/**
 * This class is in charge of using other methods to create a functioning game
 * logic for the computer to play the game
 *
 * @author Kevin Alvarado
 * @author Alina Rodriguez
 *
 * @version 15/11/2020
 */
public class ComputerMode extends GameMode {

    private BaseComputer computerPlayer;

    public ComputerMode(GameBoard board, Player player1, BaseComputer computerPlayer) {
        super(board, player1);
        computerPlayer.setComputerSymbol(randomSymbol());
        this.computerPlayer = computerPlayer;
    }

    /**
     * Method to print the player's information
     */
    @Override
    public void printPlayers() {
        IOManager.showMessage("\nJUGADORES\n"
                + getPlayer1().getPlayerSymbol() + ":\n" + getPlayer1() + "\n"
                + computerPlayer.getComputerSymbol() + ":\n" + computerPlayer + "\n");
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
     * Method to get a header for the game board with the player's symbol, ID
     * and score
     *
     * @return the header string
     */
    @Override
    public String gameBoardHeader() {
        return super.gameBoardHeader() + "(" + computerPlayer.getGameScore() + ") " + computerPlayer.getComputerID()
                + " [" + computerPlayer.getComputerSymbol() + "]\n\n";
    }

    /**
     * This method makes the computer add a random line to the board using other
     * methods. It makes sure it's the computer's turn, evaluates the position
     * where to add the line in the board and if it's available. It also checks
     * if the quadrant is now full, in that case, the computer's symbol is added
     * on the quadrant, increases their score and gives them another turn. If
     * not,it changes turns
     */
    @Override
    public void addLine() {
        GameBoard board = getGameBoard();
        String[] lineCoordinates;
        int[] numberPosition;
        String quadrant;
        String lineDir;

        //Players turn evaluation
        if (computerPlayer.getComputerSymbol().equals(playersTurnSymbol())) {
            lineCoordinates = computerPlayer.chooseLine(getGameBoard());
            IOManager.showMessage("\nMovimiento computadora: " + lineCoordinates[0] + ", " + lineCoordinates[1] + "\n");
        } else {
            lineCoordinates = IOManager.askForBoardPositions();
        }

        quadrant = lineCoordinates[0];
        lineDir = lineCoordinates[1];
        numberPosition = board.findNumberPosition(quadrant);

        //Line coordinates evaluation
        if (numberPosition != null) {
            int[] linePosition = board.findLinePosition(numberPosition, lineDir);

            if (linePosition != null) {
                if (board.isAvailable(linePosition)) {
                    board.addLine(numberPosition, linePosition);
                    int nearQuadrant = board.nearFullQuadrants(Integer.parseInt(quadrant));

                    if ((!board.fullQuadrant(numberPosition)) && (nearQuadrant == -1)) {
                        nextPlayer();

                    } else {
                        int tempScore = 0;
                        String playerSymbol = playersTurnSymbol();

                        if (board.fullQuadrant(numberPosition)) {
                            board.setQuadrantSymbol(numberPosition, playerSymbol);
                            tempScore++;
                        }

                        if (nearQuadrant != -1) {
                            board.setQuadrantSymbol(board.findNumberPosition(String.valueOf(nearQuadrant)), playerSymbol);
                            tempScore++;
                        }

                        if (computerPlayer.getComputerSymbol().equals(playersTurnSymbol())) {
                            computerPlayer.increaseScore(tempScore);
                        } else {
                            getPlayer1().increaseScore(tempScore);
                        }
                        computerPlayer.updateBoard(board);
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
     * This method prints the result of the game, if the player or computer
     * wins, or if it was a tie, depending on the score, and adds the propper
     * points to the winnings of the player
     */
    @Override
    public void printGameResult() {
        Player temp = getPlayer1();

        //Player wins
        if (temp.getGameScore() > computerPlayer.getGameScore()) {
            IOManager.showMessage("\n*-*-| PARTIDA GANADA |-*-*\n" + temp.getPlayerSymbol() + ":\n" + temp);
            temp.addNewVictory(1);

            //Computer wins
        } else if (temp.getGameScore() < computerPlayer.getGameScore()) {
            IOManager.showMessage("\n*-*-| PARTIDA GANADA |-*-*\n" + computerPlayer.getComputerSymbol() + ":\n" + computerPlayer);

            //Tie   
        } else {
            temp.addNewVictory(0.5);
            IOManager.showMessage("\n---*| EMPATE |*---\n" + temp + "\n\n" + computerPlayer);
        }
    }
}
