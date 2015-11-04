package copilot.domain;

/**
 * @author IndyGames
 */
public class Score {

    private final int score, user1id, user2id, user3id, user4id;
    private int scoreId;
    private User user1, user2, user3, user4;

    /**
     * Initializes an instance of the Score
     *
     * @param score the score
     * @param user1 the number of user 1
     * @param user2 the number of user 2
     * @param user3 the number of user 3
     * @param user4 the number of user 4
     */
    public Score(int score, int user1, int user2, int user3, int user4) {
        this.scoreId = 0;
        this.score = score;
        this.user1id = user1;
        this.user2id = user2;
        this.user3id = user3;
        this.user4id = user4;
    }

    /**
     * set the score ID
     *
     * @param scoreId the id to set the scoreID to
     */
    public void setScoreId(int scoreId) {
        if (scoreId <= 0) {
            throw new IllegalArgumentException("Value of score id too low!");
        }

        this.scoreId = scoreId;
    }

    /**
     * get the score ID
     *
     * @return the scoreId
     */
    public int getScoreId() {
        return this.scoreId;
    }

    /**
     * get the score
     *
     * @return the score
     */
    public int getScore() {
        return this.score;
    }

    /**
     * get the user id of user1
     *
     * @return the userID of user 1
     */
    public int getUser1id() {
        return this.user1id;
    }

    /**
     * get the user id of user2
     *
     * @return the userID of user 2
     */
    public int getUser2id() {
        return this.user2id;
    }

    /**
     * get the user id of user3
     *
     * @return the userID of user 3
     */
    public int getUser3id() {
        return this.user3id;
    }

    /**
     * get the user id of user4
     *
     * @return the userID of user 4
     */
    public int getUser4id() {
        return this.user4id;
    }
}
