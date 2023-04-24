import java.util.ArrayList;
import java.util.List;

public class Queen extends Piece {
    public Queen(Color color) {
        super(color);
    }

    @Override
    public String getSymbol() {
        return "Q";
    }

    @Override
    public boolean isValidMove(Board board, Spot from, Spot to) {
        int fromX = from.getX();
        int fromY = from.getY();
        int toX = to.getX();
        int toY = to.getY();

        int deltaX = Math.abs(fromX - toX);
        int deltaY = Math.abs(fromY - toY);

        // A queen can move horizontally, vertically, or diagonally.
        if (fromX == toX || fromY == toY || deltaX == deltaY) {
            if (fromX == toX) {
                // Moving horizontally
                int min = Math.min(fromY, toY);
                int max = Math.max(fromY, toY);
                for (int y = min + 1; y < max; y++) {
                    if (board.getSpot(fromX, y).getPiece() != null) {
                        return false;
                    }
                }
            } else if (fromY == toY) {
                // Moving vertically
                int min = Math.min(fromX, toX);
                int max = Math.max(fromX, toX);
                for (int x = min + 1; x < max; x++) {
                    if (board.getSpot(x, fromY).getPiece() != null) {
                        return false;
                    }
                }
            } else {
                // Moving diagonally
                int xDir = (toX - fromX > 0) ? 1 : -1;
                int yDir = (toY - fromY > 0) ? 1 : -1;

                int x = fromX + xDir;
                int y = fromY + yDir;

                while (x != toX && y != toY) {
                    if (board.getSpot(x, y).getPiece() != null) {
                        return false;
                    }
                    x += xDir;
                    y += yDir;
                }
            }

            // Check if the destination square is empty or occupied by an opponent's piece.
            Piece destPiece = board.getSpot(toX, toY).getPiece();
            if (destPiece == null || destPiece.getColor() != getColor()) {
                return true;
            }
        }

        // Invalid move
        return false;
    }

    @Override
    public List<Move> getLegalMoves(Board board, Spot spot) {
        List<Move> legalMoves = new ArrayList<>();

        // Get the legal moves for a bishop
        Bishop bishop = new Bishop(getColor());
        legalMoves.addAll(bishop.getLegalMoves(board, spot));

        // Get the legal moves for a rook
        Rook rook = new Rook(getColor());
        legalMoves.addAll(rook.getLegalMoves(board, spot));

        return legalMoves;
    }
}
