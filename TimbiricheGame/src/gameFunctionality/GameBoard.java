package gameFunctionality;

/**
 * The GameBoard class is one of the most important classes of the game, it
 * contains each behavior relared to the game board.
 *
 * @author Kevin Alvarado
 * @author Alina Rodriguez
 *
 * @version 15/11/2020
 */
public class GameBoard {
    
    public static final String[] LINES_POSITIONS = {"U", "D", "L", "R"};
    private static final String HORIZONTAL_LINE = "-";
    private static final String VERTICAL_LINE = "|";
    private static final String BLANK_SPACE = " ";
    private static final String DOT = "*";
    private static final int MAX_SIZE = 10;
    private static final int MIN_SIZE = 2;
    public static int[] vectorPositions;
    private String[][] board;

    public GameBoard(int rowSize, int columnSize) {
        //Throw an exception
        if (rowSize > MAX_SIZE) {
            rowSize = MAX_SIZE;
        } else if (rowSize < MIN_SIZE) {
            rowSize = MIN_SIZE;
        }

        if (columnSize > MAX_SIZE) {
            columnSize = MAX_SIZE;
        } else if (columnSize < MIN_SIZE) {
            columnSize = MIN_SIZE;
        }

        board = new String[rowSize * 2 + 1][columnSize * 2 + 1];
        setVectorPositions(rowSize, columnSize);
        fillBoard();
    }

    /**
     * Calculates the size of the rows on game board
     *
     * @return the actual row size
     */
    public int getRowSize() {
        return (board.length - 1) / 2;
    }

    /**
     * Calculates the size of the column on the game board
     *
     * @return the actual column size
     */
    public int getColumnSize() {
        return (board[0].length - 1) / 2;
    }
    
    public void setVectorPositions(int row, int column) {
        vectorPositions = new int[row * column];
        
        for (int i = 0, n = 1; i < vectorPositions.length; i++, n++) {
            vectorPositions[i] = n; 
        }
    }
    
