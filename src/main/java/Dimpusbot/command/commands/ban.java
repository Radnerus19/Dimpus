package Dimpusbot.command.commands;

import Dimpusbot.command.Command;
import Dimpusbot.command.CommandContext;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;

import java.awt.*;
import java.time.Instant;
import java.util.Arrays;

public class ban implements Command {


    @Override
    public void handle(CommandContext ctx) throws NullPointerException {
        if(!ctx.getMessage().getMember().hasPermission(Permission.BAN_MEMBERS)) return;
        String[] message = ctx.getMessage().getContentRaw().split("\\s+");
        MessageChannel channel = ctx.getMessage().getChannel();
        User invokeUser = ctx.getMessage().getMember().getUser();
        Member banMember = null;

        String reason = String.join(" ",Arrays.copyOfRange(message,2,message.length));
        try {
            banMember = ctx.getMessage().getGuild().getMemberById(message[1]);
        }
        catch(NullPointerException err){
            channel.sendMessage("The provided ID is not a valid Member ID.").queue();
        }
        if(reason.equals("")){
            reason = "No reason provided";
        }

        channel.sendMessage(new EmbedBuilder()
        .setAuthor(invokeUser.getAsTag()+" ("+invokeUser.getId()+")",null,invokeUser.getAvatarUrl())
        .setDescription("**Member:** `"+banMember.getUser().getAsTag()+"` ("+banMember.getUser().getId()+")\n" +
                "**Action:** Ban\n"+"**Reason:** "+reason)
        .setThumbnail(banMember.getUser().getAvatarUrl())
        .setFooter("Invoked by "+invokeUser.getName()).setTimestamp(Instant.now())
        .setColor(Color.red).build()).queue();
        banMember.ban(5,reason).queue();
    }

    @Override
    public String getName() {
        return "ban";
    }
}
