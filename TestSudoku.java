/*Given below is a Java solution to generate, validate and solve a NxN Sudoku puzzle with visualization, tracking and 100% readable code.
This solves an easy 9x9 in 1 ms and solves an evil 9x9 in 140 ms (mine is a 6th generation core i7). It has a random board generator which sometimes gives you unsolvable boards or boards that take forever to solve. The 16x16 board generator will mostly give you unsolvable or too-long-to-solve boards. Luckily it also has tracking and visualization capabilities for you to track its (occasionally) painful progress. Please feel free to euthanize it when you see fit.
The tracking and visualization makes watching the program solve the puzzle more interesting than solving the board yourself. This is in addition to the perfectly modular and readable code which makes it easy to follow the logic and optimize / customize further.*/

import java.util.*;

public class TestSudoku {
    public static final int WIDTH_9X9 = 9;
    public static final int WIDTH_16X16 = 16;

    public static int BOARD_WIDTH = WIDTH_16X16;
    public static int SUB_WIDTH = ((int) Math.sqrt(BOARD_WIDTH));
    public static char START_CHAR = 'A';

    public static long nSolutionTracker = 0;

    public static void init_board_properties(char[][] board) {
        BOARD_WIDTH = board.length;
        SUB_WIDTH = ((int) Math.sqrt(BOARD_WIDTH));

        if (WIDTH_9X9 == BOARD_WIDTH) {
            START_CHAR = '1';
        } else if (WIDTH_16X16 == BOARD_WIDTH) {
            START_CHAR = 'A';
        } else {
            // use defaults
        }
    }

    public static char[] getHorizontalSubArray(char[][] board, int ix) {
        char[] subarray = new char[board.length];

        for (int i = 0; i < board.length; i++) {
            subarray[i] = board[ix][i];
        }
        return subarray;
    }

    public static char[] getVerticalSubArray(char[][] board, int ix) {
        char[] subarray = new char[board.length];

        for (int i = 0; i < board.length; i++) {
            subarray[i] = board[i][ix];
        }
        return subarray;
    }

    public static char[] getMxMSubArray(char[][] board, int ix) {
        char[] subarray = new char[board.length];

        int cOffset = SUB_WIDTH * (ix % SUB_WIDTH);
        int rOffset = SUB_WIDTH * (ix / SUB_WIDTH);

        int i = 0;
        for (int r = 0; r < board.length / SUB_WIDTH; r++) {
            for (int c = 0; c < board.length / SUB_WIDTH; c++) {
                subarray[i] = board[rOffset + r][cOffset + c];
                i++;
            }
        }
        return subarray;
    }

    public static boolean checkSudokuSubarray(char[] array) {
        final int nBOARD_WIDTH = array.length;
        boolean[] temp = new boolean[nBOARD_WIDTH];

        for (int i = 0; i < nBOARD_WIDTH; i++) {
            if ((array[i] >= START_CHAR) && (array[i] <= (START_CHAR + nBOARD_WIDTH))) {
                int iPos = (array[i] - START_CHAR);
                if (false == temp[iPos]) {
                    temp[iPos] = true;
                } else {
                    return false;
                }
            } else if (array[i] == '.') {
                continue;
            } else {
                return false;
            }
        }

        return true;
    }

    public static boolean isValidSudoku(char[][] board) {
        if (null == board) {
            System.out.println("board is null.");
            return false;
        }
        if (board.length <= 0) {
            System.out.println("board.length is <= 0.");
            return false;
        }
        if (board[0].length != board.length) {
            System.out.println("board is not a perfect NxN square.");
            return false;
        }
        if (SUB_WIDTH * SUB_WIDTH != board.length) {
            System.out.println("board is not a perfect N=M*M square.");
            return false;
        }

        // check rows
        for (int i = 0; i < board.length; i++) {
            if (false == checkSudokuSubarray(getHorizontalSubArray(board, i))) {
                // System.out.format("Invalid Horizontal %d.\n", i);
                return false;
            }
        }
        // check columns
        for (int i = 0; i < board.length; i++) {
            if (false == checkSudokuSubarray(getVerticalSubArray(board, i))) {
                // System.out.format("Invalid Vertical %d.\n", i);
                return false;
            }
        }
        // check 3x3
        for (int i = 0; i < board.length; i++) {
            if (false == checkSudokuSubarray(getMxMSubArray(board, i))) {
                // System.out.format("Invalid 3x3 %d.\n", i);
                return false;
            }
        }

        return true;
    }

