package ru.school21.assistancegpt.telegtambot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.school21.assistancegpt.telegtambot.chatgpt.ChatGptController;
import ru.school21.assistancegpt.telegtambot.config.BotConfig;

import java.io.IOException;

@Component
public class TelegramBot extends TelegramLongPollingBot {
    @Autowired
    final BotConfig config;
    @Autowired
    private ChatGptController chatgpt;

    public TelegramBot(BotConfig config) {
        this.config = config;
    }

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText())  {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            String request = update.getMessage().getText();

            switch (messageText) {
                case "/start":
                    startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                    break;
                case "/clear":
                    chatgpt.clearDialog();
                    sendMessage(chatId, "Диалог удален");
                    break;
                default:
                    try {
                        sendMessage(chatId, chatgpt.chatWithGpt(request));
                        break;
                    } catch (IOException | InterruptedException e) {
                        throw new RuntimeException(e);
                    }
            }
        }

    }
    public void startCommandReceived(long chatId, String name) {
        String answer = "Привет, " + name + ", приятно познокомиться!" +
                "\nДля сброса диалога ввееди: /clear";
        sendMessage(chatId, answer);
    }
    public void sendMessage(long chatId, String textToSend) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public String getBotUsername() {
        return config.getBotName();
    }
    @Override
    public String getBotToken() {
        return config.getToken();
    }
}
