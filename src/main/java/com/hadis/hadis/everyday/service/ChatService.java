package com.hadis.hadis.everyday.service;

import com.hadis.hadis.everyday.model.Chat;
import com.hadis.hadis.everyday.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;

    public void saveChatId(Chat chat) {
        if (!checkChatId(chat)) {
            return;
        }

        chatRepository.save(chat);
    }

    public List<Chat> getAllChats() {
        return chatRepository.findAll();
    }

    private boolean checkChatId(Chat chat) {
        List<Chat> chats = chatRepository.findByChatId(chat.getChatId());

        return chats.isEmpty();
    }
}
