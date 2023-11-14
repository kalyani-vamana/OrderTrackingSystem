package ordersTrackingSystem.repositories;

import java.time.LocalDate;

// 11
public interface AllProductDetailsDTO {
	String getProductName();
	String getCustomerName();
	Integer getQuantity();
	Double getPrice();
	LocalDate getorderDate();
}