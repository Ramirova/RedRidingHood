/**
 * Class implements Granny agent
 */
public class Granny extends Agent {
    /**
     * Granny need to know where wolf and bear are to not locate in their cells
     */
    Granny(Location wolfLocation, Location bearLocation) {
        super();
        locate(wolfLocation, bearLocation);
    }

    /**
     * Locate granny not in rrh, wolf or bear cells
     */
    private void locate(Location wolfLocation, Location bearLocation) {
        setPossibleLocations(wolfLocation, bearLocation);
        do {
            location.randomGenerate();
        } while (!possibleLocations[location.y][location.x]);
    }

    /**
     * Remove from possble locations rrh, wolf or bear cells
     */
    private void setPossibleLocations(Location wolfLocation, Location bearLocation) {
        possibleLocations[0][0] = false;
        possibleLocations[wolfLocation.y][wolfLocation.x] = false;
        if (wolfLocation.y + 1 <= 8) possibleLocations[wolfLocation.y +1][wolfLocation.x] = false;
        if (wolfLocation.y - 1 >= 0) possibleLocations[wolfLocation.y -1][wolfLocation.x] = false;
        if (wolfLocation.x + 1 <= 8) possibleLocations[wolfLocation.y][wolfLocation.x + 1] = false;
        if (wolfLocation.x - 1 >= 0) possibleLocations[wolfLocation.y][wolfLocation.x - 1] = false;

        possibleLocations[bearLocation.y][bearLocation.x] = false;
        if (bearLocation.y + 1 <= 8) possibleLocations[bearLocation.y+1][bearLocation.x] = false;
        if (bearLocation.y - 1 >= 0) possibleLocations[bearLocation.y-1][bearLocation.x] = false;
        if (bearLocation.x + 1 <= 8) possibleLocations[bearLocation.y][bearLocation.x + 1] = false;
        if (bearLocation.x - 1 >= 0) possibleLocations[bearLocation.y][bearLocation.x - 1] = false;
        if ((bearLocation.y + 1 <= 8) && (bearLocation.x+ 1 <= 8)) possibleLocations[bearLocation.y +1][bearLocation.x +1] = false;
        if ((bearLocation.y - 1 >= 0) && (bearLocation.x + 1 <= 8)) possibleLocations[bearLocation.y -1][bearLocation.x +1] = false;
        if ((bearLocation.y + 1 <= 8) && (bearLocation.x - 1 >= 0)) possibleLocations[bearLocation.y +1][bearLocation.x -1] = false;
        if ((bearLocation.y - 1 >= 0) && (bearLocation.x - 1 >= 0)) possibleLocations[bearLocation.y -1][bearLocation.x -1] = false;
    }
}
