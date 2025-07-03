package com.skateholders.skateholders.mongoReps;

import com.skateholders.skateholders.mongoDocs.TrickTutorial;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TrickTutorialRepository extends MongoRepository<TrickTutorial, String> {
    List<TrickTutorial> findByTrickId(Long trickId);
}

