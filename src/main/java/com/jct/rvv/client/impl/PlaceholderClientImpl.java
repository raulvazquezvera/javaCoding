package com.jct.rvv.client.impl;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.jct.rvv.client.PlaceholderClient;
import com.jct.rvv.dto.CommentsDto;
import com.jct.rvv.dto.PostCreateDto;
import com.jct.rvv.dto.PostDto;

@Service
public class PlaceholderClientImpl implements PlaceholderClient {

	@Value("${placeholder.url}")
	private String url;

	@Value("${placeholder.resource.posts}")
	private String posts;

	@Value("${placeholder.resource.comments}")
	private String comments;

	@Autowired
	private RestTemplate restTemplate;

	private HttpEntity<Object> getHttpEntity() {
		List<Charset> acceptCharset = new ArrayList<>();
		acceptCharset.add(StandardCharsets.UTF_8);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAcceptCharset(acceptCharset);

		HttpEntity<Object> entity = new HttpEntity<>(null, headers);

		return entity;
	}

	@Override
	public List<PostDto> getPosts() {
		StringBuilder urlSb = new StringBuilder(url).append(posts);

		ResponseEntity<List<PostDto>> response = restTemplate.exchange(urlSb.toString(), HttpMethod.GET,
				getHttpEntity(), new ParameterizedTypeReference<List<PostDto>>() {
				});

		return response.getBody();
	}

	@Override
	public List<CommentsDto> getCommentsByPostId(Integer postId) {
		StringBuilder urlSb = new StringBuilder(url).append(posts).append("/").append(postId).append(comments);

		ResponseEntity<List<CommentsDto>> response = restTemplate.exchange(urlSb.toString(), HttpMethod.GET,
				getHttpEntity(), new ParameterizedTypeReference<List<CommentsDto>>() {
				});

		return response.getBody();
	}

	@Override
	public PostDto createPost(PostCreateDto dto) {
		StringBuilder urlSb = new StringBuilder(url).append(posts);

		List<Charset> acceptCharset = new ArrayList<>();
		acceptCharset.add(StandardCharsets.UTF_8);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAcceptCharset(acceptCharset);

		HttpEntity<PostCreateDto> entity = new HttpEntity<>(dto, headers);

		ResponseEntity<PostDto> response = restTemplate.exchange(urlSb.toString(), HttpMethod.POST, entity,
				new ParameterizedTypeReference<PostDto>() {
				});

		return response.getBody();
	}

	@Override
	public PostDto deletePost(Integer postId) {
		StringBuilder urlSb = new StringBuilder(url).append(posts).append("/").append(postId);

		ResponseEntity<PostDto> response = restTemplate.exchange(urlSb.toString(), HttpMethod.DELETE, getHttpEntity(),
				new ParameterizedTypeReference<PostDto>() {
				});

		return response.getBody();
	}

}
