package com.example.demo.repository;
import java.util.List;
import com.example.demo.model.PrizeHistory;
import com.example.demo.model.PrizeHistoryKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PrizeHistoryRepository extends JpaRepository<PrizeHistory, PrizeHistoryKey> {
    @Query("SELECT p FROM PrizeHistory p WHERE p.user.user_id = :userId")
    List<PrizeHistory> findByUserIdCustom(@Param("userId") Integer userId);

}
