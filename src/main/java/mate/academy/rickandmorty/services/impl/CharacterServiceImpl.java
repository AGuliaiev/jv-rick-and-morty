package mate.academy.rickandmorty.services.impl;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import mate.academy.rickandmorty.models.Characters;
import mate.academy.rickandmorty.repository.CharacterRepository;
import mate.academy.rickandmorty.services.CharacterService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CharacterServiceImpl implements CharacterService {
    private final CharacterRepository characterRepository;
    private final Random random = new Random();

    @Override
    public Characters getRandomCharacter() {
        List<Characters> characters = characterRepository.findAll();
        if (characters.isEmpty()) {
            throw new RuntimeException("No characters in the database");
        }
        return characters.get(random.nextInt(characters.size()));
    }

    @Override
    public List<Characters> searchCharactersByName(String name) {
        return characterRepository.findByNameContaining(name);
    }

    @Override
    public List<Long> getAllCharacterIds() {
        return characterRepository.findAll().stream()
                .map(Characters::getId)
                .collect(Collectors.toList());
    }

    @Override
    public void saveAllCharacters(List<Characters> characters) {
        characterRepository.saveAll(characters);
    }
}
