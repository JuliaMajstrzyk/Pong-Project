package application;
import application.database;
import application.gameDBInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class is responsible for loading and saving the game state to and from a db
 */
public class addingLoadingDB implements gameDBInterface {
    /**
     * This is the method for loading the game
     * @param player1Nametest the first player object to update name of player1
     * @param player2Nametest the second player object to update name of player2
     * @param player1scoretest the score of the first player to update player 1 score
     * @param play2scoretest the score of second player to update player 2 score
     * @param target the target score of the game to update
     */
    @Override
    public void loadGame(Player player1Nametest, Player player2Nametest, int player1scoretest, int play2scoretest, int target) {
        try {
            Connection connection = database.getConnection();

            String query = "SELECT player1Name, player2Name, player1Score, player2Score, target FROM game";

            PreparedStatement ps = connection.prepareStatement(query);

            ResultSet result = ps.executeQuery();

            while (result.next()) {
                String player1Name = result.getString("player1Name");
                String player2Name = result.getString("player2Name");
                int player1Score = result.getInt("player1Score");
                int player2Score = result.getInt("player2Score");
                int goalsToWin = result.getInt("target");

                builderPattern bp = new builderPattern.GameBuilder()
                                        .player1Name(player1Name)
                                        .player2Name(player2Name)
                                        .player1Score(player1Score)
                                        .player2Score(player2Score)
                                        .goalsToWin(goalsToWin)
                                        .build();
                player1Nametest.setName(bp.getPlayer1Name());
                player2Nametest.setName(bp.getPlayer2Name());

                player1Nametest.setScore(bp.getPlayer1Score());
                player2Nametest.setScore(bp.getPlayer2Score());

                ViewPong.goalsToWin = bp.getGoalsToWin();
            }

            result.close();
            ps.close();
            connection.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * This saves the current data to the database
     * @param player1Name name of player1 saved
     * @param player2Name name of player2 saved
     * @param player1score player 1 score saved
     * @param player2score player 2 score saved
     * @param target The target (number of goals needed to win) saved
      */
    @Override
    public void saveGame(String player1Name, String player2Name, int player1score, int player2score, int target) {
        try {
            Connection connection = database.getConnection();

            String query = "INSERT INTO game (player1Name, player2Name, player1Score, player2Score, target) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(query);

            ps.setString(1, player1Name);
            ps.setString(2, player2Name);
            ps.setInt(3, player1score);
            ps.setInt(4, player2score);
            ps.setInt(5, target);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Data inserted successfully");
            } else {
                System.out.println("Failed to insert data");
            }

            ps.close();
            connection.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
