package logic.chessboard;

import org.jetbrains.annotations.NotNull;

public class CYHChessboard implements Chessboard {

    public CYHChessboard(int rowCount, int columnCount) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        board = new ChessType[rowCount][columnCount];
        clear();
    }

    //棋盘
    private final int rowCount;
    private final int columnCount;

    @Override
    public int getRowCount() {
        return rowCount;
    }

    @Override
    public int getColumnCount() {
        return columnCount;
    }

    private ChessType[][] board;

    @NotNull
    @Override
    public ChessType get(int row, int column) {
        return board[row][column];
    }

    @Override
    public void set(int row, int column, @NotNull ChessType chess) {
        board[row][column] = chess;
    }


    //游戏逻辑
    private Boolean isGameOver = false;

    @Override
    public boolean isGameOver() {
        return isGameOver;
    }

    private boolean isWhiteTurn = false;

    @NotNull
    @Override
    public ChessType getHoldingChess() {
        if (isWhiteTurn) {
            return ChessType.WHITE;
        } else {
            return ChessType.BLACK;
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
        isWhiteTurn = newHoldingChess != ChessType.BLACK;

        //判断局势
        boolean win = checkMovementWin(row, column);
        boolean draw = !win && checkDraw();
        GameState newGameState;
        if (win) {
            newGameState = new GameState.Win(oldHoldingChess);
            isGameOver = true;
        } else if (draw) {
            newGameState = new GameState.Draw();
            isGameOver = true;
        } else {
            newGameState = new GameState.Playing(newHoldingChess);
            isGameOver = false;
        }

        return new MovementResult.Legal(newGameState);

    }

    @Override
    public void clear() {
        for (int row = 0; row < rowCount; row++) {
            for (int column = 0; column < columnCount; column++) {
                board[row][column] = ChessType.NONE;
            }
        }
        isGameOver = false;
    }


    //privates

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
        ChessType thisChess = board[row][column];
        for (int i = 1; i < 5; i++) {
            if (column - i < 0) break;
            if (board[row][column - i] != thisChess) break;
            count++;
        }
        //检查横向右边
        for (int i = 1; i < 5; i++) {
            if (column + i >= getColumnCount()) break;
            if (get(row, column + i) != thisChess) break;
            count++;
        }
        if (count >= 5) {
            return true;
        }
        count = 1;
        //检查纵向上边
        for (int i = 1; i < 5; i++) {
            if (row - i < 0 || row - i >= getRowCount()) break;
            if (get(row - i, column) != thisChess) break;
            count++;
        }
        //检查纵向下边
        for (int i = 1; i < 5; i++) {
            if (row + i >= getRowCount()) break;
            if (get(row + i, column) != thisChess) break;
            count++;
        }
        if (count >= 5) {
            return true;
        }

        count = 1;
        //检查左上角
        for (int i = 1; i < 5; i++) {
            if (row - i < 0 || column - i < 0) break;
            if (get(row - i, column - i) != thisChess) break;
            count++;
        }
        //检查右下角
        for (
                int i = 1;
                i < 5; i++)

        {
            if (row + i >= getRowCount() || column >= getColumnCount()) break;
            if (get(row + i, column + i) != thisChess) break;
            count++;
        }
        if (count >= 5)

        {
            return true;
        }

        count = 1;
        //检查左下角
        for (
                int i = 1;
                i < 5; i++)

        {
            if (row + i >= getRowCount() || column - i < 0) break;
            if (get(row + i, column - i) != thisChess) break;
            count++;
        }
        //检查右上角
        for (
                int i = 1;
                i < 5; i++)

        {
            if (row - i < 0 || column + i >= getColumnCount()) break;
            if (get(row - i, column + i) != thisChess) break;
            count++;
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
        for (int row = 0; row < rowCount; row++) {
            for (int column = 0; column < columnCount; column++) {
                if (board[row][column] == ChessType.NONE) {
                    isTie = false;
                }
            }
        }
        return isTie;
    }
}
