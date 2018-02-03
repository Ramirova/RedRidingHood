import java.util.LinkedList;
/**
 * Game Launcher
 */
public class GameProcess {
    private Forest forest; //environment
    private boolean rrhEaten;
    public boolean badMap; //indicates whether map is unsolvable or not
    public boolean winningMap; //indicates whether map is solvable or not
    public int pathLength;
    public String generatedMap; //string representation of generated environment

    /**
     * Method that initializes, starts and launches game
     */
    public void startGame() {
        pathLength = 0;
        badMap = false;
        forest = new Forest();
        generatedMap = forest.forestToString();
        badMap = forest.checkMap();
        forest.redRidingHood.game = this;
        forest.bear.game = this;
        forest.wolf.game = this;
        forest.redRidingHood.move();
        if (rrgAchievedGranny() && forest.redRidingHood.berries == 6) {
            winningMap = true;
        }
    }

    /**
     * Method that deletes RRH from forest before new move
     */
    public void newMove() {
        forest.dellMe();
    }

    /**
     * Method that updates RRH location after new move and acts other agents
     */
    public void updMe() {
        forest.updMe();
        forest.bear.bearAction();
        rrhEaten = forest.wolf.wolfAction();
    }

    /**
     * Checks all possible cases when game is over
     * @return true when game is over
     */
    public boolean gameOver() {
        return (rrhEaten || (rrgAchievedGranny() && forest.redRidingHood.berries == 6) || badMap || forest.redRidingHood.berries <= 0);
    }

    /**
     * @return true if RRH achieved granny or not
     */
    private boolean rrgAchievedGranny() {
        if (forest.redRidingHood.location.x == forest.granny.location.x && forest.redRidingHood.location.y == forest.granny.location.y) {
            return true;
        } else return false;
    }

    /**
     * Allows RRH know where precisely is bear if he is in her detection range
     */
    public void rrhCheckBear() {
        boolean detected = inNeumanNeighbourhood(forest.redRidingHood, forest.bear);
        if (detected) forest.redRidingHood.bearLocation = forest.bear.location;
    }

    /**
     * Allows bear know where is bear if he is in her detection range
     * @return true if RRH in bear's detection range
     */
    public boolean bearDetectedRRH() {
        boolean detected = false;
        Location bearLocation = forest.bear.location;
        Location rrhLocation = forest.redRidingHood.location;
        if (bearLocation.x + 1 <= 8 && bearLocation.x + 1 == rrhLocation.x && bearLocation.y == rrhLocation.y)
            detected = true;
        if (bearLocation.x - 1 >= 0 && bearLocation.x - 1 == rrhLocation.x && bearLocation.y == rrhLocation.y)
            detected = true;
        if (bearLocation.y + 1 <= 8 && bearLocation.x == rrhLocation.x && bearLocation.y + 1 == rrhLocation.y)
            detected = true;
        if (bearLocation.y - 1 >= 0 && bearLocation.x == rrhLocation.x && bearLocation.y - 1 == rrhLocation.y)
            detected = true;
        if (bearLocation.y - 1 >= 0 && bearLocation.x + 1 <= 8 && bearLocation.x + 1 == rrhLocation.x && bearLocation.y - 1 == rrhLocation.y)
            detected = true;
        if (bearLocation.y - 1 >= 0 && bearLocation.x - 1 <= 8 && bearLocation.x - 1 == rrhLocation.x && bearLocation.y - 1 == rrhLocation.y)
            detected = true;
        if (bearLocation.y + 1 >= 0 && bearLocation.x + 1 <= 8 && bearLocation.x + 1 == rrhLocation.x && bearLocation.y + 1 == rrhLocation.y)
            detected = true;
        if (bearLocation.y + 1 >= 0 && bearLocation.x - 1 <= 8 && bearLocation.x - 1 == rrhLocation.x && bearLocation.y + 1 == rrhLocation.y)
            detected = true;
        return detected;
    }

    public void bearEatBerries() {
        forest.redRidingHood.berries -= 2;
    }

    /**
     * Allows wolf know where is bear if he is in her detection range
     * @return true if RRH in wolf's detection range
     */
    public boolean wolfDetectedRRH() {
        return inNeumanNeighbourhood(forest.wolf, forest.redRidingHood);
    }

    /**
     * checks whether passive agent in Neuman neighbourhood of active or not
     * @return true if passive agent in Neuman neighbourhood of active
     */
    private boolean inNeumanNeighbourhood(Agent active, Agent passive) {
        if ((active.location.y + 1 == passive.location.y) && (active.location.x == passive.location.x)) return true;
        if ((active.location.y - 1 == passive.location.y) && (active.location.x == passive.location.x)) return true;
        if ((active.location.y == passive.location.y) && (active.location.x + 1 == passive.location.x)) return true;
        if ((active.location.y == passive.location.y) && (active.location.x + 1 == passive.location.x)) return true;
        return false;
    }

