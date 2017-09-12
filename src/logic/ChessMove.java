package logic;

public class ChessMove {
    //仅考虑棋子位置不考虑局势的分数
    private int score;
    private int layer;
    private boolean isWhiteTurn;

    public ChessMove(int score, int layer, boolean isWhiteTurn) {
        this.score = score;
        this.layer=layer;
        this.isWhiteTurn=isWhiteTurn;
    }
}
