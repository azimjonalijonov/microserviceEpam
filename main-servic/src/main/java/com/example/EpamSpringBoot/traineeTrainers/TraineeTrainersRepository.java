package com.example.EpamSpringBoot.traineeTrainers;

import com.example.EpamSpringBoot.trainer.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TraineeTrainersRepository extends JpaRepository<TraineeTrainer, Long> {

	List<TraineeTrainer> findAllByTrainer(Trainer trainer);

}
