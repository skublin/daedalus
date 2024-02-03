package map;

import java.util.HashSet;

public class Tile extends Node {
    // walls as N, E, S, W values, where true - no wall, false - wall, null - border (!)
    public Walls<Boolean, Boolean, Boolean, Boolean> walls;
    public HashSet<Tile> neighboursSet;
    public Boolean isStart;
    public Boolean isEnd;

    public Tile(Graph parent, Position<Integer, Integer> position, Walls<Boolean, Boolean, Boolean, Boolean> walls) {
        super(parent, position);
        this.walls = walls;
        this.neighboursSet = new HashSet<>();
        this.isStart = false;
        this.isEnd = false;
    }

    public Walls<Boolean, Boolean, Boolean, Boolean> getWalls() {
        return walls;
    }

    public void setWall(Character wall, Boolean value) {
        switch (wall) {
            case 'N' -> walls.setN(value);
            case 'E' -> walls.setE(value);
            case 'S' -> walls.setS(value);
            case 'W' -> walls.setW(value);
        };
    }

    public void setAsStart() {
        isStart = true;
    }

    public void setAsEnd(Character wall) {
        isEnd = true;
        this.setWall(wall, true);
    }

    public HashSet<Tile> getNeighboursSet() {
        return neighboursSet;
    }

    public void addNeighbour(Tile neighbour, Boolean createEdge) {
        this.neighboursSet.add(neighbour);
        if (createEdge) {
            Edge e = new Edge(parent, this, neighbour);
            this.edges.add(e);
        }
    }

    public void markVisitedNeighbour(Tile neighbour) {
        this.neighboursSet.remove(neighbour);
    }

    /*public void updateTileString(Integer k) {
        Character[][] m = this.getParent().getMap();
        int i = 2 * this.getPosition().y + 1;
        int j = 2 * this.getPosition().x + 2 * k + 2;
        m[i][j] = 'X';
    }*/
}
