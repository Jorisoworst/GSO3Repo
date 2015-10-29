/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copilot.domain;

/**
 *
 * @author IndyGames
 */
public class Score {
    private int scoreId, score, user1id, user2id, user3id, user4id;
    private User user1, user2, user3, user4;
    
    public Score(int score, int user1, int user2, int user3, int user4)
    {
        this.score = score;
        this.user1id = user1;
        this.user2id = user2;
        this.user3id = user3;
        this.user4id = user4;
    }

    public void setScoreId(int scoreId) {
        this.scoreId = scoreId;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setUser1id(int user1id) {
        this.user1id = user1id;
    }

    public void setUser2id(int user2id) {
        this.user2id = user2id;
    }

    public void setUser3id(int user3id) {
        this.user3id = user3id;
    }

    public void setUser4id(int user4id) {
        this.user4id = user4id;
    }

    public void setUser1(User user1) {
        this.user1 = user1;
    }

    public void setUser2(User user2) {
        this.user2 = user2;
    }

    public void setUser3(User user3) {
        this.user3 = user3;
    }

    public void setUser4(User user4) {
        this.user4 = user4;
    }
    
    public int getScoreId() {
        return scoreId;
    }

    public int getScore() {
        return score;
    }

    public int getUser1id() {
        return user1id;
    }

    public int getUser2id() {
        return user2id;
    }

    public int getUser3id() {
        return user3id;
    }

    public int getUser4id() {
        return user4id;
    }

    public User getUser1() {
        return user1;
    }

    public User getUser2() {
        return user2;
    }

    public User getUser3() {
        return user3;
    }

    public User getUser4() {
        return user4;
    }
}
