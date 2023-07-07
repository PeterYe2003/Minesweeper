/**Peter Ye
 * I acknowledge working with Seth on some parts of the code and consulting stack overflow.
 */
 class MineSweeper
{
    public static void main(String[] args)
    {
        MineModel mineModel = new PetersMineModel();
        MineView mineView = new MineView(mineModel, 800, 400);
    }
}