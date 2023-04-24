import java.util.ArrayList;
import java.util.List;

public class Rook extends Piece {
    public Rook(Color color) {
        super(color);
    }
    @Override
    public String getSymbol() {
        if (getColor() == Color.WHITE) {
            return "\u2656"; // white rook Unicode character
        } else {
            return "\u265C"; // black rook Unicode character
        }
    }


    @Override
    public boolean isValidMove(Board board, Spot from, Spot to) {
        int fromX = from.getX();
        int fromY = from.getY();
        int toX = to.getX();
        int toY = to.getY();

        if (fromX == toX) {
            // Moving horizontally
            int min = Math.min(fromY, toY);
            int max = Math.max(fromY, toY);
            for (int y = min + 1; y < max; y++) {
                if (board.getSpot(fromX, y).getPiece() != null) {
                    return false;
                }
            }
            return true;
        } else if (fromY == toY) {
            // Moving vertically
            int min = Math.min(fromX, toX);
            int max = Math.max(fromX, toX);
            for (int x = min + 1; x < max; x++) {
                if (board.getSpot(x, fromY).getPiece() != null) {
                    return false;
                }
            }
            return true;
        }

        // Invalid move
        return false;
    }
    @Override
    public List<Move> getLegalMoves(Board board, Spot spot) {
        List<Move> legalMoves = new ArrayList<>();

        int x = spot.getX();
        int y = spot.getY();

        // check all squares in the same row as the rook
        for (int i = y + 1; i < Board.SIZE; i++) {
            Spot destSpot = board.getSpot(x, i);
            Piece destPiece = destSpot.getPiece();
            if (destPiece == null) {
                legalMoves.add(new Move(spot, destSpot, this, destPiece, false, false, board));
            } else if (destPiece.getColor() != getColor()) {
                legalMoves.add(new Move(spot, destSpot, this, destPiece, true, false, board));
                break;
            } else {
                break;
            }
        }

        for (int i = y - 1; i >= 0; i--) {
            Spot destSpot = board.getSpot(x, i);
            Piece destPiece = destSpot.getPiece();
            if (destPiece == null) {
                legalMoves.add(new Move(spot, destSpot, this, destPiece, false, false, board));
            } else if (destPiece.getColor() != getColor()) {
                legalMoves.add(new Move(spot, destSpot, this, destPiece, true, false, board));
                break;
            } else {
                break;
            }
        }

        // check all squares in the same column as the rook
        for (int i = x + 1; i < Board.SIZE; i++) {
            Spot destSpot = board.getSpot(i, y);
            Piece destPiece = destSpot.getPiece();
            if (destPiece == null) {
                legalMoves.add(new Move(spot, destSpot, this, destPiece, false, false, board));
            } else if (destPiece.getColor() != getColor()) {
                legalMoves.add(new Move(spot, destSpot, this, destPiece, true, false, board));
                break;
            } else {
                break;
            }
        }

        for (int i = x - 1; i >= 0; i--) {
            Spot destSpot = board.getSpot(i, y);
            Piece destPiece = destSpot.getPiece();
            if (destPiece == null) {
                legalMoves.add(new Move(spot, destSpot, this, destPiece, false, false, board));
            } else if (destPiece.getColor() != getColor()) {
                legalMoves.add(new Move(spot, destSpot, this, destPiece, true, false, board));
                break;
            } else {
                break;
            }
        }

        return legalMoves;
    }
}