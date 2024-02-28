package com.example.EpamSpringBoot.trainer;

import com.example.EpamSpringBoot.traineeTrainers.TraineeTrainer;
import com.example.EpamSpringBoot.trainingType.TrainingType;
import com.example.EpamSpringBoot.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

import java.util.List;

@Entity
public class Trainer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToMany(mappedBy = "trainer", fetch = FetchType.EAGER)
	private List<TraineeTrainer> trainingSessions;

	@ManyToOne
	private TrainingType specialization;

	@OneToOne
	private User user;

	@Override
	public String toString() {
		return "Trainer{" + "id=" + id + ", trainingSessionsSize=" + trainingSessions + ", specializationName="
				+ specialization + ", user=" + user + '}';
	}

	public List<TraineeTrainer> getTrainingSessions() {
		return trainingSessions;
	}

	public void setTrainingSessions(List<TraineeTrainer> trainingSessions) {
		this.trainingSessions = trainingSessions;
	}

	public Long getId() {
		return id;
	}

	public Trainer() {
	}

	public Trainer(Long id, TrainingType specialization, User user) {
		this.id = id;
		this.specialization = specialization;
		this.user = user;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TrainingType getSpecialization() {
		return specialization;
	}

	public void setSpecialization(TrainingType specialization) {
		this.specialization = specialization;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
