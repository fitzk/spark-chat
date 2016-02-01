import access.ChannelItem;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.TableCollection;
import com.amazonaws.services.dynamodbv2.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import schema.ChannelMetaDataItem;


import java.nio.channels.Channel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static spark.Spark.*;


public class Main {
    public static void main(String[] args) {

        get("/", (req, res) -> {
         //   System.out.println(req);
            return "This is a chat application!";
        });
        /**
         * Returns all users
         */
        get("/user", (req, res) -> {
            System.out.println(req);
            return res;
        });
        /**
         * Updates a user based on an ID
         */
        post("/user", (req,res)-> {
            return 0;
        });
        /**
         * Deletes a user based on id
         */
        delete("/user", (req,res)-> {
            return 0;
        });
        /**
         * Adds a user
         */
        post("/user",(req,res)-> {
            return 0;
        });
        /**
         * Returns all channels if id is NULL
         * else returns a channel
         */
        get("/channel", (req, res) -> {

            return 0;
        });
        /**
         * Adds a Channel
         */
        post("/channel", (req,res)-> {

            ObjectMapper mapper = new ObjectMapper();
            ChannelItem newItem = new ChannelItem();
            ChannelMetaDataItem channelData = mapper.readValue(req.body(), ChannelMetaDataItem.class);
            newItem.setChannelMeta(channelData);
            newItem.addChannel();
            System.out.println("New ChannelItem: " + channelData.getName());
            return 0;
        });
        /**
         * Removes a Channel
         */
        delete("/Channel", (req,res)-> {
            return 0;
        });
        /**
         * Associates a Channel with a URI
         */
        put("/channel", (req,res)-> {
            return 0;
        });

      get("/chatroom", (req, res) -> {
       //   System.out.println(req);
          return "This is a chat application!";
      });

    }
}