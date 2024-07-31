package mate.academy.rickandmorty.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.rickandmorty.models.Characters;
import mate.academy.rickandmorty.services.CharacterService;
import mate.academy.rickandmorty.services.DataLoader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(
        name = "Rick and Morty characters",
        description = "Endpoints for managing Rick and Morty characters"
)
@RestController
@RequestMapping("/api/characters")
@RequiredArgsConstructor
public class CharacterController {

    private final CharacterService characterService;
    private final DataLoader dataLoader;

    @GetMapping("/random")
    @Operation(
            summary = "Get random character",
            description = "Get random character from Rick and Morty"
    )
    public Characters getRandomCharacter() {
        return characterService.getRandomCharacter();
    }

    @GetMapping("/search")
    @Operation(
            summary = "Search characters by name",
            description = "Search characters by name from Rick and Morty"
    )
    public List<Characters> searchCharacters(@RequestParam String name) {
        return characterService.searchCharactersByName(name);
    }
}
