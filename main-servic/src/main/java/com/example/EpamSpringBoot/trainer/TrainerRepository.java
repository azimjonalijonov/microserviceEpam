package com.example.EpamSpringBoot.trainer;

import com.example.EpamSpringBoot.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainerRepository extends JpaRepository<Trainer, Long> {

	Trainer findTrainerByUser(User user);

	@Query(value = "SELECT DISTINCT t.* " + "FROM Trainer t " + "JOIN User u ON t.user_id = u.id "
			+ "LEFT JOIN TraineeTrainer tt ON t.id = tt.trainer_id " + "WHERE u.is_active = true "
			+ "AND tt.trainer_id IS NULL", nativeQuery = true)
	List<Trainer> findActiveTrainersWithoutTrainees();

}
