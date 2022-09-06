package com.interviews.system.repository;

import com.interviews.system.entity.OpenPosition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OpenPositionRepository extends JpaRepository<OpenPosition, Long> {
}
