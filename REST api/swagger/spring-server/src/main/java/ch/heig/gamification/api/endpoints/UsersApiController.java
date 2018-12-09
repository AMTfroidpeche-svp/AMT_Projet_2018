package ch.heig.gamification.api.endpoints;

import ch.heig.gamification.entities.ApplicationEntity;
import ch.heig.gamification.entities.UserEntity;
import ch.heig.gamification.entities.CompositeId;
import ch.heig.gamification.repositories.ApplicationRepository;
import ch.heig.gamification.repositories.UserRepository;
import io.avalia.gamification.api.UsersApi;
import io.avalia.gamification.api.model.User;
import io.avalia.gamification.api.model.UserInfos;
import io.avalia.gamification.api.UsersApi;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-07-26T19:36:34.802Z")

@Controller
public class UsersApiController implements UsersApi {

    @Autowired
    UserRepository userRepository;

    @Override
    public ResponseEntity<User> getUser(UserInfos infos) {
        UserEntity user = userRepository.findById(new CompositeId(infos.getApiToken(), infos.getName()));
        if(user == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(toUser(user));
    }

    private UserEntity toUserEntity(User User) {
        UserEntity entity = new UserEntity(new CompositeId(User.getApiToken(), User.getName()));
        return entity;
    }

    private User toUser(UserEntity entity) {
        User User = new User();
        User.setApiToken(entity.getId().getApiToken());
        User.setName(entity.getId().getName());
        return User;
    }

}
