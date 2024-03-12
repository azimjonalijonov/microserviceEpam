package org.example.repository;

import org.example.entity.TrainerSummaryEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TrainerSummaryRepository extends MongoRepository<TrainerSummaryEntity, Long> {
    TrainerSummaryEntity findByUsername(String username);

}
