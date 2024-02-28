package org.example;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainerSummaryRepository extends JpaRepository<TrainerSummaryEntity, Long> {
    List<TrainerSummaryEntity> findAllByUsername(String username);
}
