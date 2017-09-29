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

    int isGameOver(ChessType[][] board, int depth);

    void makeMove(ChessMove chessMove, ChessType type);

    void unMakeMove(ChessMove chessMove);

    int NegaSearch(ChessType[][] board, int alpha, int beta, int depth);
}
