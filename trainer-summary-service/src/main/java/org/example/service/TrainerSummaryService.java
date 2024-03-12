package org.example.service;

import org.example.config.jwt.JwtService;
import org.example.repository.TrainerSummaryRepository;
import org.example.dto.PartialDTO;
import org.example.dto.RequestDTO;
import org.example.dto.ResponseDto;
import org.example.entity.TrainerSummaryEntity;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        TrainerSummaryEntity trainerSummaryEntity1 = trainerSummaryRepository.findByUsernameAndYearAndMonth(map.get("username"), year.toString(), month.toString());
        if (trainerSummaryEntity1 == null) {


            TrainerSummaryEntity trainerSummaryEntity = new TrainerSummaryEntity();
            trainerSummaryEntity.setUsername((String) map.get("username"));
            trainerSummaryEntity.setFirstname((String) map.get("firstname"));
            trainerSummaryEntity.setLastname((String) map.get("lastname"));

            trainerSummaryEntity.setYear(year.toString());
            trainerSummaryEntity.setMonth(month.toString());

            trainerSummaryEntity.setActive(Boolean.valueOf(map.get("active")));
            trainerSummaryEntity.setDuration((Integer.valueOf(map.get("duration"))));

            trainerSummaryRepository.save(trainerSummaryEntity);
        } else {
            trainerSummaryEntity1.setDuration((Integer.valueOf(map.get("duration"))) + (Integer) trainerSummaryEntity1.getDuration());
            trainerSummaryRepository.save(trainerSummaryEntity1);
        }
    }


    public ResponseDto get(String username) throws Exception {

        List<TrainerSummaryEntity> list = trainerSummaryRepository.findAllByUsername(username);
        if (list.size() == 0) {
            throw new Exception("no  trainerSummary found for this username");
        }
        ResponseDto responseDto = new ResponseDto();
        responseDto.setUsername(list.get(0).getUsername());
        responseDto.setFirstname(list.get(0).getFirstname());
        responseDto.setLastname(list.get(0).getLastname());
        responseDto.setTrainerStatus(list.get(0).getActive());
        List<PartialDTO> list1 = new ArrayList<>();
        for (TrainerSummaryEntity trainerSummaryEntity : list
        ) {
            PartialDTO partialDTO = new PartialDTO();
            partialDTO.setYear(trainerSummaryEntity.getYear());
            partialDTO.setMonth(trainerSummaryEntity.getMonth());
            partialDTO.setSummaryDuration(trainerSummaryEntity.getDuration());
            list1.add(partialDTO);
        }
        responseDto.setPartialDTOS(list1);
        return responseDto;
    }

}
