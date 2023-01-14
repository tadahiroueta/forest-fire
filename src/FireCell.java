public class FireCell
{
    public static final int DIRT = 0, GREEN = 1, BURNING = 2;
    private int status;
    private int row, column;

    public FireCell(int row, int column)
    {
        status = DIRT;
        if ( Math.random() <= 0.60 )
            status = GREEN;

        this.row = row;
        this.column = column;
    }

    public int getStatus()
    {
        return status;
    }

    public void setStatus(int n)
    {
        status = n;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return column;
    }
}
