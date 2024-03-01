/**
 * Project: Lab3Caratini
 * Purpose Details: This project demonstrates CRUD for Redis database.
 * Course: IST242
 * Author: Maximo Caratini
 * Date Developed: 2024-02-15
 * Last Date Changed: 2024-02-29
 * Rev: 1.0
 */
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;

public class RedisCRUD {
    public static void main(String[] args) {
        try {
            // Connect to Redis server running on localhost
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

            // Close connection to Redis
            jedis.close();
        } catch (JedisConnectionException e) {
            System.out.println("Could not connect to Redis: " + e.getMessage());
        }
    }
}