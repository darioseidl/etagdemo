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
        ResponseEntity<Child> responseEntity =
                restTemplate.exchange("http://localhost:" + port + "/children/1/", HttpMethod.PATCH, headers(body().toString()), Child.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void patchChildWithProjectionTest() {
        ResponseEntity<Child> responseEntity =
                restTemplate.exchange("http://localhost:" + port + "/children/1/?projection=with-parent", HttpMethod.PATCH, headers(body().toString()), Child.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    public JSONObject body() {
        JSONObject body = new JSONObject();
        body.put("name", "bert");
        return body;
    }

    public HttpEntity headers(String body) {
        List<MediaType> accept = new ArrayList<>();
        accept.add(MediaType.APPLICATION_JSON_UTF8);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.setAccept(accept);

        return new HttpEntity<>(body, headers);
    }
}
