package map;

public class Maze extends Graph {
    private Integer size;    // map is always square (e.g. 4x4 or 10x10)
    private Integer seed;    // TODO: seed for random operations (?)

    public Maze(Integer size, Integer seed) {
        super();
        this.size = size;
        this.seed = seed;
    }
}
