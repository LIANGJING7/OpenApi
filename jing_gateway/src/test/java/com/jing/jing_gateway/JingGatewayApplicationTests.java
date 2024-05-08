package com.jing.jing_gateway;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
class JingGatewayApplicationTests {
    private static final List<String> IP_WHITE_LIST = Arrays.asList("127.0.0.1");
    @Test
    void contextLoads() {
        System.out.println(IP_WHITE_LIST);
    }

}
