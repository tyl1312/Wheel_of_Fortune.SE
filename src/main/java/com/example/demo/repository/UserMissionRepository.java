package com.example.demo.repository;

import com.example.demo.model.UserMission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface UserMissionRepository extends JpaRepository<UserMission, Integer> {
    List<UserMission> findByUserIdAndMissionDate(Integer userId, LocalDate missionDate);
}