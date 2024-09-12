package application;

/**
 * This is the builder pattern class, used for creating game objects
 */
public class builderPattern {
    private String player1Name;
    private String player2Name;
    private int player1Score;
    private int player2Score;
    private int goalsToWin;

    // priv constructor to prevent instantiation from outside
    private builderPattern(GameBuilder builder) {
        this.player1Name = builder.player1Name;
        this.player2Name = builder.player2Name;
        this.player1Score = builder.player1Score;
        this.player2Score = builder.player2Score;
        this.goalsToWin = builder.goalsToWin;
    }

    /**
     * get the name of player1
     * @return player 1 name
     */

    public String getPlayer1Name() {
        return player1Name;
    }

    /**
     * get the name of player2
     * @return player 2 name
     */

    public String getPlayer2Name() {
        return player2Name;
    }

    /**
     * get score of player 1
     * @return score of player 1
     */

    public int getPlayer1Score() {
        return player1Score;
    }

    /**
     * get score of player 2
     * @return score of player 2
     */

    public int getPlayer2Score() {
        return player2Score;
    }

    /**
     * get goals to win
     * @return number of goals to win
     */
    public int getGoalsToWin() {
        return goalsToWin;
    }

    public static class GameBuilder {
        private String player1Name;
        private String player2Name;
        private int player1Score;
        private int player2Score;
        private int goalsToWin;

        /**
         * setting the name of player 1
         * @param name name of player 1
         * @return
         */
        public GameBuilder player1Name(String name) {
            this.player1Name = name;
            return this;
        }

        /**
         * setting the name of player 2
         * @param name name of player 2
         * @return
         */

        public GameBuilder player2Name(String name) {
            this.player2Name = name;
            return this;
        }

        /**
         * setting the score of player 1
         * @param score score of player 1
         * @return
         */
        public GameBuilder player1Score(int score) {
            this.player1Score = score;
            return this;
        }

        /**
         * setting the score of player 2
         * @param score score of player 2
         * @return
         */
        public GameBuilder player2Score(int score) {
            this.player2Score = score;
            return this;
        }

        /**
         * setting the target score to win the game
         * @param score the target score to win the game
         * @return
         */
        public GameBuilder goalsToWin(int score) {
            this.goalsToWin = score;
            return this;
        }

        /**
         * Builds and returns a new instance of builderPattern class
         * @return 
         */
        public builderPattern build() {
            return new builderPattern(this);
        }
    }
}

