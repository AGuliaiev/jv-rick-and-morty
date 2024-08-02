package mate.academy.rickandmorty.dto.external;

import java.util.List;

public record CharacterResponseDto(
        InfoDto info,
        List<CharacterDto> results
) {
}
