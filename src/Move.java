public class Move {
    private Spot start;
    private Spot end;
    private Piece pieceMoved;
    private Piece pieceKilled;
    private boolean isCheck;
    private boolean isCheckmate;
    private Board board;

    public Move(Spot start, Spot end, Piece pieceMoved, Piece pieceKilled, boolean isCheck, boolean isCheckmate, Board board) {
        this.start = start;
        this.end = end;
        this.pieceMoved = pieceMoved;
        this.pieceKilled = pieceKilled;
        this.isCheck = isCheck;
        this.isCheckmate = isCheckmate;
        this.board = board;
    }

    public Spot getStart() {
        return start;
    }

    public void setStart(Spot start) {
        this.start = start;
    }

    public Spot getEnd() {
        return end;
    }

    public void setEnd(Spot end) {
        this.end = end;
    }

    public Piece getPieceMoved() {
        return pieceMoved;
    }

    public void setPieceMoved(Piece pieceMoved) {
        this.pieceMoved = pieceMoved;
    }

    public Piece getPieceKilled() {
        return pieceKilled;
    }

    public void setPieceKilled(Piece pieceKilled) {
        this.pieceKilled = pieceKilled;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public boolean isCheckmate() {
        return isCheckmate;
    }

    public void setCheckmate(boolean checkmate) {
        isCheckmate = checkmate;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public boolean isValid() {
        return board.isValidMove(this);
    }


    public int getFromX() {
        return start.getX();
    }

    public int getFromY() {
        return start.getY();
    }

    public int getToX() {
        return end.getX();
    }

    public int getToY() {
        return end.getY();
    }


    public void make() {
        // Remove piece from starting spot
        start.setPiece(null);

        // If there's a piece at the destination spot, capture it
        if (end.getPiece() != null) {
            pieceKilled = end.getPiece();
        }

        // Move the piece to the destination spot
        end.setPiece(pieceMoved);

    }

    public void undo() {
        // Move the piece back to the starting spot
        start.setPiece(pieceMoved);

        // If a piece was captured, restore it
        if (pieceKilled != null) {
            end.setPiece(pieceKilled);
        } else {
            end.setPiece(null);
        }
    }

    @Override
    public String toString() {
        String result = pieceMoved.getType() + " from " + start + " to " + end;
        if (pieceKilled != null) {
            result += " (captured " + pieceKilled.getType() + ")";
        }
        if (isCheck) {
            result += " (check)";
        }
        if (isCheckmate) {
            result += " (checkmate)";
        }
        return result;
    }
}

