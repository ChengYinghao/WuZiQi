package logic.ai;

import logic.chessboard.CYHChessboard;
import logic.chessboard.ChessType;

import java.util.ArrayList;

public class SearchEngine implements BaseSearchEngine {
    int maxSearchDepth = 3;
    CYHChessboard cyhChessboard = new CYHChessboard(15, 15);
    BaseEvaluator evaluator = new Evaluator();
    BaseMoveGenerator baseMoveGenerator = new MoveGenerator();
    ChessMove bestMove;

    @Override
    public ChessMove searchABestMove(ChessType[][] board, boolean isWhiteTurn) {
        bestMove = null;
        NegaSearch(-2000, 2000, maxSearchDepth);
        return bestMove;
    }

    @Override
    public void setSearch(int depth) {
        maxSearchDepth = depth;
    }

    @Override
    public int isGameOver(ChessType[][] board, int depth) {
        boolean isWhiteTurn;
        int score;
        int i;
        i = (maxSearchDepth - depth) % 2;
        isWhiteTurn = i != 0;
        score = evaluator.evaluate(cyhChessboard.board, isWhiteTurn);
        if (Math.abs(score) > 8000) {
            return score;
        }
        return 0;
    }

    @Override
    public void makeMove(ChessMove chessMove, ChessType type) {
        cyhChessboard.board[chessMove.getPos().getRow()][chessMove.getPos().getColumn()] = type;
    }

    @Override
    public void unMakeMove(ChessMove chessMove) {
        cyhChessboard.board[chessMove.getPos().getRow()][chessMove.getPos().getColumn()] = ChessType.NONE;
    }

    @Override
    public int NegaSearch(int alpha, int beta, int depth) {
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
        int isGameOver = isGameOver(cyhChessboard.board, depth);
        if (isGameOver != 0) {
            bestMove.setScore(isGameOver);
            return isGameOver;
        }
        if (depth  == 0) {
            int score = evaluator.evaluate(cyhChessboard.board, isWhiteTurn);
            return score;
        }
        ArrayList<ChessMove> moveList = baseMoveGenerator.createPossibleMove(cyhChessboard.board, depth, isWhiteTurn);
        for (int i = 0; i < moveList.size(); i++) {
            makeMove(moveList.get(i), type);
            int score = -NegaSearch(-alpha, -beta, depth - 1);
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
