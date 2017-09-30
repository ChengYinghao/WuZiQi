package logic.ai;

import logic.chessboard.ChessType;

import java.util.ArrayList;

public class MoveGenerator implements BaseMoveGenerator {
    ArrayList<ChessMove> moveList = new ArrayList<>();
    Evaluator eval = new Evaluator();


    @Override
    public ArrayList<ChessMove> createPossibleMove(ChessType[][] board, int layer, boolean isWhiteTurn) {
        ArrayList<ChessMove> moveList = new ArrayList<>();
        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board[row].length; column++) {
                if (board[row][column] == ChessType.NONE) {
                    int score = eval.posValue[column][row];
                    ChessMove move = new ChessMove(new ChessPos(row, column), score, layer, isWhiteTurn);
                    moveList.add(move);
                }
            }
        }

        return moveList;
    }
}
