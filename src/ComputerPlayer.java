import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ComputerPlayer extends Player {

    public ComputerPlayer(Color color) {
        super(color);
    }

    @Override
    public Move getNextMove(Board board) {
        // Generate a list of all possible moves for the computer player
        List<Move> moves = new ArrayList<>();
        for (Piece piece : board.getPieces(getColor())) {
            moves.addAll(piece.getLegalMoves(board, board.getSpot(piece.getX(), piece.getY())));
        }

        // Choose a random move from the list of possible moves
        Random rand = new Random();
        int randomIndex = rand.nextInt(moves.size());
        return moves.get(randomIndex);
    }
}
