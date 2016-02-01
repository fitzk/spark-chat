package access;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.*;

/**
 * Unit test for simple App.
 */
public class DynamoDBUtils
{

    public static Table createTable(AmazonDynamoDB client, Class schema) throws InterruptedException {
        DynamoDB dynamodb = new DynamoDB(client);
        DynamoDBMapper mapper = new DynamoDBMapper(client);
        CreateTableRequest req = mapper.generateCreateTableRequest(schema);
        req.setProvisionedThroughput(new ProvisionedThroughput(5L, 5L));
        Table table = dynamodb.createTable(req);
        return table;
    }

    public static DeleteTableResult deleteTable(AmazonDynamoDB dynamodb, Class schema) {
        DynamoDBMapper mapper = new DynamoDBMapper(dynamodb);
        DeleteTableRequest req = mapper.generateDeleteTableRequest(schema);
        return dynamodb.deleteTable(req);
    }
}
