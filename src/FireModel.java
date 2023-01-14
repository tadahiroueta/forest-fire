import java.util.ArrayList;
import java.util.List;

public class FireModel
{
    public static int SIZE = 47;
    private FireCell[][] myGrid;
    private FireView myView;

    public FireModel(FireView view)
    {
        myGrid = new FireCell[SIZE][SIZE];
        int setNum = 0;
        for (int r=0; r<SIZE; r++)
        {
            for (int c=0; c<SIZE; c++)
            {
                myGrid[r][c] = new FireCell(r, c);
            }
        }
        myView = view;
        myView.updateView(myGrid);
    }

    private List<FireCell> getNeighbours(FireCell source) {
        List<FireCell> neighbours = new ArrayList<FireCell>();
        int row = source.getRow();
        int col = source.getCol();

        if (row > 0) neighbours.add(myGrid[row - 1][col]);
        if (row < SIZE - 1) neighbours.add(myGrid[row + 1][col]);
        if (col > 0) neighbours.add(myGrid[row][col - 1]);
        if (col < SIZE - 1) neighbours.add(myGrid[row][col + 1]);

        return neighbours;
    }

    private boolean path(FireCell source) {
        boolean path = false;
        
        if (source.getStatus() == FireCell.GREEN) {
            source.setStatus(FireCell.BURNING);
            if (source.getRow() == 0) return true; // end case
            for (FireCell neighbour : getNeighbours(source)) if (path(neighbour)) path = true;
        }
        return path;
    }

    public void solve()
    {
        boolean trouble = false;
        for (FireCell cell : myGrid[SIZE - 1]) if (path(cell)) trouble = true;
        System.out.println(trouble ? "Onett is in trouble!" : "Onett is safe.");

        myView.updateView(myGrid);
    }

}
