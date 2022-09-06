package com.interviews.system.controller;

import com.interviews.system.dto.OpenPositionDTO;
import com.interviews.system.dto.OpenPositionResponse;
import com.interviews.system.dto.UserResponse;
import com.interviews.system.service.OpenPositionService;
import com.interviews.system.utils.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/openPositions")
public class OpenPositionController {

    @Autowired
    private OpenPositionService openPositionService;

    @GetMapping
    public OpenPositionResponse getAllOpenPositions(@RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
                                                    @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
                                                    @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
                                                    @RequestParam(value = "sortOrder", defaultValue = AppConstants.DEFAULT_SORT_ORDER, required = false) String sortOrder) {
        return openPositionService.getAllOpenPositions(pageNo, pageSize, sortBy, sortOrder);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OpenPositionDTO> getOpenPositionById(@PathVariable(name = "id") long id) {
        return ResponseEntity.ok(openPositionService.getOpenPositionById(id));
    }

    @PostMapping
    public ResponseEntity<OpenPositionDTO> createOpenPosition(@RequestBody OpenPositionDTO openPositionDTO) {
        return new ResponseEntity<>(openPositionService.createOpenPosition(openPositionDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OpenPositionDTO> updateOpenPosition(@RequestBody OpenPositionDTO openPositionDTO, @PathVariable(name = "id") long id) {
        return ResponseEntity.ok(openPositionService.updateOpenPosition(openPositionDTO, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePosition(@PathVariable(name = "id") long id) {
        openPositionService.deleteOpenPosition(id);
        return new ResponseEntity<>("Position deleted successfully", HttpStatus.OK);
    }
}
