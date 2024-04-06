package com.hadis.hadis.everyday.bot;

import com.hadis.hadis.everyday.model.Chat;
import com.hadis.hadis.everyday.model.Hadis;
import com.hadis.hadis.everyday.service.ChatService;
import com.hadis.hadis.everyday.service.HadisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.logging.Logger;

@Component
@EnableScheduling
@ConditionalOnProperty(name = "scheduler.enabled", matchIfMissing = true)
public class ExchangeRatesBot extends TelegramLongPollingBot {

    private final static String START = "/start";

    @Autowired
    private ChatService chatService;
    @Autowired
    private HadisService hadisService;

    static final Logger LOGGER =
            Logger.getLogger(Hadis.class.getName());


    public ExchangeRatesBot(@Value("${bot.token}") String botToken) {
        super(botToken);
    }


    @Override
    public void onUpdateReceived(Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText()) {
            return;
        }

        String message = update.getMessage().getText();
        Long chatId = update.getMessage().getChatId();

        if (message.equals(START)) {
            String userName = update.getMessage().getChat().getUserName();
            startCommand(chatId, userName);
        }
    }

    @Override
    public String getBotUsername() {
        return "hadis_everyday_bot";
    }

    private void sendMessage(Long chatId, String text) {
        String chatIdStr = String.valueOf(chatId);
        SendMessage sendMessage = new SendMessage(chatIdStr, text);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void startCommand(Long chatId, String userName) {
        String startText = "Hello %s, " +
                " it is bot will send every day one hadis in kazakh" +
                " Thanks!";

        String formattedText = String.format(startText, userName);

        saveChatIdToDataBase(chatId);
        sendMessage(chatId, formattedText);
    }

    private void saveChatIdToDataBase(Long chatId) {
        Chat chat = Chat
                .builder()
                .chatId(chatId)
                .build();

        chatService.saveChatId(chat);
    }


    @Scheduled(cron = "0 */5 * * * *",zone = "${time.zone}")
    public void sendDailyMessage() {
        List<Chat> chatIds = chatService.getAllChats();
        List<Hadis> hadisses = hadisService.getAllHadis();

        for (Chat chat : chatIds) {
            Long chatId = chat.getChatId();

            int index = (int) (Math.random() * hadisses.size());

            String hadisOfDay = hadisses.get(index).getDescription();

            sendMessage(chatId, hadisOfDay);
        }
    }



}
