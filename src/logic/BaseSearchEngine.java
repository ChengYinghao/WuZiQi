package logic;

public interface BaseSearchEngine {
    ChessMove searchABestMove(ChessType[][] board, boolean isWhiteTurn);

    void setSearch(int depth);

    void setEvaluator(BaseEvaluator evaluator);

    void setMoveGenerator(BaseMoveGenerator moveGenerator);

    boolean isGameOver(ChessType[][] board, int depth);

    ChessType[][] makeMove(ChessType[][] board, ChessMove chessMove);

    ChessType[][] unMakeMove(ChessType[][] board, ChessMove chessMove);

    int NegaSearch(int alpha, int beta, int depth);
}
