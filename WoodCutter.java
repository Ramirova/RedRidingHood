import java.util.Random;
/**
 * Class implements Wood Cutter agent
 */
public class WoodCutter extends Agent {
    public Location woodCuttingPlace;
    public Location woodCutterHome;

    /**
     * Wood cutter need to know location of granny^ wolf and bear to not locate in their cells
     */
    WoodCutter(Location wolfLocation, Location bearLocation, Location grannyLocation) {
        super();
        woodCuttingPlace = new Location();
        woodCutterHome = new Location();
        locate(wolfLocation, bearLocation, grannyLocation);
    }

    /**
     * Random generation of woodCuttingPlace and woodCutterHome and then WoodCutter in one of this locations
     */
    private void locate(Location wolfLocation, Location bearLocation, Location grannyLocation) {
        setPossibleLocations(wolfLocation, bearLocation, grannyLocation);

        do {
            woodCuttingPlace.randomGenerate();
        } while (!possibleLocations[woodCuttingPlace.y][woodCuttingPlace.x]);

        do {
            woodCutterHome.randomGenerate();
        } while ((!possibleLocations[woodCutterHome.y][woodCutterHome.x]) || ((woodCuttingPlace.y == woodCutterHome.y) && (woodCuttingPlace.x == woodCutterHome.x)));

        Random random = new Random();
        int woodCutterLocation = random.nextInt(2);

        if (woodCutterLocation == 0) {
            location = woodCuttingPlace;
        } else {
            location = woodCutterHome;
        }
    }

    /**
     * Remove from possible locations granny, wolf, rrh, bear
     */
    private void setPossibleLocations(Location wolfLocation, Location bearLocation, Location grannyLocation) {
        possibleLocations[0][0] = false;
        possibleLocations[wolfLocation.y][wolfLocation.x] = false;
        if (wolfLocation.y + 1 <= 8) possibleLocations[wolfLocation.y +1][wolfLocation.x] = false;
        if (wolfLocation.y - 1 >= 0) possibleLocations[wolfLocation.y -1][wolfLocation.x] = false;
        if (wolfLocation.x + 1 <= 8) possibleLocations[wolfLocation.x][wolfLocation.x + 1] = false;
        if (wolfLocation.x - 1 >= 0) possibleLocations[wolfLocation.x][wolfLocation.x - 1] = false;
        possibleLocations[bearLocation.y][bearLocation.x] = false;
        if (bearLocation.y + 1 <= 8) possibleLocations[bearLocation.y +1][bearLocation.x] = false;
        if (bearLocation.y - 1 >= 0) possibleLocations[bearLocation.y -1][bearLocation.x] = false;
        if (bearLocation.x + 1 <= 8) possibleLocations[bearLocation.y][bearLocation.x + 1] = false;
        if (bearLocation.x - 1 >= 0) possibleLocations[bearLocation.y][bearLocation.x - 1] = false;
        if ((bearLocation.y + 1 <= 8) && (bearLocation.x + 1 <= 8)) possibleLocations[bearLocation.y +1][bearLocation.x +1] = false;
        if ((bearLocation.y - 1 >= 0) && (bearLocation.x + 1 <= 8)) possibleLocations[bearLocation.y -1][bearLocation.x +1] = false;
        if ((bearLocation.y + 1 <= 8) && (bearLocation.x - 1 >= 0)) possibleLocations[bearLocation.y +1][bearLocation.x -1] = false;
        if ((bearLocation.y - 1 >= 0) && (bearLocation.x - 1 >= 0)) possibleLocations[bearLocation.y -1][bearLocation.x -1] = false;
        possibleLocations[grannyLocation.y][grannyLocation.x] = false;
    }
}
