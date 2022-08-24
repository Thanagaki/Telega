package cs.go.updateReceiver;

import cs.go.handler.Handler;
import cs.go.model.State;
import cs.go.model.User;
import cs.go.repository.JpaUserRepository;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@Component
public class UpdateReceiver {

    private final List<Handler> handlerList;

    private final JpaUserRepository userRepository;

    public UpdateReceiver (List<Handler> handlerList, JpaUserRepository userRepository) {

        this.handlerList = handlerList;
        this.userRepository = userRepository;
    }

    public List<PartialBotApiMethod<? extends Serializable>> handle (Update update) {

        try{
            if (isMessageWithText(update)) {
                final Message message = update.getMessage();
                final long chatId = message.getFrom().getId();
                final User user = userRepository.getByChatId(chatId)
                        .orElseGet(() -> userRepository.save(new User(chatId)));
                return getHandlerByState(user.getBotState()).handle(user, message.getText());

            } else if (update.hasCallbackQuery()) {
                final CallbackQuery callbackQuery = update.getCallbackQuery();
                final long chatId = callbackQuery.getFrom().getId();
                final User user = userRepository.getByChatId(chatId).orElseGet(() ->  userRepository.save(new User(chatId)));
                return getHandlerByCallBackQuery(callbackQuery.getData()).handle(user, callbackQuery.getData());
            }
                throw new UnsupportedOperationException();
        } catch (UnsupportedOperationException e ) {
            return Collections.emptyList();
        }
    }

    private Handler getHandlerByState (State state) {
        return handlerList.stream().filter(h -> h.operatedBotState() != null)
                .filter(h -> h.operatedBotState().equals(state))
                .findAny()
                .orElseThrow(UnsupportedOperationException::new);
    }

    private Handler getHandlerByCallBackQuery(String query) {
        return handlerList.stream().filter(h -> h.operatedCallBackQuery().stream().anyMatch(query :: startsWith))
                .findAny()
                .orElseThrow(UnsupportedOperationException :: new );
    }

    //Посмотреть нужен ли тут "!"
    private boolean isMessageWithText(Update update) {
        return  !update.hasCallbackQuery() && update.hasMessage() && update.getMessage().hasText();
    }



}
