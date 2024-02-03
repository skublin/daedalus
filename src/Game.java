import enums.DifficultyEnum;

import map.Maze;

import java.util.HashMap;
import java.util.Map;

public class Game {
    private final Map<DifficultyEnum, Integer> difficultySizeMap = new HashMap<>();
    private final DifficultyEnum difficulty;
    private Maze map;

    public Game(DifficultyEnum difficulty) {
        this.difficulty = difficulty;

        difficultySizeMap.put(DifficultyEnum.EASY, 6);
        difficultySizeMap.put(DifficultyEnum.NORMAL, 8);
        difficultySizeMap.put(DifficultyEnum.HARD, 10);
        difficultySizeMap.put(DifficultyEnum.HARDCORE, 12);

        this.prepareMap();
    }

    private void prepareMap() {
        Integer size = difficultySizeMap.get(difficulty);
        map = new Maze(size);
        map.generateMaze();
    }
}
