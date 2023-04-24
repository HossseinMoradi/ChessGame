import java.util.ArrayList;
import java.util.List;

public class King extends Piece {
    public King(Color color) {
        super(color);
    }

    @Override
    public String getSymbol() {
        return "K";
    }

    @Override
    public List<Move> getLegalMoves(Board board, Spot spot) {
        int x = spot.getX();
        int y = spot.getY();
        List<Move> legalMoves = new ArrayList<>();

        // check all possible moves
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int newX = x + i;
                int newY = y + j;
                if (newX >= 0 && newX < Board.SIZE && newY >= 0 && newY < Board.SIZE) {
                    Spot destSpot = board.getSpot(newX, newY);
                    Piece destPiece = destSpot.getPiece();
                    if (destPiece == null || destPiece.getColor() != getColor()) {
                        legalMoves.add(new Move(spot, destSpot, this, destPiece, false, false, board));
                    }
                }
            }
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

        // A king can move one square in any direction.
        if (deltaX <= 1 && deltaY <= 1) {
            // Check if the destination square is empty or occupied by an opponent's piece.
            Piece destPiece = board.getSpot(toX, toY).getPiece();
            if (destPiece == null || destPiece.getColor() != getColor()) {
                return true;
            }
        }

        // Invalid move
        return false;
    }
}
