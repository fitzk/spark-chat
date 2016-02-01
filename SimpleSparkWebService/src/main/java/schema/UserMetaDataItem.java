package schema; /**
 * Created by kaylafitzsimmons on 1/30/16.
 */


import java.util.Date;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName="UserMetaData")
public class UserMetaDataItem {

    private Integer id;
    private String username;
    private Date dateCreated;


    @DynamoDBHashKey(attributeName="Id")
    public Integer getId() { return id;}
    public void setId(Integer id) {this.id = id;}

    @DynamoDBAttribute(attributeName="Username")
    public String getUsername() {return username; }
    public void setUsername(String username) { this.username = username; }

    @DynamoDBAttribute(attributeName="DateCreated")
    public Date getDateCreated() { return dateCreated;}
    public void setDateCreated(Date dateCreated) { this.dateCreated = dateCreated; }


}