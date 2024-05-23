package com.se.leopard.rest_template_resilience4j.entity;

public class Phone {
	
	private long id;
	private String brand;
    private String model;
    private double price;
    private int stocks;
    private int storage;
    private int ram;
    private String color;
    private String operatingSystem;
    private double screenSize;
    private int batteryCapacity;
    private int cameraResolution;
    
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getStocks() {
		return stocks;
	}
	public void setStocks(int stocks) {
		this.stocks = stocks;
	}
	public int getStorage() {
		return storage;
	}
	public void setStorage(int storage) {
		this.storage = storage;
	}
	public int getRam() {
		return ram;
	}
	public void setRam(int ram) {
		this.ram = ram;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getOperatingSystem() {
		return operatingSystem;
	}
	public void setOperatingSystem(String operatingSystem) {
		this.operatingSystem = operatingSystem;
	}
	public double getScreenSize() {
		return screenSize;
	}
	public void setScreenSize(double screenSize) {
		this.screenSize = screenSize;
	}
	public int getBatteryCapacity() {
		return batteryCapacity;
	}
	public void setBatteryCapacity(int batteryCapacity) {
		this.batteryCapacity = batteryCapacity;
	}
	public int getCameraResolution() {
		return cameraResolution;
	}
	public void setCameraResolution(int cameraResolution) {
		this.cameraResolution = cameraResolution;
	}
	public Phone() {
		super();
	}
	public Phone(long id, String brand, String model, double price, int stocks, int storage, int ram, String color,
			String operatingSystem, double screenSize, int batteryCapacity, int cameraResolution) {
		super();
		this.id = id;
		this.brand = brand;
		this.model = model;
		this.price = price;
		this.stocks = stocks;
		this.storage = storage;
		this.ram = ram;
		this.color = color;
		this.operatingSystem = operatingSystem;
		this.screenSize = screenSize;
		this.batteryCapacity = batteryCapacity;
		this.cameraResolution = cameraResolution;
	}
	@Override
	public String toString() {
		return "Phone [id=" + id + ", brand=" + brand + ", model=" + model + ", price=" + price + ", stocks=" + stocks
				+ ", storage=" + storage + ", ram=" + ram + ", color=" + color + ", operatingSystem=" + operatingSystem
				+ ", screenSize=" + screenSize + ", batteryCapacity=" + batteryCapacity + ", cameraResolution="
				+ cameraResolution + "]";
	}
}
