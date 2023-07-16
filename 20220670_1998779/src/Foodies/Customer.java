package Foodies;

public class Customer {

    private String firstName;

    private String lastName;

    private int numOfBurgers;

    public Customer(String firstName, int numOfBurgers) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.numOfBurgers = numOfBurgers;
    }

    public String getName() {
        return firstName + lastName;
    }

    public int getNumOfBurgers() {
        return numOfBurgers;
    }
}
