public class Score {
    private int whiteScore;
    private int blackScore;

    public Score() {
        this.whiteScore = 0;
        this.blackScore = 0;
    }

    public int getWhiteScore() {
        return whiteScore;
    }

    public int getBlackScore() {
        return blackScore;
    }

    public void incrementScore(Color color) {
        if (color == Color.WHITE) {
            whiteScore++;
        } else {
            blackScore++;
        }
    }
}

