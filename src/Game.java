import enums.DifficultyEnum;

import map.Maze;
import map.Position;

import java.util.HashMap;
import java.util.Map;

public class Game {
    private final Map<DifficultyEnum, Integer> difficultySizeMap = new HashMap<>();
    private final DifficultyEnum difficulty;
    public Maze map;
    public Player player;
    public Boolean running;


    public Game(DifficultyEnum difficulty, Player player) {
        this.difficulty = difficulty;
        this.player = player;

        difficultySizeMap.put(DifficultyEnum.EASY, 6);
        difficultySizeMap.put(DifficultyEnum.NORMAL, 8);
        difficultySizeMap.put(DifficultyEnum.HARD, 10);
        difficultySizeMap.put(DifficultyEnum.HARDCORE, 12);

        this.prepareMap();
        this.setPlayersPosition();

        this.running = true;
    }

    private void prepareMap() {
        Integer size = difficultySizeMap.get(difficulty);
        map = new Maze(size);
        map.generateMaze();
    }

    public void setPlayersPosition() {
        Position<Integer, Integer> start = map.start.getPosition();
        player.setPosition(start);
        map.setPlayersSign(player.sign, start.x, start.y);
    }

    public void movePlayer(Integer nx, Integer ny) {
        map.removeCharacter(player.position.x, player.position.y);
        player.moveTo(nx, ny);
        map.setPlayersSign(player.sign, nx, ny);
    }
}
