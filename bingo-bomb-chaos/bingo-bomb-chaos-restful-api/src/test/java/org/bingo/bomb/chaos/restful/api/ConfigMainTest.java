package org.bingo.bomb.chaos.restful.api;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * 用于测试
 * @author jiangchangcheng  
 * @date 2017年8月16日 上午9:42:13
 * @since JDK 1.7
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = { "classpath*:/spring/spring-bootstrap.xml", "/spring/classpath*:/spring-mvc.xml" })
public abstract class ConfigMainTest {

	protected final Logger logger = Logger.getLogger(getClass());

	@Autowired
	protected WebApplicationContext context;

	protected MockMvc mockMvc;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
	}

}