    /**
     *  Allows RRH to know all wolf zone cells in her detection range
     * @return all cells of wolf zone that in detection range of RRH
     */
    public LinkedList<Location> checkWolfZone() {
        Location location = forest.redRidingHood.location;
        LinkedList<Location> smellLocation = new LinkedList<>();
        if ((location.x + 1 < 9) && ((forest.forest[location.y][location.x + 1].wolfSmell) || forest.forest[location.y][location.x + 1].agent == forest.wolf))
            smellLocation.add(new Location(location.x + 1, location.y));
        if ((location.y - 1 >= 0) && ((forest.forest[location.y - 1][location.x].wolfSmell) || forest.forest[location.y - 1][location.x].agent == forest.wolf))
            smellLocation.add(new Location(location.x, location.y - 1));
        if ((location.x - 1 >= 0) && ((forest.forest[location.y][location.x - 1].wolfSmell) || forest.forest[location.y][location.x - 1].agent == forest.wolf))
            smellLocation.add(new Location(location.x - 1, location.y));
        if ((location.y + 1 < 9) && ((forest.forest[location.y + 1][location.x].wolfSmell) || forest.forest[location.y + 1][location.x].agent == forest.wolf))
            smellLocation.add(new Location(location.x, location.y + 1));
        if ((location.y + 2 < 9) && ((forest.forest[location.y + 2][location.x].wolfSmell) || forest.forest[location.y + 2][location.x].agent == forest.wolf))
            smellLocation.add(new Location(location.x, location.y + 2));
        if ((location.y - 2 >= 0) && ((forest.forest[location.y - 2][location.x].wolfSmell) || forest.forest[location.y - 2][location.x].agent == forest.wolf))
            smellLocation.add(new Location(location.x, location.y - 2));
        if ((location.x + 2 < 9) && ((forest.forest[location.y][location.x + 2].wolfSmell) || forest.forest[location.y][location.x + 2].agent == forest.wolf))
            smellLocation.add(new Location(location.x + 2, location.y));
        if ((location.x - 2 >= 0) && ((forest.forest[location.y][location.x - 2].wolfSmell) || forest.forest[location.y][location.x - 2].agent == forest.wolf))
            smellLocation.add(new Location(location.x - 2, location.y));
        return smellLocation;
    }

    /**
     * Allows RRH know where precisely is wolf if he is in her detection range
     */
    public void rrhCheckWolf() {
        Location location = forest.redRidingHood.location;
        boolean detected = false;
        if ((location.x + 1 < 9) && (forest.forest[location.y][location.x + 1].agent == forest.wolf)) detected = true;
        if ((location.y - 1 >= 0) && (forest.forest[location.y - 1][location.x].agent == forest.wolf)) detected = true;
        if ((location.x - 1 >= 0) && (forest.forest[location.y][location.x - 1].agent == forest.wolf)) detected = true;
        if ((location.y + 1 < 9) && (forest.forest[location.y + 1][location.x].agent == forest.wolf)) detected = true;
        if ((location.y + 2 < 9) && (forest.forest[location.y + 2][location.x].agent == forest.wolf)) detected = true;
        if ((location.y - 2 >= 0) && (forest.forest[location.y - 2][location.x].agent == forest.wolf)) detected = true;
        if ((location.x + 2 < 9) && (forest.forest[location.y][location.x + 2].agent == forest.wolf)) detected = true;
        if ((location.x - 2 >= 0) && (forest.forest[location.y][location.x - 2].agent == forest.wolf)) detected = true;
        if (detected) forest.redRidingHood.wolfLocation = forest.wolf.location;
    }

    /**
     * Allows RRH to know that she found RRH and take berries
     * @return true if wood cutter found by RRH
     */
    public LinkedList<Location> checkBearZone() {
        Location location = forest.redRidingHood.location;
        LinkedList<Location> smellLocation = new LinkedList<>();
        if ((location.y + 1 < 9) && ((forest.forest[location.y + 1][location.x].bearSmell) || forest.forest[location.y + 1][location.x].agent == forest.bear))
            smellLocation.add(new Location(location.x, location.y + 1));
        if ((location.y - 1 >= 0) && ((forest.forest[location.y - 1][location.x].bearSmell) || forest.forest[location.y - 1][location.x].agent == forest.bear))
            smellLocation.add(new Location(location.x, location.y - 1));
        if ((location.x + 1 < 9) && ((forest.forest[location.y][location.x + 1].bearSmell) || forest.forest[location.y][location.x + 1].agent == forest.bear))
            smellLocation.add(new Location(location.x + 1, location.y));
        if ((location.x - 1 >= 0) && ((forest.forest[location.y][location.x - 1].bearSmell) || forest.forest[location.y][location.x - 1].agent == forest.bear))
            smellLocation.add(new Location(location.x - 1, location.y));
        return smellLocation;
    }

    /**
     * Fill RRH basket if she in WoodCutter Cell
     */
    public void fillBasket() {
        if ((forest.redRidingHood.location.x == forest.woodCutter.location.x) && (forest.redRidingHood.location.y == forest.woodCutter.location.y)) {
            forest.redRidingHood.berries = 6;
        }
    }

    /**
     * Allows RRH to know that she found RRH and take berries
     * @return true if wood cutter found by RRH
     */
    public boolean woodCutterFound() {
        if ((forest.redRidingHood.location.x == forest.woodCutter.location.x) && (forest.redRidingHood.location.y == forest.woodCutter.location.y))
            return true;
        else return false;
    }
}
