import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Activity {
    private String name;
    private String description;
    private double cost;
    private int capacity;

    public Activity(String name, String description, double cost, int capacity) {
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.capacity = capacity;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getCost() {
        return cost;
    }

    public int getCapacity() {
        return capacity;
    }
}

class Destination {
    private String name;
    private List<Activity> activities;

    public Destination(String name) {
        this.name = name;
        this.activities = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public void addActivity(Activity activity) {
        activities.add(activity);
    }
}

class Passenger {
    private String name;
    private int passengerNumber;
    private double balance;
    private String type;
    private List<Activity> signedActivities;

    public Passenger(String name, int passengerNumber, double balance, String type) {
        this.name = name;
        this.passengerNumber = passengerNumber;
        this.balance = balance;
        this.type = type;
        this.signedActivities = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public int getPassengerNumber() {
        return passengerNumber;
    }

    public double getBalance() {
        return balance;
    }

    public String getType() {
        return type;
    }

    public List<Activity> getSignedActivities() {
        return signedActivities;
    }

    public void signUpForActivity(Activity activity) {
        if (type.equals("standard")) {
            if (balance >= activity.getCost()) {
                balance -= activity.getCost();
                signedActivities.add(activity);
            } else {
                System.out.println("Insufficient balance for activity: " + activity.getName());
            }
        } else if (type.equals("gold")) {
            double discountedCost = activity.getCost() * 0.9; // 10% discount
            if (balance >= discountedCost) {
                balance -= discountedCost;
                signedActivities.add(activity);
            } else {
                System.out.println("Insufficient balance for activity: " + activity.getName());
            }
        } else if (type.equals("premium")) {
            signedActivities.add(activity);
        }
    }
}

class TravelPackage {
    private String name;
    private int passengerCapacity;
    private List<Passenger> passengers;
    private List<Destination> itinerary;

    public TravelPackage(String name, int passengerCapacity) {
        this.name = name;
        this.passengerCapacity = passengerCapacity;
        this.passengers = new ArrayList<>();
        this.itinerary = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public int getPassengerCapacity() {
        return passengerCapacity;
    }

    public List<Passenger> getPassengers() {
        return passengers;
    }

    public List<Destination> getItinerary() {
        return itinerary;
    }

    public void addDestination(Destination destination) {
        itinerary.add(destination);
    }

    public void addPassenger(Passenger passenger) {
        if (passengers.size() < passengerCapacity) {
            passengers.add(passenger);
        } else {
            System.out.println("Passenger capacity reached for this travel package.");
        }
    }

    public void printItinerary() {
        System.out.println("Travel Package: " + name);
        for (Destination destination : itinerary) {
            System.out.println("Destination: " + destination.getName());
            System.out.println("Activities:");
            for (Activity activity : destination.getActivities()) {
                System.out.println("Name: " + activity.getName());
                System.out.println("Description: " + activity.getDescription());
                System.out.println("Cost: " + activity.getCost());
                System.out.println("Capacity: " + activity.getCapacity());
            }
        }
    }

    public void printPassengerList() {
        System.out.println("Travel Package: " + name);
        System.out.println("Passenger Capacity: " + passengerCapacity);
        System.out.println("Number of Passengers Enrolled: " + passengers.size());
        System.out.println("Passenger List:");
        for (Passenger passenger : passengers) {
            System.out.println("Name: " + passenger.getName() + ", Passenger Number: " + passenger.getPassengerNumber());
        }
    }

    public void printPassengerDetails(Passenger passenger) {
        System.out.println("Passenger Details:");
        System.out.println("Name: " + passenger.getName());
        System.out.println("Passenger Number: " + passenger.getPassengerNumber());
        System.out.println("Balance: " + passenger.getBalance());
        System.out.println("Activities Signed Up For:");
        for (Activity activity : passenger.getSignedActivities()) {
            System.out.println("Activity: " + activity.getName() + ", Destination: " + findDestination(activity));
        }
    }

    private String findDestination(Activity activity) {
        for (Destination destination : itinerary) {
            if (destination.getActivities().contains(activity)) {
                return destination.getName();
            }
        }
        return "Destination not found";
    }

    public void printAvailableActivities() {
        System.out.println("Available Activities:");
        Map<Activity, Integer> activitySpaces = new HashMap<>();
        for (Destination destination : itinerary) {
            for (Activity activity : destination.getActivities()) {
                int remainingCapacity = activity.getCapacity() - countSignedUpPassengers(activity);
                activitySpaces.put(activity, remainingCapacity);
            }
        }
        for (Map.Entry<Activity, Integer> entry : activitySpaces.entrySet()) {
            System.out.println("Activity: " + entry.getKey().getName() + ", Remaining Spaces: " + entry.getValue());
        }
    }

    private int countSignedUpPassengers(Activity activity) {
        int count = 0;
        for (Passenger passenger : passengers) {
            if (passenger.getSignedActivities().contains(activity)) {
                count++;
            }
        }
        return count;
    }
}

public class Main {
    public static void main(String[] args) {
        // Create activities
        Activity hiking = new Activity("Hiking", "Explore nature on foot", 50.0, 20);
        Activity snorkeling = new Activity("Snorkeling", "Discover underwater world", 70.0, 15);

        // Create destinations
        Destination destination1 = new Destination("Mountains");
        destination1.addActivity(hiking);

        Destination destination2 = new Destination("Beach");
        destination2.addActivity(snorkeling);

        // Create passengers
        Passenger standardPassenger = new Passenger("John", 1, 200.0, "standard");
        Passenger goldPassenger = new Passenger("Alice", 2, 300.0, "gold");
        Passenger premiumPassenger = new Passenger("Bob", 3, 0.0, "premium");

        // Create travel package
        TravelPackage travelPackage = new TravelPackage("Adventure Trip", 3);
        travelPackage.addDestination(destination1);
        travelPackage.addDestination(destination2);
        travelPackage.addPassenger(standardPassenger);
        travelPackage.addPassenger(goldPassenger);
        travelPackage.addPassenger(premiumPassenger);

        // Sign up passengers for activities
        standardPassenger.signUpForActivity(hiking);
        goldPassenger.signUpForActivity(snorkeling);
        premiumPassenger.signUpForActivity(hiking);

        // Print itinerary
        travelPackage.printItinerary();

        // Print passenger list
        travelPackage.printPassengerList();

        // Print individual passenger details
        travelPackage.printPassengerDetails(standardPassenger);
        travelPackage.printPassengerDetails(goldPassenger);
        travelPackage.printPassengerDetails(premiumPassenger);

        // Print available activities
        travelPackage.printAvailableActivities();



        System.out.println();

        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();

    }
}
