//Author Name: Christopher Woodley

//Date: 03/14/2023

//Description: This is the connection class. This class sets up and closes connection to the MongoDB client

package com.mongodb.taskscheduler;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

public class Connection {

    private static MongoClient mongoClient;
    private static Connection connection;

    //Class attempts to make a connection to MongoDB named taskScheduler
    public Connection() {
        try {
            //Set ConnectionString to MongoDB server
            ConnectionString connectionString = new ConnectionString("mongodb+srv://cwoodley:AdminPassword4Use@cluster0.3vibaxi.mongodb.net/?retryWrites=true&w=majority");
            //Set Client settings as per server setup on MongoDB
            MongoClientSettings settings = MongoClientSettings.builder()
                    .applyConnectionString(connectionString)
                    .serverApi(ServerApi.builder()
                            .version(ServerApiVersion.V1)
                            .build())
                    .build();

            //Create mongo client variable to be accessed by application
            mongoClient = MongoClients.create(settings);
        } catch (Exception exception) {
            System.err.println(exception.getClass().getName() + "L : "+ exception.getMessage());
        }
    }

    // Checks if a client is currently connected, if not, it will create a new Connection
    // Else it will return the connected mongoClient
    public static MongoClient getClient() {

        if (connection == null) {
            connection = new Connection();
        }

        return mongoClient;
    }

    // Closes the client as needed
    public static void closeClient() {
        mongoClient.close();
    }

}
