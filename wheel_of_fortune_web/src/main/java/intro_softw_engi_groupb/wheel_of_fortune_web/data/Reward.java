package intro_softw_engi_groupb.wheel_of_fortune_web.data;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Negative;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name = "rewards")
public class Reward {
    @Id
    private Long rewardId;
    @NotEmpty
    private String rewardName;
    @Negative
    @NotEmpty
    private int redeemPoints;

    public Long getRewardId() {
        return rewardId;
    }
    public String getRewardName() {
        return rewardName;
    }
    public int getRedeemPoints() {
        return redeemPoints;
    }    
}
