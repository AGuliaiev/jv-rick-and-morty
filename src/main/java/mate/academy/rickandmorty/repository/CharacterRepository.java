package mate.academy.rickandmorty.repository;

import java.util.List;
import mate.academy.rickandmorty.models.Characters;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacterRepository extends JpaRepository<Characters, Long> {
    List<Characters> findByNameContaining(String name);
}
