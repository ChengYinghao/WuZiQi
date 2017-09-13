package logic;

import model.ChessType;

public class ChessBoard {
    protected static final int ROW_COUNT = 15;
    protected static final int COLUMN_COUNT = 15;
    boolean isWhiteTurn = false;

    //棋盘
    ChessType[][] board = new ChessType[COLUMN_COUNT][ROW_COUNT];

}
