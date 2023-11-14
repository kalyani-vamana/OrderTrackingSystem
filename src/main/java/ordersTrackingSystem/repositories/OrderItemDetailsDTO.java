package ordersTrackingSystem.repositories;

import java.time.LocalDate;

// 12
public interface OrderItemDetailsDTO {
	Integer getOrderId();
	Integer getProductId();
	Integer getCustomerId();
	LocalDate getOrderDate();
	LocalDate getDeliveryDate();
	Character getStatus();
	Integer getQuantity();
	Double getPrice();
}