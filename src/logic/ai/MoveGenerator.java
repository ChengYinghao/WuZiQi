package logic.ai;

import logic.chessboard.CYHChessboard;
import logic.chessboard.ChessType;
import logic.chessboard.Chessboard;

import java.util.ArrayList;

public class MoveGenerator implements BaseMoveGenerator {
    ArrayList<ChessMove> moveList = new ArrayList<>();
    Chessboard chessboard = new CYHChessboard(15, 15);
    Evaluator eval = new Evaluator();


    @Override
    public ArrayList<ChessMove> createPossibleMove(ChessType[][] board, int layer, boolean isWhiteTurn) {
        ArrayList<ChessMove> moveList = new ArrayList<>();
        for (int row = 0; row < chessboard.getRowCount(); row++) {
            for (int column = 0; column < chessboard.getColumnCount(); column++) {
                if (chessboard.get(row,column) == ChessType.NONE) {
                    int score =eval.posValue[column][row];
                    ChessMove move = new ChessMove(new ChessPos(row, column), score, layer, isWhiteTurn);
                    moveList.add(move);
                }
            }
        }

        return moveList;
    }
}
