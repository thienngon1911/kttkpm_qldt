package com.se.leopard.servicephone.entity;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class Phone implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8863764031277067948L;
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private long id;
	private String brand;
    private String model;
    private double price;
    private int storage;
    private int ram;
    private String color;
    private String operatingSystem;
    private double screenSize;
    private int batteryCapacity;
    private int cameraResolution;
}
