import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {
    public Pawn(Color color) {
        super(color);
    }

    @Override
    public List<Move> getLegalMoves(Board board, Spot spot) {
        int x = spot.getX();
        int y = spot.getY();
        List<Move> legalMoves = new ArrayList<>();

        int direction = (getColor() == Color.WHITE) ? -1 : 1;
        Spot forwardOneSpot = board.getSpot(x + direction, y);
        Spot forwardTwoSpot = board.getSpot(x + 2 * direction, y);
        Spot leftDiagonalSpot = board.getSpot(x + direction, y - 1);
        Spot rightDiagonalSpot = board.getSpot(x + direction, y + 1);

        // Check the forward one spot
        if (forwardOneSpot.getPiece() == null) {
            legalMoves.add(new Move(spot, forwardOneSpot, this, null, false, false, board));
            // Check the forward two spot
            if (((getColor() == Color.WHITE && x == 6) || (getColor() == Color.BLACK && x == 1))
                    && forwardTwoSpot.getPiece() == null) {
                legalMoves.add(new Move(spot, forwardTwoSpot, this, null, false, true, board));
            }
        }

        // Check the left diagonal spot
        if (y > 0 && leftDiagonalSpot.getPiece() != null
                && leftDiagonalSpot.getPiece().getColor() != getColor()) {
            legalMoves.add(new Move(spot, leftDiagonalSpot, this, leftDiagonalSpot.getPiece(), true, false, board));
        }

        // Check the right diagonal spot
        if (y < Board.SIZE - 1 && rightDiagonalSpot.getPiece() != null
                && rightDiagonalSpot.getPiece().getColor() != getColor()) {
            legalMoves.add(new Move(spot, rightDiagonalSpot, this, rightDiagonalSpot.getPiece(), true, false, board));
        }

        return legalMoves;
    }

    @Override
    public boolean isValidMove(Board board, Spot from, Spot to) {
        int fromX = from.getX();
        int fromY = from.getY();
        int toX = to.getX();
        int toY = to.getY();

        int deltaX = Math.abs(fromX - toX);
        int deltaY = Math.abs(fromY - toY);

        // Check if the destination spot is occupied by own piece
        if (board.getSpot(toX, toY).getPiece() != null && board.getSpot(toX, toY).getPiece().getColor() == getColor()) {
            return false;
        }

        if (fromY == toY) {
            // Moving one square forward
            if (getColor() == Color.WHITE) {
                if (deltaX == 1) {
                    return board.getSpot(toX, toY).getPiece() == null;
                } else if (deltaX == 2 && fromX == 6) {
                    // Moving two squares forward from the starting position
                    return board.getSpot(fromX - 1, fromY).getPiece() == null && board.getSpot(toX, toY).getPiece() == null;
                }
            } else {
                if (deltaX == 1) {
                    return board.getSpot(toX, toY).getPiece() == null;
                } else if (deltaX == 2 && fromX == 1) {
                    // Moving two squares forward from the starting position
                    return board.getSpot(fromX + 1, fromY).getPiece() == null && board.getSpot(toX, toY).getPiece() == null;
                }
            }
        } else if (deltaX == 1 && deltaY == 1) {
            // Capturing an opponent's piece diagonally
            if (getColor() == Color.WHITE) {
                return board.getSpot(toX, toY).getPiece() != null && board.getSpot(toX, toY).getPiece().getColor() == Color.BLACK;
            } else {
                return board.getSpot(toX, toY).getPiece() != null && board.getSpot(toX, toY).getPiece().getColor() == Color.WHITE;
            }
        }

        // Invalid move
        return false;
    }

    @Override
    public String getSymbol() {
        return "P";
    }

}
