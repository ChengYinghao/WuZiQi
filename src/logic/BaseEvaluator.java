package logic;

public interface BaseEvaluator {
    int evaluate(ChessType[][] board, boolean isWhiteTurn);

    int analysisHorizon(ChessType[][] board, int row, int column);

    int analysisVertical(ChessType[][] board, int row, int column);

    int analysisLeftOblique(ChessType[][] board, int row, int column);

    int analysisRightOblique(ChessType[][] board, int row, int column);
}
