package org.pluralsight.services;

import org.pluralsight.models.Vehicle;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Dealership
{
    private String name;
    private String address;
    private String phone;
    private ArrayList<Vehicle> inventory;

    public Dealership(String name, String address, String phone)
    {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.inventory = new ArrayList<>();
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public List<Vehicle> getVehicleByPrice(double min, double max){
        return inventory.stream()
                .filter(v-> v.getPrice() >= min && v.getPrice() <= max)
                .collect(Collectors.toList());
    }

    public List<Vehicle> getVehicleByMakeModel(String make, String model){
        return inventory.stream()
                .filter(v -> v.getMake().equalsIgnoreCase(make) && v.getModel().equalsIgnoreCase(model) )
                .collect(Collectors.toList());
    }

    public List<Vehicle> getVehicleByYear(double min, double max){
        return inventory.stream()
                .filter(v-> v.getYear() >= min && v.getYear() <= max)
                .collect(Collectors.toList());
    }

    public List<Vehicle> getVehicleByColor(String color){

        return inventory.stream()
                .filter(v -> v.getColor().equalsIgnoreCase(color))
                .collect(Collectors.toList());
    }

    public List<Vehicle> getVehicleByMileage(double min, double max){
        return inventory.stream()
                .filter(v-> v.getOdometer() >= min && v.getOdometer() <= max)
                .collect(Collectors.toList());
    }

    public List<Vehicle> getVehicleByType(String vehicleType){
        return inventory.stream()
                .filter(v -> v.getVehicleType().equalsIgnoreCase(vehicleType))
                .collect(Collectors.toList());
    }

    public List<Vehicle> getAllVehicles(){
        return inventory;
    }

    public void addVehicle(Vehicle vehicle){
        inventory.add(vehicle);
    }

    public void removeVehicle(Vehicle vehicle)
    {
        inventory.remove(vehicle);
    }
}
