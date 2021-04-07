package Dimpusbot.command;

import java.util.List;

public interface Command {
    void handle(CommandContext ctx);
    String getName();
    default List<String> Aliases(){
        return List.of();
    };
}
