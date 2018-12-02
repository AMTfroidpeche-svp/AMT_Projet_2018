package io.avalia.fruits.api.endpoints;

import io.avalia.fruits.api.BadgesApi;
import io.avalia.fruits.api.model.Badge;
import io.avalia.fruits.api.model.Infos;
import io.avalia.fruits.entities.BadgeEntity;
import io.avalia.fruits.repositories.BadgeRepository;
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
public class BadgesApiController implements BadgesApi {

    @Autowired
    BadgeRepository badgeRepository;

    public ResponseEntity<Object> createBadge(@ApiParam(value = "", required = true) @Valid @RequestBody Badge badge) {
        BadgeEntity newBadgeEntity = toBadgeEntity(badge);
        badgeRepository.save(newBadgeEntity);
        String id = newBadgeEntity.getId();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(newBadgeEntity.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @Override
    public ResponseEntity<List<Badge>> getBadges(Infos infos) {
        List<Badge> badges = new ArrayList<>();
        List<String> ids = new ArrayList<>();
        ids.add(infos.getApiToken() + infos.getUserId());
        for (BadgeEntity badgeEntity : badgeRepository.findAll(ids)) {
            badges.add(toBadge(badgeEntity));
        }
        return ResponseEntity.ok(badges);
    }

    private BadgeEntity toBadgeEntity(Badge badge) {
        BadgeEntity entity = new BadgeEntity();
        entity.setApiToken(badge.getApiToken());
        entity.setName(badge.getName());
        entity.setUserId(badge.getUserId());
        return entity;
    }

    private Badge toBadge(BadgeEntity entity) {
        Badge badge = new Badge();
        badge.setApiToken(entity.getApiToken());
        badge.setName(entity.getName());
        badge.setUserId(badge.getUserId());
        return badge;
    }

}
