package com.interviews.system.service;

import com.interviews.system.dto.OpenPositionDTO;
import com.interviews.system.dto.OpenPositionResponse;
import com.interviews.system.entity.OpenPosition;
import com.interviews.system.exceptions.ResourceNotFoundException;
import com.interviews.system.repository.OpenPositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OpenPositionImpl implements OpenPositionService {

    @Autowired
    private OpenPositionRepository openPositionRepository;


    @Override
    public OpenPositionDTO createOpenPosition(OpenPositionDTO openPositionDTO) {
        OpenPosition openPosition = mapEntity(openPositionDTO);

        OpenPosition newOpenPosition = newOpenPosition = openPositionRepository.save(openPosition);

        OpenPositionDTO openPositionReponse = mapDTO(newOpenPosition);

        return openPositionReponse;
    }

    @Override
    public OpenPositionResponse getAllOpenPositions(int pageNo, int pageSize, String sortBy,String sortOrder) {

        Sort sort = sortOrder.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize,sort);

        Page<OpenPosition> openPositions = openPositionRepository.findAll(pageable);

        List<OpenPosition> listOfOpenPositions = openPositions.getContent();
        List<OpenPositionDTO> content = listOfOpenPositions.stream().map(openPosition -> mapDTO(openPosition)).collect(Collectors.toList());

        OpenPositionResponse openPositionResponse = new OpenPositionResponse();
        openPositionResponse.setContent(content);
        openPositionResponse.setPageNo(openPositions.getNumber());
        openPositionResponse.setPageSize(openPositions.getSize());
        openPositionResponse.setTotalElements(openPositions.getTotalElements());
        openPositionResponse.setTotalPages(openPositions.getTotalPages());
        openPositionResponse.setIs_last(openPositions.isLast());

        return openPositionResponse;
    }

    @Override
    public OpenPositionDTO getOpenPositionById(long id) {
        OpenPosition openPosition = openPositionRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("OpenPosition", "id", id));

        return mapDTO(openPosition);
    }

    @Override
    public OpenPositionDTO updateOpenPosition(OpenPositionDTO openPositionDTO, long id) {
        OpenPosition openPosition = openPositionRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("OpenPosition", "id", id));

        openPosition.setName(openPositionDTO.getName());


        OpenPosition updatedOpenPosition = openPositionRepository.save(openPosition);

        return mapDTO(openPosition);
    }

    @Override
    public void deleteOpenPosition(long id) {
        OpenPosition openPosition = openPositionRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("OpenPosition", "id", id));

        openPositionRepository.delete(openPosition);
    }

    //Convertir entidad a DTO
    private OpenPositionDTO mapDTO(OpenPosition openPosition) {
        OpenPositionDTO openPositionDTO = new OpenPositionDTO();

        openPositionDTO.setId(openPosition.getId());
        openPositionDTO.setName(openPosition.getName());

        return openPositionDTO;
    }

    //Convertir DTO a entidad
    private OpenPosition mapEntity(OpenPositionDTO openPositionDTO) {
        OpenPosition openPosition = new OpenPosition();

        openPosition.setId(openPositionDTO.getId());
        openPosition.setName(openPositionDTO.getName());

        return openPosition;
    }
}
