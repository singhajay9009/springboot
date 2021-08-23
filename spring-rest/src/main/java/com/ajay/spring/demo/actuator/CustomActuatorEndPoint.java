package com.ajay.spring.demo.actuator;


import org.springframework.boot.actuate.endpoint.annotation.DeleteOperation;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Endpoint(id="custom")
public class CustomActuatorEndPoint {

    Map<String, List<String>> releaseNotes = new HashMap<>();

    // it will be built just after bean is initialized
    @PostConstruct
    public void init(){
        releaseNotes.put("version-1.0", Arrays.asList("Initial Draft", "New App 1.0"));
        releaseNotes.put("version-1.1", Arrays.asList("New Features", "Email feature"));
    }
    @ReadOperation
    public Map<String, List<String>> customEndPoint(){
        return  releaseNotes;
    }

    // to filter for version // append /version-1.0 in the end point in browser and see
    // only that release note will be displayed
    @ReadOperation
    public List<String> getNotesByVersion(@Selector String version){
        return  releaseNotes.get(version);
    }

    @DeleteOperation
    public List<String> deleteNotesByVersion(@Selector String version){
        return  releaseNotes.remove(version);
    }
}
