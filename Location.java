import java.util.Random;

/**
 * Class implements location of all entities of forest
 */
public class Location {
    int x;
    int y;

    Location() {}
    Location (int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Random generation of location
     */
    public void randomGenerate() {
        Random random = new Random();
        x = random.nextInt(9);
        y = random.nextInt(9);
    }
}