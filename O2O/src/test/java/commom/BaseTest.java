package commom;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 配置spring 和 junit整合
 * @author stephen
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring的配置文件的路径
@ContextConfiguration({"classpath:spring/spring-dao.xml","classpath:spring/spring-service.xml"})
public class BaseTest {

}
