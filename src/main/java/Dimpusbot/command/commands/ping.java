package Dimpusbot.command.commands;

import Dimpusbot.command.Command;
import Dimpusbot.command.CommandContext;

public class ping implements Command {


    @Override
    public void handle(CommandContext ctx) {
        ctx.getChannel().sendMessageFormat("Latency: %sms",ctx.getJDA().getGatewayPing()).queue();
    }

    @Override
    public String getName() {
        return "ping";
    }
}
