package logic;

import model.ChessType;

public interface BaseEvaluator {
    int evaluate(ChessType[][] board, ChessPos pos, boolean isWhiteTurn);

    void analysisHorizon(ChessType[][] board, ChessPos pos);

    void analysisVertical(ChessType[][] board, ChessPos pos);

    void analysisLeftOblique(ChessType[][] board, ChessPos pos);

    void analysisRightOblique(ChessType[][] board, ChessPos pos);
}
