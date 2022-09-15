package players;

import gameFunctionality.GameBoard;

/**
 * This class contains the information and methods necessary for the computer to
 * be able to play the game. It's the base for the easy mode, but also the
 * medium and hard modes extend from this class.
 *
 * @author Kevin Alvarado
 * @author Alina Rodriguez
 *
 * @version 15/11/2020
 */
public class BaseComputer {

    private static final String COMPUTER_ID = "Computer";
    private String[] linePositions;
    private String computerSymbol;
    private int[] boardPositions;
    private int gameScore;

    public BaseComputer(GameBoard board) {
        boardPositions = board.vectorPositions;
        linePositions = board.LINES_POSITIONS;
    }

    public String getComputerID() {
        return COMPUTER_ID;
    }

    public void setComputerSymbol(String computerSymbol) {
        this.computerSymbol = computerSymbol;
    }

    public String getComputerSymbol() {
        return computerSymbol;
    }

    public int getGameScore() {
        return gameScore;
    }

    public int[] getBoardPositions() {
        return boardPositions;
    }

    /**
     * This method returns the length of the boardPositions vector
     *
     * @return the int length of boardPositions
     */
    protected int getBoardPositionsLength() {
        return boardPositions.length;
    }

    /**
     * Method to increase the score of the computer
     */
    public void increaseScore(int points) {
        gameScore += points;
    }

    /**
     * Method for the computer to choose a place in the board to
     * add a line in later, making it's move. It chooses a 
     * position randomly evaluating if the quadrant and line 
     * position are available.
     *
     * @param board the board to analize
     * @return the position where to add the line, with the 
     * quadrant number and then the line position
     */
    public String[] chooseLine(GameBoard board) {
        String quadrant = String.valueOf(randomAvailableNumber(board));
        String line = randomAvailableLine(board);
        int[] quadrantPosition = board.findNumberPosition(quadrant);
        int[] linePosition = board.findLinePosition(quadrantPosition, line);

        if (!board.isAvailable(linePosition)) {
            if (boardPositions.length == 1) {
                removeLinePosition(line);
            }
            return chooseLine(board);

        } else {
            return new String[]{quadrant, line};
        }
    }
    
    /**
     * This method updates the state of the board to the computer
     * so it can make a propper move choice considering past moves
     * 
     * @param board the board to analize
     */
    public void updateBoard(GameBoard board) {
        int[] temp = new int[boardPositions.length];

        for (int i = 0; i < boardPositions.length; i++) {
            int[] c = board.findNumberPosition(String.valueOf(boardPositions[i]));
            if (c == null) {
                temp[i] = boardPositions[i];
            }
        }

        for (int i = 0; i < temp.length; i++) {
            if (temp[i] != 0) {
                removeNumberPosition(temp[i]);
            }
        }
    }

    /**
     * Method to get a random available position on the board
     *
     * @param board the board to analize
     * @return the randomly chosen position 
     */
    public int randomAvailableNumber(GameBoard board) {
        int randomNumber = getRandomNumber(boardPositions.length);
        return boardPositions[randomNumber];
    }

    /**
     * Method to get a random available line on the board
     *
     * @param board the board to analize
     * @return the randomly chosen line 
     */
    public String randomAvailableLine(GameBoard board) {
        int randomNumber = getRandomNumber(linePositions.length);
        return linePositions[randomNumber];
    }

    /**
     * Method to get a random number
     *
     * @param length is the limit of numers to choose from
     * @return the random number
     */
    private int getRandomNumber(int length) {
        return (int) Math.floor(Math.random() * length);
    }

    /**
     * Method to remove a certain number position from the
     * board positions recognized, so it's not used again
     *
     * @param position is the number position
     */
    public void removeNumberPosition(int position) {
        int indexPosition = findQuadrantIndex(position);
        shiftFromNumberPosition(indexPosition);

        int[] temp = new int[boardPositions.length - 1];

        for (int i = 0; i < temp.length; i++) {
            temp[i] = boardPositions[i];
        }
        boardPositions = temp;
    }

    /**
     * Method to remove a certain line position from the
     * line positions recognized, so it's not used again
     *
     * @param line is the line position
     */
    public void removeLinePosition(String line) {
        int indexPosition = findLineIndex(line);
        shiftFromLinePosition(indexPosition);

        String[] temp = new String[linePositions.length - 1];

        for (int i = 0; i < temp.length; i++) {
            temp[i] = linePositions[i];
        }

        linePositions = temp;
    }

    /**
     * Method to get the line index of the linePositions vector
     *
     * @param line is the line to find
     * @return the line vector index or -1 if the line's not found
     */
    private int findLineIndex(String line) {
        for (int i = 0; i < linePositions.length; i++) {
            if (linePositions[i].equals(line)) {
                return i;
            }
        }
        return -1; //Line not found
    }

    /**
     * Method to get the quadrant index of the boardPositions vector
     *
     * @param quadrant is the quadrant number to find
     * @return the quadrant vector index or -1 if the line's not found
     */
    public int findQuadrantIndex(int quadrant) {
        for (int i = 0; i < boardPositions.length; i++) {
            if (quadrant == boardPositions[i]) {
                return i;
            }
        }
        return -1; //Position not found
    }

    /**
     * Method change certain line position, by getting rid of one
     * and moving the others in the vector
     *
     * @param index is the line position to get rid of
     */
    private void shiftFromLinePosition(int index) {
        for (int i = index; i < linePositions.length - 1; i++) {
            linePositions[i] = linePositions[i + 1];
        }
        linePositions[linePositions.length - 1] = null;
    }

    /**
     * Method change certain quadrant position, by getting rid of one
     * and moving the others in the vector
     *
     * @param index is the quadrant number position to get rid of
     */
    public void shiftFromNumberPosition(int index) {
        for (int i = index; i < boardPositions.length - 1; i++) {
            boardPositions[i] = boardPositions[i + 1];
        }
        boardPositions[boardPositions.length - 1] = 0;
    }

    @Override
    public String toString() {
        return "* Indentificador: " + COMPUTER_ID + "\n";
    }
}
