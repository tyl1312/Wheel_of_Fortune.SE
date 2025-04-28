package intro_softw_engi_groupb.wheel_of_fortune_web.data;

import com.vaadin.flow.component.template.Id;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

@Entity
public class Customer {

    //infomation for customer

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "id_generator")
    @SequenceGenerator(name = "id_generator", initialValue = 1000)
    private Long id;

    @NotEmpty
    private String name;

    @Pattern(regexp = "^(\\+84|0)(\\d{9,10})$", message = "{invalid.phonenumber}")
    private String phoneNumber;

    @Email
    @NotEmpty
    private String email;

    @Min(0)
    private int points;

    //class methods

    @Override
    public String toString() {
        return id + " " + name;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Customer other = (Customer) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

    //// Getter and Setter methods

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPoints() {
        return points;
    }

    public void adjustPoints(int points) {
        this.points += points;
    }    

}
