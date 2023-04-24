import java.util.List;

public abstract class Piece {
    private int x;
    private int y;
    private Color color;

    // constructor and other methods

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String getImagePath() {
        String color = getColor() == Color.WHITE ? "white" : "black";
        String type = getType().toLowerCase();
        return "icons/" + color + "_" + type + ".png";
    }

    public Piece(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public abstract boolean isValidMove(Board board, Spot from, Spot to);

    public abstract String getSymbol();

    public String getType() {
        String symbol = getSymbol();
        switch (symbol) {
            case "P":
                return "Pawn";
            case "R":
                return "Rook";
            case "N":
                return "Knight";
            case "B":
                return "Bishop";
            case "Q":
                return "Queen";
            case "K":
                return "King";
            default:
                throw new IllegalStateException("Unexpected value: " + symbol);
        }
    }
    public abstract List<Move> getLegalMoves(Board board, Spot spot);

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
}
