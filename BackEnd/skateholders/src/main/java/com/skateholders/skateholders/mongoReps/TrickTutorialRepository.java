package com.skateholders.skateholders.mongoReps;

import com.skateholders.skateholders.mongoDocs.TrickTutorial;
import org.springframework.data.mongodb.repository.MongoRepository;

// Não precisamos de nenhum mtodo customizado aqui, o MongoRepository já nos dá tudo.
public interface TrickTutorialRepository extends MongoRepository<TrickTutorial, String> {
}