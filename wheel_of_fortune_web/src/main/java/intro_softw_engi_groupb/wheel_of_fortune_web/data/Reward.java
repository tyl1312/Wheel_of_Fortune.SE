package intro_softw_engi_groupb.wheel_of_fortune_web.data;

import jakarta.validation.constraints.Negative;
import jakarta.validation.constraints.NotEmpty;

public class Reward extends Product {
    @Negative
    @NotEmpty
    private int pointsPerPurchase;
}
