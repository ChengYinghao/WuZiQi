package logic.chessboard;

import org.jetbrains.annotations.NotNull;

public class CYHChessboard implements Chessboard {
    private final int ROW_COUNT;
    private final int COLUMN_COUNT;
    public ChessType[][] board;
    private boolean isWhiteTurn = false;

    public CYHChessboard(int rowCount, int columnCount) {
        ROW_COUNT = rowCount;
        COLUMN_COUNT = columnCount;
        board = new ChessType[ROW_COUNT][COLUMN_COUNT];
        initChessboard();
    }

    private void initChessboard() {
        for (int row = 0; row < ROW_COUNT; row++) {
            for (int column = 0; column < COLUMN_COUNT; column++) {
                board[row][column] = ChessType.NONE;
            }
        }
    }

    //棋盘


    @Override
    public int getRowCount() {
        return ROW_COUNT;
    }

    @Override
    public int getColumnCount() {
        return COLUMN_COUNT;
    }

    @NotNull
    @Override
    public ChessType get(int row, int column) {
        return board[row][column];
    }

    @Override
    public void set(int row, int column, @NotNull ChessType chess) {
        board[row][column] = chess;
    }

    @NotNull
    @Override
    public ChessType getHoldingChess() {
        if (isWhiteTurn) {
            return ChessType.WHITE;
        } else {
            return ChessType.BLACK;
        }
    }

    @Override
    public void setHoldingChess(@NotNull ChessType chessType) {
        isWhiteTurn = chessType != ChessType.BLACK;
    }


    @Override
    public void clear() {
        for (int i = 0; i < ROW_COUNT; i++) {
            for (int j = 0; j < COLUMN_COUNT; j++) {
                board[i][j] = ChessType.NONE;
            }
        }
    }

    @NotNull
    @Override
    public MovementResult makeMovement(int row, int column) {
        //如果原来有棋子了，就不能再往上面下
        if (this.get(row, column) != ChessType.NONE) return new MovementResult.Illegal();

        //往上面放棋子
        ChessType oldHoldingChess = getHoldingChess();
        this.set(row, column, oldHoldingChess);

        //交换棋手
        ChessType newHoldingChess = oldHoldingChess.opposite();
        setHoldingChess(newHoldingChess);

        //判断局势
        boolean win = checkMovementWin(row, column);
        boolean draw = !win && checkDraw();
        GameState newGameState;
        if (win) {
            newGameState = new GameState.Win(oldHoldingChess);
        } else if (draw) {
            newGameState = new GameState.Draw();
        } else {
            newGameState = new GameState.Playing(newHoldingChess);
        }

        return new MovementResult.Legal(newGameState);

    }

    /**
     * 检查是否因为某个落子而获胜
     *
     * @param row    落子的行数
     * @param column 落子的列数
     * @return 是否因为该落子而胜利
     */
    private boolean checkMovementWin(int row, int column) {
        int count = 1;
        //检查横向左边
        for (int i = 1; i < 5; i++) {
            if (column - i >= 0 && board[row][column - i] == board[row][column]) {
                count++;
            } else break;
        }
        //检查横向右边
        for (int i = 1; i < 5; i++) {
            if (column + i < COLUMN_COUNT && board[row][column - i] == board[row][column]) {
                count++;
            } else break;
        }
        if (count >= 5) {
            return true;
        }
        count = 1;
        //检查纵向上边
        for (int i = 1; i < 5; i++) {
            if ((row - i) >= 0 && board[row - i][column] == board[row][column]) {
                count++;
            } else break;
        }
        //检查纵向下边
        for (int i = 1; i < 5; i++) {
            if (row + i < ROW_COUNT && board[row + i][column] == board[row][column]) {
                count++;
            } else break;
        }
        if (count >= 5) {
            return true;
        }
        count = 1;
        //检查左上角
        for (int i = 1; i < 5; i++) {
            if (column - i >= 0 && row - i >= 0 && board[row - i][column - i] == board[row][column]) {
                count++;
            } else break;
        }
        //检查右下角
        for (int i = 1; i < 5; i++) {
            if (row + i < ROW_COUNT && column + i < COLUMN_COUNT && board[row + i][column + i] == board[row][column]) {
                count++;
            } else break;
        }
        if (count >= 5) {
            return true;
        }
        count = 1;
        //检查左下角
        for (int i = 1; i < 5; i++) {
            if (column - i >= 0 && row + i < ROW_COUNT && board[row + i][column - i] == board[row][column]) {
                count++;
            } else break;
        }
        //检查右上角
        for (int i = 1; i < 5; i++) {
            if (row - i >= 0 && column + i < COLUMN_COUNT && board[row - i][column + i] == board[row][column]) {
                count++;
            } else break;
        }
        return count >= 5;
    }

    /**
     * 检查棋盘是否下满了（平手）。
     *
     * @return 棋盘是否下满了（平手）。
     */
    private boolean checkDraw() {
        boolean isTie = true;
        for (int row = 0; row < ROW_COUNT; row++) {
            for (int column = 0; column < COLUMN_COUNT; column++) {
                if (board[row][column] == ChessType.NONE) {
                    isTie = false;
                }
            }
        }
        return isTie;
    }
}
