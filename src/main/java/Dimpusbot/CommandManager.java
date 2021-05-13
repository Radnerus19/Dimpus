package Dimpusbot;

import Dimpusbot.command.Command;
import Dimpusbot.command.CommandContext;
import Dimpusbot.command.commands.ban;
import Dimpusbot.command.commands.docs;
import Dimpusbot.command.commands.kick;
import Dimpusbot.command.commands.ping;
import Dimpusbot.command.help;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class CommandManager {
    public static List<Command> command = new ArrayList<>();

    public CommandManager(){
        addCommand(new ping());
        addCommand(new ban());
        addCommand(new kick());
        addCommand(new help());
        try {
            addCommand(new docs());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addCommand(Command cmd){
        boolean nameFound = command.stream().anyMatch((name)->name.getName().equalsIgnoreCase(cmd.getName()));
        if(nameFound){
            throw new IllegalArgumentException("Command with this name is already present.");
        }
        command.add(cmd);
    }
    @Nullable
    private Command getCommand(String search){
        String searchLower = search.toLowerCase();
        for (Command cmd : command) {
            if(cmd.getName().equals(searchLower) || cmd.Aliases().contains(search)){
                return cmd;
            }
        }
        return null;
    }

    void handle(GuildMessageReceivedEvent event){
        String[] args = event.getMessage().getContentRaw().replaceFirst("(?i)"+ Pattern.quote(Dimpus.prefix),"").split("\\s+");
        String commandCall = args[0];
        Command cmd = this.getCommand(commandCall);
        if(cmd!=null){
            List<String> args1 = Arrays.asList(args).subList(1, args.length);
            CommandContext ctx = new CommandContext(event,args1);
            cmd.handle(ctx,args);
        }
    }

}
