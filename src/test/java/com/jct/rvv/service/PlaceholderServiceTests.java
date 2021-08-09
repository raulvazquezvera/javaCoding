package com.jct.rvv.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jct.rvv.constants.Constants;
import com.jct.rvv.dto.CommentsDto;
import com.jct.rvv.dto.PostCreateDto;
import com.jct.rvv.dto.PostDto;
import com.jct.rvv.utils.UtilJson;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PlaceholderServiceTests {

	private static final String OK = "200 OK";

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate template;

	private URL url_get_posts;
	private URL url_get_comments_by_postid;
	private URL url_create_post;
	private URL url_delete_post;
	private URL url_get_posts_in_jsonfile;
	private URL url_get_posts_in_xmlfile;

	@Before
	public void setUp() throws MalformedURLException {
		String url = "http://localhost:" + String.valueOf(port);
		this.url_get_posts = new URL(url + Constants.URL_GET_POSTS);
		this.url_get_comments_by_postid = new URL(url + Constants.URL_GET_COMMENTS_BY_POSTID);
		this.url_create_post = new URL(url + Constants.URL_CREATE_POST);
		this.url_delete_post = new URL(url + Constants.URL_DELETE_POST);
		this.url_get_posts_in_jsonfile = new URL(url + Constants.URL_GET_POSTS_IN_JSON_FILE);
		this.url_get_posts_in_xmlfile = new URL(url + Constants.URL_GET_POSTS_IN_XML_FILE);
	}

	@Test
	public void getPostsTest() {
		ResponseEntity<List<PostDto>> response = template.exchange(this.url_get_posts.toString(), HttpMethod.GET,
				getHttpEntity(null), new ParameterizedTypeReference<List<PostDto>>() {
				});

		assertEquals(OK, response.getStatusCode().toString());
		assertNotNull(response.getBody());
		assertFalse(response.getBody().isEmpty());
		assertTrue(response.getBody().size() > 0);
	}

	@Test
	public void getCommentsByPostIdTest() {
		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(this.url_get_comments_by_postid.toString())
				.queryParam("postId", 2);

		ResponseEntity<List<CommentsDto>> response = template.exchange(builder.toUriString(), HttpMethod.GET,
				getHttpEntity(null), new ParameterizedTypeReference<List<CommentsDto>>() {
				});

		assertEquals(OK, response.getStatusCode().toString());
		assertNotNull(response.getBody());
		assertFalse(response.getBody().isEmpty());
		assertTrue(response.getBody().size() > 0);
	}

	@Test
	public void createPostTest() throws JsonProcessingException {
		String json = UtilJson
				.mapperToJson(PostCreateDto.builder().userId(2).title("title test").body("body test").build());

		ResponseEntity<PostDto> response = template.exchange(this.url_create_post.toString(), HttpMethod.POST,
				getHttpEntity(json), new ParameterizedTypeReference<PostDto>() {
				});

		assertEquals(OK, response.getStatusCode().toString());
		assertNotNull(response.getBody());
	}

	@Test
	public void deletePostTest() {
		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(this.url_delete_post.toString())
				.queryParam("postId", 2);

		ResponseEntity<Boolean> response = template.exchange(builder.toUriString(), HttpMethod.DELETE,
				getHttpEntity(null), new ParameterizedTypeReference<Boolean>() {
				});

		assertEquals(OK, response.getStatusCode().toString());
		assertTrue(response.getBody());
	}

	@Test
	public void getPostsInJSONFileTest() {
		ResponseEntity<Resource> response = template.exchange(this.url_get_posts_in_jsonfile.toString(), HttpMethod.GET,
				getHttpEntity(null), new ParameterizedTypeReference<Resource>() {
				});

		assertEquals(OK, response.getStatusCode().toString());
		assertNotNull(response.getBody());
	}

	@Test
	public void getPostsInXMLFileTest() {
		ResponseEntity<Resource> response = template.exchange(this.url_get_posts_in_xmlfile.toString(), HttpMethod.GET,
				getHttpEntity(null), new ParameterizedTypeReference<Resource>() {
				});

		assertEquals(OK, response.getStatusCode().toString());
		assertNotNull(response.getBody());
	}

	private HttpEntity<Object> getHttpEntity(String str) {
		List<Charset> acceptCharset = new ArrayList<>();
		acceptCharset.add(StandardCharsets.UTF_8);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAcceptCharset(acceptCharset);

		HttpEntity<Object> entity = new HttpEntity<>(str, headers);

		return entity;
	}

}
