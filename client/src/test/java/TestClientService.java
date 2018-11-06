import com.gsw.easyrpc.common.annotations.EasyRpcClient;
import org.springframework.stereotype.Service;

@Service
public class TestClientService {

    @EasyRpcClient(remoteServerName = "easy-rpc-server-example")
    private TestClientInterface testClientInterface;


    public void testPrint() {
        testClientInterface.aaa();
    }
}
