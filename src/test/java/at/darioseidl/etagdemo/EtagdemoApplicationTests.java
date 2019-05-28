package at.darioseidl.etagdemo;

import net.minidev.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EtagdemoApplicationTests {

    @LocalServerPort
    private int port;

    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        restTemplate = new TestRestTemplate(new RestTemplateBuilder().requestFactory(HttpComponentsClientHttpRequestFactory.class));
    }

    @Test
    public void patchChildTest() {
        ResponseEntity<String> responseEntity =
                restTemplate.exchange("http://localhost:" + port + "/children/1/", HttpMethod.PATCH, headers(body("patch").toString()), String.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        System.out.println(responseEntity.getBody());
    }

    @Test
    public void patchChildWithProjectionTest() {
        ResponseEntity<String> responseEntity =
                restTemplate.exchange("http://localhost:" + port + "/children/1/?projection=with-parent", HttpMethod.PATCH, headers(body("patch-with-projection").toString()), String.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        System.out.println(responseEntity.getBody());
    }

    @Test
    public void putChildWithProjectionTest() {
        ResponseEntity<String> responseEntity =
                restTemplate.exchange("http://localhost:" + port + "/children/1/?projection=with-parent", HttpMethod.PUT, headers(body("put-with-projection").toString()), String.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        System.out.println(responseEntity.getBody());
    }

    @Test
    public void patchChildWithOpenProjectionTest() {
        ResponseEntity<String> responseEntity =
                restTemplate.exchange("http://localhost:" + port + "/children/1/?projection=with-value", HttpMethod.PATCH, headers(body("patch-with-projection").toString()), String.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        System.out.println(responseEntity.getBody());
    }

    @Test
    public void putChildWithOpenProjectionTest() {
        ResponseEntity<String> responseEntity =
                restTemplate.exchange("http://localhost:" + port + "/children/1/?projection=with-value", HttpMethod.PUT, headers(body("put-with-projection").toString()), String.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        System.out.println(responseEntity.getBody());
    }

    public JSONObject body(String name) {
        JSONObject body = new JSONObject();
        body.put("name", name);
        return body;
    }

    public HttpEntity headers(String body) {
        List<MediaType> accept = new ArrayList<>();
        accept.add(MediaType.APPLICATION_JSON);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(accept);

        return new HttpEntity<>(body, headers);
    }
}
