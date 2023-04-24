import java.util.ArrayList;
import java.util.List;

public class Board {
    public static final int SIZE = 8;
    private Spot[][] spots = new Spot[8][8];
    private List<Move> moveHistory = new ArrayList<>();

    public Board() {
        initializeBoard();
        initializePieces();
    }

    public void initializeBoard() {
        // Initialize spots with null pieces
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                spots[i][j] = new Spot(i, j, null);
            }
        }
    }

    public void initializePieces() {
        // Initialize white pieces
        spots[0][0].setPiece(new Rook(Color.WHITE));
        spots[0][1].setPiece(new Knight(Color.WHITE));
        spots[0][2].setPiece(new Bishop(Color.WHITE));
        spots[0][3].setPiece(new Queen(Color.WHITE));
        spots[0][4].setPiece(new King(Color.WHITE));
        spots[0][5].setPiece(new Bishop(Color.WHITE));
        spots[0][6].setPiece(new Knight(Color.WHITE));
        spots[0][7].setPiece(new Rook(Color.WHITE));
        for (int i = 0; i < 8; i++) {
            spots[1][i].setPiece(new Pawn(Color.WHITE));
        }

        // Initialize black pieces
        spots[7][0].setPiece(new Rook(Color.BLACK));
        spots[7][1].setPiece(new Knight(Color.BLACK));
        spots[7][2].setPiece(new Bishop(Color.BLACK));
        spots[7][3].setPiece(new Queen(Color.BLACK));
        spots[7][4].setPiece(new King(Color.BLACK));
        spots[7][5].setPiece(new Bishop(Color.BLACK));
        spots[7][6].setPiece(new Knight(Color.BLACK));
        spots[7][7].setPiece(new Rook(Color.BLACK));
        for (int i = 0; i < 8; i++) {
            spots[6][i].setPiece(new Pawn(Color.BLACK));
        }
    }

    public Spot getSpot(int x, int y) {
        return spots[x][y];
    }

    public boolean isValidMove(Move move) {
        // Check if move is within board bounds
        if (move.getFromX() < 0 || move.getFromX() > 7 ||
                move.getFromY() < 0 || move.getFromY() > 7 ||
                move.getToX() < 0 || move.getToX() > 7 ||
                move.getToY() < 0 || move.getToY() > 7) {
            return false;
        }

        // Check if move is valid for the piece at the starting spot
        Spot fromSpot = spots[move.getFromX()][move.getFromY()];
        Spot toSpot = spots[move.getToX()][move.getToY()];
        Piece piece = fromSpot.getPiece();
        if (piece == null || !piece.isValidMove(this, fromSpot, toSpot)) {
            return false;
        }

        return true;
    }


    public boolean isCheck(Color color) {
        // Find the king of the specified color
        Piece king = null;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece piece = spots[i][j].getPiece();
                if (piece != null && piece instanceof King && piece.getColor() == color) {
                    king = piece;
                    break;
                }
            }
            if (king != null) {
                break;
            }
        }

        // Check if any of the opponent's pieces can attack the king
        Color opponentColor = color.opposite();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece piece = spots[i][j].getPiece();
                if (piece != null && piece.getColor() == opponentColor) {
                    Move move = new Move(spots[i][j], spots[king.getX()][king.getY()], piece, null, false, false, this);
                    if (piece.isValidMove(this, spots[i][j], spots[king.getX()][king.getY()])) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public King findKing(Color color) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece piece = spots[i][j].getPiece();
                if (piece != null && piece instanceof King && piece.getColor() == color) {
                    return (King) piece;
                }
            }
        }
        return null;
    }


    public List<Piece> getPieces(Color color) {
        List<Piece> pieces = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Spot spot = spots[i][j];
                Piece piece = spot.getPiece();
                if (piece != null && piece.getColor() == color) {
                    pieces.add(piece);
                }
            }
        }

        return pieces;
    }


    public boolean isCheckmate(Color color) {
        if (!isCheck(color)) {
            // The color is not in check, so it can't be in checkmate
            return false;
        }

        // Find the king of the specified color
        King king = findKing(color);
        if (king == null) {
            // There is no king of the specified color, so the game can't continue
            throw new IllegalStateException("No king of color " + color + " found on the board");
        }



        // Check if the king can move to any safe spot
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) {
                    continue;
                }
                int newX = king.getX() + i;
                int newY = king.getY() + j;
                if (newX < 0 || newX > 7 || newY < 0 || newY > 7) {
                    continue;
                }
                Move move = new Move(getSpot(king.getX(), king.getY()), getSpot(newX, newY), king, null, false, false, this);
                if (isValidMove(move)) {
                    // The king can move to a safe spot, so the color is not in checkmate
                    return false;
                }
            }
        }

        // Check if any piece of the specified color can move to block the check or capture the attacker
        List<Piece> pieces = getPieces(color);
        for (Piece piece : pieces) {
            List<Move> moves = piece.getLegalMoves(this, getSpot(piece.getX(), piece.getY()));
            for (Move move : moves) {
                if (isValidMove(move)) {
                    // The piece can move to block the check or capture the attacker, so the color is not in checkmate
                    return false;
                }
            }
        }

        // The color is in checkmate
        return true;
    }

    public boolean isStalemate(Player currentPlayer) {
        // Check if any pieces of the specified color have a legal move
        Color color = currentPlayer.getColor();
        List<Piece> pieces = getPieces(color);
        for (Piece piece : pieces) {
            List<Move> moves = piece.getLegalMoves(this, getSpot(piece.getX(), piece.getY()));
            for (Move move : moves) {
                if (isValidMove(move)) {
                    return false;
                }
            }
        }

        // The color is in stalemate
        return true;
    }


    public void display() {
        System.out.println("   A B C D E F G H");
        System.out.println("  -----------------");
        for (int i = 0; i < 8; i++) {
            System.out.print((8 - i) + "| ");
            for (int j = 0; j < 8; j++) {
                Piece piece = spots[i][j].getPiece();
                if (piece == null) {
                    System.out.print("  ");
                } else {
                    System.out.print(piece.getSymbol() + " ");
                }
            }
            System.out.println("|" + (8 - i));
        }
        System.out.println("  -----------------");
        System.out.println("   A B C D E F G H");
    }

    public GameStatus getGameStatus(Player currentPlayer) {
        if (isCheckmate(currentPlayer.getColor())) {
            if (currentPlayer.getColor() == Color.WHITE) {
                return GameStatus.BLACK_WIN;
            } else {
                return GameStatus.WHITE_WIN;
            }
        } else if (isStalemate(currentPlayer)) {
            return GameStatus.STALEMATE;
        } else if (isCheck(currentPlayer.getColor())) {
            return GameStatus.IN_PROGRESS;
        } else {
            return GameStatus.ACTIVE;
        }
    }




    public void movePiece(Move move) {
        Spot fromSpot = spots[move.getFromX()][move.getFromY()];
        Spot toSpot = spots[move.getToX()][move.getToY()];
        Piece piece = fromSpot.getPiece();

        // If there's a piece at the destination spot, capture it
        if (toSpot.getPiece() != null) {
            move.setPieceKilled(toSpot.getPiece());
        }

        // Move the piece to the destination spot
        toSpot.setPiece(piece);
        fromSpot.setPiece(null);

        // Update piece's position
        piece.setPosition(toSpot.getX(), toSpot.getY());

        // Update move with new piece position and other details
        move.setPieceMoved(piece);
        move.setBoard(this);

        // Check if move results in a check or checkmate
        if (isCheck(piece.getColor().opposite())) {
            move.setCheck(true);
            if (isCheckmate(piece.getColor().opposite())) {
                move.setCheckmate(true);
            }
        }
    }
}



