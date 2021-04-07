package Dimpusbot.command;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public interface Icommandcontext {
    GuildMessageReceivedEvent getEvent();

    default Guild getGuild(){
        return this.getEvent().getGuild();
    }

    default TextChannel getChannel(){
        return this.getEvent().getChannel();
    }

    default Message getMessage(){
        return this.getEvent().getMessage();
    }

    default JDA getJDA(){ return this.getEvent().getJDA();}
}

