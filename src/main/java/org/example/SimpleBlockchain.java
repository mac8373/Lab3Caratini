/**
 * Project: Lab3Caratini
 * Purpose Details: This project implements a blockchain for managing customer data.
 * Course: IST242
 * Author: Maximo Caratini
 * Date Developed: 2024-02-15
 * Last Date Changed: 2024-02-29
 * Rev: 1.0
 */
import java.util.List;
import java.util.ArrayList;

public class SimpleBlockchain {
    private List<CustomerBlock> chain;

    // Constructor
    public SimpleBlockchain() {
        chain = new ArrayList<>();
        // Create the genesis block (the first block in the chain)
        chain.add(new CustomerBlock(0, "0", null, null));
    }

    // Add a new block to the blockchain
    public void addBlock(Customer customer, String transactionData) {
        // Step 1: Retrieve previous block
        CustomerBlock previousBlock = chain.get(chain.size() - 1);
        // Step 2: Calculate new block number
        int newBlockNumber = previousBlock.getIndex() + 1;
        // Step 3: Create new block
        CustomerBlock newBlock = new CustomerBlock(newBlockNumber, previousBlock.getHash(), customer, transactionData);
        // Step 4: Add new block to chain
        chain.add(newBlock);
    }

    // Print the blockchain
    public void printBlockchain() {
        for (CustomerBlock block : chain) {
            System.out.println("Block #" + block.getIndex());
            System.out.println("Timestamp: " + block.getTimestamp());
            System.out.println("Previous Hash: " + block.getPreviousHash());

            if (block.getCustomer() != null) {
                System.out.println("Customer Information: " + block.getCustomerInfo());
            }

            System.out.println("Hash: " + block.getHash());
            System.out.println();
        }
    }

    // Define a CustomerBlock class
    static class CustomerBlock {
        private int index;
        private long timestamp;
        private String previousHash;
        private Customer customer;
        private String transactionData;

        public CustomerBlock(int index, String previousHash, Customer customer, String transactionData) {
            this.index = index;
            this.timestamp = System.currentTimeMillis();
            this.previousHash = previousHash;
            this.customer = customer;
            this.transactionData = transactionData;
        }

        private String calculateHash() {
            // Implementation of hash calculation
            // (You can use the previous implementation or any other secure hashing algorithm)
            // ...
            return ""; // Placeholder, replace with actual hash
        }

        public int getIndex() {
            return index;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public String getPreviousHash() {
            return previousHash;
        }

        public Customer getCustomer() {
            return customer;
        }

        public String getCustomerInfo() {
            return "ID: " + customer.getId() + ", Name: " + customer.getFirstName() + " " + customer.getLastName() +
                    ", Age: " + customer.getAge() + ", Email: " + customer.getEmail();
        }

        public String getHash() {
            return calculateHash();
        }
    }

    // Define a Customer class
    static class Customer {
        private int id;
        private String firstName;
        private String lastName;
        private int age;
        private String email;

        public Customer(int id, String firstName, String lastName, int age, String email) {
            this.id = id;
            this.firstName = firstName;
            this.lastName = lastName;
            this.age = age;
            this.email = email;
        }

        public int getId() {
            return id;
        }

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
    }
}