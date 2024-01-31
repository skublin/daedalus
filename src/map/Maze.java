package map;

import java.util.Random;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;

public class Maze extends Graph {
    public Tile[][] nodes;
    public Character[][] map;
    private final Integer size;    // Map is always square (e.g. 6x6 or 10x10) (!)
    private final Integer seed;    // TODO: seed for random operations (?)

    public Maze(Integer size, Integer seed) {
        super();
        this.nodes = new Tile[size][size];
        this.map = new Character[2*size + 1][4*size + 1];
        this.size = size;
        this.seed = seed;
    }

    public Character[][] getMap() {
        return map;
    }

    public void addTile(Tile vertex, Integer x, Integer y) {
        this.nodes[x][y] = vertex;
    }

    public void generateMaze() {
        this.generateNodes();
        this.setStartEnd();
        this.DFS();
    }

    private void generateNodes() {
        // Node start position is (0, 0), and it's a top-left corner of the map,
        // but node end position is (size - 1, size - 1), and it's a right-bottom corner of the map.
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                // Tile position is (i, j), where i - row index, j - column index.
                Tile tile = new Tile(this, new Position<>(i, j), new Walls<>(false, false, false, false));
                this.addTile(tile, i, j);

                // Set WEST and EAST border (null value).
                if (i == 0) tile.setWall("W", null);
                if (i == size - 1) tile.setWall("E", null);
                // Set NORTH and SOUTH border (null value).
                if (j == 0) tile.setWall("N", null);
                if (j == size - 1) tile.setWall("S", null);
            }
        }
    }

    private void setStartEnd() {
        // Get random maze enter direction and exit direction.
        List<String> directions = new ArrayList<>();
        Random random = new Random();

        directions.add("N");
        directions.add("E");
        directions.add("S");
        directions.add("W");

        Collections.shuffle(directions);

        String startDirection = directions.get(0);
        Integer startIndex = random.nextInt(size);

        String endDirection = directions.get(3);
        Integer endIndex = random.nextInt(size);

        switch (startDirection) {
            case "N" -> nodes[startIndex][0].setAsStart();
            case "E" -> nodes[size - 1][startIndex].setAsStart();
            case "S" -> nodes[startIndex][size - 1].setAsStart();
            case "W" -> nodes[0][startIndex].setAsStart();
        };

        switch (endDirection) {
            case "N" -> nodes[endIndex][0].setAsEnd("N");
            case "E" -> nodes[size - 1][endIndex].setAsEnd("E");
            case "S" -> nodes[endIndex][size - 1].setAsEnd("S");
            case "W" -> nodes[0][endIndex].setAsEnd("W");
        };
    }

    public void DFS() {

    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < size; i++) {
            for (int k = 0; k < 2; k++) {
                for (int j = 0; j < size; j++) {
                    Tile tile = nodes[i][j];


                }
                result.append("\n");
            }
        }
        return result.toString();
    }

    public static void main(String[] args) {
        System.out.println("Test Maze generation with DFS...");

        Maze m = new Maze(8, null);    // seed set to null results in random seed used (!)

        m.generateMaze();

        System.out.println("Generated Maze: \n");
        System.out.println(m);
    }
}
