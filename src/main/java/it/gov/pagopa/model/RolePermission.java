package it.gov.pagopa.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
@Document("role_permission")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RolePermission {

    @Id
//    private ObjectId id;
    private String id;

    //    @Field("role")
    private String role;

    private String description;

    private List<Permission> permissions;

}