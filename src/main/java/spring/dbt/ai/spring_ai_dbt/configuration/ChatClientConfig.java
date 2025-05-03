package spring.dbt.ai.spring_ai_dbt.configuration;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatClientConfig {

    @Bean
    ChatClient client(ChatClient.Builder builder){
        return builder.defaultSystem("Recommend {type} movies on {ott}")
                .build();
    }
}
