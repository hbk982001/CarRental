package carrental;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class car{
	private String carId;
	private String carBrand;
	private String carModel;
	private double basePricePerDay;
	private boolean isAvailable;
	
	//constructor to setup car details
	car(String carId, String carBrand, String carModel, double basePricePerDay){
		this.carId = carId;
		this.carBrand = carBrand;
		this.carModel = carModel;
		this.basePricePerDay = basePricePerDay;
		this.isAvailable = true;
		
	}
	//getter for carId
	public String carId() {
		return carId;
	}
	
	//getter for carBrand
	public String carBrand() {
		return carBrand;
	}
	
	//getter for carModel
	public String carModel() {
		return carModel;
	}
	
	//getter for basePricePerDay
	public double calculatePricePerDay(int noOfDays) {
		return basePricePerDay * noOfDays;
	}
	
	//getter for isAvailable
	public boolean isAvailable() {
		return isAvailable;
	}
	
	//for rent a car
	public boolean rent() {
		return isAvailable = false;
	}
	
	//for return a car
	public boolean returnCar() {
		return isAvailable = true;
	}
}


class customer{
	private String customerId;
	private String customerName;
	
	customer(String customerId, String customerName){
		this.customerId = customerId;
		this.customerName = customerName;
		
	}
	
	//getter for customer id
	public String customerId() {
		return customerId;
	}
	
	//getter for customer name
	public String customerName() {
		return customerName;
	}
}

class rental{
	private car car;
	private customer customer;
	private int days;
	
	rental(car car, customer customer, int days){
		this.car = car;
		this.customer = customer;
		this.days = days;
		
	}
	
	//getter for car
	public car getCar() {
		return car;
	}
	
	//getter for customer
	public customer customer() {
		return customer;
	}
	
	//getter for rentaldays
	public int getDays() {
		return days;
		
	}
}

class rentalSystem{
	private List<car> cars;
	private List<customer> customers;
	private List<rental> rentals;
	
	
	rentalSystem(){
		cars = new ArrayList<>();
		customers = new ArrayList<>();
		rentals = new ArrayList<>();
		
	}
	
	//to add new car
	public void addCar(car car) {
		cars.add(car);
	}
	
	//to addnew customer
	public void addCustomer(customer customer) {
		customers.add(customer);
	}
	
	//method to rent a car
	public void rentCar(car car, customer customer, int days) {
		if(car.isAvailable()) {
			car.rent();
			rentals.add(new rental(car, customer, days));
		}
		else {
			System.out.println("Car is not available for rent");
		}
	}
	
	//method to return a rented car
	public void returnCar(car car) {
		car.returnCar();
		rental rentalToRemove = null;
		for(rental rental: rentals) {
			if(rental.getCar() == car) {
				rentalToRemove = rental;
				break;
			}
		}
		if(rentalToRemove != null) {
			rentals.remove(rentalToRemove);
			System.out.println("Car returned successfully");
		}
		else {
			System.out.println("Car was not rented");
		}
	}
	
	public void menu() {
		Scanner scanner = new Scanner(System.in);
		
		while(true) {
			System.out.println("===== Car Rental System =====");
			System.out.println("1. Rent a Car");
			System.out.println("2. Return a Car");
			System.out.println("3. Exit");
			System.out.println("Enter your choice.....");
			
			int choice = scanner.nextInt();
			scanner.nextLine();
			
			//if user choose rent a car
			if(choice == 1) {
				System.out.println("==== Rent a Car =====");
				System.out.print("Enter your name: ");
				String customerName = scanner.nextLine();
				
				System.out.println("/nAvaialble Cars: ");
				for(car car: cars) {
					if(car.isAvailable()) {
						System.out.println(car.carId()+"-"+car.carBrand()+"-"+car.carModel());
					}
				}
				
				System.out.print("Enter the carID you want to rent: ");
				String carId = scanner.nextLine();
				
				System.out.print("Eneter the number of days for rent a car: ");
				int rentalDays = scanner.nextInt();
				scanner.nextLine();
				
				customer newCustomers = new customer("Customer Name: "+(customers.size()+1), customerName);
				addCustomer(newCustomers);
				
				car selectedCar = null;
				for(car car: cars) {
					if(car.carId().equals(carId) && car.isAvailable()) {
						selectedCar = car;
						break;
					}
				}
				
				//details of the rented car
				if(selectedCar != null) {
					double totalPrice = selectedCar.calculatePricePerDay(rentalDays);
					System.out.println("===== Rental Information ====");
					System.out.println("Customer Id: "+ newCustomers.customerId());
					System.out.println("Customer Name: "+newCustomers.customerName());
					System.out.println("Car: "+selectedCar.carBrand()+" "+ selectedCar.carModel());
					System.out.println("Rental Days: "+rentalDays);
					System.out.println("TotalPrice : "+totalPrice);
					
					System.out.println("Confirm rental : YES/NO");
					String confirm = scanner.nextLine();
					
					if(confirm.equalsIgnoreCase("YES")) {
						rentCar(selectedCar, newCustomers, rentalDays);
						System.out.println("Car rent successfully....");
					}
					else {
						System.out.println("Rental cancelled");
					}
				}
				else {
					System.out.println("Invalid car selection car not available for rent.....!!!");
				}
			}
			
			//if user choose return a car
			else if(choice == 2) {
				System.out.println("===== Return a Car =====");
				System.out.println("Enter the car Id you want to return");
				String carId = scanner.nextLine();
				
				car carToReturn = null;
				for(car car:cars) {
					if(car.carId().equals(carId) && !car.isAvailable()) {
						carToReturn =car;
						break;
					}
				}
				
				//return car details
				if(carToReturn != null) {
					customer customer =null;
					for(rental rental:rentals) {
						if(rental.getCar() == carToReturn) {
							customer = rental.customer();
							break;
						}
					}
					if(customer !=null) {
						returnCar(carToReturn);
						System.out.println("Car returned successfully by: "+customer.customerName());
					}
					else {
						System.out.println("Car was not rented or rental information is missing.....!!!!");
					}
				}
				else {
					System.out.println("Invalid Car Id or car is not rented.....!!!");
				}
			}
			
			//if user select Exit
			else if(choice == 3) {
				break;
			}
			
			//if user not select the proper choice
			else {
				System.out.println("Invalid choice....!!!\nPlease enter a valid choice: ");
			}
		}
		
		System.out.println("Thankyou for using Car Rental System....");
		
	}
}
public class Main {	
	public static void main(String[] args) {
		rentalSystem rent = new rentalSystem();
		car car1 = new car("C001","Mahindra","Thar",60.0);
		car car2 = new car("C002","Tata","Harrier",50.0);
		
		rent.addCar(car1);
		rent.addCar(car2);
		
		rent.menu();

	}

}
