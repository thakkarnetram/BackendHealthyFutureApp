package com.devil.backend;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserModal {
    @Id
    private ObjectId userId;
    private String emailId;
    private String otp;

}
