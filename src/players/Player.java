package players;

/**
 * This class cointains all the information related to the players of the game,
 * their ID, symbol (P1 or P2), score and how many times they've won. It also
 * has common methods, and methods to increase the player's score and games won.
 *
 * @author Kevin Alvarado
 * @author Alina Rodriguez
 *
 * @version 15/11/2020
 */
public class Player {

    private String playerID;
    private String playerSymbol;
    private double wonGames;
    private int gameScore;

    public Player(String playerID, int wonGames) {
        // Evaluation (ID >= 4 && ID <= 8): ExceptionsS
        this.playerID = playerID;
        this.wonGames = wonGames;
        gameScore = 0;
    }

    public void setPlayerSymbol(String playerSymbol) {
        this.playerSymbol = playerSymbol;
    }

    public String getPlayerID() {
        return playerID;
    }

    public String getPlayerSymbol() {
        return playerSymbol;
    }

    public int getGameScore() {
        return gameScore;
    }

    public double getWonGames() {
        return wonGames;
    }

    /**
     * This method increases the player's score
     *
     */
    public void increaseScore(int points) {
        gameScore += points;
    }

    /**
     * This method increases amount of times the player has won a game
     *
     * @param victory is the points to add to the games won. If they win they
     * get 1 point, and in a tie they get 0.5 points
     */
    public void addNewVictory(double victory) {
        wonGames += victory;
    }

    @Override
    public String toString() {
        return "* Indentificador: " + playerID + "\n* Partidas ganadas: " + wonGames;
    }
}
