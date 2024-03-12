package org.example.service;

import org.example.config.jwt.JwtService;
import org.example.entity.YearlySummary;
import org.example.repository.TrainerSummaryRepository;
import org.example.dto.ResponseDto;
import org.example.entity.TrainerSummaryEntity;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Service
public class TrainerSummaryService {
    private final JwtService jwtService;
    private final TrainerSummaryRepository trainerSummaryRepository;

    public TrainerSummaryService(JwtService jwtService, TrainerSummaryRepository trainerSummaryRepository) {
        this.jwtService = jwtService;
        this.trainerSummaryRepository = trainerSummaryRepository;
    }

    @JmsListener(destination = "dest")
    public void listener(Map<String, String> payloadmap) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
        LocalDateTime dateTime = LocalDateTime.parse(payloadmap.get("date"), formatter);
        Month month = dateTime.getMonth();
        Year year = Year.of(dateTime.getYear());
        TrainerSummaryEntity trainerSummaryEntityExisting = trainerSummaryRepository.findByUsername(payloadmap.get("username"));
        if (trainerSummaryEntityExisting == null) {
            TrainerSummaryEntity trainerSummaryEntity = new TrainerSummaryEntity();
            trainerSummaryEntity.setUsername(payloadmap.get("username"));
            trainerSummaryEntity.setFirstname(payloadmap.get("firstname"));
            trainerSummaryEntity.setLastname( payloadmap.get("lastname"));
            Map<String, YearlySummary> allSummary = new HashMap<>();
            Map<String, Number> monthlySummaryMap = new HashMap<>();
             monthlySummaryMap.put(month.toString(), (Integer.valueOf(payloadmap.get("duration"))));
            YearlySummary yearlySummary =new YearlySummary();
            yearlySummary.setMonthlySummary(monthlySummaryMap);
            allSummary.put(year.toString(), yearlySummary);
            trainerSummaryEntity.setSummaryList(allSummary);

            trainerSummaryEntity.setActive(Boolean.valueOf(payloadmap.get("active")));
            trainerSummaryRepository.save(trainerSummaryEntity);
        } else {
            Map<String,YearlySummary> allSummary = trainerSummaryEntityExisting.getSummaryList();
            if (allSummary.containsKey(year.toString())) {
                Map<String, Number> monthlySummary = allSummary.get(year.toString()).getMonthlySummary();
                if (monthlySummary.containsKey(month.toString())) {
                    monthlySummary.put(month.toString(), ((Integer.valueOf(payloadmap.get("duration"))) + (Integer) monthlySummary.get(month.toString())));
                } else {
                    monthlySummary.put(month.toString(), (Integer.valueOf(payloadmap.get("duration"))));

                }
            } else {
                Map<String, Number> monthlySummary = new HashMap<>();
                monthlySummary.put(month.toString(), (Integer.valueOf(payloadmap.get("duration"))));
                YearlySummary yearlySummary =new YearlySummary();
                yearlySummary.setMonthlySummary(monthlySummary);
                allSummary.put(year.toString(), yearlySummary);
                trainerSummaryEntityExisting.setSummaryList(allSummary);
            }
            trainerSummaryRepository.save(trainerSummaryEntityExisting);
        }
    }


    public ResponseDto get(String username) throws Exception {

        TrainerSummaryEntity trainerSummaryEntity = trainerSummaryRepository.findByUsername(username);
        if (trainerSummaryEntity == null) {
            throw new Exception("no  trainerSummary found for this username");
        }
        ResponseDto responseDto = new ResponseDto();
        responseDto.setUsername(trainerSummaryEntity.getUsername());
        responseDto.setFirstname(trainerSummaryEntity.getFirstname());
        responseDto.setLastname(trainerSummaryEntity.getLastname());
        responseDto.setTrainerStatus(trainerSummaryEntity.getActive());
        Map<String, YearlySummary> allSummary = trainerSummaryEntity.getSummaryList();
        responseDto.setMap(allSummary);
        return responseDto;
    }

}
