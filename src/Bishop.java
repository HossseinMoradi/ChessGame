import java.util.ArrayList;
import java.util.List;

public class Bishop extends Piece {
    public Bishop(Color color) {
        super(color);
    }

    @Override
    public String getSymbol() {
        return "B";
    }

    @Override
    public List<Move> getLegalMoves(Board board, Spot spot) {
        int x = spot.getX();
        int y = spot.getY();
        List<Move> legalMoves = new ArrayList<>();

        // check all possible moves
        for (int i = -1; i <= 1; i += 2) {
            for (int j = -1; j <= 1; j += 2) {
                int newX = x + i;
                int newY = y + j;
                while (newX >= 0 && newX < Board.SIZE && newY >= 0 && newY < Board.SIZE) {
                    Spot destSpot = board.getSpot(newX, newY);
                    Piece destPiece = destSpot.getPiece();
                    if (destPiece == null) {
                        legalMoves.add(new Move(spot, destSpot, this, destPiece, false, false, board));
                    } else if (destPiece.getColor() != getColor()) {
                        legalMoves.add(new Move(spot, destSpot, this, destPiece, true, false, board));
                        break;
                    } else {
                        break;
                    }
                    newX += i;
                    newY += j;
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

        // A bishop can move diagonally in any direction.
        if (deltaX == deltaY) {
            int xDir = (toX - fromX > 0) ? 1 : -1;
            int yDir = (toY - fromY > 0) ? 1 : -1;

            int x = fromX + xDir;
            int y = fromY + yDir;

            // Check that all spots between the starting and ending coordinates are empty.
            while (x != toX && y != toY) {
                if (board.getSpot(x, y).getPiece() != null) {
                    return false;
                }
                x += xDir;
                y += yDir;
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
}
