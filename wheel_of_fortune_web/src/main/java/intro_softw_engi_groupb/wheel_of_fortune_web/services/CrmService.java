package intro_softw_engi_groupb.wheel_of_fortune_web.services;

import java.util.List;

import org.springframework.stereotype.Service;

import intro_softw_engi_groupb.wheel_of_fortune_web.data.Customer;
import intro_softw_engi_groupb.wheel_of_fortune_web.data.CustomerRepository;
import intro_softw_engi_groupb.wheel_of_fortune_web.data.Reward;
import intro_softw_engi_groupb.wheel_of_fortune_web.data.RewardRepository;

@Service
public class CrmService {

    private CustomerRepository customerRepository;
    private RewardRepository rewardRepository;

    public CrmService(CustomerRepository customerRepository, RewardRepository rewardRepository) {
        this.customerRepository = customerRepository;
        this.rewardRepository = rewardRepository;
    }

    //services for customers

    public long countCustomers() {
        return customerRepository.count();
    }
    
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }


    //services for rewards
    
    public long countRewards() {
        return rewardRepository.count();
    }

    public List<Reward> getAllRewards() {
        return rewardRepository.findAll();
    }

    public List<Reward> searchRewards(String searchTerm) {
        return rewardRepository.search(searchTerm);
    }    
    
}
