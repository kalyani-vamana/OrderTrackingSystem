package ordersTrackingSystem.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ordersTrackingSystem.entities.Customer;

public interface CustomerRepo extends JpaRepository<Customer, Integer> {
}
