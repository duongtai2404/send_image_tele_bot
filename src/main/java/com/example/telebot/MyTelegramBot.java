package com.example.telebot;

import java.io.File;
import java.io.InputStream;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.LongPollingBot;

@Component
public class MyTelegramBot extends TelegramLongPollingBot {
	
	private String botToken = "6874402777:AAHGytw6e2qR_MqIun7cbA8HCAwu77W2RNg";
	private String botUserName = "ex_send_images_bot";
	private String pathRandomImage = "https://random.imagecdn.app/500/300";

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            if ("/start".equals(messageText)) {
                handleStartCommand(update.getMessage().getChatId());
            }
            if("/image".equals(messageText)) {
            	handleImageCommand(update.getMessage().getChatId());
            }
        }
    }

    private void handleStartCommand(Long chatId) {
    	SendMessage message = new SendMessage(chatId.toString(), "Hello! Wanna generate image ? Type /image ");

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    
    private void handleImageCommand(Long chatId) {
        SendPhoto photo = new SendPhoto();
        photo.setChatId(chatId.toString());
        
        InputFile inputFile = new InputFile();
        inputFile.setMedia(pathRandomImage);
        
        photo.setPhoto(inputFile);
        try {
            execute(photo);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return botUserName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }
}