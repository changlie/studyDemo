import com.changlie.Consumer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/consumer.xml"})
public class TestClient {


    @Autowired
    Consumer consumer;

    @Test
    public void TestOne(){
        System.out.println(consumer.name);
    }
}
