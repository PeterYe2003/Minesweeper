/**Peter Ye
*/

 public class PeterCell implements Cell {

    private int rowNumber;
    private int colNumber;
    private boolean mine = false;
    private boolean visible = false;
    private boolean flagged = false;
    private int mineNeighbors = 0;

    PeterCell(int getRow, int getCol, boolean isMine, boolean isVisible, boolean isFlagged, int numMineNighbors) {
        rowNumber = getRow;
        colNumber = getCol;
        mine = isMine;
        visible = isVisible;
        flagged= isFlagged;
        mineNeighbors = numMineNighbors;
    }

    @Override
    public int getRow() {
        return rowNumber;
    }

    @Override
    public int getCol() {
        return colNumber;
    }

    @Override
    public boolean isVisible() {
        return visible;
    }

    @Override
    public boolean isMine() {
        return mine;
    }

    @Override
    public boolean isFlagged() {
        return flagged;
    }

    @Override
    public int getNeighborMines() {
        return mineNeighbors;
    }

    //Makes the PeterCell visible.
    public void show() {
        visible = true;
    }

    // Toggles flag on and off.
    public void flag() {
        if (flagged == true) {
            flagged = false;
        } else
            flagged = true;
    }
    //places flag on PeterCell even if it is already flagged.
    public void yesFlag(){
        flagged = true;
    }
    //makes sure there is no flag on the PeterCell.
    public void noFlag(){
        flagged = false;
    }
}