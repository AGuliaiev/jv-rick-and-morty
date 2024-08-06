package mate.academy.rickandmorty.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import mate.academy.rickandmorty.dto.external.CharacterDto;
import mate.academy.rickandmorty.dto.external.CharacterResponseDto;
import mate.academy.rickandmorty.mapper.CharacterMapper;
import mate.academy.rickandmorty.models.Characters;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataLoader {
    @Value("${rick-and-morty.url}")
    private String baseUrl;
    private final CharacterService characterService;
    private final ObjectMapper objectMapper;
    private final CharacterMapper characterMapper;

    public void getCharacters() {
        HttpClient client = HttpClient.newHttpClient();
        String url = baseUrl;
        Set<Long> existingIds = new HashSet<>(characterService.getAllCharacterIds());
        while (url != null) {
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create(url))
                    .build();
            try {
                HttpResponse<String> response = client.send(
                        request,
                        HttpResponse.BodyHandlers.ofString()
                );
                CharacterResponseDto characterDto = objectMapper.readValue(
                        response.body(),
                        CharacterResponseDto.class
                );
                List<CharacterDto> characterDtoList = characterDto.results().stream().toList();
                List<Characters> newCharacters = characterDtoList.stream()
                        .filter(dto -> !existingIds.contains(dto.id()))
                        .map(characterMapper::toModel)
                        .toList();
                if (!newCharacters.isEmpty()) {
                    characterService.saveAllCharacters(newCharacters);
                }
                url = characterDto.info().next();
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
