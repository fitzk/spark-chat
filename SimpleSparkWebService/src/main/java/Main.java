import access.ChannelItem;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.TableCollection;
import com.amazonaws.services.dynamodbv2.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import schema.ChannelMetaDataItem;
import schema.ChatRoomMetaDataItem;
import schema.UserMetaDataItem;


import java.nio.channels.Channel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static spark.Spark.*;


public class Main {
    public static void main(String[] args) {

        get("/", (request, res) -> {
         //   System.out.println(request);
            return "This is a chat application!";
        });
        /**
         * Updates a user based on an ID
         */
        get("/user/:id", (request,response)-> {
            return 0;
        });
        /**
         * Returns all users
         */
        get("/users", (request,response)-> {
            return 0;
        });
        /**
         * Deletes a user based on id
         */
        delete("/user/:id", (request,response)-> {
            return 0;
        });
        /**
         * Adds a user
         */
        post("/user",(request,response)-> {
            ObjectMapper mapper = new ObjectMapper();
            UserMetaDataItem newUser = mapper.readValue(request.body(),UserMetaDataItem.class);
            if(newUser.getUsername()!=null){
                response.status(201);
                return "User "+newUser.getUsername()+" was successfully created.";
            }else{
                response.status(400);
                return "Bad Request";
            }
        });
        /**
         *  Returns a channel
         */
        get("/channel/:id", (request, response) -> {
            String id = request.params(":id");
            return 0;
        });
        /**
         * Adds a Channel
         */
        post("/channel", (request,response)-> {
            ObjectMapper mapper = new ObjectMapper();
            ChannelItem newItem = new ChannelItem();
            ChannelMetaDataItem channelData = mapper.readValue(request.body(), ChannelMetaDataItem.class);
            if(channelData.getName() != null && channelData.getTags() != null){
                newItem.setChannelMeta(channelData);
                newItem.addChannel();
                response.status(201);
                return "Successfully added "+ newItem.getChannelMeta().getName();
            }else{
                response.status(400);
                return "Bad Request";
            }
        });
        /**
         * Removes a Channel
         */
        delete("/channel/:id", (request,response)-> {
            String id = request.params(":id");
            /*
            * Insert query to find and delete channel
            * if (successful){
            *    response.status(200);
            *    return "This channel has been deleted.";
            * }else{
            *   response.status(400);
            *   return "Bad Request";
            * }
            *
            * */

            return 0;
        });
        /**
         * Updates a channel
         */
        put("/channel/:id", (request,response)-> {
            String id = request.params(":id");
            String name = request.params(":name");
            if(name != null){
                /*
                *   Insert query for getting channel based on id
                *   channel.setName(name);
                * */
                response.status(201);
                return "Successfully updated "+ name;
            }else{
                response.status(400);
                return "Bad Request";
            }
        });
        /**
         * Returns a chatroom
         */
        get("/chatroom/:id", (request, response) -> {
       //   System.out.println(request);
            String id = request.params(":id");
          return "This is a chat application!";
        });
        /**
         *  Add new chatroom
         */
        post("/chatroom", (request, response) -> {
        //   System.out.println(request);
          return "This is a chat application!";
        });
        /**
         * Updates a chatroom
         */
        put("/chatroom/:id", (request, response) -> {

            String id = request.params(":id");
         /*   ChatRoomMetaDataItem chatroom =
            if (book != null) {
                String newAuthor = request.queryParams("author");
                String newTitle = request.queryParams("title");
                if (newAuthor != null) {
                    book.setAuthor(newAuthor);
                }
                if (newTitle != null) {
                    book.setTitle(newTitle);
                }
                return "Book with id '" + id + "' updated";
            } else {
                response.status(404); // 404 Not found
                return "Book not found";
            }
            *
            * */
            return 0;
        });
        /**
         * Deletes a chatroom
         */
        delete("/chatroom/:id", (request, response) -> {
            //   System.out.println(request);
            return "This is a chat application!";
        });
        /**
         * gets all messages
         */
        get("/messages", (request, response) -> {
            //   System.out.println(request);
            return "This is a chat application!";
        });
        /**
         * add a message
         */
        post("/message", (request, response) -> {
            //   System.out.println(request);
            return "This is a chat application!";
        });
        /**
         * Delete a message
         */
        delete("/message/:id", (request, response) -> {
            //   System.out.println(request);
            return "This is a chat application!";
        });

    }
}