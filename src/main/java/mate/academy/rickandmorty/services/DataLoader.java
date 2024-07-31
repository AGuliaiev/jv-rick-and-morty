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
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataLoader {
    private static final String BASE_URL = "https://rickandmortyapi.com/api/character";
    private final CharacterService characterService;
    private final ObjectMapper objectMapper;
    private final CharacterMapper characterMapper;

    public void getCharacters() {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(BASE_URL))
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
            List<CharacterDto> list = characterDto.results().stream().toList();
            Set<Long> existingIds = new HashSet<>(characterService.getAllCharacterIds());
            List<Characters> newCharacters = list.stream()
                    .filter(dto -> !existingIds.contains(dto.id()))
                    .map(characterMapper::toModel)
                    .toList();
            if (!newCharacters.isEmpty()) {
                characterService.saveAllCharacters(newCharacters);
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
