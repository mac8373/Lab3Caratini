/**
 * Project: Lab3Caratini
 * Purpose Details: This project represents a Customer.
 * Course: IST242
 * Author: Maximo Caratini
 * Date Developed: 2024-02-15
 * Last Date Changed: 2024-02-29
 * Rev: 1.0
 */
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Customer {
    // Customer class representing singular customers in blockchain
    private int id;
    private String firstName;
    private String lastName;
    private int age;
    private String email;

    // Constructor to create customer object
    public Customer(int id, String firstName, String lastName, int age, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
    }
    // toString method for easy printing of customer information
    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                '}';
    }

    // Transaction class to represent transactions with customers
    static class Transaction {
        private int blockNumber;
        private Customer customer;
        private String transactionData;

        // Constructor to create transaction object
        public Transaction(int blockNumber, Customer customer, String transactionData) {
            this.customer = customer;
            this.transactionData = transactionData;
        }
        // Getter method to retrieve block number
        public int getBlockNumber() {
            return blockNumber;
        }
        // Getter method for retrieving customer
        public Customer getCustomer() {
            return customer;
        }
        // Getter method for retrieving transaction data
        public String getTransactionData() {
            return transactionData;
        }
    }

    // Block class representing blockchain blocks
    static class Block {
        private int index;
        private long timestamp;
        private String previousHash;
        private String hash;
        private List<Transaction> transactions;

        // Constructor to create a block object
        public Block(int index, String previousHash, List<Transaction> transactions) {
            this.index = index;
            this.timestamp = new Date().getTime();
            this.previousHash = previousHash;
            this.transactions = transactions;
            this.hash = calculateHash();
        }
        // Private method calculating hash
        private String calculateHash() {
            try {
                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                String transactionData = transactions.toString();
                String input = index + timestamp + previousHash + transactionData;
                byte[] hashBytes = digest.digest(input.getBytes("UTF-8"));
                StringBuilder hexString = new StringBuilder();

                for (byte b : hashBytes) {
                    String hex = Integer.toHexString(0xff & b);
                    if (hex.length() == 1) hexString.append('0');
                    hexString.append(hex);
                }

                return hexString.toString();
            } catch (NoSuchAlgorithmException | java.io.UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }
        // Getter methods retrieving block information
        public int getIndex() {
            return index;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public String getPreviousHash() {
            return previousHash;
        }

        public String getHash() {
            return hash;
        }

        public List<Transaction> getTransactions() {
            return transactions;
        }
    }

    // Blockchain class to manage chain of blocks
    static class Blockchain {
        private List<Block> chain;
        // Constructor to create a blockchain object
        public Blockchain() {
            chain = new ArrayList<>();
            // Create the genesis block (the first block in the chain)
            chain.add(new Block(0, "0", new ArrayList<>()));
        }
        // Method adding new block
        public void addBlock(Customer customer, String transactionData) {
            Block previousBlock = chain.get(chain.size() - 1);
            int newBlockNumber = previousBlock.getIndex() + 1;
            List<Transaction> transactions = new ArrayList<>();
            transactions.add(new Transaction(newBlockNumber, customer, transactionData));

            Block newBlock = new Block(newBlockNumber, previousBlock.getHash(), transactions);
            chain.add(newBlock);
        }
        // Method printing entire blockchain
        public void printBlockchain() {
            for (Block block : chain) {
                System.out.println("Block #" + block.getIndex());
                System.out.println("Timestamp: " + block.getTimestamp());
                System.out.println("Previous Hash: " + block.getPreviousHash());
                System.out.println("Transactions:");

                for (Transaction transaction : block.getTransactions()) {
                    Customer customer = transaction.getCustomer();
                    System.out.println("  Customer: " + customer.toString());
                    System.out.println("  Transaction Data: " + transaction.getTransactionData());
                    System.out.println();
                }

                System.out.println("Hash: " + block.getHash());
                System.out.println();
            }
        }
    }
    // Main method to test blockchain
    public static void main(String[] args) {
        // Create a new blockchain
        Blockchain blockchain = new Blockchain();

        Customer customer1 = new Customer(1, "John", "Doe", 30, "john@example.com");
        blockchain.addBlock(customer1, "Transaction 1");

        Customer customer2 = new Customer(2, "Jane", "Smith", 25, "jane@example.com");
        blockchain.addBlock(customer2, "Transaction 2");

        // Print the blockchain
        blockchain.printBlockchain();
    }
}