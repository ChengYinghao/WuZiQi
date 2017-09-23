package logic.ai;

import logic.chessboard.ChessType;

interface BaseSearchEngine {
    /**
     * 根据输入的棋盘和到谁走返回推荐电脑走的一步
     * @param board
     * @param isWhiteTurn
     * @return
     */
    ChessMove searchABestMove(ChessType[][] board, boolean isWhiteTurn);

    void setSearch(int depth);

    void setEvaluator(BaseEvaluator evaluator);

    void setMoveGenerator(BaseMoveGenerator moveGenerator);

    int isGameOver(ChessType[][] board, int depth);

    void makeMove(ChessMove chessMove, ChessType type);

    void unMakeMove(ChessMove chessMove);

    int NegaSearch(int alpha, int beta, int depth);
}
