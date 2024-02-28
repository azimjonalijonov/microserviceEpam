package com.example.EpamSpringBoot.training;

import com.example.EpamSpringBoot.trainee.Trainee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainingRepository extends JpaRepository<Training, Long> {

	List<Training> findAllByTrainee(Trainee trainee);

}
