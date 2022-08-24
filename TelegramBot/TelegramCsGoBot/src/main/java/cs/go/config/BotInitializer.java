package cs.go.config;

import cs.go.bot.TelegramBot;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Component
public class BotInitializer {

    TelegramBot telegramBot;

   public BotInitializer (TelegramBot telegramBot) {
       this.telegramBot = telegramBot;

   }

   @EventListener ({ContextRefreshedEvent.class})
    public void init() throws TelegramApiException {

       TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);

       try{

           telegramBotsApi.registerBot(telegramBot);

       } catch (TelegramApiException e) {

           e.printStackTrace();

       }



   }
}
