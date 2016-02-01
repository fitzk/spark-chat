import access.ChannelItem;
import com.fasterxml.jackson.databind.ObjectMapper;
import schema.ChannelMetaDataItem;


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
            newItem.create("foo"/* FIXME: need name */);
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