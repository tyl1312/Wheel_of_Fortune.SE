package com.example.demo.repository;

import com.example.demo.model.Mission;
import com.example.demo.model.MissionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MissionRepository extends JpaRepository<Mission, Integer> {
    List<Mission> findByMissionType(MissionType missionType);
}