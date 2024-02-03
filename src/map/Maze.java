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
    private final Integer rows;
    private final Integer columns;
    public Tile start;
    public Tile end;
    public HashSet<Tile> notVisited;

    public Maze(Integer size) {
        super();
        this.nodes = new Tile[size][size];
        this.map = new Character[2*size + 1][4*size + 1];
        this.size = size;
        this.rows = 2*size + 1;
        this.columns = 4*size + 1;
        this.start = null;
        this.end = null;
        this.notVisited = new HashSet<>();
        this.setEmptyMap();
    }

    public void setEmptyMap() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                // (x, y) = (1 + 2*i, 2 + 4*j) - middle of the tile
                int y = 1 + 2*i;
                int x = 2 + 4*j;
                // north wall
                map[y - 1][x - 2] = '+';
                map[y - 1][x - 1] = '-';
                map[y - 1][x] = '-';
                map[y - 1][x + 1] = '-';
                map[y - 1][x + 2] = '+';
                // east wall
                map[y][x + 2] = '|';
                // south wall
                map[y + 1][x - 2] = '+';
                map[y + 1][x - 1] = '-';
                map[y + 1][x] = '-';
                map[y + 1][x + 1] = '-';
                map[y + 1][x + 2] = '+';
                // west wall
                map[y][x - 2] = '|';
                // empty space inside tile
                map[y][x - 1] = ' ';
                map[y][x] = ' ';
                map[y][x + 1] = ' ';
            }
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
        this.generateNodes();
        this.setStartEnd();
        //this.DFS();
        this.recursiveDFS(start);
        System.out.println("Start position = (" + start.getPosition().x + ", " + start.getPosition().y + ") and random neighbour position = ("+ end.getPosition().x + ", " + end.getPosition().y + ")");
    }

    private void generateNodes() {
        // Node start position is (0, 0), and it's a top-left corner of the map,
        // but node end position is (size - 1, size - 1), and it's a right-bottom corner of the map.
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                // Tile position is (i, j), where i - row index, j - column index.
                Tile tile = new Tile(this, new Position<>(i, j), new Walls<>(false, false, false, false));
                this.addTile(tile, i, j);
                this.notVisited.add(tile);

                // Set WEST and EAST border (null value).
                if (i == 0) tile.setWall('W', null);
                if (i == size - 1) tile.setWall('E', null);
                // Set NORTH and SOUTH border (null value).
                if (j == 0) tile.setWall('N', null);
                if (j == size - 1) tile.setWall('S', null);
            }
        }

        this.addTilesNeighbours();
    }

    private void addTilesNeighbours() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Tile t = nodes[i][j];
                int x = t.getPosition().x;
                int y = t.getPosition().y;

                if (y - 1 >= 0) t.addNeighbour(nodes[x][y - 1], true);    // TODO: create edges (true)?
                if (y + 1 < size) t.addNeighbour(nodes[x][y + 1], true);
                if (x + 1 < size) t.addNeighbour(nodes[x + 1][y], true);
                if (x - 1 >= 0) t.addNeighbour(nodes[x - 1][y], true);
            }
        }
    }

    private void setStartEnd() {
        // Get random maze enter direction and exit direction.
        List<Character> directions = new ArrayList<>();
        Random random = new Random();

        directions.add('N');
        directions.add('E');
        directions.add('S');
        directions.add('W');

        Collections.shuffle(directions);

        Character startDirection = directions.get(0);
        int startIndex = random.nextInt(size);

        Character endDirection = directions.get(3);
        int endIndex = random.nextInt(size);

        Tile ts = null;
        switch (startDirection) {
            case 'N' -> ts = nodes[startIndex][0];
            case 'E' -> ts = nodes[size - 1][startIndex];
            case 'S' -> ts = nodes[startIndex][size - 1];
            case 'W' -> ts = nodes[0][startIndex];
        }

        if (ts != null) ts.setAsStart();
        this.start = ts;

        Tile te = null;
        switch (endDirection) {
            case 'N' -> te = nodes[endIndex][0];
            case 'E' -> te = nodes[size - 1][endIndex];
            case 'S' -> te = nodes[endIndex][size - 1];
            case 'W' -> te = nodes[0][endIndex];
        }

        if (te != null) te.setAsEnd(endDirection);
        this.end = te;
    }

    private void removeWall(Tile tile, Character direction) {
        tile.setWall(direction, true);
        // (j, i) - tile's middle position
        int i = 1 + 2 * tile.getPosition().y;
        int j = 2 + 4 * tile.getPosition().x;
        //System.out.println("(y, x) = " + "(" + i + ", " + j + ")");
        switch (direction) {
            case 'W' -> map[i][j - 2] = ' ';
            case 'E' -> map[i][j + 2] = ' ';
            case 'N' -> {
                map[i - 1][j - 1] = ' ';
                map[i - 1][j] = ' ';
                map[i - 1][j + 1] = ' ';
            }
            case 'S' -> {
                map[i + 1][j - 1] = ' ';
                map[i + 1][j] = ' ';
                map[i + 1][j + 1] = ' ';
            }
        }
    }

    public void DFS() {
        // first node is always start node
        Tile current = start;
        Tile next;

        //this.start.setAsVisited();
        //this.notVisited.remove(start);

        //Tile rn = this.randomNeighbour(start);

        //this.moveDFS(start, rn);
        //rn.setAsVisited();
        //System.out.println("Start position = (" + start.getPosition().x + ", " + start.getPosition().y + ") and random neighbour position = ("+ rn.getPosition().x + ", " + rn.getPosition().y + ")");
    }

    public void recursiveDFS(Tile current) {
        current.isVisited = true;
        List<int[]> directions = new ArrayList<>();
        directions.add(new int[]{0, -1}); // up
        directions.add(new int[]{0, 1}); // down
        directions.add(new int[]{-1, 0}); // left
        directions.add(new int[]{1, 0}); // right

        Collections.shuffle(directions);

        for (int[] d : directions) {
            int nx = current.getPosition().x + d[0];
            int ny = current.getPosition().y + d[1];

            if (nx >= 0 && ny >= 0 && nx < size && ny < size) {
                Tile n = nodes[nx][ny];
                if (!n.isVisited) {
                    this.moveDFS(current, n);
                    this.recursiveDFS(n);
                }
            }
        }
    }

    private void moveDFS(Tile s, Tile e) {
        int dx = s.getPosition().x - e.getPosition().x;
        int dy = s.getPosition().y - e.getPosition().y;

        if (dx == 0) {
            if (dy > 0 ) this.removeWall(s, 'N');    // moving North
            if (dy < 0) this.removeWall(s, 'S');    // moving South
        }
        if (dy == 0) {
            if (dx > 0) this.removeWall(s, 'W');    // moving East
            if (dx < 0) this.removeWall(s, 'E');    // moving West
        }
    }

    public Tile randomNeighbour(Tile t) {
        HashSet<Tile> neighbours = t.getNeighboursSet();
        int s = neighbours.size();
        int idx = new Random().nextInt(s);
        int i = 0;
        for(Tile n : neighbours)
        {
            if (i == idx)
                return n;
            i++;
        }
        return null;
    }

    public Tile getStartNode() {
        return start;
    }

    public Tile getEndNode() {
        return end;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++)
                result.append(map[i][j]);    // TODO: show only visited tiles (here or separate method), and show player's position as first letter of name!
            result.append('\n');
        }

        return result.toString();
    }

    public static void main(String[] args) {
        System.out.println("Test Maze generation with DFS...");

        Maze m = new Maze(6);    // Seed set to null results in random seed use. (!)

        m.generateMaze();

        System.out.println("Generated Maze: \n");
        System.out.println(m);
    }
}
