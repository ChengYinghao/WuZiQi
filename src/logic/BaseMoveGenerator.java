package logic;

import model.ChessType;

import java.util.ArrayList;

public interface BaseMoveGenerator {
    ArrayList<ChessMove> createPossibleMove(ChessType[][] board, int layer, boolean isWhiteTurn);


}
