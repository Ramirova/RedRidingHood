import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Comparator;
/**
 * Class implements Red Riding Hood agent
 */
public class RedRidingHood extends Agent {
    int berries;
    private Cell[][] knowledge; //knowledge about current environment
    GameProcess game; //to make connection with launcher
    Location bearLocation; //not null when RRH have seen bear
    Location wolfLocation; //not null when RRH have seen wolf
    private Location woodCutterLocation; //not null when RRH knows where is woodCutter
    private Location grannyHome;
    public boolean woodCutterFound; //indicates if wodd cutter found or not
    private PriorityQueue<Cell> movesQueue; //needed to A star algorithm
    private Location woodCutterHome;
    private Location woodCuttingPlace;
    private boolean inRisk; //indicates if RRH in risk or not

    /**
     * Red Riding Hood needs to know where are woodCutterHome, woodCuttingPlace and grannyHome
     */
    RedRidingHood(Location woodCutterHome, Location woodCuttingPlace, Location grannyHome) {
        super();
        woodCutterLocation = new Location();
        this.grannyHome = grannyHome;
        location.x = 0;
        location.y = 0;
        berries = 6;
        inRisk = false;
        initKnowledge(woodCutterHome, woodCuttingPlace);
        this.woodCutterHome = woodCutterHome;
        this.woodCuttingPlace = woodCuttingPlace;

        Comparator comparator = new CellComparator();
        movesQueue = new PriorityQueue<>(100, comparator);
        woodCutterFound = false;
    }

    /**
     * @param current is cell for what we search neighbours
     * @return neighbours of cell
     */
    private Cell[] neighbours(Cell current) {
        Cell[] result = new Cell[4];

        if (current.location.y + 1 < 9) { result[0] = knowledge[current.location.y + 1][current.location.x];
        } else result[0] = null;

        if (current.location.y - 1 >= 0) { result[1] = knowledge[current.location.y - 1][current.location.x];
        } else result[1] = null;

        if (current.location.x + 1 < 9) { result[2] = knowledge[current.location.y][current.location.x + 1];
        } else result[2] = null;

        if (current.location.x - 1 >= 0) { result[3] = knowledge[current.location.y][current.location.x - 1];
        } else result[3] = null;
        return result;
    }

    /**
     * Method implements RRH moves in forest. It activates risk mode if it is needed
     */
    public void move() {
        movesQueue.add(knowledge[0][0]);
        knowledge[0][0].g = 0;
        knowledge[0][0].f = knowledge[0][0].g + knowledge[0][0].h;
        LinkedList<Cell> closed = new LinkedList<>();
        while (movesQueue.size() != 0 && !game.gameOver() && !(location.x == grannyHome.x && location.y == grannyHome.y)) {
            game.pathLength++;
            Cell current = movesQueue.poll();
            if (current.wolfZone) {
                game.badMap = true;
                break;
            }
            game.newMove();
            location = current.location;
            game.updMe();
            if (woodCutterFound && current.bearZone) game.badMap = true;
            learn();
            closed.add(current);
            Cell[] neighbours = neighbours(current);
            for (Cell next : neighbours) {
                if (next != null && !next.wolfZone && !next.bearZone) {
                    if (!closed.contains(next)) {
                        next.prev = current;
                        next.g = current.g + 1;
                        next.f = next.h + next.g;
                        movesQueue.add(next);
                    }
                }
                if (next != null && next.bearZone && !next.wolfZone) {
                    next.prev = current;
                    next.g = current.g + 100;
                    next.f = next.h + next.g;
                    movesQueue.add(next);
                }
            }
            if (berries != 6 && !game.gameOver()) riskMode();
        }
    }

