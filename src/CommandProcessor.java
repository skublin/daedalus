import java.util.HashMap;
import java.util.Map;

import enums.CommandEnum;
import enums.MoveEnum;

public class CommandProcessor {
    private final Map<String, CommandEnum> commandMap = new HashMap<>();
    private final Map<String, MoveEnum> moveMap = new HashMap<>();

    public CommandProcessor() {
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
    }

    public String processCommand(String command) {
        // TODO: logic to process commands (!)
        String[] parts = command.split(" ");
        String text = parts[0].toLowerCase();
        CommandEnum cmd = commandMap.get(text);

        if (cmd == null)
            return "Unknown command...";

        return switch (cmd) {
            case MOVE -> moveCommand(parts);
            case MAP -> showMapCommand();
            case HELP -> showHelpCommand();
            case QUIT -> quitCommand();
        };
    }

    private String moveCommand(String[] parts) {
        if (parts.length != 2)
            return "Wrong move command...";

        String text = parts[1].toLowerCase();

        MoveEnum direction = moveMap.get(text);

        if (direction == null)
            return "Wrong direction...";

        // TODO: prepare method to check if possible to move (depending on current map)
        return switch (direction) {
            case NORTH -> "Moving north";
            case SOUTH -> "Moving south";
            case WEST -> "Moving west";
            case EAST -> "Moving east";
        };
    }

    private String showMapCommand() {
        return "Map (known area)";
    }

    private String showHelpCommand() {
        return "HELP TEXT";
    }

    private String quitCommand() {
        return "QUIT";
    }
}
