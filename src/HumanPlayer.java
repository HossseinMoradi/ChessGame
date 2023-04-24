import java.util.Scanner;

public class HumanPlayer extends Player {
    public HumanPlayer(Color color) {
        super(color);
    }

    @Override
    public Move getNextMove(Board board) {
        Scanner scanner = new Scanner(System.in);

        // Get the start and end positions of the move from the user
        System.out.println("Enter the start and end positions (e.g. e2 e4):");
        String input = scanner.nextLine();
        String[] positions = input.split(" ");
        int startX = positions[0].charAt(0) - 'a';
        int startY = Integer.parseInt(positions[0].substring(1)) - 1;
        int endX = positions[1].charAt(0) - 'a';
        int endY = Integer.parseInt(positions[1].substring(1)) - 1;

        // Create the move object and return it
        Spot startSpot = board.getSpot(startX, startY);
        Spot endSpot = board.getSpot(endX, endY);
        return new Move(startSpot, endSpot, startSpot.getPiece(), endSpot.getPiece(), board.isCheck(getColor()), board.isCheckmate(getColor()), board);
    }

}