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
    private final Integer rows;
    private final Integer columns;

    public Maze(Integer size, Integer seed) {
        super();
        this.nodes = new Tile[size][size];
        this.map = new Character[2*size + 1][4*size + 1];
        this.size = size;
        this.seed = seed;
        this.rows = 2*size + 1;
        this.columns = 4*size + 1;
        this.fillClearMap();
    }

    private void fillClearMap() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++)
                map[i][j] = ' ';
        }
    }

    @Override
    public Character[][] getMap() {
        return map;
    }

    public void addTile(Tile vertex, Integer x, Integer y) {
        this.nodes[x][y] = vertex;
    }

    public void generateMaze() {
        // TODO: set random seed if given null to constructor.
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

    public void drawTest() {
        /*for (int i = 0; i < 2*size + 1; i++) {
            for (int j = 0; j < 4*size + 1; j++)
                map[i][j] = 'X';
        }*/

        // draw odd lines
        for (int i = 0; i < rows; i += 2) {
            // draw corners (+) and horizontal walls (---)
            for (int j = 0; j < columns; j += 4) {
                map[i][j] = '+';

                for (int k = 1; k < 4; k++) {
                    if (j + k < columns)
                        map[i][j + k] = '-';
                }
            }
        }
        // draw even lines
        for (int i = 1; i < rows; i += 2) {
            for (int j = 0; j < columns; j += 4)
                map[i][j] = '|';
        }
    }

    @Override
    public String toString() {
        this.drawTest();

        for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    Tile tile = nodes[i][j];
                    tile.updateTileString(i);
                }
        }

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++)
                result.append(map[i][j]);
            result.append('\n');
        }

        return result.toString();
    }

    public static void main(String[] args) {
        System.out.println("Test Maze generation with DFS...");

        Maze m = new Maze(6, null);    // Seed set to null results in random seed use. (!)

        m.generateMaze();

        System.out.println("Generated Maze: \n");
        System.out.println(m);
    }
}
