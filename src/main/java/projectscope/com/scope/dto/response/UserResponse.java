package projectscope.com.scope.dto.response;

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
public class UserResponse {

    private String name;
    private String surName;
    private String email;
    private FileEntity profilePicture;
    private UserType userType;
}