    public static void printHorizontalBorder(char[][] board) {
        for (int c = 0; c < board.length; c++) {
            if (0 == (c % SUB_WIDTH))
                System.out.format("-");
            System.out.format("--");
        }
        System.out.println("-");
    }

    public static void printBoard(char[][] board) {
        if (null == board)
            return;
        System.out.format("\n");
        for (int r = 0; r < board.length; r++) {
            if (0 == (r % SUB_WIDTH))
                printHorizontalBorder(board);
            for (int c = 0; c < board.length; c++) {
                if (0 == (c % SUB_WIDTH))
                    System.out.format("|");
                System.out.format("%2c", board[r][c]);
            }
            System.out.println("|");
        }
        printHorizontalBorder(board);
    }

    public static char[][] getRandomBoard(int N) {
        char[][] board = new char[N][N];
        char[] aNums = new char[N];

        init_board_properties(board);

        int iAttempt = 0;
        long starttime = System.currentTimeMillis();
        while (!isValidSudoku(board)) {
            iAttempt++;
            for (int ix = 0; ix < board.length; ix++) {
                for (char i = 0; i < board.length; i++) {
                    aNums[i] = (char) (START_CHAR + i);
                }

                int cOffset = SUB_WIDTH * (ix % SUB_WIDTH);
                int rOffset = SUB_WIDTH * (ix / SUB_WIDTH);

                int i = 0;
                for (int r = 0; r < board.length / SUB_WIDTH; r++) {
                    for (int c = 0; c < board.length / SUB_WIDTH; c++) {
                        int iRandom = (int) (Math.random() * (board.length - i) + i); // randomize
                        if ((Math.random() * BOARD_WIDTH) >= ((BOARD_WIDTH * 7 / 9))) // sparseness
                        {
                            board[rOffset + r][cOffset + c] = aNums[iRandom];
                        } else {
                            board[rOffset + r][cOffset + c] = '.';
                        }
                        aNums[iRandom] = aNums[i];
                        i++;
                    }
                }
            }
        }
        long stoptime = System.currentTimeMillis();
        System.out.format("Board generation took %d ms after %d attempts.\n", (stoptime - starttime), iAttempt);
        return board;
    }

    public static boolean canPutChar(char[][] board, int r, int c, char digit) {
        if ((r >= 0) && (r < board.length)) {
            if ((c >= 0) && (c < board.length)) {
                if ('.' == board[r][c]) {
                    board[r][c] = digit;
                    if (checkSudokuSubarray(getHorizontalSubArray(board, r)) &&
                            checkSudokuSubarray(getVerticalSubArray(board, c)) &&
                            checkSudokuSubarray(getMxMSubArray(board, SUB_WIDTH * (r / SUB_WIDTH) + c / SUB_WIDTH)) &&
                            solveBoard(board, r, c + 1)) {
                        return true;
                    } else {
                        board[r][c] = '.';
                        return false;
                    }
                } else {
                    // already contains a potentially valid digit
                    return true;
                }
            }
        }
        return true;
    }

