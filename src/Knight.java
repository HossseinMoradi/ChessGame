import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece {
    public Knight(Color color) {
        super(color);
    }

    @Override
    public String getSymbol() {
        return "N";
    }

    @Override
    public List<Move> getLegalMoves(Board board, Spot spot) {
        int x = spot.getX();
        int y = spot.getY();
        List<Move> legalMoves = new ArrayList<>();

        // check all possible moves
        for (int i = -2; i <= 2; i++) {
            for (int j = -2; j <= 2; j++) {
                if (Math.abs(i * j) == 2) {
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

        // A knight can move two squares in one direction and one square in the other direction.
        if ((deltaX == 2 && deltaY == 1) || (deltaX == 1 && deltaY == 2)) {
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

