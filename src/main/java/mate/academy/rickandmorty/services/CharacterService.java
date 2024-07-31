package mate.academy.rickandmorty.services;

import java.util.List;
import mate.academy.rickandmorty.models.Characters;

public interface CharacterService {
    Characters getRandomCharacter();

    List<Characters> searchCharactersByName(String name);

    List<Long> getAllCharacterIds();

    void saveAllCharacters(List<Characters> characters);
}
