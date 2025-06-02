package com.example.demo.repository;

import com.example.demo.model.UserDailyMission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserDailyMissionRepository extends JpaRepository<UserDailyMission, Integer> {
    Optional<UserDailyMission> findByUserIdAndMissionIdAndMissionDate(int userId, int missionId, LocalDate missionDate);
    
    List<UserDailyMission> findByUserIdAndIsCompletedAndIsClaimedAndMissionDate(
        int userId, boolean isCompleted, boolean isClaimed, LocalDate missionDate);
        
    List<UserDailyMission> findByUserIdAndMissionDate(int userId, LocalDate missionDate);
}