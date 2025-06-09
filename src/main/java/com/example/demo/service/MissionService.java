package com.example.demo.service;

import com.example.demo.model.*;
import com.example.demo.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MissionService {
    private final UserDailyMissionRepository userDailyMissionRepository;
    private final UserOnetimeMissionRepository userOnetimeMissionRepository;
    private final MissionRepository missionRepository;
    private final UserRepository userRepository;

    public int claimMission(Integer userId, Integer missionId, String missionType) {
        if ("DAILY".equals(missionType)) {
            return claimDailyMission(userId, missionId);
        } else if ("ONE_TIME".equals(missionType)) {
            return claimOnetimeMission(userId, missionId);
        }
        throw new IllegalArgumentException("Invalid mission type: " + missionType);
    }

    public void updateDailyMission(Integer userId, Integer missionId) {
        LocalDate today = LocalDate.now();

        List<UserDailyMission> existingMissions = userDailyMissionRepository
                .findByUserIdAndMissionDate(userId, today);

        UserDailyMission userMission = existingMissions.stream()
                .filter(m -> m.getMissionId().equals(missionId))
                .findFirst()
                .orElse(null);

        if (userMission == null) {
            userMission = new UserDailyMission();
            userMission.setUserId(userId);
            userMission.setMissionId(missionId);
            userMission.setMissionDate(today);
            userMission.setCompleted(true);
            userMission.setClaimed(false);
            userDailyMissionRepository.save(userMission);
        } else if (!userMission.isCompleted()) {
            userMission.setCompleted(true);
            userDailyMissionRepository.save(userMission);
        }
    }

    public void updateOnetimeMission(Integer userId, Integer missionId) {
        UserOnetimeMission userMission = userOnetimeMissionRepository
                .findByUserIdAndMissionId(userId, missionId)
                .orElse(null);

        if (userMission == null) {
            userMission = new UserOnetimeMission();
            userMission.setCurrentProgress(1);
            userMission.setUserId(userId);
            userMission.setMissionId(missionId);
            userMission.setCompleted(false);
            userMission.setClaimed(false);
            userOnetimeMissionRepository.save(userMission);
        } else if (!userMission.isCompleted()) {
            // Update existing mission
            userMission.setCurrentProgress(userMission.getCurrentProgress() + 1);

            Mission mission = missionRepository.findById(missionId).orElse(null);
            if (mission != null && userMission.getCurrentProgress() >= mission.getTargetValue()) {
                userMission.setCompleted(true);
            }

            userOnetimeMissionRepository.save(userMission);

        }
    }

    public void addMissionsToModel(Model model, Integer userId) {
        List<Mission> dailyMissions = missionRepository.findByMissionType(MissionType.DAILY);
        List<Mission> oneTimeMissions = missionRepository.findByMissionType(MissionType.ONE_TIME);

        List<UserDailyMission> userDailyMissions = userDailyMissionRepository
                .findByUserIdAndMissionDate(userId, LocalDate.now());
        List<UserOnetimeMission> userOnetimeMissions = userOnetimeMissionRepository
                .findByUserId(userId);

        model.addAttribute("dailyMissions", dailyMissions);
        model.addAttribute("oneTimeMissions", oneTimeMissions);
        model.addAttribute("userDailyMissions", userDailyMissions);
        model.addAttribute("userOnetimeMissions", userOnetimeMissions);
    }

    private int claimDailyMission(Integer userId, Integer missionId) {
        LocalDate today = LocalDate.now();
        List<UserDailyMission> missions = userDailyMissionRepository
                .findByUserIdAndMissionDate(userId, today);

        UserDailyMission userMission = missions.stream()
                .filter(m -> m.getMissionId().equals(missionId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Mission not found"));

        if (!userMission.isCompleted()) {
            throw new RuntimeException("Mission not completed yet");
        }
        if (userMission.isClaimed()) {
            throw new RuntimeException("Mission already claimed");
        }

        Mission mission = missionRepository.findById(missionId)
                .orElseThrow(() -> new RuntimeException("Mission details not found"));

        userMission.setClaimed(true);
        userDailyMissionRepository.save(userMission);

        // Update user's spin count
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setSpin(user.getSpin() + mission.getSpinReward());
        userRepository.save(user);

        return mission.getSpinReward();
    }

    private int claimOnetimeMission(Integer userId, Integer missionId) {
        UserOnetimeMission userMission = userOnetimeMissionRepository
                .findByUserIdAndMissionId(userId, missionId)
                .orElseThrow(() -> new RuntimeException("Mission not found"));

        if (!userMission.isCompleted()) {
            throw new RuntimeException("Mission not completed yet");
        }
        if (userMission.isClaimed()) {
            throw new RuntimeException("Mission already claimed");
        }

        Mission mission = missionRepository.findById(missionId)
                .orElseThrow(() -> new RuntimeException("Mission details not found"));

        userMission.setClaimed(true);
        userOnetimeMissionRepository.save(userMission);

        // Update user's spin count
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setSpin(user.getSpin() + mission.getSpinReward());
        userRepository.save(user);

        return mission.getSpinReward();
    }

    public int claimAllCompletedMissions(Integer userId) {
        int totalSpins = 0;
        LocalDate today = LocalDate.now();

        // Claim all completed daily missions
        List<UserDailyMission> dailyMissions = userDailyMissionRepository
                .findByUserIdAndMissionDate(userId, today);

        for (UserDailyMission mission : dailyMissions) {
            if (mission.isCompleted() && !mission.isClaimed()) {
                try {
                    totalSpins += claimDailyMission(userId, mission.getMissionId());
                } catch (Exception e) {
                }
            }
        }
        
        List<UserOnetimeMission> onetimeMissions = userOnetimeMissionRepository
                .findByUserId(userId);

        for (UserOnetimeMission mission : onetimeMissions) {
            if (mission.isCompleted() && !mission.isClaimed()) {
                try {
                    totalSpins += claimOnetimeMission(userId, mission.getMissionId());
                } catch (Exception e) {
                    // Continue with other missions if one fails
                }
            }
        }

        return totalSpins;
    }

    public void handleLoginMissions(Integer userId) {
        LocalDate today = LocalDate.now();

        List<UserDailyMission> existingMissions = userDailyMissionRepository
                .findByUserIdAndMissionDate(userId, today);

        boolean alreadyLoggedInToday = existingMissions.stream()
                .anyMatch(m -> m.getMissionId().equals(1) && m.isCompleted());
    
        updateDailyMission(userId, 1);
        if (!alreadyLoggedInToday) {
            updateOnetimeMission(userId, 7);
        }
    }
}