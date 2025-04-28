package intro_softw_engi_groupb.wheel_of_fortune_web.data;

import com.vaadin.flow.component.template.Id;

import jakarta.validation.constraints.NotEmpty;

public class Product {
    @Id
    protected Long id;
    @NotEmpty
    protected String name;
    @NotEmpty
    protected int pointsPerPurchase;

    protected Long getId() {
        return id;
    }
    protected String getName() {
        return name;
    }
    protected int getPointsPerPurchase() {
        return pointsPerPurchase;
    }
}
