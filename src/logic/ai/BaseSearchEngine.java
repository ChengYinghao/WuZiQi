package logic.ai;

import logic.chessboard.ChessType;

public interface BaseSearchEngine {
    ChessMove searchABestMove(ChessType[][] board, boolean isWhiteTurn);

    void setSearch(int depth);

    void setEvaluator(BaseEvaluator evaluator);

    void setMoveGenerator(BaseMoveGenerator moveGenerator);

    int isGameOver(ChessType[][] board, int depth);

    void makeMove(ChessMove chessMove, ChessType type);

    void unMakeMove(ChessMove chessMove);

    int NegaSearch(int alpha, int beta, int depth);
}
