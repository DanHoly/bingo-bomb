package org.bingo.bomb.chaos.bootstrap;

import javax.annotation.Resource;

import org.bingo.bomb.commons.fix.SpringContextHolder;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 用于测试
 * @author jiangchangcheng  
 * @date 2017年8月15日 下午3:16:46
 * @since JDK 1.7
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:/spring/spring-bootstrap.xml")
public abstract class ConfigMainTest {

	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Resource(name = "springContextHolder")
	protected SpringContextHolder springContextHolder;

	public void setSpringContextHolder(SpringContextHolder springContextHolder) {
		this.springContextHolder = springContextHolder;
	}

}
