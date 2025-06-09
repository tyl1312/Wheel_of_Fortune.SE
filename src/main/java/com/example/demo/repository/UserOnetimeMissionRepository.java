package com.example.demo.repository;

import com.example.demo.model.UserOnetimeMission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserOnetimeMissionRepository extends JpaRepository<UserOnetimeMission, Integer> {
    Optional<UserOnetimeMission> findByUserIdAndMissionId(Integer userId, Integer missionId);
    
    List<UserOnetimeMission> findByUserId(Integer userId);
    
    List<UserOnetimeMission> findByUserIdAndIsCompletedAndIsClaimed(
        Integer userId, boolean isCompleted, boolean isClaimed);
}