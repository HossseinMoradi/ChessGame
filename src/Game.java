public class Game {
    public static void main(String[] args) {
        Board board = new Board();
        HumanPlayer humanPlayer = new HumanPlayer(Color.WHITE);
        ComputerPlayer computerPlayer = new ComputerPlayer(Color.BLACK);

        GameStatus status = GameStatus.IN_PROGRESS;
        Player currentPlayer = humanPlayer;

        while (status == GameStatus.IN_PROGRESS) {
            board.display();

            Move move;
            if (currentPlayer instanceof HumanPlayer) {
                move = humanPlayer.getNextMove(board);
            } else {
                move = computerPlayer.getNextMove(board);
            }

            if (board.isValidMove(move)) {
                board.movePiece(move);
                currentPlayer = (currentPlayer == humanPlayer) ? computerPlayer : humanPlayer;
                status = board.getGameStatus(currentPlayer);
            } else {
                System.out.println("Invalid move. Try again.");
            }
        }

        System.out.println(status);
    }
}
