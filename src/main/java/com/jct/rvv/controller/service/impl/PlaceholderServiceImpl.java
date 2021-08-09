package com.jct.rvv.controller.service.impl;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import javax.ws.rs.core.HttpHeaders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.jct.rvv.client.PlaceholderClient;
import com.jct.rvv.controller.service.PlaceholderService;
import com.jct.rvv.dto.CommentsDto;
import com.jct.rvv.dto.PostCreateDto;
import com.jct.rvv.dto.PostDto;
import com.jct.rvv.utils.UtilJson;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PlaceholderServiceImpl implements PlaceholderService {

	@Value("${placeholder.json.filename}")
	private String filenameJSON;

	@Value("${placeholder.xml.filename}")
	private String filenameXML;

	@Autowired
	PlaceholderClient phClient;

	@Override
	public List<PostDto> getPosts() {
		return phClient.getPosts();
	}

	@Override
	public List<CommentsDto> getCommentsByPostId(Integer postId) {
		return phClient.getCommentsByPostId(postId);
	}

	@Override
	public PostDto createPost(PostCreateDto dto) {
		return phClient.createPost(dto);
	}

	@Override
	public Boolean deletePost(Integer postId) {
		PostDto response = phClient.deletePost(postId);

		return (response == null || (response.getId() == null && response.getUserId() == null
				&& response.getTitle() == null && response.getBody() == null));
	}

	@Override
	public ResponseEntity<Resource> getPostsInJSONFile() {
		List<PostDto> postsList = this.getPosts();

		try {
			String postsStr = UtilJson.mapperToJsonFormatted(postsList);

			Resource resource = new ByteArrayResource(postsStr.getBytes());

			return ResponseEntity.ok().contentLength(resource.contentLength())
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + filenameJSON)
					.contentType(MediaType.APPLICATION_JSON).body(resource);
		} catch (IOException e) {
			log.error(e.getMessage());
		}

		return null;
	}

	@Override
	public ResponseEntity<Resource> getPostsInXMLFile() {
		List<PostDto> postsList = this.getPosts();

		try {
			XmlMapper mapper = new XmlMapper();
			mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
			StringWriter sw = new StringWriter();

			mapper.writeValue(sw, postsList);

			Resource resource = new ByteArrayResource(sw.toString().getBytes());

			return ResponseEntity.ok().contentLength(resource.contentLength())
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + filenameXML)
					.contentType(MediaType.APPLICATION_XML).body(resource);
		} catch (IOException e) {
			log.error(e.getMessage());
		}

		return null;
	}

}
