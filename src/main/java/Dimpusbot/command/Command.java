package Dimpusbot.command;

import java.lang.reflect.Array;
import java.util.List;

public interface Command {
    void handle(CommandContext ctx, String[] ar);
    String getName();
    String getDescription();
    default List<String> Aliases(){
        return List.of();
    };
}
