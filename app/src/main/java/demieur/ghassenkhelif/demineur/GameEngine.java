package demieur.ghassenkhelif.demineur;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import demieur.ghassenkhelif.demineur.util.Generator;
import demieur.ghassenkhelif.demineur.util.PrintGrid;
import demieur.ghassenkhelif.demineur.views.grid.Cell;

/**
 * Created by Ghassen on 05/02/2017.
 */

public class GameEngine {

    private static GameEngine instance;

    public static final int BOMB_NUMBER = 10;
    public static final int HEIGHT = 10;
    public static final int WIDTH = 10;


    private Context context;

    private Cell[][] DemineurGrid = new Cell[WIDTH][HEIGHT];


    public static GameEngine getInstance() {
        if (instance == null) {
            instance = new GameEngine();
        }
        return instance;
    }

    private GameEngine() {

    }

    public void createGrid(Context context) {
        this.context = context;

        int[][] GeneratedGrid = Generator.generate(BOMB_NUMBER, WIDTH, HEIGHT);
        Log.e("err", "ee");
        PrintGrid.print(GeneratedGrid, WIDTH, HEIGHT);
        setGrid(context, GeneratedGrid);
    }

    private void setGrid(final Context context, final int[][] grid) {
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                if (DemineurGrid[x][y] == null) {
                    DemineurGrid[x][y] = new Cell(context, x,y);
                }
                DemineurGrid[x][y].setValue(grid[x][y]);
                DemineurGrid[x][y].invalidate();


            }
        }
    }

    public Cell getCellAt(int position) {
        int x = position % WIDTH;
        int y = position / WIDTH;

        return DemineurGrid[x][y];
    }

    public Cell getCellAt(int x, int y) {
        return DemineurGrid[x][y];
    }

    public void click(int x, int y) {
        if (x >= 0 && y >= 0 && x < WIDTH && y < HEIGHT && !getCellAt(x, y).isClicked()) {
            getCellAt(x, y).setClicked();

            if (getCellAt(x, y).getValue() == 0) {
                for (int xt = -1; xt <= 1; xt++) {
                    for (int yt = -1; yt <= 1; yt++) {
                        if (xt != yt) {
                            click(x + xt, y + yt);
                        }
                    }
                }
            }

            if (getCellAt(x, y).isBomb()) {
                onGameLost();
            }
        }

        checkEnd();
    }

    private boolean checkEnd(){
        int bombNotFound = BOMB_NUMBER;
        int notRevealed = WIDTH * HEIGHT;
        for ( int x = 0 ; x < WIDTH ; x++ ){
            for( int y = 0 ; y < HEIGHT ; y++ ){
                if( getCellAt(x,y).isRevealed() || getCellAt(x,y).isFlagged() ){
                    notRevealed--;
                }

                if( getCellAt(x,y).isFlagged() && getCellAt(x,y).isBomb() ){
                    bombNotFound--;
                }
            }
        }

        if( bombNotFound == 0 && notRevealed == 0 ){
            Toast.makeText(context,"Game won", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    public void flag( int x , int y ){
        boolean isFlagged = getCellAt(x,y).isFlagged();
        getCellAt(x,y).setFlagged(!isFlagged);
        getCellAt(x,y).invalidate();
    }

    private void onGameLost(){
        // handle lost game
        Toast.makeText(context,"Game lost", Toast.LENGTH_SHORT).show();

        for ( int x = 0 ; x < WIDTH ; x++ ) {
            for (int y = 0; y < HEIGHT; y++) {
                getCellAt(x,y).setRevealed();
            }
        }
    }
}
