/**
 * Class implements Wolf agent
 */
public class Wolf extends Agent {
    GameProcess game;

    Wolf() {
        super();
        locate();
    }

    /**
     * remove from possible locations RRH and closest cells
     */
    private void locate() {
        possibleLocations[0][0] = false;
        possibleLocations[0][1] = false;
        possibleLocations[1][0] = false;
        do {
            location.randomGenerate();
        } while (!possibleLocations[location.y][location.x]);
    }

    /**
     * Wolf action is to eat Red Riding Hood
     * @return true if RRH eaten be wolf
     */
    public boolean wolfAction() {
        if (game.wolfDetectedRRH())
            return true;
        else return false;
    }
}

