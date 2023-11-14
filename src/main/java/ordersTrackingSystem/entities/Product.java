package ordersTrackingSystem.entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

@Entity 
@Table (name = "products")
public class Product {
	@Id 
	@GeneratedValue (strategy = GenerationType.IDENTITY) 
	@Column (name = "prodid")
	private Integer productId;
	
	@Column (name = "prodname")
	@NotBlank (message = "product name cannot be blank")
	private String productName;
	
	@Column (name = "description")
	private String productDescription;
	
	@Column (name = "price")
	@PositiveOrZero (message = "price should be >= 0")
	private Double price;
	
	@JsonIgnore
	@OneToMany (cascade = CascadeType.ALL, mappedBy = "product")
	private List<OrderItem> orderItems = new ArrayList<>();

	public List<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
	@Override 
	public String toString() {
		return String.format("%-8d %-28s %-52s %-8.2f\n", 
				this.productId, this.productName, this.productDescription, this.price);
	}
}