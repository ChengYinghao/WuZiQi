package logic;

import model.ChessType;

import java.util.ArrayList;

public class MoveGenerator implements BaseMoveGenerator {
    ArrayList<ChessMove> moveList = new ArrayList<>();
    ChessBoard chessBoard = new ChessBoard();
    Evaluator eval = new Evaluator();


    @Override
    public ArrayList<ChessMove> createPossibleMove(ChessType[][] board, int layer, boolean isWhiteTurn) {
        for (int row = 0; row < ChessBoard.ROW_COUNT; row++) {
            for (int column = 0; column < ChessBoard.COLUMN_COUNT; column++) {
                if (chessBoard.board[column][row] == ChessType.NONE) {
                    int score =eval.posValue[column][row];
                    ChessMove move = new ChessMove(score, layer, isWhiteTurn);
                }
            }
        }
        //todo
        return null;
    }
}
