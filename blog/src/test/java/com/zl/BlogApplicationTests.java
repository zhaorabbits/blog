package com.zl;

import com.zl.mapper.BlogMapper;
import com.zl.mapper.BlogTagMapper;
import com.zl.mapper.TagMapper;
import com.zl.pojo.*;
import com.zl.service.BlogService;
import com.zl.service.CommentService;
import com.zl.service.TagService;
import com.zl.service.TypeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

@SpringBootTest
class BlogApplicationTests {


	@Autowired
	private CommentService commentService;

	@Autowired
	private BlogMapper blogMapper;
	@Test
	void contextLoads() {
		List<String> groupYear = blogMapper.findGroupYear();
		System.out.println(groupYear);
	}


}
