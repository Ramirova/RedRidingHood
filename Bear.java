/**
 * Class that implements bear agent
 */
public class Bear extends Agent {
    GameProcess game;

    /**
     * Constructor method
     * @param wolfLocation is needed to know for not locate bear in the same cell as wolf
     */
    Bear(Location wolfLocation) {
        super();
        locate(wolfLocation);
    }

    /**
     * Bear action is to eat Red Riding Hood berries
     */
    public void bearAction() {
        if (game.bearDetectedRRH()) {
            game.bearEatBerries();
        }
    }

    /**
     * Locate bear not in wolf and rrh cells
     */
    private void locate(Location wolfLocation) {
        possibleLocations[0][0] = false;
        possibleLocations[0][1] = false;
        possibleLocations[1][0] = false;
        possibleLocations[1][1] = false;
        possibleLocations[wolfLocation.y][wolfLocation.x] = false;

        do {
            location.randomGenerate();
        } while (!possibleLocations[location.y][location.x]);
    }
}