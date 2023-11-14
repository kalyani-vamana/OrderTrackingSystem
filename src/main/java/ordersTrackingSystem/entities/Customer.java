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
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity 
@Table (name = "customers")
public class Customer {
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	@Column (name = "cust_id")
	private Integer id;
	
	@Column (name = "name") 
	@NotBlank (message = "customer name cannot be null")
	private String name;
	
	@Column (name = "email")
	@Email
	@Size(max = 40, message = "email max length is 40")
	private String email;
	
	@Column (name = "mobile")
	private String mobile;
	
	@JsonIgnore
	@OneToMany (cascade = CascadeType.ALL, mappedBy = "customer")
	private List<Order> orders = new ArrayList<>();

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Override
	public String toString() {
		return String.format("%-8d %-20s %-20s %-10s\n", this.id, this.name, this.email, this.mobile);
	}
}
