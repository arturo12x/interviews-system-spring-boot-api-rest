package com.interviews.system.service;

import com.interviews.system.dto.OpenPositionDTO;
import com.interviews.system.dto.OpenPositionResponse;
import com.interviews.system.dto.UserDTO;
import com.interviews.system.dto.UserResponse;

public interface OpenPositionService {

    public OpenPositionDTO createOpenPosition(OpenPositionDTO openPositionDTO);

    public OpenPositionResponse getAllOpenPositions(int pageNo, int pageSize, String sortBy, String sortOrder);

    public OpenPositionDTO getOpenPositionById(long id);

    public OpenPositionDTO updateOpenPosition(OpenPositionDTO openPositionDTO, long id);

    public void deleteOpenPosition(long id);
}