    /**
     * The fillBoard class is responsible of filling the board with an
     * established look, adapting itself to any possible size of the matrix
     */
    private void fillBoard() {
        int num = 1;
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[r].length; c++) {
                if ((r % 2) == 0) {
                    board[r][c] = ((c % 2) == 0) ? DOT : BLANK_SPACE;
                } else {
                    if ((c % 2) != 0) {
                        board[r][c] = String.valueOf(num);
                        num++;
                    } else {
                        board[r][c] = BLANK_SPACE;
                    }
                }
            }
        }
    }

    /**
     * Runs through the matrix and creates a string text with the structure and
     * the elements of said matrix.
     *
     * @return the precharged matrix string.
     */
    public String getBoard() {
        String boardString = "";

        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[r].length; c++) {
                boardString += " " + board[r][c] + "\t";
            }
            boardString += "\n";
        }
        return boardString;
    }

    /**
     * Finds the position of a quadrant number in the matrix
     *
     * @param number, it's the number to find
     * @return the position as an int vector
     */
    public int[] findNumberPosition(String number) {
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[r].length; c++) {
                if (board[r][c].equals(number)) {
                    return new int[]{r, c};
                }
            }
        }
        return null; //Element not found
    }

    /**
     * It finds the positions where a line can be put in the matrix
     *
     * @param numberPosition is the position of the quadrant number
     * @param lineDirection is the identifier of the line position the user
     * wants relative to the quadrant, it can be up (U), down (D), left (L) or
     * right (R)
     * @return an int vector with the position of the line selected in the
     * matrix
     */
    public int[] findLinePosition(int[] numberPosition, String lineDirection) {
        lineDirection = lineDirection.toUpperCase();

        switch (lineDirection) {
            case "U":
                return new int[]{numberPosition[0] - 1, numberPosition[1]};
            case "D":
                return new int[]{numberPosition[0] + 1, numberPosition[1]};
            case "L":
                return new int[]{numberPosition[0], numberPosition[1] - 1};
            case "R":
                return new int[]{numberPosition[0], numberPosition[1] + 1};
            default:
                return null; //Wrong line direction
        }
    }

    /**
     * Method to see if a position is empty, so a line can be added
     *
     * @param position, it's the position to check
     * @return a boolean depending on the availability of the position, if it's
     * available it returns true, else it returns false
     */
    public boolean isAvailable(int[] position) {
        return board[position[0]][position[1]].equals(BLANK_SPACE);
    }

    /**
     * This method is responsible of giving all the line positions around a
     * quadrant.
     *
     * @param quadrant, it's the given quadrant number.
     * @return a String vector with the line positions.
     */
    private String[] quadrantPositions(int[] quadrant) {
        return new String[]{
            board[quadrant[0] - 1][quadrant[1]], // Top line
            board[quadrant[0] + 1][quadrant[1]], // Bottom line
            board[quadrant[0]][quadrant[1] - 1], // Left line
            board[quadrant[0]][quadrant[1] + 1] // Right line
        };
    }

    /**
     * Method to check if a quadrant is completed, with lines on every side
     *
     * @param quadrant is the position of the quadrant number
     * @return a boolean depending on the condition of the quadrant, if it's
     * full it returns true, else it returns false
     */
    public boolean fullQuadrant(int[] quadrant) {
        String[] positions = quadrantPositions(quadrant);

        for (int i = 0; i < positions.length; i++) {
            if (positions[i].equals(BLANK_SPACE)) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * 
     */
    public int nearFullQuadrants(int actualQuadrant) {
        int[] quadrant;
        for (int i = 0; i < vectorPositions.length; i++) {
            quadrant = findNumberPosition(String.valueOf(vectorPositions[i]));
            if (vectorPositions[i] != actualQuadrant && quadrant != null && fullQuadrant(quadrant)) {
                return vectorPositions[i];
            }
        }
        return -1;
    }

    /**
     * Method to check how many occupied position are on the given quadrant
     *
     * @param quadrant is the position of the quadrant number
     * @return a integer counter with the number of lines in the quadrant
     */
    public int nearPositions(int[] quadrant) {
        String[] positions = quadrantPositions(quadrant);
        int counter = 0;

        for (int i = 0; i < positions.length; i++) {
            if (!positions[i].equals(BLANK_SPACE)) {
                counter++;
            }
        }
        return counter;
    }

    /**
     * This method is in charge of returning the blank line space on the given
     * quadrant
     *
     * @param quadrant is the position of the quadrant number
     * @return the line direction of the blank space
     */
    public String getBlankSpace(int[] quadrant) {
        String[] positions = quadrantPositions(quadrant);

        for (int i = 0; i < positions.length; i++) {
            if (positions[i].equals(BLANK_SPACE)) {
                return LINES_POSITIONS[i];
            }
        }
        return null;
    }

    /**
     * Method to check if the game board is completed, with every quadrant full.
     * If so, the game is over
     *
     * @return boolean, if the board is full it returns true, else it returns
     * false
     */
    public boolean fullBoard() {
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[r].length; c++) {
                if ((((r % 2) == 0) && ((c % 2) != 0)) || (((c % 2) == 0) && ((r % 2) != 0))) {
                    if (board[r][c].equals(BLANK_SPACE)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Method to check with kind of line should be added, vertical or horizontal
     * depending on the position
     *
     * @param numberPosition1 is the position of the quadrant number
     * @param linePosition1 is the position of the line to be added
     * @return the kind of line to be added
     */
    private String getLineType(int numberPosition1, int linePosition1) {
        if (numberPosition1 != linePosition1) {
            return HORIZONTAL_LINE;
        } else {
            return VERTICAL_LINE;
        }
    }

    /**
     * Method to add a line to the board
     *
     * @param numberPosition is the position of the quadrant number
     * @param linePosition is the position of the line to be added
     */
    public void addLine(int[] numberPosition, int[] linePosition) {
        board[linePosition[0]][linePosition[1]] = getLineType(numberPosition[0], linePosition[0]);
    }

    /**
     * Method to mark a quadrant with the player's symbol. It's used when a
     * quadrant is completed by someone
     *
     * @param numberPosition is the position of the quadrant number
     * @param playerSymbol is the symbol that represents each player
     */
    public void setQuadrantSymbol(int[] numberPosition, String playerSymbol) {
        board[numberPosition[0]][numberPosition[1]] = playerSymbol;
    }
}
