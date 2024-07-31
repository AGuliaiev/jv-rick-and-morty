package mate.academy.rickandmorty.dto.external;

public record CharacterDto(
        Long id,
        String name,
        String status,
        String species,
        String gender,
        String type) {

}
