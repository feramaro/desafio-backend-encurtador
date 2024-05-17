package dev.feramaro.urlshortener.entities;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;


@DynamoDBTable(tableName = "URL")
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class Url {

    private String id;
    private String url;
    private String shortCode;
    private Long expDate;

    @DynamoDBHashKey
    @DynamoDBAutoGeneratedKey
    public String getId() {
        return id;
    }

    @DynamoDBAttribute
    public String getUrl() {
        return url;
    }

    @DynamoDBAttribute
    public String getShortCode() {
        return shortCode;
    }

    @DynamoDBAttribute
    public Long getExpDate() {
        return expDate;
    }
}