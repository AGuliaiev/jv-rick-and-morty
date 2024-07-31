package mate.academy.rickandmorty.mapper;

import mate.academy.rickandmorty.dto.external.CharacterDto;
import mate.academy.rickandmorty.models.Characters;
import org.springframework.stereotype.Component;

@Component
public class CharacterMapper {
    public Characters toModel(CharacterDto characterDto) {
        if (characterDto == null) {
            return null;
        }
        Characters character = new Characters();
        character.setExternalId(String.valueOf(characterDto.id()));
        character.setName(characterDto.name());
        character.setStatus(characterDto.status());
        character.setGender(characterDto.gender());
        return character;
    }
}
