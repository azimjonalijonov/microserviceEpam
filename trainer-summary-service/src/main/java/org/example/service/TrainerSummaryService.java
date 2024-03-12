package org.example.service;

import org.example.config.jwt.JwtService;
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

    public void listener(Map<String, String> map) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
        LocalDateTime dateTime = LocalDateTime.parse(map.get("date"), formatter);
        Month month = dateTime.getMonth();
        Year year = Year.of(dateTime.getYear());
        TrainerSummaryEntity trainerSummaryEntity1 = trainerSummaryRepository.findByUsername(map.get("username"));
        if (trainerSummaryEntity1 == null) {
            TrainerSummaryEntity trainerSummaryEntity = new TrainerSummaryEntity();
            trainerSummaryEntity.setUsername(map.get("username"));
            trainerSummaryEntity.setFirstname((String) map.get("firstname"));
            trainerSummaryEntity.setLastname((String) map.get("lastname"));
            Map<String, Map<String, Number>> map1 = new HashMap<>();
            Map<String, Number> map2 = new HashMap<>();
            map2.put(month.toString(), (Integer.valueOf(map.get("duration"))));
            map1.put(year.toString(), map2);
            trainerSummaryEntity.setSummaryList(map1);

            trainerSummaryEntity.setActive(Boolean.valueOf(map.get("active")));
            trainerSummaryRepository.save(trainerSummaryEntity);
        } else {
            Map<String, Map<String, Number>> map1 = trainerSummaryEntity1.getSummaryList();
            if (map1.containsKey(year.toString())) {
                Map<String, Number> map2 = map1.get(year.toString());
                if (map2.containsKey(month.toString())) {
                    map2.put(month.toString(), ((Integer.valueOf(map.get("duration"))) + (Integer) map2.get(month.toString())));
                } else {
                    map2.put(month.toString(), (Integer.valueOf(map.get("duration"))));

                }
            } else {
                Map<String, Number> map2 = new HashMap<>();
                map2.put(month.toString(), (Integer.valueOf(map.get("duration"))));
                map1.put(year.toString(), map2);
                trainerSummaryEntity1.setSummaryList(map1);
            }
            trainerSummaryRepository.save(trainerSummaryEntity1);
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
        Map<String, Map<String, Number>> map = trainerSummaryEntity.getSummaryList();
        responseDto.setMap(map);
        return responseDto;
    }

}
