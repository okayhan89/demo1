package sk.demo.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RestServiceControllerTest {

	@Autowired
	private MockMvc mvc;
	
	
	@Test
	public void getRestWithGetAndGet() throws Exception {
		
		String methodVal = "get";				
		String ifVal = "getGetIF";
		String verVal = "1.0";
		String uiNameVal = "getGetUI";
		String resultVal = "0000";
		String reasonVal = "요청 성공";
		
		mvc.perform(get("/v1/rest")	
	       .param("method",methodVal)
	       .param("if", ifVal)
	       .param("ver",verVal)
	       .param("ui_name",uiNameVal)
	       .accept(MediaType.APPLICATION_JSON_UTF8))
		.andDo(print())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.if").value(ifVal))
		.andExpect(jsonPath("$.ver").value(verVal))
		.andExpect(jsonPath("$.ui_name").value(uiNameVal))
		.andExpect(jsonPath("$.result").value(resultVal))
		.andExpect(jsonPath("$.reason").value(reasonVal));	                          
			
	}
}
