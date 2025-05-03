package spring.dbt.ai.spring_ai_dbt.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/ai")
public class ClientController {

    private ChatClient chatClient;

    private static final String PROMPT = """
            {
                "model": "llama3.2",
                "stream": false,
                "prompt": "$message$"
            }
            """;

    private static final String MESSAGE_FORMAT = """
            {
              "model": "llama3.2",
              "messages": [{\s
                "role": "user",\s
                "content": "$message$"\s
                }
              ]
            }
           \s""";

    public ClientController(ChatClient.Builder clientBuilder){
        this.chatClient = clientBuilder.build();
    }

    @GetMapping("/ok")
    public String home(){
        return "Client is up and running!!";
    }

    @GetMapping("/{message}")
    Flux<String> generateResponse(@PathVariable(value = "message") String userInput){

        userInput = MESSAGE_FORMAT.replace("$message$", userInput);
        System.out.println(userInput);

        return this.chatClient.prompt(userInput)
                .stream()
                .content();
    }
}
