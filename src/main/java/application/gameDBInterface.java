package application;

import java.sql.SQLException;

/**
 * This is an interface for interacting wiht the database
 */

public interface gameDBInterface {
    /**
     * This loads the game from the DB
     * @param player1Name name of player1
     * @param player2Name name of player2
     * @param player1score player 1 score
     * @param player2score player 2 score
     * @param goalsToWin This is the numebr of goals required to win
     * @throws SQLException If a database access error occurs
     * @throws ClassNotFoundException If DB driver class is not found
     */

    void loadGame(Player player1Name, Player player2Name, int player1score, int player2score, int goalsToWin) throws SQLException, ClassNotFoundException;

    /**
     * This saves the current data to the database
     * @param player1Name name of player1
     * @param player2Name name of player2
     * @param player1score player 1 score
     * @param player2score player 2 score
     * @param goalsToWin The number of goals needed to win
     * @throws SQLException If a database access error occurs
     * @throws ClassNotFoundException If DB driver class is not found
     */
    void saveGame(String player1Name, String player2Name, int player1score, int player2score, int goalsToWin) throws SQLException, ClassNotFoundException;
}
