package com.dbtest.assignment.trade;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
class TradeApplicationTests {

	@Test
	void contextLoads() {
	}
	
	
  @Before
    public void setup() {
	  
    }

    @Test
    public void firstTest() throws Exception {
    	System.out.println("First Junit");
    	assertTrue(true);
    }
 

}
