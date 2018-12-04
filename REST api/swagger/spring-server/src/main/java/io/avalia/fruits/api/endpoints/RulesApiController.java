package io.avalia.fruits.api.endpoints;

import io.avalia.fruits.api.RulesApi;
import io.avalia.fruits.api.model.AppId;
import io.avalia.fruits.api.model.Infos;
import io.avalia.fruits.api.model.Rule;
import io.avalia.fruits.entities.RuleEntity;
import io.avalia.fruits.repositories.RuleRepository;
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
public class RulesApiController implements RulesApi {

    @Autowired
    io.avalia.fruits.repositories.RuleRepository RuleRepository;

    @Override
    public ResponseEntity<Object> createRule(@ApiParam(value = "", required = true) @Valid @RequestBody Rule Rule) {
        RuleEntity newRuleEntity = toRuleEntity(Rule);
        RuleRepository.save(newRuleEntity);
        Long id = newRuleEntity.getId();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(newRuleEntity.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @Override
    public ResponseEntity<List<Rule>> getRules(AppId infos) {
        List<Rule> Rules = new ArrayList<>();
        List<String> ids = new ArrayList<>();
        ids.add(infos.getApiToken());
        for (RuleEntity RuleEntity : RuleRepository.findAll(ids)) {
            Rules.add(toRule(RuleEntity));
        }
        return ResponseEntity.ok(Rules);
    }


    private RuleEntity toRuleEntity(Rule Rule) {
        RuleEntity entity = new RuleEntity();
        entity.setColour(Rule.getColour());
        entity.setKind(Rule.getKind());
        entity.setSize(Rule.getSize());
        return entity;
    }

    private Rule toRule(RuleEntity entity) {
        Rule Rule = new Rule();
        Rule.setColour(entity.getColour());
        Rule.setKind(entity.getKind());
        Rule.setSize(entity.getSize());
        return Rule;
    }

}
