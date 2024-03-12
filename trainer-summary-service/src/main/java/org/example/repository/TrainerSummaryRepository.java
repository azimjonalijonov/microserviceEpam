package org.example.repository;

import org.example.entity.TrainerSummaryEntity;
 import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainerSummaryRepository extends MongoRepository<TrainerSummaryEntity, Long> {
    List<TrainerSummaryEntity> findAllByUsername(String username);
}
