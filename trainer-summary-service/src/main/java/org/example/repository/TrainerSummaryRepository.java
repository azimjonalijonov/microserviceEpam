package org.example.repository;

import org.example.entity.TrainerSummaryEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.Month;
import java.time.Year;
import java.util.List;
import java.util.Map;

@Repository
public interface TrainerSummaryRepository extends MongoRepository<TrainerSummaryEntity, Long> {
    List<TrainerSummaryEntity> findAllByUsername(String username);

    TrainerSummaryEntity findByUsernameAndYearAndMonth(String username, String year, String month);

}
