package mate.academy.rickandmorty.services;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class DataLoaderInitializer {
    private final DataLoader dataLoader;

    public DataLoaderInitializer(DataLoader dataLoader) {
        this.dataLoader = dataLoader;
    }

    @PostConstruct
    public void loadInitialData() {
        dataLoader.getCharacters();
    }
}
