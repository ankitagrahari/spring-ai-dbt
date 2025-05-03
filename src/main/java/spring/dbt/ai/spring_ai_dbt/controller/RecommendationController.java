package spring.dbt.ai.spring_ai_dbt.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import spring.dbt.ai.spring_ai_dbt.entities.Movie;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/recommend")
public class RecommendationController {

    private final ChatClient client;

    public RecommendationController(ChatClient client) {
        this.client = client;
    }

    @GetMapping("/ok")
    public String isOK(){
        return "Recommendation is up and running!!";
    }

    @GetMapping("/movies")
    public List<Movie> recommendAPI(
                                    @RequestParam(value = "message", defaultValue = "Recommend trending movies on netflix") String message,
                                    @RequestParam(value = "type") String type,
                                    @RequestParam(value = "ott") String ottPlatform){

        var converter = new BeanOutputConverter<>(new ParameterizedTypeReference<List<Movie>>() {});

        Map<String, Object> params = new HashMap<>();
        params.put("type", type);
        params.put("ott", ottPlatform);
        params.put("format", converter.getFormat());

        return this.client.prompt()
                        .system(promptSystemSpec -> promptSystemSpec.params(params))
                        .user(prompt -> prompt
                                .text(message + "{format}")
                                .params(params))
                        .call()
                        .entity(new ParameterizedTypeReference<>() {});
    }
}
