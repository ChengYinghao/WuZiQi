package logic.ai;
import logic.chessboard.ChessType;

import java.util.ArrayList;

public class SearchEngine implements BaseSearchEngine {
    int maxSearchDepth = 3;
    BaseEvaluator evaluator = new Evaluator();
    BaseMoveGenerator baseMoveGenerator = new MoveGenerator();
    ChessMove bestMove;
    ChessType[][] board;

    @Override
    public ChessMove searchABestMove(ChessType[][] board, boolean isWhiteTurn) {
        this.board = board;
        bestMove = null;
        NegaSearch(board, -2000, 2000, maxSearchDepth);
        return bestMove;
    }

    @Override
    public void setSearch(int depth) {
        maxSearchDepth = depth;
    }

    @Override
    public int isGameOver(ChessType[][] board, int depth) {
        int i = (maxSearchDepth - depth) % 2;
        boolean isWhiteTurn = i != 0;
        int score = evaluator.evaluate(board, isWhiteTurn);
        if (Math.abs(score) > 8000) {
            return score;
        }
        return 0;
    }

    @Override
    public void makeMove(ChessMove chessMove, ChessType type) {
        board[chessMove.getPos().getRow()][chessMove.getPos().getColumn()] = type;
    }

    @Override
    public void unMakeMove(ChessMove chessMove) {
        board[chessMove.getPos().getRow()][chessMove.getPos().getColumn()] = ChessType.NONE;
    }

    @Override
    public int NegaSearch(ChessType[][] board, int alpha, int beta, int depth) {
        int side;
        ChessType type;
        boolean isWhiteTurn;
        side = (maxSearchDepth - depth) % 2;
        if (side == 0) {
            isWhiteTurn = true;
            type = ChessType.WHITE;
        } else {
            isWhiteTurn = false;
            type = ChessType.BLACK;
        }
        int isGameOver = isGameOver(board, depth);
        if (isGameOver != 0) {
            return isGameOver;
        }
        if (depth == 0) {
            int score = evaluator.evaluate(board, isWhiteTurn);
            return score;
        }
        ArrayList<ChessMove> moveList = baseMoveGenerator.createPossibleMove(board, depth, isWhiteTurn);
        for (int i = 0; i < moveList.size(); i++) {
            makeMove(moveList.get(i), type);
            int score = -NegaSearch(board, -alpha, -beta, depth - 1);
            unMakeMove(moveList.get(i));
            if (score > alpha) {
                alpha = score;
                bestMove = moveList.get(i);
            }
            if (alpha >= beta) {
                break;
            }
        }
        return alpha;
    }
}
