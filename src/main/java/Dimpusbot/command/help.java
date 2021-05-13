package Dimpusbot.command;

import Dimpusbot.CommandManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;

public class help implements Command {
    @Override
    public void handle(CommandContext ctx, String[] arg) {
        StringBuilder str = new StringBuilder();
        for(Command commands: CommandManager.command){
            str.append("**").append(commands.getName()).append("**").append("-").append(commands.getDescription()).append("\n");
        }
        Member member = ctx.getMessage().getMember();
        assert member != null;
        ctx.getMessage().getChannel().sendMessage(new EmbedBuilder()
                .setThumbnail(member.getUser().getAvatarUrl())
                .setAuthor(member.getUser().getName(),null,member.getUser().getAvatarUrl())
                .setDescription(str).build()).queue(message -> message.addReaction("\u23ED")
                .queue());
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "Returns this help command";
    }
}
