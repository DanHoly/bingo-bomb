package org.bingo.bomb.chaos.restful.api.user;

import org.bingo.bomb.chaos.restful.api.ConfigMainTest;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
/**
 * 用户restful接口测试
 * @author jiangchangcheng  
 * @date 2017年8月17日 下午1:12:30
 * @since JDK 1.7
 */
public class TestUserRestfulApi extends ConfigMainTest {
	@Test
	public void testfindByUserId() throws Exception {
		MvcResult vcResult = this.mockMvc
				.perform((MockMvcRequestBuilders.post("/userRest/findByUserId/27613346230685810337309166528"))
				.accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
				.andReturn();
		if (vcResult.getResponse().getStatus() == 200) {
			String contentAsString = vcResult.getResponse().getContentAsString();
			logger.info("请求成功,返回结果:" + contentAsString);
		}
	}

}
