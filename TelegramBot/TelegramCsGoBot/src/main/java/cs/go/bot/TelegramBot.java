package cs.go.bot;

import cs.go.config.BotConfig;
import cs.go.updateReceiver.UpdateReceiver;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.Serializable;
import java.util.List;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    final private BotConfig botConfig;

    final private UpdateReceiver updateReceiver;


    public TelegramBot(BotConfig botConfig, UpdateReceiver updateReceiver) {

        this.updateReceiver = updateReceiver;

        this.botConfig = botConfig;
    }

    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }

    @Override
    public String getBotToken() {
        return botConfig.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        List<PartialBotApiMethod<? extends Serializable>> messageToSend = updateReceiver.handle(update);
        if(messageToSend !=null && !messageToSend.isEmpty()) {
            messageToSend.forEach(response -> {
                if (response instanceof SendMessage) { //Проверить тут если будет ошибка!
                    executeWithExceptionCheck((SendMessage) response);
                }
            });
        }
    }

    public void executeWithExceptionCheck (SendMessage sendMessage) {
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}


