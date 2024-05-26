package com.se.leopard.serviceorder.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.se.leopard.serviceorder.entity.Order;
import com.se.leopard.serviceorder.repository.PhoneRepository;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "order_table")
public class Order implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1877917115053458965L;

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private long id;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	@JsonBackReference
	private User user;
	
	@OneToOne
	@JoinColumn(name = "phone_id", referencedColumnName = "id")
	private Phone phone;

	private LocalDateTime orderAt;
	
	@PrePersist
	protected void onCreate() {
		orderAt = LocalDateTime.now();
	}
	
	@Component
    public static class OrderListener {
        private static PhoneRepository phoneRepository;

        @Autowired
        public OrderListener(PhoneRepository phoneRepository) {
            OrderListener.phoneRepository = phoneRepository;
        }

        @PostPersist
        public void afterCreate(Order order) {
            Phone phone = order.getPhone();
            if (phone != null) {
                phone = phoneRepository.findById(phone.getId()).orElseThrow(() -> new RuntimeException("Phone not found"));
                if (phone.getStocks() > 0) {
                    phone.setStocks(phone.getStocks() - 1);
                    phoneRepository.save(phone);
                }
            }
        }
    }
}
