package intro_softw_engi_groupb.wheel_of_fortune_web.data;


import java.util.Calendar;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Purchase {

    @Id
    private Long id;
    @NotBlank
    private Long customerId;
    @NotBlank
    private Long productId;
    @NotBlank
    private Date purchaseDate;
    @NotBlank
    private int quantity;
    @NotBlank
    private int totalPoints;

    public Purchase(Date date, Customer customer, Product product, int quantity) {
        this.customerId = customer.getId();
        this.productId = product.getId();
        this.purchaseDate = date;

        this.quantity = quantity;
        int points = quantity * product.getPointsPerPurchase();
        this.totalPoints = points;
        customer.adjustPoints(points);
    }

    public Purchase(Customer customer, Product product, int quantity) {
        this(Calendar.getInstance().getTime(), customer, product, quantity);
    }
}
