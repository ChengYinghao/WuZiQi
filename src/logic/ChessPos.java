package logic;

public class ChessPos {
    private int row;
    private int column;
    public ChessPos(int row, int column){
        this.row=row;
        this.column=column;
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }
}
