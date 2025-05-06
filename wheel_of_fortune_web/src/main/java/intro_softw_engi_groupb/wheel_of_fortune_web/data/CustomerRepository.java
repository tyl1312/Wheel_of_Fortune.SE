package intro_softw_engi_groupb.wheel_of_fortune_web.data;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    // This interface will automatically provide CRUD operations for Customer entities
    // No need to implement any methods here
}