package com.ajay.spring.demo.actuator;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/*
We can send this User status ( active, inactive) to info end point
Run the app and access - http://localhost:8080/actuator/info
 */
@Component
public class UserStatusInfo implements InfoContributor {
    @Override
    public void contribute(Info.Builder builder) {

        Map<String, Integer> map = new HashMap<>();
        map.put("active", 5);
        map.put("inactive", 2);

        builder.withDetail("userStatus", map);
    }
}
