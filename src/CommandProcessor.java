import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;
import enums.CommandEnum;
import enums.DifficultyEnum;
import enums.MoveEnum;
import map.Position;
import map.Tile;
import map.Walls;

public class CommandProcessor {
    private final Map<String, CommandEnum> commandMap = new HashMap<>();
    private final Map<String, MoveEnum> moveMap = new HashMap<>();
    private final Map<String, DifficultyEnum> difficultyMap = new HashMap<>();
    public Server.CommandHandler handler;
    public HttpExchange exchange;
    public Game currentGame;

    public CommandProcessor(Server.CommandHandler handler, HttpExchange exchange) {
        this.handler = handler;
        this.exchange = exchange;

        commandMap.put("start", CommandEnum.START);
        commandMap.put("look", CommandEnum.LOOK);
        commandMap.put("mv", CommandEnum.MOVE);
        commandMap.put("move", CommandEnum.MOVE);
        commandMap.put("m", CommandEnum.MAP);
        commandMap.put("map", CommandEnum.MAP);
        commandMap.put("h", CommandEnum.HELP);
        commandMap.put("help", CommandEnum.HELP);
        commandMap.put("q", CommandEnum.QUIT);
        commandMap.put("quit", CommandEnum.QUIT);
        moveMap.put("n", MoveEnum.NORTH);
        moveMap.put("north", MoveEnum.NORTH);
        moveMap.put("s", MoveEnum.SOUTH);
        moveMap.put("south", MoveEnum.SOUTH);
        moveMap.put("w", MoveEnum.WEST);
        moveMap.put("west", MoveEnum.WEST);
        moveMap.put("e", MoveEnum.EAST);
        moveMap.put("east", MoveEnum.EAST);
        difficultyMap.put("easy", DifficultyEnum.EASY);
        difficultyMap.put("normal", DifficultyEnum.NORMAL);
        difficultyMap.put("hard", DifficultyEnum.HARD);
        difficultyMap.put("hardcore", DifficultyEnum.HARDCORE);

        this.currentGame = null;
    }

    public String processCommand(String command) throws IOException {
        String[] parts = command.split(" ");
        String text = parts[0].toLowerCase();
        CommandEnum cmd = commandMap.get(text);

        if (cmd == null)
            return "Unknown command...";

        return switch (cmd) {
            case START -> startGameCommand(parts);
            case LOOK -> lookCommand();
            case MOVE -> moveCommand(parts);
            case MAP -> showMapCommand();
            case HELP -> showHelpCommand();
            case QUIT -> quitCommand();
        };
    }

    private String lookCommand() {
        if (currentGame == null) return "Start the game first...";

        Position<Integer, Integer> p = currentGame.player.position;
        Walls w = currentGame.map.nodes[p.y][p.x].getWalls();

        return String.format("Possible direction(s):" + w.getTrueWalls());
    }

    private String moveCommand(String[] parts) {
        if (parts.length != 2)
            return "Wrong move command...";

        String text = parts[1].toLowerCase();

        MoveEnum direction = moveMap.get(text);

        if (direction == null)
            return "Wrong direction...";

        return this.move(direction);
    }

    private String move(MoveEnum direction) {
        Position<Integer, Integer> pp = currentGame.player.position;
        Tile t = currentGame.map.nodes[pp.y][pp.x];
        Walls w = currentGame.map.nodes[pp.y][pp.x].getWalls();

        return switch (direction) {
            case NORTH -> {
                if (w.getN()) {
                    currentGame.movePlayer(pp.x - 1, pp.y);
                    if (isFinished()) {
                        String n = currentGame.player.name;
                        currentGame.running = false;
                        currentGame = null;
                        yield String.format("Congratulations, " + n + "! You won!");
                    }
                    yield "You went north.";
                } else
                    yield "Cannot go north.";
            }
            case EAST -> {
                if (w.getE()) {
                    currentGame.movePlayer(pp.x, pp.y + 1);
                    if (isFinished()) {
                        String n = currentGame.player.name;
                        currentGame.running = false;
                        currentGame = null;
                        yield String.format("Congratulations, " + n + "! You won!");
                    }
                    yield "You went east.";
                } else
                    yield "Cannot go east.";
            }
            case SOUTH -> {
                if (w.getS()) {
                    currentGame.movePlayer(pp.x + 1, pp.y);
                    if (isFinished()) {
                        String n = currentGame.player.name;
                        currentGame.running = false;
                        currentGame = null;
                        yield String.format("Congratulations, " + n + "! You won!");
                    }
                    yield "You went south.";
                } else
                    yield "Cannot go south.";
            }
            case WEST -> {
                if (w.getW()) {
                    currentGame.movePlayer(pp.x, pp.y - 1);
                    if (isFinished()) {
                        String n = currentGame.player.name;
                        currentGame.running = false;
                        currentGame = null;
                        yield String.format("Congratulations, " + n + "! You won!");
                    }
                    yield "You went west.";
                } else
                    yield "Cannot go west.";
            }
        };
    }

    public String startGameCommand(String[] parts) throws IOException {
        if (!(currentGame == null) && currentGame.running)
            return "";

        if (parts.length != 3)
            return "Wrong start command";

        DifficultyEnum difficulty = difficultyMap.get(parts[1].toLowerCase());

        if (difficulty == null)
            return "Wrong difficulty...";

        Player player = new Player(parts[2]);

        Game g = new Game(difficulty, player); // TODO: show only visited tiles (here or separate method)
        g.setPlayersPosition();
        currentGame = g;

        while (g.running) {
            if (isFinished()) {
                String n = currentGame.player.name;
                currentGame.running = false;
                currentGame = null;
                return String.format("Congratulations, " + n + "! You won!");
            }
            handler.handle(exchange);
        }
        return "";
    }

    private Boolean isFinished() {
        Position<Integer, Integer> p = currentGame.player.position;
        return currentGame.map.nodes[p.y][p.x].isEnd;
    }

    private String showMapCommand() {
        if (currentGame == null)
            return "Start a new game to see map.";
        return currentGame.map.toString();
    }

    private String showHelpCommand() {
        StringBuilder result = new StringBuilder();

        result.append("---------- HELP ----------\n\n");

        result.append("Difficulty: \n");
        result.append("- EASY - maze size 6x6, full access to map (not implemented yet) \n");
        result.append("- NORMAL - maze size 8x8, limited access to map (not implemented yet) \n");
        result.append("- HARD - maze size 10x10, limited access to map (not implemented yet) \n");
        result.append("- HARDCORE - maze size 12x12, no access to map (not implemented yet) \n\n");

        result.append("To move type 'move {DIRECTION}' or 'mv {DIRECTION}', where direction might be: \n");
        result.append("- 'north' / 'n' - up\n");
        result.append("- 'east' / 'e' - right\n");
        result.append("- 'south' / 's' - down\n");
        result.append("- 'west' / 'w' - left\n\n");

        result.append("To show the map type 'map' or 'm'. Your position on the map is selected as first letter (capital) of your name.\n\n");

        result.append("Type 'look' to look around and find out where you can go.");

        result.append("To quit the game, type 'quit' or 'q'.\n\n");

        result.append("--------------------------\n");

        return result.toString();
    }

    private String quitCommand() {
        if (currentGame == null || !currentGame.running)
            return "There is no game to quit.";

        currentGame.running = false;
        currentGame = null;

        return "See you later!";
    }
}
