package logic;

import java.util.ArrayList;

public interface BaseMoveGenerator {
    ArrayList<ChessMove> createPossibleMove(ChessType[][] board, int layer, boolean isWhiteTurn);


}
