package main.main;

import access.ChannelDAO;
import access.ChatRoomDAO;
import access.MessageDAO;
import access.UserDAO;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.ListTablesResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import schema.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static spark.Spark.*;


public class Main {

    private static AmazonDynamoDB dynamodb;
    private static ChatRoomDAO chatRoomAccessor;
    private static ChannelDAO channelAccessor;
    private static UserDAO userAccessor;
    private static MessageDAO messageAccessor;




    public static void main(String[] args) throws InterruptedException {
        setup();


        get("/", (request, res) -> {
            return "This is a chat application!";
        });
        /**
         * Updates a user based on an ID
         */
        put("/user/:id", (request,response)-> {
            String id = request.params("id");
            System.out.println(id);
            ObjectMapper mapper = new ObjectMapper();
            UserItem userUpdated = mapper.readValue(request.body(), UserItem.class);
            userUpdated.setId(userUpdated.getUsername());
            UserItem original = userAccessor.read(id);
            if(original == null) {
                response.status(404);
                return "The user was not found!";
            }
            if(userUpdated.getUsername()==null){
               userUpdated.setUsername(original.getUsername());
                userUpdated.setId(original.getUsername());
            }
            if(original.getOnline() == null ){
                userUpdated.setOnline(original.getOnline());
            }
                userAccessor.save(userUpdated);
                userAccessor.delete(original);
                response.status(200);
                return "User "+ userUpdated.getUsername()+" was successfully updated.";
        });
        get("/user/:id", (request,response)-> {
            String id = request.params(":id");
            UserItem user = userAccessor.read(id);
            ObjectMapper mapper = new ObjectMapper();
            if(user != null) {
                String userstr = mapper.writeValueAsString(user);
                return "found you: " + userstr;
            }else{
                response.status(404);
                return "Sorry you don't exsist!";
            }
        });

        /**
         * Deletes a user based on id
         */
        delete("/user/:id", (request,response)-> {
            String id = request.params(":id");
            userAccessor.delete(id);
            response.status(200);
            return "Successfully deleted "+ id +"!";
        });
        /**
         * Adds a user
         */
        post("/user",(request,response)-> {
            ObjectMapper mapper = new ObjectMapper();
            UserItem newUser = mapper.readValue(request.body(), UserItem.class);
            if(newUser.getUsername()!=null){
                UserItem exists = userAccessor.read(newUser.getUsername());
                if(exists == null) {
                    String username = newUser.getUsername();
                    System.out.println("username in main: " + username);
                    String id = userAccessor.create(username);
                    System.out.println("id in main: " + id);
                    response.status(201);
                    return "User "+newUser.getUsername()+" was successfully created.";

                }else{
                    response.status(302);
                    return "Sorry username "+newUser.getUsername()+" already exits!";
                }

            }else{
                response.status(400);
                return "Bad Request";
            }

        });
        /**
         * Adds a Channel
         */
        post("/channel", (request,response)-> {
            ObjectMapper mapper = new ObjectMapper();

            ChannelMetadataItem channelData = mapper.readValue(request.body(), ChannelMetadataItem.class);

            if(channelData.getName() != null){
                channelData.setId(channelData.getName());
                ChannelMetadataItem exists = channelAccessor.getChannelMetaById(channelData.getId());
                if(exists == null) {
                    String id = channelAccessor.create(channelData.getName());
                    response.status(200);
                    return "Successfully added " + channelAccessor.getChannelMetaById(id).getName() + " /channel/" + id;
                }else{
                    response.status(202);
                    return "Sorry, That channel name is already taken!";
                }
            }else{
                response.status(400);
                return "Bad Request";
            }
        });
        /**
         * Gets a Channel
         */
        get("/channel/:id", (request,response)-> {
            String id = request.params(":id");
            if(id!= null){
                ChannelMetadataItem meta = channelAccessor.getChannelMetaById(id);
                ChannelStateItem state = channelAccessor.getChannelStateById(id);
                response.status(200);
                return "{"+ meta.getName()+","+meta.getDateCreated() + "},{"+state.getRooms()+"}";
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
            channelAccessor.delete(id);
            response.status(200);
            return "Successfully deleted "+ id +"!";
        });
        /**
         * Updates a channel name
         */
        put("/channel/:id", (request,response)-> {
            ObjectMapper mapper = new ObjectMapper();
            String id = request.params(":id");
            ChannelMetadataItem channelUpdated = mapper.readValue(request.body(), ChannelMetadataItem.class);
            String name = channelUpdated.getName();
            if(name != null){
                ChannelMetadataItem meta = channelAccessor.getChannelMetaById(id);
                meta.setId(name);
                meta.setName(name);
                ChannelStateItem state = channelAccessor.getChannelStateById(id);
                state.setId(name);
                state.setId(name);
                channelAccessor.save(meta);
                channelAccessor.save(state);
                response.status(200);
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
            String id = request.params(":id");
            ChatRoomMetadataItem meta = chatRoomAccessor.getChatRoomMetaDataById(id);
            if(meta != null){
                ObjectMapper mapper = new ObjectMapper();
                String metastring = mapper.writeValueAsString(meta);
                ChatRoomStateItem state = chatRoomAccessor.getChatRoomStateById(id);
                String statestring = mapper.writeValueAsString(state);
                ChatRoomContentItem content = chatRoomAccessor.getChatRoomContentById(id);
                String contentstring = mapper.writeValueAsString(content);
                response.status(200);
                return "[ "+metastring + "," + statestring+ "," + contentstring + " ]";
            }else{
                response.status(404);
                return "Not Found";
            }
        });
        /**
         *  Add new chatroom
         *  body: { name(chatroom),type(public/private)}
         */
        post("channel/:channelid/chatroom", (request, response) -> {
            ObjectMapper mapper = new ObjectMapper();
            ChatRoomMetadataItem newRoom = mapper.readValue(request.body(), ChatRoomMetadataItem.class);
            String channelid = request.params(":channelid");
            if(newRoom.getName() != null){
                ChannelMetadataItem channelData = channelAccessor.getChannelMetaById(channelid);
                if(channelData == null) {
                   response.status(404);
                    return "This channel doesn't exist!";
                }else {
                    String id = channelAccessor.addRoomToChannel(channelData.getId(),newRoom.getName());
                    response.status(200);
                    return "Successfully added " + id ;
                }

            }else{
                response.status(400);
                return "Bad Request";
            }
        });
        /**
         * Updates a chatroom name
         */
        put("channel/:channelid/chatroom/:id", (request, response) -> {
            String roomid = request.params(":id");
            String channelid = request.params(":channelid");

          //  channelAccessor.removeRoomFromChannel(channelid,roomid);
            ObjectMapper mapper = new ObjectMapper();
            String id = request.params(":id");
            ChatRoomMetadataItem updated = mapper.readValue(request.body(), ChatRoomMetadataItem.class);

            String name = updated.getName();
            System.out.println(name);
            if(name != null){
                ChannelMetadataItem meta = channelAccessor.getChannelMetaById(channelid);
                if(meta != null) {
                    ChatRoomMetadataItem old = chatRoomAccessor.getChatRoomMetaDataById(roomid);
                    if(old != null) {
                        channelAccessor.removeRoomFromChannel(channelid, roomid);
                        channelAccessor.addRoomToChannel(channelid, name);
                        chatRoomAccessor.delete(roomid);
                        chatRoomAccessor.create(name);
                        updated.setId(name);
                        updated.setDateCreated(old.getDateCreated());
                        updated.setType(old.getType());
                        updated.setState(old.getState());
                        chatRoomAccessor.save(updated);
                        response.status(200);
                        return "Successfully updated " + name;

                    }else{
                        response.status(400);
                        return "Bad Request";
                    }


                }else{
                    response.status(404);
                    return "Can't find channel!";
                }
            }else{
                response.status(400);
                return "Can't find chat room!";
            }
        });
        /**
         * Deletes a chatroom
         * body: { channelid }
         */
        delete("channel/:channelid/chatroom/:id", (request, response) -> {
            String roomid = request.params(":id");
            String channelid = request.params(":channelid");
            channelAccessor.removeRoomFromChannel(channelid,roomid);
            response.status(200);
            return "Successfully deleted "+ roomid +"!";
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
        get("/destroy", (request, response) -> {
            response.status(404);
            return "Goodbye!";
        });


    }
    public static void setup() throws InterruptedException {
        dynamodb = new AmazonDynamoDBClient();
        //dynamodb.setEndpoint("http://localhost:8000/");
        _createTables();
        ListTablesResult tables = dynamodb.listTables();
        List<String> names = tables.getTableNames();
        assertThat(names.size()).isNotEqualTo(0);
        System.out.println("Tables:");
        for (String name : names) {
            System.out.println(name);
        }
        // chat room
        chatRoomAccessor = new ChatRoomDAO();
        chatRoomAccessor.init(dynamodb);

        // channel
        channelAccessor = new ChannelDAO(chatRoomAccessor);
        channelAccessor.init(dynamodb);

        // user
        userAccessor = new UserDAO();
        userAccessor.init(dynamodb);

        // message
        messageAccessor = new MessageDAO();
        messageAccessor.init(dynamodb);
    }
    private static void _createTables() throws InterruptedException {
        try {
            DynamoDBUtils.createTable(dynamodb, UserItem.class);
            DynamoDBUtils.createTable(dynamodb, ChannelMetadataItem.class);
            DynamoDBUtils.createTable(dynamodb, ChannelStateItem.class);
            DynamoDBUtils.createTable(dynamodb, ChatRoomMetadataItem.class);
            DynamoDBUtils.createTable(dynamodb, ChatRoomContentItem.class);
            DynamoDBUtils.createTable(dynamodb, MessageItem.class);
            Table table = DynamoDBUtils.createTable(dynamodb, ChatRoomStateItem.class);

            table.waitForActive();
        }catch(InterruptedException e){
            System.err.println(e);
        }
    }

    private static void _deleteTables() {
            DynamoDBUtils.deleteTable(dynamodb, UserItem.class);
            DynamoDBUtils.deleteTable(dynamodb, ChannelMetadataItem.class);
            DynamoDBUtils.deleteTable(dynamodb, ChannelStateItem.class);
            DynamoDBUtils.deleteTable(dynamodb, ChatRoomMetadataItem.class);
            DynamoDBUtils.deleteTable(dynamodb, ChatRoomStateItem.class);
            DynamoDBUtils.deleteTable(dynamodb, ChatRoomContentItem.class);
            DynamoDBUtils.deleteTable(dynamodb, MessageItem.class);


    }
    public static void teardown() {
        _deleteTables();
    }
}
