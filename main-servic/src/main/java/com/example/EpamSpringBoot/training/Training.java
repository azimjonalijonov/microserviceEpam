package com.example.EpamSpringBoot.training;

import com.example.EpamSpringBoot.trainee.Trainee;
import com.example.EpamSpringBoot.trainer.Trainer;
import com.example.EpamSpringBoot.trainingType.TrainingType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalDateTime;

@Entity
public class Training {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	private Long id;

	@ManyToOne
	@JoinColumn(name = "trainee_id")

	private Trainee trainee;

	@ManyToOne
	@JoinColumn(name = "trainer_id")
	private Trainer trainer;

	@Column(nullable = false)
	private String trainingName;

	@ManyToOne
	private TrainingType trainingType;

	private LocalDateTime trainingDate;

	private Number duration;

	@Override
	public String toString() {
		return "Training{" + "id=" + id + ", traineeId=" + trainee.getId() + ", trainerId=" + trainer.getId()
				+ ", trainingName='" + trainingName + '\'' + ", trainingType=" + trainingType + ", trainingDate="
				+ trainingDate + ", duration=" + duration + '}';
	}

	public Long getId() {
		return id;
	}

	public Training(Long id, Trainee trainee, Trainer trainer, String trainingName, TrainingType trainingType,
			LocalDateTime trainingDate, Number duration) {
		this.id = id;
		this.trainee = trainee;
		this.trainer = trainer;
		this.trainingName = trainingName;
		this.trainingType = trainingType;
		this.trainingDate = trainingDate;
		this.duration = duration;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Trainee getTrainee() {
		return trainee;
	}

	public void setTrainee(Trainee trainee) {
		this.trainee = trainee;
	}

	public Trainer getTrainer() {
		return trainer;
	}

	public void setTrainer(Trainer trainer) {
		this.trainer = trainer;
	}

	public String getTrainingName() {
		return trainingName;
	}

	public void setTrainingName(String trainingName) {
		this.trainingName = trainingName;
	}

	public TrainingType getTrainingType() {
		return trainingType;
	}

	public void setTrainingType(TrainingType trainingType) {
		this.trainingType = trainingType;
	}

	public LocalDateTime getTrainingDate() {
		return trainingDate;
	}

	public void setTrainingDate(LocalDateTime trainingDate) {
		this.trainingDate = trainingDate;
	}

	public Number getDuration() {
		return duration;
	}

	public void setDuration(Number duration) {
		this.duration = duration;
	}

	public Training() {
	}

}
