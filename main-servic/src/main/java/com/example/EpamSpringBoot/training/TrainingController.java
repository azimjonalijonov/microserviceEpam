package com.example.EpamSpringBoot.training;

import com.example.EpamSpringBoot.trainee.Trainee;
import com.example.EpamSpringBoot.trainee.TraineeService;
import com.example.EpamSpringBoot.traineeTrainers.TraineeTrainerService;
import com.example.EpamSpringBoot.trainer.Trainer;
import com.example.EpamSpringBoot.trainer.TrainerService;
import com.example.EpamSpringBoot.training.dto.PostTrainingDTO;
import com.example.EpamSpringBoot.user.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/training")
public class TrainingController {

    final UserService userService;

    final TraineeService traineeService;

    final TrainingService trainingService;

    final TrainerService trainerService;

    final TraineeTrainerService trainerTraineeService;

    public TrainingController(UserService userService, TraineeService traineeService, TrainingService trainingService,
                              TrainerService trainerService, TraineeTrainerService trainerTraineeService) {
        this.userService = userService;
        this.traineeService = traineeService;
        this.trainingService = trainingService;
        this.trainerService = trainerService;
        this.trainerTraineeService = trainerTraineeService;
    }

    //
    @DeleteMapping("/delete")
    public void delete(@RequestBody Long id) {
        Training training = trainingService.readById(id);
        trainingService.createTrainerSummary(training, false, "delete");

        trainingService.deleteById(id);
    }

    @PostMapping("/post")
    public ResponseEntity post(@RequestBody PostTrainingDTO postTraining) {

        if (postTraining.getTrainerUsername() == null || postTraining.getTraineeUsername() == null
                || postTraining.getDuration() == null || postTraining.getDate() == null
                || postTraining.getName() == null) {
            throw new RuntimeException("all fields must be filled");
        }
        Trainee trainee = traineeService.readByUsername(postTraining.getTraineeUsername());
        Trainer trainer = trainerService.readByUsername(postTraining.getTrainerUsername());
        System.out.println(trainer.toString());
        Training training = new Training();
        training.setTrainer(trainer);
        training.setTrainingName(postTraining.getName());
        training.setTrainee(trainee);
        training.setDuration(postTraining.getDuration());
        training.setTrainingDate(postTraining.getDate());
        Training training1 = trainingService.create(training);
        ResponseEntity message = trainingService.createTrainerSummary(training1, true, "post");
        return ResponseEntity.ok(message);

    }

}
