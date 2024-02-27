package org.example;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;
import org.example.Customer;

public class RedisCRUD {
    public static void main(String[] args) {
        try {
            Jedis jedis = new Jedis("localhost");
            //Create (Set a key-value pair)
            jedis.set("key", "value");

            //Read (Get the value of a key)
            String value = jedis.get("key");
            System.out.println(value);

            //Update (Update the value of a key)
            jedis.set("key", "newValue");

            //Delete (Delete a key-value pair)
            jedis.del("key");

            jedis.close();
        } catch (JedisConnectionException e) {
            System.out.println("Could not connect to Redis: " + e.getMessage());
        }
    }
}