package org.example;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// Define a Block class
class Block {
    private int index;
    private long timestamp;
    private String previousHash;
    private String hash;
    private String data;

    // Constructor
    public Block(int index, String previousHash, String data) {
        this.index = index;
        this.timestamp = new Date().getTime();
        this.previousHash = previousHash;
        this.data = data;
        this.hash = calculateHash();
    }

    // Calculate the hash of the block
    public String calculateHash() {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            String input = index + timestamp + previousHash + data;
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

    // Getters
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

    public String getData() {
        return data;
    }
}

// Define a Blockchain class
class Blockchain {
    private List<Block> chain;

// Constructor
public Blockchain() {
    chain = new ArrayList<Block>();
    // Create the genesis block (the first block in the chain)
    chain.add(new Block(0, "0", "Genesis Block"));
}

    // Add a new block to the blockchain
    public void addBlock(String data) {
        Block previousBlock = chain.get(chain.size() - 1);
        Block newBlock = new Block(previousBlock.getIndex() + 1, previousBlock.getHash(), data);
        chain.add(newBlock);
    }

    public void printBlockchain() {
        for (Block block : chain) {
            System.out.println("Block #" + block.getIndex());
            System.out.println("Timestamp: " + block.getTimestamp());
            System.out.println("Previous Hash: " + block.getPreviousHash());
            System.out.println("Hash: " + block.getHash());
            System.out.println("Data: " + block.getData());
            System.out.println();
        }
    }

}

public class SimpleBlockchain {
    public static void main(String[] args) {
        // Create a new blockchain
        Blockchain blockchain = new Blockchain();

        // Add some blocks to the blockchain
        blockchain.addBlock("Transaction 1");
        blockchain.addBlock("Transaction 2");
        blockchain.addBlock("Transaction 3");

        // Print the blockchain
        blockchain.printBlockchain();

    }
}