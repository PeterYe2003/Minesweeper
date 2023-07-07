/**Peter Ye
// I did the bonus challenge of making the first click = 0 and not being a mine.
 I also did the MineField game where I start from the top left corner and try to go to the bottom right corner.
*/
public class PetersMineModel implements MineModel {
    PeterCell gameboard[][];
    // a and b will be the randomly generator bomb coordinates.
    private int a;
    private int b;
    private int totalMines = 0;
    private boolean gameStatus = false;
    private boolean gameOver = false;
    private boolean dead = true;
    private boolean firstClick;
    private int columns;
    private int rows;
    private long startTime;
    private boolean pathExists;
    private boolean gameType;

    @Override

//Creates new game on a numRows x numCols board with numRows.
    public void newGame(int numRows, int numCols, int numMines, boolean gameType) {
        this.gameType = gameType;
        firstClick = true;
        totalMines = numMines;
        pathExists = false;
        gameStatus = true;
        gameOver = false;
        dead = false;
        gameboard = new PeterCell[numRows][numCols];
        columns = numCols;
        rows = numRows;
        if (gameType == true) {
            for (int x = 0; x < numRows; x++) {
                for (int y = 0; y < numCols; y++) {
                    gameboard[x][y] = new PeterCell(x, y, false, false, false, 0);
                }
            }
            for (int x = 0; x < numMines; x++) {
                a = (int) (Math.random() * numRows);
                b = (int) (Math.random() * numCols);
                while (gameboard[a][b].isMine() == true) {
                    a = (int) (Math.random() * numRows);
                    b = (int) (Math.random() * numCols);
                }
                gameboard[a][b] = new PeterCell(a, b, true, false, false, 0);
            }
            totalMines = numMines;
            for (int x = 0; x < numRows; x++) {
                for (int y = 0; y < numCols; y++) {
                    for (int a = -1; a < 2; a++) {
                        for (int b = -1; b < 2; b++) {
                            if (0 <= x + a && x + a < numRows && 0 <= y + b && y + b < numCols) {
                                if (gameboard[x + a][y + b].isMine() == true) {
                                    gameboard[x][y] = new PeterCell(x, y, gameboard[x][y].isMine(),
                                            false, false, gameboard[x][y].getNeighborMines() + 1);
                                }
                            }
                        }
                    }
                }
            }
        }
        //Initializing gameboard for MineField gametype.
        else {

            while (pathExists == false) {
                for (int x = 0; x < numRows; x++) {
                    for (int y = 0; y < numCols; y++) {
                        gameboard[x][y] = new PeterCell(x, y, false, false, false, 0);
                    }
                }
                for (int x = 0; x < numMines; x++) {
                    a = (int) (Math.random() * numRows);
                    b = (int) (Math.random() * numCols);
                    while (gameboard[a][b].isMine() == true || (a == 0 && b == 0) || (a == gameboard.length - 1 && b == gameboard[0].length - 1)) {
                        a = (int) (Math.random() * numRows);
                        b = (int) (Math.random() * numCols);
                    }
                    gameboard[a][b] = new PeterCell(a, b, true, false, false, 0);
                }
                isPath(0,0);
            }
            for (int x = 0; x < numRows; x++) {
                for (int y = 0; y < numCols; y++) {
                    gameboard[x][y].noFlag();
                    for (int a = -1; a < 2; a++) {
                        for (int b = -1; b < 2; b++) {
                            if (0 <= x + a && x + a < numRows && 0 <= y + b && y + b < numCols) {
                                if (gameboard[x + a][y + b].isMine() == true) {
                                    gameboard[x][y] = new PeterCell(x, y, gameboard[x][y].isMine(),
                                            false, false, gameboard[x][y].getNeighborMines() + 1);
                                }
                            }
                        }
                    }
                }
            }
            gameboard[0][0].show();
        }
    }

    @Override
    public int getNumRows() {
        return gameboard.length;
    }

    @Override
    public int getNumCols() {
        return gameboard[0].length;
    }

    @Override
    public int getNumMines() {
        return totalMines;
    }

