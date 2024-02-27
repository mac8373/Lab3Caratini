package org.example;

/**
 * Represents customers with name & age.
 * Gives methods to retrieve and update customers imnformation.
 */
public class Customer {
    private String firstName;
    private String lastName;
    private int age;
    private String email;


    //Constructors
    public Customer(String firstName, String lastName, int age, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.email = email;

    }
    //Getter and Setter methods

    /**
     * Get the name of the customer.
     *
     * @return The name of the customer.
     */

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public void setFirstName(String firstName) {
        if (firstName == null || firstName.trim().isEmpty()) {
            throw new IllegalArgumentException("First name can't be null or empty.");
        }
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        // Add validation or checks if needed
        this.lastName = lastName;
    }

    public void setAge(int age) {
        if (age < 0) {
            throw new IllegalArgumentException("Age can't be negative.");
        }
        this.age = age;
    }

    public void setEmail(String email) {
        // Add validation or checks if needed
        this.email = email;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                '}';
    }
}