package com.huishu.ait.test;

import javax.annotation.Resource;

import org.elasticsearch.client.Client;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.alibaba.fastjson.JSONArray;
import com.huishu.ait.app.Application;
import com.huishu.ait.entity.dto.CompanyDTO;
import com.huishu.ait.service.company.impl.CompanyServiceImpl;

//这是JUnit的注解，通过这个注解让SpringJUnit4ClassRunner这个类提供Spring测试上下文。  
@RunWith(SpringJUnit4ClassRunner.class)  
//这是Spring Boot注解，为了进行集成测试，需要通过这个注解加载和配置Spring应用上下  
@SpringBootTest(classes = Application.class)  
@WebAppConfiguration 
public class CompanyTest {
	/***/
	@Resource
	private Client client;
	@Resource
	private CompanyServiceImpl impl;

	@Test
	public void testFindCompaniesOder() {
		CompanyDTO dto = new CompanyDTO();
		dto.setIndustry("高科技");
		dto.setIndustryLabel("网络游戏");
		JSONArray findCompaniesOder = impl.findCompaniesOder(dto);
		System.out.println(findCompaniesOder.toJSONString());
	}
	

}