    @Override
    public int getNumFlags() {
        int flags = 0;
        for (int x = 0; x < gameboard.length; x++) {
            for (int y = 0; y < gameboard[0].length; y++) {
                if (gameboard[x][y].isFlagged() == true)
                    flags += 1;
            }
        }
        return flags;
    }

    @Override
    public int getElapsedSeconds() {
        if (firstClick == true) {
            return 0;
        } else
            return ((int) ((System.currentTimeMillis() - startTime) / 1000));
    }

    @Override
    public Cell getCell(int row, int col) {
        if (row <= gameboard.length && col <= gameboard[0].length) {
            return gameboard[row][col];
        } else
            return gameboard[0][0];
    }

    @Override
    public void stepOnCell(int row, int col) {
        if (gameType == true) {
            if (firstClick == true) {
                startTime = System.currentTimeMillis();
            }

            if (firstClick == true && (gameboard[row][col].getNeighborMines() != 0 || gameboard[row][col].isMine())) {
                for (int x = 0; x < rows; x++) {
                    for (int y = 0; y < columns; y++) {
                        gameboard[x][y] = new PeterCell(x, y, false, false, false, 0);
                    }
                }
                for (int x = 0; x < totalMines; x++) {
                    a = (int) (Math.random() * rows);
                    b = (int) (Math.random() * columns);
                    while (gameboard[a][b].isMine() == true || (a == row && b == col) || (a == row + 1 && b == col + 1) || (a == row + 1 && b == col) || (a == row + 1 && b == col - 1) || (a == row && b == col + 1) ||
                            (a == row && b == col - 1) || (a == row - 1 && b == col + 1) || (a == row - 1 && b == col) || (a == row - 1 && b == col - 1)) {
                        a = (int) (Math.random() * rows);
                        b = (int) (Math.random() * columns);
                    }
                    gameboard[a][b] = new PeterCell(a, b, true, false, false, 0);
                }
                for (int x = 0; x < rows; x++) {
                    for (int y = 0; y < columns; y++) {
                        for (int a = -1; a < 2; a++) {
                            for (int b = -1; b < 2; b++) {
                                if (0 <= x + a && x + a < rows && 0 <= y + b && y + b < columns) {
                                    if (gameboard[x + a][y + b].isMine() == true) {
                                        gameboard[x][y] = new PeterCell(x, y, gameboard[x][y].isMine(),
                                                false, false, gameboard[x][y].getNeighborMines() + 1);
                                    }
                                }
                            }
                        }
                    }
                }
                firstClick = false;
            }
            firstClick = false;
            gameboard[row][col] = new PeterCell(row, col, gameboard[row][col].isMine(),
                    true, gameboard[row][col].isFlagged(), gameboard[row][col].getNeighborMines());
            if (gameboard[row][col].isMine() == true) {
                dead = true;
                gameOver = true;
            } else if (gameboard[row][col].getNeighborMines() == 0) {
                revealZeros(row, col);
            }
        } else {
            if (firstClick == true) {
                startTime = System.currentTimeMillis();
                firstClick = false;
            }
            for (int a = -1; a < 2; a++) {
                for (int b = -1; b < 2; b++) {
                    if (row + a < gameboard.length && 0 <= row + a && col + b < gameboard[0].length && 0 <= col + b) {
                        if (gameboard[row + a][col + b].isVisible() == true) {
                            if (gameboard[row][col].isMine() == false) {
                                gameboard[row][col].show();
                            } else {
                                gameboard[row][col].show();
                                dead = true;
                                gameOver = true;
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void placeOrRemoveFlagOnCell(int row, int col) {
        gameboard[row][col].flag();
    }

    @Override
    public boolean isGameStarted() {
        return gameStatus;
    }

    @Override
    public boolean isGameOver() {
        return gameOver;
    }

    @Override
    public boolean isPlayerDead() {
        return dead;
    }

    @Override
    public boolean isGameWon() {
        if (gameType == true) {
            int squares = 0;
            for (int x = 0; x < gameboard.length; x++) {
                for (int y = 0; y < gameboard[0].length; y++) {
                    if (gameboard[x][y].isMine() == false && gameboard[x][y].isVisible() == true) {
                        squares++;
                    }
                }
            }
            if (squares == gameboard.length * gameboard[0].length - totalMines) {
                gameOver = true;
                return true;
            } else {
                return false;
            }
        } else {
            if (gameboard[gameboard.length - 1][gameboard[0].length - 1].isVisible() == true) {
                gameOver = true;
                return true;
            } else {
                return false;
            }

        }
    }

    //Reveals all adjacent 0s if the current cell is a 0.
    private void revealZeros(int row, int col) {
        gameboard[row][col].show();
        for (int x = -1; x < 2; x++) {
            for (int y = -1; y < 2; y++) {
                if (0 <= row + x && row + x < gameboard.length && 0 <= col + y && col + y < gameboard[0].length) {
                    if (gameboard[row + x][col + y].isVisible() == false) {
                        gameboard[row + x][col + y].show();
                        if (gameboard[row + x][col + y].getNeighborMines() == 0) {
                            revealZeros(row + x, col + y);
                        }
                    }
                }
            }
        }
    }

    // Finds if a path exists or not for the MineField game mode. I use Flags to make sure I'm not recursing backwards.
    private void isPath(int row, int col) {
        if (pathExists == true) {
        } else {
            for (int x = -1; x < 2; x++) {
                for (int y = -1; y < 2; y++) {
                    if (0 <= row + x && row + x < gameboard.length && 0 <= col + y && col + y < gameboard[0].length && (x != 0 && y != 0)) {
                        if (gameboard[row + x][col + y].isMine() == false && gameboard[row + x][col + y].isFlagged() == false) {
                            gameboard[row + x][col + y].yesFlag();
                            isPath(row + x, col + y);
                        }
                    }
                    if (gameboard[gameboard.length - 1][gameboard[0].length - 1].isFlagged() == true) {
                        pathExists = true;
                    }
                }
            }
        }
    }
//This is another way of finding paths. However, I wasn't super sure how to multiply matrix in a fast way without loops.
    // this actually works for small boards like, a 5x5 board. For larger boards, the runtime is too long.
    private void isPathMatrix() {
        pathExists = false;
        int reset = 0;
        int adjacencyMatrix[][] = new int[gameboard.length * gameboard[0].length][gameboard.length * gameboard[0].length];
        int poweredMatrix[][] = new int[gameboard.length * gameboard[0].length][gameboard.length * gameboard[0].length];
        int productMatrix[][] = new int[gameboard.length * gameboard[0].length][gameboard.length * gameboard[0].length];
        for (int row = 0; row < gameboard.length; row++) {
            for (int col = 0; col < gameboard[0].length; col++) {
                for (int a = -1; a < 2; a++) {
                    for (int b = -1; b < 2; b++) {
                        if (-1 < row + a && row + a < gameboard.length && -1 < col + b && col + b < gameboard[0].length && (a != 0 && b != 0)) {
                            if (gameboard[row + a][col + b].isMine() == false) {
                                adjacencyMatrix[row + col * gameboard.length][(row + a) + (col + b) * gameboard.length] = 1;
                                poweredMatrix[row + col * gameboard.length][(row + a) + (col + b) * gameboard.length] = 1;
                            }
                        }
                    }
                }
            }
        }
        for (int x = 0; x < gameboard.length + gameboard[0].length; x++) {
            for (int a = 0; a < gameboard.length * gameboard[0].length; a++) {
                for (int b = 0; b < gameboard.length * gameboard[0].length; b++) {
                    for (int holder = 0; holder < gameboard.length * gameboard[0].length; holder++) {
                        if (holder == 0) {
                            reset = 0;
                        }
                        reset = reset + poweredMatrix[a][holder] * adjacencyMatrix[holder][b];
                    }
                    productMatrix[a][b] = reset;
                }
            }
            for (int a = 0; a < gameboard.length * gameboard[0].length; a++) {
                for (int b = 0; b < gameboard.length * gameboard[0].length; b++) {
                    poweredMatrix[a][b] = productMatrix[a][b];
                }
            }
            if (poweredMatrix[0][gameboard.length * (gameboard[0].length - 1)] != 0) {
                break;
            }
        }
        if (poweredMatrix[0][gameboard.length * (gameboard[0].length - 1)] != 0) {
            pathExists = true;
        }
    }
}