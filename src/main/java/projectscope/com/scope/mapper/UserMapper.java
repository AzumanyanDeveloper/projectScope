package projectscope.com.scope.mapper;

import projectscope.com.scope.dto.request.UserRequest;
import projectscope.com.scope.dto.response.UserResponse;
import projectscope.com.scope.entity.User;
import springfox.documentation.swagger2.mappers.ModelMapper;

public class UserMapper {
    private final ModelMapper mapper;

    public UserMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }


    public static UserResponse toDto(User user) {
        return UserResponse.builder()
                .name(user.getName())
                .surName(user.getSurName())
                .email(user.getEmail())
                .userType(user.getUserType())
                .build();
    }



    public static User toEntity(UserRequest request) {
        return User.builder()
                .name(request.getName())
                .surName(request.getSurName())
                .email(request.getEmail())
                .password(request.getPassword())
                .profilePicture(request.getProfilePicture())
                .userType(request.getUserType())
                .build();

    }
}
