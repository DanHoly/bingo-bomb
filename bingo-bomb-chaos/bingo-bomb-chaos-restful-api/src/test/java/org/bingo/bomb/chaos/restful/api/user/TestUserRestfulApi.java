package org.bingo.bomb.chaos.restful.api.user;

import org.bingo.bomb.chaos.restful.api.ConfigMainTest;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public class TestUserRestfulApi extends ConfigMainTest {
	@Test
	public void testfindByUserId() throws Exception {
		MvcResult andReturn = this.mockMvc
				.perform((MockMvcRequestBuilders.post("/userRest/findByUserId/27613346230685810337309166528"))
				.accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
				.andReturn();
		if (andReturn.getResponse().getStatus() == 200) {
			String contentAsString = andReturn.getResponse().getContentAsString();
			logger.info("请求成功,返回结果:" + contentAsString);
		}
	}

}