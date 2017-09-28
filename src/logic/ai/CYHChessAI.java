package logic.ai;

import logic.chessboard.ChessType;
import logic.chessboard.Chessboard;

public class CYHChessAI implements ChessAI {
    private SearchEngine search = new SearchEngine();

    @Override
    public ChessPos nextMovement(Chessboard chessboard) {
        ChessType[][] board = new ChessType[chessboard.getRowCount()][chessboard.getColumnCount()];
        for (int i = 0; i < chessboard.getRowCount(); i++) {
            for (int j = 0; j < chessboard.getColumnCount(); j++) {
                board[i][j] = chessboard.get(i, j);
            }
        }
        ChessMove nextMove = search.searchABestMove(board, chessboard.getHoldingChess() == ChessType.WHITE);
        if(nextMove==null) return null;
        return nextMove.getPos();
    }
}
