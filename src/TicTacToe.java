import java.util.Scanner;

public class TicTacToe
{
    private static final int ROWS = 3;
    private static final int COLS = 3;
    private static String board[][] = new String[ROWS][COLS];

    public static void main(String[] args)
    {
        /*
         * PSEUDO CODE
         *
         * create Scanner
         * do (play again loop)
         *     clear the board
         *     set player to X (X always moves first)
         *     set move counter to 0, done = false
         *     do (one turn per loop)
         *         display the board
         *         do (until the move is valid)
         *             get row (1-3) and col (1-3) from the current player
         *             convert row and col to array indices by subtracting 1
         *             if the space is not empty, tell the player it is not legal
         *         while the move is not valid
         *         record the move (player's mark) on the board
         *         increment the move counter
         *         if a win is possible (moveCnt >= 5)
         *             if isWin(player): display board, announce winner, done = true
         *         if a tie is possible (moveCnt >= 7) and not done
         *             if isTie(): display board, announce tie, done = true
         *         if not done, toggle the player (X <-> O)
         *     while not done
         *     ask the players if they want to play again
         * while they want to play again
         */

        Scanner in = new Scanner(System.in);
        String player;
        int moveCnt;
        int rowMove;
        int colMove;
        boolean gameOver;
        boolean playAgain;

        do
        {
            clearBoard();
            player = "X";
            moveCnt = 0;
            gameOver = false;

            do
            {
                display();
                System.out.println("\nPlayer " + player + ", it is your move.");

                do
                {
                    rowMove = SafeInput.getRangedInt(in, "Enter the row", 1, ROWS) - 1;
                    colMove = SafeInput.getRangedInt(in, "Enter the column", 1, COLS) - 1;
                    if (!isValidMove(rowMove, colMove))
                    {
                        System.out.println("That space is already taken. Pick an empty space.");
                    }
                } while (!isValidMove(rowMove, colMove));

                board[rowMove][colMove] = player;
                moveCnt++;

                // A win needs at least 5 moves total (3 by one player)
                if (moveCnt >= 5 && isWin(player))
                {
                    display();
                    System.out.println("\nPlayer " + player + " wins!");
                    gameOver = true;
                }
                // A tie can't be declared before 7 moves are made
                else if (moveCnt >= 7 && isTie())
                {
                    display();
                    System.out.println("\nIt's a tie!");
                    gameOver = true;
                }
                else
                {
                    player = player.equals("X") ? "O" : "X";
                }
            } while (!gameOver);

            playAgain = SafeInput.getYNConfirm(in, "Would you like to play again?");
        } while (playAgain);

        System.out.println("\nThanks for playing!");
    }

    // sets all the board elements to a space
    private static void clearBoard()
    {
        for (int row = 0; row < ROWS; row++)
        {
            for (int col = 0; col < COLS; col++)
            {
                board[row][col] = " ";
            }
        }
    }

    // shows the Tic Tac Toe board
    private static void display()
    {
        System.out.println();
        System.out.println("     1   2   3");
        for (int row = 0; row < ROWS; row++)
        {
            System.out.print("  " + (row + 1) + "  ");
            for (int col = 0; col < COLS; col++)
            {
                System.out.print(board[row][col]);
                if (col < COLS - 1)
                {
                    System.out.print(" | ");
                }
            }
            System.out.println();
            if (row < ROWS - 1)
            {
                System.out.println("     --+---+--");
            }
        }
    }

    // returns true if the space at the proposed move is empty
    private static boolean isValidMove(int row, int col)
    {
        return board[row][col].equals(" ");
    }

    // checks for a win state for the given player (X or O)
    private static boolean isWin(String player)
    {
        return isColWin(player) || isRowWin(player) || isDiagnalWin(player);
    }

    // checks for a column win for the given player
    private static boolean isColWin(String player)
    {
        for (int col = 0; col < COLS; col++)
        {
            boolean allMatch = true;
            for (int row = 0; row < ROWS; row++)
            {
                if (!board[row][col].equals(player))
                {
                    allMatch = false;
                }
            }
            if (allMatch)
            {
                return true;
            }
        }
        return false;
    }

    // checks for a row win for the given player
    private static boolean isRowWin(String player)
    {
        for (int row = 0; row < ROWS; row++)
        {
            boolean allMatch = true;
            for (int col = 0; col < COLS; col++)
            {
                if (!board[row][col].equals(player))
                {
                    allMatch = false;
                }
            }
            if (allMatch)
            {
                return true;
            }
        }
        return false;
    }

    // checks for a diagonal win for the given player
    private static boolean isDiagnalWin(String player)
    {
        boolean mainDiag = true;
        boolean antiDiag = true;
        for (int i = 0; i < ROWS; i++)
        {
            if (!board[i][i].equals(player))
            {
                mainDiag = false;
            }
            if (!board[i][COLS - 1 - i].equals(player))
            {
                antiDiag = false;
            }
        }
        return mainDiag || antiDiag;
    }

    // a tie exists when every possible win vector contains both an X and an O
    // (this also covers a completely full board with no winner)
    private static boolean isTie()
    {
        // rows
        for (int row = 0; row < ROWS; row++)
        {
            boolean hasX = false;
            boolean hasO = false;
            for (int col = 0; col < COLS; col++)
            {
                if (board[row][col].equals("X")) hasX = true;
                if (board[row][col].equals("O")) hasO = true;
            }
            if (!(hasX && hasO)) return false;
        }

        // columns
        for (int col = 0; col < COLS; col++)
        {
            boolean hasX = false;
            boolean hasO = false;
            for (int row = 0; row < ROWS; row++)
            {
                if (board[row][col].equals("X")) hasX = true;
                if (board[row][col].equals("O")) hasO = true;
            }
            if (!(hasX && hasO)) return false;
        }

        // main diagonal and anti-diagonal
        boolean mainX = false, mainO = false, antiX = false, antiO = false;
        for (int i = 0; i < ROWS; i++)
        {
            if (board[i][i].equals("X")) mainX = true;
            if (board[i][i].equals("O")) mainO = true;
            if (board[i][COLS - 1 - i].equals("X")) antiX = true;
            if (board[i][COLS - 1 - i].equals("O")) antiO = true;
        }
        if (!(mainX && mainO)) return false;
        if (!(antiX && antiO)) return false;

        return true;
    }
}