    /**
     * Method  risk mode, when RRH lost some berries. If RRH already was in woodCuttingPlace or in woodCutterHome, she will try to check other place if there is WoodCutter and than teleport to Granny
     */
    private void riskMode() {
        if (!woodCutterFound) {
            if (knowledge[woodCuttingPlace.y][woodCuttingPlace.x].been && !knowledge[woodCutterHome.y][woodCutterHome.x].been) {
                location = knowledge[woodCutterHome.y][woodCutterHome.x].location;
                game.fillBasket();
                location = knowledge[grannyHome.y][grannyHome.x].location;
            }
            if (!knowledge[woodCuttingPlace.y][woodCuttingPlace.x].been && knowledge[woodCutterHome.y][woodCutterHome.x].been) {
                location = knowledge[woodCuttingPlace.y][woodCuttingPlace.x].location;
                game.fillBasket();
                location = knowledge[grannyHome.y][grannyHome.x].location;
            }
            if (!knowledge[woodCuttingPlace.y][woodCuttingPlace.x].been  && !knowledge[woodCutterHome.y][woodCutterHome.x].been) {
                location = knowledge[woodCutterHome.y][woodCutterHome.x].location;
                game.fillBasket();
                location = knowledge[woodCuttingPlace.y][woodCuttingPlace.x].location;
                game.fillBasket();
                location = knowledge[grannyHome.y][grannyHome.x].location;
            }
        }
    }

    /**
     * Method that adds knowledges to knowledge[][] by checking neighbourhood for bear, wolf and current cell for wood cutter
     */
    private void learn() {
        switch (game.checkWolfZone().size()) {
            case 0:
                break;
            case 1:
                knowledge[game.checkWolfZone().get(0).y][game.checkWolfZone().get(0).x].wolfZone = true;
                break;
            case 2:
                knowledge[game.checkWolfZone().get(0).y][game.checkWolfZone().get(0).x].wolfZone = true;
                knowledge[game.checkWolfZone().get(1).y][game.checkWolfZone().get(1).x].wolfZone = true;
                break;
            default:
                break;
        }
        switch (game.checkBearZone().size()) {
            case 0:
                break;
            case 1:
                knowledge[game.checkBearZone().get(0).y][game.checkBearZone().get(0).x].bearZone = true;
                break;
            case 2:
                knowledge[game.checkBearZone().get(0).y][game.checkBearZone().get(0).x].bearZone = true;
                knowledge[game.checkBearZone().get(1).y][game.checkBearZone().get(1).x].bearZone = true;
                break;
            default:
                break;
        }
        if (game.woodCutterFound()) {
            knowledge[location.y][location.x].woodCutter = true;
            berries = 6;
            woodCutterFound = true;
        }
        knowledge[location.y][location.x].been = true;
        game.rrhCheckBear();
        game.rrhCheckWolf();
    }

    /**
     * Method initializes RRH knowledge map
     */
    private void initKnowledge(Location woodCutterHome, Location woodCuttingPlace) {
        knowledge = new Cell[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                knowledge[j][i] = new Cell(i, j);
                knowledge[j][i].h = Math.abs(grannyHome.y - j) + Math.abs(grannyHome.x - i);
                knowledge[j][i].f = knowledge[j][i].h + knowledge[j][i].g;
            }
        }
        knowledge[woodCutterHome.y][woodCutterHome.x].woodCutterHome = true;
        knowledge[woodCuttingPlace.y][woodCuttingPlace.x].woodCuttingPlace = true;
        knowledge[grannyHome.y][grannyHome.x].granny = true;
    }

    /**
     * Class implements one cell from RRH knowledge
     */
    public class Cell {
        Cell() {}
        Cell(int x, int y) {
            location = new Location(x, y);
            prev = new Cell();
            g = 0;
        }
        int f; //parameters for A star algorithm
        int g;
        int h;
        Cell prev;
        boolean been;
        Location location;
        boolean woodCutter;
        boolean bearZone; //indicators of cell
        boolean wolfZone;
        boolean woodCutterHome;
        boolean woodCuttingPlace;
        boolean granny;
    }

    /**
     * Class that compares cells according ro A star princip
     */
    private  class CellComparator implements Comparator<Cell> {
        public int compare(Cell cell1, Cell cell2) {
            if (cell1.f > cell2.f) {
                return 1;
            } else {
                if (cell1.f < cell2.f) return -1;
            }
            if (cell1.f == cell2.f) {
                if (cell1.h < cell2.h) return -1;
                if (cell1.h > cell2.h) return 11;
            }
            return 0;
        }
    }
}
