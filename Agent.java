/**
 * Abstract class that is ancestor of all agents in the environment
 */
public abstract class Agent {
    Location location;
    boolean[][] possibleLocations;

    /**
     * Constructor sets all locations are possible to the agent in the environment
     */
    Agent() {
        location = new Location();
        possibleLocations = new boolean[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                possibleLocations[i][j] = Boolean.TRUE;
            }
        }
    }
}
