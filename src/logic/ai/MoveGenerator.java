package logic.ai;

import java.util.ArrayList;

import logic.chessboard.CYHChessboard;
import logic.chessboard.ChessType;
import logic.chessboard.Chessboard;

public class MoveGenerator implements BaseMoveGenerator {
    ArrayList<ChessMove> moveList = new ArrayList<>();
    Chessboard chessboard = new CYHChessboard();
    Evaluator eval = new Evaluator();


    @Override
    public ArrayList<ChessMove> createPossibleMove(ChessType[][] board, int layer, boolean isWhiteTurn) {
        ArrayList<ChessMove> moveList = new ArrayList<>();
        for (int row = 0; row < Evaluator.ROW_COUNT; row++) {
            for (int column = 0; column < Evaluator.COLUMN_COUNT; column++) {
                if (chessboard.get(row,column) == ChessType.NONE) {
                    int score =eval.posValue[column][row];
                    ChessMove move = new ChessMove(score, layer, isWhiteTurn);
                    moveList.add(move);
                }
            }
        }

        return moveList;
    }
}
