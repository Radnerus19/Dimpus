package Dimpusbot.command.commands;

import Dimpusbot.command.Command;
import Dimpusbot.command.CommandContext;

import java.io.IOException;
import java.net.HttpURLConnection;

import net.dv8tion.jda.api.EmbedBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.net.URL;
import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;

public class docs implements Command {
    public docs() throws IOException {

    }
    @Override
    public void handle(CommandContext ctx,String[] args) {
        if(args.length<2){
            ctx.getMessage().getChannel().sendMessage("Provide query to seach through the docs.").queue();
            return;
        }
        Document html = null;
        try {
            html = Jsoup.parse(getDocs("https://ci.dv8tion.net/job/JDA/javadoc/allclasses.html"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String link,anoClass;
        Element linkad;
        assert html != null;
        boolean classes = html.body().getElementsByTag("li").text().toLowerCase(Locale.ROOT).contains(args[1].toLowerCase(Locale.ROOT));
        if(classes){
            linkad = html.body().select("[href*="+args[1]+"]").first();
            link = linkad.attr("href");
            anoClass = "https://ci.dv8tion.net/job/JDA/javadoc/"+link;
            System.out.println(anoClass);
            try {
                html = Jsoup.parse(getDocs(anoClass));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Element desc = html.getElementsByAttributeValue("class","block").first();
            ctx.getMessage().getChannel().sendMessage(new EmbedBuilder()
            .setTitle(html.body().select("h2").first().text(),anoClass)
                    .setAuthor("JDA docs",null,ctx.getMessage().getJDA().getSelfUser().getAvatarUrl())
                    .setDescription(desc.ownText()+"\n\n" + (desc.select("b").size()>0?"**"+desc.select("b").first().ownText()+"**":"")+
                            (desc.select("pre").size()>0?"\n```java\n"+desc.select("pre").text()+"```":"")).build()).queue();
        }
        else{
            ctx.getMessage().getChannel().sendMessage("No class exists.").queue();
        }


    }

    @Override
    public String getName() {
        return "docs";
    }

    @Override
    public String getDescription() {
        return "Search through the documents";
    }
    public String getDocs(String docURL) throws IOException {
        String inline = null;
        URL url  = new URL(docURL) ;
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.connect();
        int responsecode = con.getResponseCode();
        if(responsecode!=200) throw new RuntimeException("HttpResponseCode: " +responsecode);
        else{
            Scanner sc = new Scanner(url.openStream());
            while(sc.hasNext()){
                inline+=sc.nextLine()+"\n";
            }
            return inline;

        }
    }
}
