package com.jct.rvv.controller;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jct.rvv.constants.Constants;
import com.jct.rvv.controller.service.PlaceholderService;
import com.jct.rvv.dto.CommentsDto;
import com.jct.rvv.dto.PostCreateDto;
import com.jct.rvv.dto.PostDto;
import com.jct.rvv.utils.UtilJson;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WebAppConfiguration
public class PlaceholderControllerTests {

	@Autowired
	private MockMvc mockMvc;

	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	@Autowired
	private WebApplicationContext webApplicationContext;

	@MockBean
	private PlaceholderService phService;

	@Before
	public void setup() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void testGetPosts() {
		List<PostDto> list = new ArrayList<>();

		list.add(PostDto.builder().id(1).userId(1).title("title test").body("body test").build());

		when(phService.getPosts()).thenReturn(list);

		try {
			this.mockMvc.perform(get(Constants.URL_GET_POSTS)).andDo(print()).andExpect(status().isOk())
					.andExpect(jsonPath("$", not(nullValue())));
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testGetCommentsByPostId() {
		List<CommentsDto> list = new ArrayList<>();

		list.add(CommentsDto.builder().id(1).postId(1).name("name test").email("email test").body("body test").build());

		when(phService.getCommentsByPostId(any(Integer.class))).thenReturn(list);

		try {
			this.mockMvc.perform(get(Constants.URL_GET_COMMENTS_BY_POSTID, any(Integer.class))).andDo(print())
					.andExpect(status().isOk()).andExpect(jsonPath("$", not(nullValue())));
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testCreatePost() {
		PostDto post = PostDto.builder().id(1).userId(1).title("title test").body("body test").build();

		when(phService.createPost(any(PostCreateDto.class))).thenReturn(post);

		try {
			PostCreateDto dto = PostCreateDto.builder().userId(1).title("title test").body("body test").build();

			this.mockMvc
					.perform(post(Constants.URL_CREATE_POST).contentType(contentType)
							.content(UtilJson.mapperToJson(dto)))
					.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$", not(nullValue())));
		} catch (JsonProcessingException e) {
			fail(e.getMessage());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testDeletePost() {
		when(phService.deletePost(any(Integer.class))).thenReturn(true);

		try {
			this.mockMvc.perform(delete(Constants.URL_DELETE_POST, any(Integer.class))).andDo(print())
					.andExpect(status().isOk()).andExpect(jsonPath("$", not(nullValue())));
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testGetPostsInJSONFile() throws JsonProcessingException {
		List<PostDto> list = new ArrayList<>();
		list.add(PostDto.builder().id(1).userId(1).title("title test").body("body test").build());

		String json = UtilJson.mapperToJson(list);

		Resource resource = new ByteArrayResource(json.getBytes());

		ResponseEntity<Resource> response = ResponseEntity.ok().body(resource);

		when(phService.getPostsInJSONFile()).thenReturn(response);

		try {
			this.mockMvc.perform(get(Constants.URL_GET_POSTS_IN_JSON_FILE)).andDo(print()).andExpect(status().isOk())
					.andExpect(jsonPath("$", not(nullValue())));
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testGetPostsInXMLFile() throws JsonProcessingException {
		List<PostDto> list = new ArrayList<>();
		list.add(PostDto.builder().id(1).userId(1).title("title test").body("body test").build());

		String json = UtilJson.mapperToJson(list);

		Resource resource = new ByteArrayResource(json.getBytes());

		ResponseEntity<Resource> response = ResponseEntity.ok().body(resource);

		when(phService.getPostsInXMLFile()).thenReturn(response);

		try {
			this.mockMvc.perform(get(Constants.URL_GET_POSTS_IN_XML_FILE)).andDo(print()).andExpect(status().isOk())
					.andExpect(jsonPath("$", not(nullValue())));
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

}
