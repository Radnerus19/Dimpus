package Dimpusbot;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.LoginException;

public class Dimpus {
    public static final String prefix = "!";
    public static void main(String[] args) throws LoginException {
        JDA jda = JDABuilder.createDefault("ODI0MTM4NTY3NzkzMzc3MzEx.YFrA2g.HoQ0Q4p87e_AAxXLCSBUnd1rMOI").build();
        jda.addEventListener(new Listener());
    }

}

