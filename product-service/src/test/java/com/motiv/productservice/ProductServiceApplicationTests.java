package com.motiv.productservice;

import com.motiv.productservice.dto.ProductRequest;
import com.motiv.productservice.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.math.BigDecimal;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class ProductServiceApplicationTests {

	private MockMvc mockMvc;
	private ObjectMapper objectMapper;
	private ProductRepository productRepository;

	@Autowired
	public ProductServiceApplicationTests(MockMvc mockMvc, ObjectMapper objectMapper,
			ProductRepository productRepository) {
		this.mockMvc = mockMvc;
		this.objectMapper = objectMapper;
		this.productRepository = productRepository;
	}

	@Container
	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:6.0.2");

	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry dymDynamicPropertyRegistry) {
		dymDynamicPropertyRegistry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
	}

	@DisplayName("POST /api/product")
	@Test
	void shouldCreateProduct() throws Exception {
		ProductRequest productRequest = getProductRequest();
		String productRequestString = objectMapper.writeValueAsString(productRequest);
		mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
				.contentType(MediaType.APPLICATION_JSON)
				.content(productRequestString))
				.andExpect(status().isCreated());

		// assertTrue(productRepository.findAll().size() == 1);
		assertEquals(1, productRepository.findAll().size());
	}


//	@DisplayName("GET /api/product")
//	@Test
//	void shouldGetProduct() throws Exception {
//		ProductRequest productRequest = getProductRequest();
//		String productRequestString = objectMapper.writeValueAsString(productRequest);
//		mockMvc.perform(MockMvcRequestBuilders.get("/api/product")
//						.contentType(MediaType.APPLICATION_JSON)
//						.content(productRequestString))
//				.andExpect(status().isCreated());
//
//		assertEquals(1, productRepository.findAll().size());
//	}


	private ProductRequest getProductRequest() {
		return ProductRequest.builder()
				.name("iphone 13")
				.description("iphone is good")
				.price(BigDecimal.valueOf(1200))
				.build();
	}
}