    public static boolean isBoardSolved(char[][] board) {
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board.length; c++) {
                if ('.' == board[r][c])
                    return false;
            }
        }
        return isValidSudoku(board);
    }

    public static boolean solveBoard(char[][] board, int rStart, int cStart) {
        if (cStart >= board.length) {
            // roll over to the next row
            cStart = 0;
            rStart++;
        }

        long currentTime = System.currentTimeMillis();
        if (0 == nSolutionTracker)
            nSolutionTracker = currentTime;
        if ((currentTime - nSolutionTracker) > 5000) {
            nSolutionTracker = currentTime;
            System.out.format("\nSolved %d %%:", ((rStart * BOARD_WIDTH + cStart) * 100) / (BOARD_WIDTH * BOARD_WIDTH));
            printBoard(board);
        }

        boolean bPutChar = false;
        for (int r = rStart; r < board.length; r++) {
            for (int c = cStart; c < board.length; c++) {
                for (char i = 0; i < board.length; i++) {
                    bPutChar = canPutChar(board, r, c, (char) (START_CHAR + i));
                    if (bPutChar)
                        break; // potentially solved !
                }
                if (false == bPutChar)
                    return false; // exhausted all possibilities
            }
            cStart = 0; // for next cycle cStart starts from zero.
        }
        return isBoardSolved(board);
    }

    public static char[][] getBoard_9x9_Easy_1() {
        char[][] board = {
                { '.', '9', '.', '.', '7', '2', '3', '4', '.', },
                { '.', '4', '5', '.', '.', '.', '1', '7', '.', },
                { '.', '2', '.', '3', '.', '.', '.', '.', '.', },
                { '5', '.', '7', '2', '3', '.', '.', '.', '9', },
                { '.', '.', '1', '.', '5', '.', '2', '.', '.', },
                { '4', '.', '.', '.', '8', '9', '6', '.', '7', },
                { '.', '.', '.', '.', '.', '3', '.', '2', '.', },
                { '.', '7', '3', '.', '.', '.', '4', '6', '.', },
                { '.', '1', '4', '6', '2', '.', '.', '9', '.', },
        };
        init_board_properties(board);
        return board;
    }

    public static char[][] getBoard_9x9_Evil_1() {
        char[][] board = {
                { '.', '4', '6', '.', '3', '.', '5', '.', '.', },
                { '.', '3', '.', '9', '1', '5', '.', '.', '.', },
                { '.', '.', '.', '.', '.', '.', '.', '3', '.', },
                { '9', '1', '.', '.', '.', '8', '.', '.', '4', },
                { '.', '.', '.', '.', '.', '.', '.', '.', '.', },
                { '4', '.', '.', '5', '.', '.', '.', '2', '7', },
                { '.', '6', '.', '.', '.', '.', '.', '.', '.', },
                { '.', '.', '.', '4', '7', '6', '.', '8', '.', },
                { '.', '.', '4', '.', '9', '.', '2', '7', '.', },
        };
        init_board_properties(board);
        return board;
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        char[][] board = getRandomBoard(WIDTH_9X9);
        // char[][] board = getRandomBoard(WIDTH_16X16);
        // char[][] board = getBoard_9x9_Easy_1();
        // char[][] board = getBoard_9x9_Evil_1();
        System.out.print("\nProblem board:");
        printBoard(board);
        if (isValidSudoku(board)) {
            System.out.println("isValidSudoku() before solving returned true.");
            long starttime = System.currentTimeMillis();
            solveBoard(board, 0, 0);
            long stoptime = System.currentTimeMillis();
            System.out.print("\nSolved board:");
            printBoard(board);
            System.out.format("Solution took %d ms.\n", (stoptime - starttime));
            if (isValidSudoku(board)) {
                System.out.println("isValidSudoku() after solving returned true.");
                if (isBoardSolved(board)) {
                    System.out.println("isBoardSolved() after solving returned true.");
                } else {
                    System.out.println("isBoardSolved() after solving returned false.");
                }
            } else {
                System.out.println("isValidSudoku() after solving returned false.");
            }
        } else {
            System.out.println("isValidSudoku() before solving returned false.");
        }
    }
}
