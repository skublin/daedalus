import enums.DifficultyEnum;

import map.Maze;

import java.util.HashMap;
import java.util.Map;

public class Game {
    private final Map<DifficultyEnum, Integer> difficultySizeMap = new HashMap<>();
    private final DifficultyEnum difficulty;
    private final Integer seed;
    private Maze map;

    public Game(DifficultyEnum difficulty, Integer seed) {
        this.difficulty = difficulty;
        this.seed = seed;

        difficultySizeMap.put(DifficultyEnum.EASY, 6);
        difficultySizeMap.put(DifficultyEnum.NORMAL, 8);
        difficultySizeMap.put(DifficultyEnum.HARD, 10);
        difficultySizeMap.put(DifficultyEnum.HARDCORE, 12);

        this.prepareMap();
    }

    private void prepareMap() {
        Integer size = difficultySizeMap.get(difficulty);
        map = new Maze(size, seed);
        map.generateMaze();
    }
}
