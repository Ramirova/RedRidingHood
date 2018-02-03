/**
 * Class implements environment
 */
public class Forest {
    public Cell[][] forest;
    public RedRidingHood redRidingHood;
    public WoodCutter woodCutter;
    public Wolf wolf;
    public Bear bear;
    public Granny granny;

    /**
     * Constructor creates all agents and calls initialization of environment with agents
     */
    Forest() {
        wolf = new Wolf();
        bear = new Bear(wolf.location);
        granny = new Granny(wolf.location, bear.location);
        woodCutter = new WoodCutter(wolf.location, bear.location, granny.location);
        redRidingHood = new RedRidingHood(woodCutter.woodCutterHome, woodCutter.woodCuttingPlace, granny.location);
        initForest();
    }

    /**
     * Checks ont of unsolvable cases
     * @return
     */
    public boolean checkMap() {
        if (redRidingHood.location.x == 0 && redRidingHood.location.y == 0 && wolf.location.x == 1 && wolf.location.y == 1)
            return true;
        return false;
    }

    /**
     * Initialization agents (and smells) in forest
     */
    private void initForest() {
        forest = new Cell[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                forest[i][j] = new Cell();
            }
        }
        forest[wolf.location.y][wolf.location.x].agent = wolf;
        forest[bear.location.y][bear.location.x].agent = bear;
        forest[redRidingHood.location.y][redRidingHood.location.x].agent = redRidingHood;
        forest[granny.location.y][granny.location.x].agent = granny;
        forest[woodCutter.location.y][woodCutter.location.x].agent = woodCutter;

        for (int i = bear.location.x - 1; i <= bear.location.x + 1; i++) {
            for (int j = bear.location.y - 1; j <= bear.location.y +1; j++) {
                if (i < 9 && i >= 0 && j >= 0 && j < 9) forest[j][i].bearSmell = true;
            }
        }
        forest[bear.location.y][bear.location.x].bearSmell = false;
        if (wolf.location.x -1 >= 0) forest[wolf.location.y][wolf.location.x -1].wolfSmell = true;
        if (wolf.location.x +1 < 9) forest[wolf.location.y][wolf.location.x +1].wolfSmell = true;
        if (wolf.location.y -1 >= 0) forest[wolf.location.y -1][wolf.location.x].wolfSmell = true;
        if (wolf.location.y +1 < 9) forest[wolf.location.y +1][wolf.location.x].wolfSmell = true;
    }

    /**
     * Converting environment to string representation
     */
    public String forestToString() {
        String result = "";
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (forest[i][j].agent == redRidingHood) result += "R ";
                else if (forest[i][j].agent == wolf) result += "W ";
                else if (forest[i][j].agent == bear) result += "B ";
                else if (forest[i][j].bearSmell) result += "Q ";
                else if (forest[i][j].wolfSmell) result += "S ";
                else if (forest[i][j].agent == granny) result += "G ";
                else if (forest[i][j].agent == woodCutter) result += "C ";
                else if ((forest[i][j].agent == null) && (!forest[i][j].wolfSmell) && (!forest[i][j].bearSmell)) result += "0 ";
            }
            result += "\n";
        }
        return result;
    }

    /**
     * Update RRH location in forest after new move
     */
    public void  updMe() {
        forest[redRidingHood.location.y][redRidingHood.location.x].agent = redRidingHood;
    }

    /**
     * Deletes location of RRH in forest before new move
     */
    public void dellMe() {
        forest[redRidingHood.location.y][redRidingHood.location.x].agent = null;
    }


    /**
     * Cell of environment
     */
    public class Cell {
        Agent agent;
        boolean wolfSmell;
        boolean bearSmell;

    }

}
