package spring.dbt.ai.spring_ai_dbt.controller;

import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.Map;

@RestController
public class ClientOllamaController {

    private final OllamaChatModel chatModel;

    @Autowired
    public ClientOllamaController(OllamaChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @GetMapping("/generate")
    public Map<String, String> generate(
            @RequestParam(value = "message", defaultValue = "Tell a mathematical joke") String message){
        return Map.of("generation", chatModel.call(message));
    }

    @GetMapping("/generate/stream")
    public Flux<ChatResponse> generateStream(
            @RequestParam(value = "message", defaultValue = "Tell a mathematical joke") String message){
        Prompt prompt = new Prompt(new UserMessage(message));
        return chatModel.stream(prompt);
    }
}
