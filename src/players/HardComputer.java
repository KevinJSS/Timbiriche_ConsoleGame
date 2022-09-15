/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package players;

import gameFunctionality.GameBoard;

/**
 * Using other methods and extending other classes (MediumComputerMode), this
 * class contains the methods necessary for the hard mode computer to work
 *
 * @author Kevin Alvarado
 * @author Alina Rodriguez
 *
 * @version 15/11/2020
 */
public class HardComputer extends MediumComputer {

    public HardComputer(GameBoard board) {
        super(board);
    }

    /**
     * Method for the computer to find a position to add a line where it could
     * complete a quadrant and get a point. If there is, it returns that
     * position else returns a null
     *
     * @param boardPositions is the board positions vector
     * @param board the board to analize
     * @return the position of the move to complete a quadrant
     */
    private String[] posibleWins(GameBoard board, int[] boardPositions) {
        for (int n : boardPositions) {
            if (couldWin(String.valueOf(n), board)) {
                String quadrant = String.valueOf(n);
                String line = board.getBlankSpace(board.findNumberPosition(quadrant));
                return new String[]{quadrant, line};
            }
        }
        return null;

    }

    /**
     * Method for the computer to choose a place in the board to
     * add a line in later, making it's move. It chooses a 
     * position randomly evaluating if the quadrant and line 
     * position is available, but trying not to give the player
     * a possibility to complete a quadrant, and trying to complete 
     * a quadrant itself
     *
     * @param board the board to analize
     * @return the position where to add the line
     */
    @Override
    public String[] chooseLine(GameBoard board) {
        String[] chosenLine = super.chooseLine(board);

        if (couldWin(chosenLine[0], board)) {
            return chosenLine;

        } else {
            String[] winnerLine = posibleWins(board, getBoardPositions());

            if (winnerLine != null) {
                return winnerLine;
            } else {
                return chosenLine;
            }
        }
    }

   /**
     * Method for check if a quadrant is missing one line in 
     * order to be completed
     * 
     * @param quadrantNumber the quadrant to analize
     * @param board the board to analize
     * @return a boolean value depending on the state of the quadrant
     */
    public boolean couldWin(String quadrantNumber, GameBoard board) {
        return board.nearPositions(board.findNumberPosition(quadrantNumber)) == 3;
    }
}
