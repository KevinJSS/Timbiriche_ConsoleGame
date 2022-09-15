/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package players;

import gameFunctionality.GameBoard;

/**
 * Using other methods and extending other classes (ComputerBase), this class
 * contains the methods necessary for the medium mode computer to work
 *
 * @author Kevin Alvarado
 * @author Alina Rodriguez
 *
 * @version 15/11/2020
 */
public class MediumComputer extends BaseComputer {

    public MediumComputer(GameBoard board) {
        super(board);
    }

    /**
     * Method to check what position is available in the board to make a move,
     * but trying not to give the player a possibility to complete a quadrant
     *
     * @param boardPositions is the board positions vector
     * @param board the board to analize
     * @return a String vector with the quadrant and line positions, if there's
     * any available positions, else returns null
     */
    private String[] positionsAvailable(int[] boardPositions, GameBoard board) {
        for (int n : boardPositions) {
            if (!couldLose(String.valueOf(n), board)) {
                String quadrant = String.valueOf(n);
                String line = board.getBlankSpace(board.findNumberPosition(quadrant));
                return new String[]{quadrant, line};
            }
        }
        return null;
    }

    /**
     * Method for the computer to choose a place in the board to add a line in
     * later, making it's move. It chooses a position randomly evaluating if the
     * quadrant and line position is available, but trying not to give the
     * player a possibility to complete a quadrant
     *
     * @param board the board to analize
     * @return the position where to add the line
     */
    @Override
    public String[] chooseLine(GameBoard board) {
        String[] chosenLine = super.chooseLine(board);

        if (couldLose(chosenLine[0], board)) {
            String[] temp = positionsAvailable(getBoardPositions(), board);

            if (temp != null) {
                return temp;
            } else {
                return chosenLine;
            }

        } else {
            return chosenLine;
        }
    }

    /**
     * Method to check if a quadrant already has two lines in order to avoid
     * adding a third line on it, and giving the player the chase to complete it.
     *
     * @param quadrantNumber the quadrant to analize
     * @param board the board to analize
     * @return a boolean value depending on the state of the quadrant
     */
    public boolean couldLose(String quadrantNumber, GameBoard board) {
        return board.nearPositions(board.findNumberPosition(quadrantNumber)) == 2;
    }
}
