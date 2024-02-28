package org.example;

import org.springframework.stereotype.Service;

import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TrainerSummaryService {
    private final TrainerSummaryRepository trainerSummaryRepository;

    public TrainerSummaryService(TrainerSummaryRepository trainerSummaryRepository) {
        this.trainerSummaryRepository = trainerSummaryRepository;
    }

    public void save(RequestDTO requestDTO) {
        TrainerSummaryEntity trainerSummaryEntity = new TrainerSummaryEntity();
        trainerSummaryEntity.setUsername(requestDTO.getUsername());
        trainerSummaryEntity.setFirstname(requestDTO.getFirstname());
        trainerSummaryEntity.setLastname(requestDTO.getLastname());
        trainerSummaryEntity.setDATE(requestDTO.getDATE());
        trainerSummaryEntity.setActive(requestDTO.getActive());
        trainerSummaryEntity.setDuration(requestDTO.getDuration());
        trainerSummaryRepository.save(trainerSummaryEntity);
    }

    public ResponseDto get(String username) {
        List<TrainerSummaryEntity> list = trainerSummaryRepository.findAllByUsername(username);
        ResponseDto responseDto = new ResponseDto();
        responseDto.setUsername(list.get(0).getUsername());
        responseDto.setFirstname(list.get(0).getFirstname());
        responseDto.setLastname(list.get(0).getLastname());
        responseDto.setTrainerStatus(list.get(0).getActive());
        Map<Long, List<PartialDTO>> listMap = new HashMap<>();
        responseDto.setMap(listMap);

        for (TrainerSummaryEntity trainerSummaryEntity : list
        ) {
            Long year = (long) trainerSummaryEntity.getDATE().getYear();
            if (responseDto.getMap().containsKey(year)) {
                Month month = trainerSummaryEntity.getDATE().getMonth();
                System.out.println(month + "_______________________");
                System.out.println(Month.MAY);
                for (PartialDTO partialDTO : responseDto.getMap().get(year)) {
                    if (partialDTO.getMonth().equals(month)) {
                        partialDTO.setSummaryDuration(trainerSummaryEntity.getDuration().doubleValue() + partialDTO.summaryDuration.doubleValue());
                    }
                }
            } else {
                List<PartialDTO> partialDTOS = new ArrayList<>();
                PartialDTO partialDTO1 = new PartialDTO();

                partialDTO1.setMonth(Month.JANUARY);
                if (trainerSummaryEntity.getDATE().getMonth().equals(Month.JANUARY)) {
                    partialDTO1.setSummaryDuration(trainerSummaryEntity.getDuration());
                }
                PartialDTO partialDTO2 = new PartialDTO();
                partialDTO2.setMonth(Month.FEBRUARY);
                if (trainerSummaryEntity.getDATE().getMonth().equals(Month.FEBRUARY)) {
                    partialDTO2.setSummaryDuration(trainerSummaryEntity.getDuration());
                }
                PartialDTO partialDTO3 = new PartialDTO();
                partialDTO3.setMonth(Month.MARCH);
                if (trainerSummaryEntity.getDATE().getMonth().equals(Month.MARCH)) {
                    partialDTO3.setSummaryDuration(trainerSummaryEntity.getDuration());
                }
                PartialDTO partialDTO4 = new PartialDTO();
                partialDTO4.setMonth(Month.APRIL);
                if (trainerSummaryEntity.getDATE().getMonth().equals(Month.APRIL)) {
                    partialDTO4.setSummaryDuration(trainerSummaryEntity.getDuration());
                }
                PartialDTO partialDTO5 = new PartialDTO();
                partialDTO5.setMonth(Month.MAY);
                if (trainerSummaryEntity.getDATE().getMonth().equals(Month.MAY)) {
                    partialDTO5.setSummaryDuration(trainerSummaryEntity.getDuration());
                }
                PartialDTO partialDTO6 = new PartialDTO();
                partialDTO6.setMonth(Month.JUNE);
                if (trainerSummaryEntity.getDATE().getMonth().equals(Month.JUNE)) {
                    partialDTO6.setSummaryDuration(trainerSummaryEntity.getDuration());
                }
                PartialDTO partialDTO7 = new PartialDTO();
                partialDTO7.setMonth(Month.JULY);
                if (trainerSummaryEntity.getDATE().getMonth().equals(Month.JULY)) {
                    partialDTO7.setSummaryDuration(trainerSummaryEntity.getDuration());
                }
                PartialDTO partialDTO8 = new PartialDTO();
                partialDTO8.setMonth(Month.AUGUST);
                if (trainerSummaryEntity.getDATE().getMonth().equals(Month.AUGUST)) {
                    partialDTO8.setSummaryDuration(trainerSummaryEntity.getDuration());
                }
                PartialDTO partialDTO9 = new PartialDTO();
                partialDTO9.setMonth(Month.SEPTEMBER);
                if (trainerSummaryEntity.getDATE().getMonth().equals(Month.SEPTEMBER)) {
                    partialDTO9.setSummaryDuration(trainerSummaryEntity.getDuration());
                }
                PartialDTO partialDTO10 = new PartialDTO();
                partialDTO10.setMonth(Month.OCTOBER);
                if (trainerSummaryEntity.getDATE().getMonth().equals(Month.OCTOBER)) {
                    partialDTO10.setSummaryDuration(trainerSummaryEntity.getDuration());
                }
                PartialDTO partialDTO11 = new PartialDTO();
                partialDTO11.setMonth(Month.NOVEMBER);
                if (trainerSummaryEntity.getDATE().getMonth().equals(Month.NOVEMBER)) {
                    partialDTO11.setSummaryDuration(trainerSummaryEntity.getDuration());
                }
                PartialDTO partialDTO12 = new PartialDTO();
                partialDTO12.setMonth(Month.DECEMBER);
                if (trainerSummaryEntity.getDATE().getMonth().equals(Month.DECEMBER)) {
                    partialDTO12.setSummaryDuration(trainerSummaryEntity.getDuration());
                }
                partialDTOS.add(partialDTO1);
                partialDTOS.add(partialDTO2);
                partialDTOS.add(partialDTO3);
                partialDTOS.add(partialDTO4);
                partialDTOS.add(partialDTO5);
                partialDTOS.add(partialDTO6);
                partialDTOS.add(partialDTO7);
                partialDTOS.add(partialDTO8);
                partialDTOS.add(partialDTO9);
                partialDTOS.add(partialDTO10);
                partialDTOS.add(partialDTO11);
                partialDTOS.add(partialDTO12);


                responseDto.getMap().put(year, partialDTOS);
            }
        }
        return responseDto;
    }

}
