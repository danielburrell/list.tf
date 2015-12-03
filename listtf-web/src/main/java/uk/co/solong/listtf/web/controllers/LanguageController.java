package uk.co.solong.listtf.web.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import uk.co.solong.listtf.core.pojo.language.ButtonValue;

import com.fasterxml.jackson.databind.JsonNode;

@RestController
@RequestMapping("/api")
public class LanguageController {
/*
    @RequestMapping("/addWantedDetail")
    public @ResponseBody JsonNode getButtonMappings()  {
        Map<String,List<ButtonValue>> key = new HashMap<>();
        ButtonValue tradable = new ButtonValue(0,"Tradable");
        ButtonValue untradable = new ButtonValue(1,"Untradable");
        List<ButtonValue> trade = new ArrayList<>();
        key.put("trade", trade);
        List<ButtonValue> craft = new ArrayList<>();
        key.put("craft", craft);
        List<ButtonValue> gift = new ArrayList<>();
        key.put("craft", craft);
    }*/
}

