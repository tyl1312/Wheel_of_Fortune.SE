package intro_softw_engi_groupb.wheel_of_fortune_web.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RewardRepository extends JpaRepository<Reward, Long> {

    @Query(value = "SELECT * FROM rewards WHERE LOWER(reward_name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) ",
            nativeQuery = true)
    List<Reward> search(@Param("searchTerm") String searchTerm);
}