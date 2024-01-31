package map;

public class Tile extends Node {
    // walls as N, E, S, W values, where true - no wall, false - wall, null - border (!)
    public Walls<Boolean, Boolean, Boolean, Boolean> walls;
    public Boolean isStart;
    public Boolean isEnd;

    public Tile(Graph parent, Position<Integer, Integer> position, Walls<Boolean, Boolean, Boolean, Boolean> walls) {
        super(parent, position);
        this.walls = walls;
        this.isStart = false;
        this.isEnd = false;
    }

    public Walls<Boolean, Boolean, Boolean, Boolean> getWalls() {
        return walls;
    }

    public void setWall(String wall, Boolean value) {
        switch (wall) {
            case "N" -> walls.setN(value);
            case "E" -> walls.setE(value);
            case "S" -> walls.setS(value);
            case "W" -> walls.setW(value);
        };
    }

    public void setAsStart() {
        isStart = true;
    }

    public void setAsEnd(String wall) {
        isEnd = true;
        this.setWall(wall, true);
    }

    public void updateTileString() {

    }
}
