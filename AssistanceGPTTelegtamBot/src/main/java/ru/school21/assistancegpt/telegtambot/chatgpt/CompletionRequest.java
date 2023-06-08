package ru.school21.assistancegpt.telegtambot.chatgpt;

import java.util.List;

public record CompletionRequest(String model, List<Message> messages) {

    record Message(String role, String content) {}
}