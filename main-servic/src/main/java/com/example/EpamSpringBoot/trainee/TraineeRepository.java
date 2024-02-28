package com.example.EpamSpringBoot.trainee;

import com.example.EpamSpringBoot.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TraineeRepository extends JpaRepository<Trainee, Long> {

	Trainee findTraineeByUser(User user);

	@Modifying
	@Query("DELETE FROM Trainee t WHERE t.user.id = :userId")
	void deleteByUserId(@Param("userId") Long userId);

	void deleteByUser(User user);

}
