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

public class kick implements Command {


    @Override
    public void handle(CommandContext ctx,String[] args){
        if(!ctx.getMessage().getMember().hasPermission(Permission.BAN_MEMBERS)) return;
        String[] message = ctx.getMessage().getContentRaw().split("\\s+");
        MessageChannel channel = ctx.getMessage().getChannel();
        User invokeUser = ctx.getMessage().getMember().getUser();
        Member banMember = null;

        String reason = String.join(" ",Arrays.copyOfRange(message,2,message.length));
        try {
            banMember = ctx.getMessage().getGuild().getMemberCache().getElementById(message[1]);
        }
        catch(NumberFormatException err){
            channel.sendMessage("The specified ID is not a valid snowflake (" +message[1]+").").queue();
            return;
        }
        if(banMember==null) {
            channel.sendMessage("The provided ID is not a valid Snowflake.").queue();
            return;
        }
        if(banMember.hasPermission(Permission.KICK_MEMBERS,Permission.BAN_MEMBERS )) return;
        if(invokeUser.getId().equals(banMember.getId())) return;
        if(reason.equals("")){
            reason = "No reason provided";
        }

        channel.sendMessage(new EmbedBuilder()
                .setAuthor(invokeUser.getAsTag()+" ("+invokeUser.getId()+")",null,invokeUser.getAvatarUrl())
                .setDescription("**Member:** `"+banMember.getUser().getAsTag()+"` ("+banMember.getUser().getId()+")\n" +
                        "**Action:** Kick\n"+"**Reason:** "+reason)
                .setThumbnail(banMember.getUser().getDefaultAvatarUrl())
                .setFooter("Invoked by "+invokeUser.getName()).setTimestamp(Instant.now())
                .setColor(Color.red).build()).queue();
        ctx.getMessage().getGuild().kick(banMember,reason).queue();
    }

    @Override
    public String getName() {
        return "kick";
    }

    @Override
    public String getDescription() {
        return "Kicks the member from the guild";
    }
}
