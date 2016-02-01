package schema;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by kaylafitzsimmons on 1/30/16.
 */

public class MessageItem {


    private Integer id;
    private String name;
    private Date date;
    private Set<String> tags= new HashSet<>();
    private String text;
    private Integer part;
    private Integer total;

    @DynamoDBHashKey(attributeName="Id")
    public Integer getId() { return id;}
    public void setId(Integer id) {this.id = id;}

    @DynamoDBAttribute(attributeName="Name")
    public String getName() {return name; }
    public void setName(String name) { this.name = name; }

    @DynamoDBAttribute(attributeName="Date")
    public Date getDate() {return date; }
    public void setDate(Date date) { this.date = date; }

    @DynamoDBAttribute(attributeName="Tags")
    public Set<String>getTags() {return tags; }
    public void setTags(Set<String> tags) { this.tags = tags; }

    @DynamoDBAttribute(attributeName="Text")
    public String getText() {return text; }
    public void setText(String text) { this.text = text; }

    // part/total
    @DynamoDBAttribute(attributeName = "Part")
    public Integer getPart() { return part;}
    public void setPart(Integer part) {this.part = part;}

    @DynamoDBAttribute(attributeName = "Total")
    public Integer getTotal() { return total;}
    public void setTotal(Integer total) {this.total = total;}



}
