package Dimpusbot;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;

import javax.security.auth.login.LoginException;

public class Dimpus {
    public static final String prefix = "!";
    public static void main(String[] args) throws LoginException {
        JDABuilder.create("token",GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_MEMBERS)
        .addEventListeners(new Listener()).build();;
    }

}

