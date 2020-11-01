package projectscope.com.scope.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import projectscope.com.scope.entity.FileEntity;
import projectscope.com.scope.entity.UserType;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest {

    private String name;
    private String surName;
    private String email;
    private String password;
    private FileEntity profilePicture;
    private UserType userType;
}
