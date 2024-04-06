package com.hadis.hadis.everyday.repository;

import com.hadis.hadis.everyday.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {

     List<Chat> findByChatId(Long chatId);

}
