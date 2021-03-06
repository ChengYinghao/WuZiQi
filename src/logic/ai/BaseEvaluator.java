package logic.ai;

import logic.chessboard.ChessType;

interface BaseEvaluator {
    int evaluate(ChessType[][] board, boolean isWhiteTurn);

    void analysisHorizon(ChessType[][] board, ChessPos pos);

    void analysisVertical(ChessType[][] board, ChessPos pos);

    void analysisLeftOblique(ChessType[][] board, ChessPos pos);

    void analysisRightOblique(ChessType[][] board, ChessPos pos);
}
