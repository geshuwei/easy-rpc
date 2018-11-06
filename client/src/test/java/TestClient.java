import com.gsw.easyrpc.client.EasyRpcClientAutowireProcessor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestClient.class, TestClientService.class, TestClient.class, EasyRpcClientAutowireProcessor.class})
public class TestClient {

    @Resource
    private TestClientService testClientService;

    @Test
    public void testClient() {
        testClientService.testPrint();
    }

}
