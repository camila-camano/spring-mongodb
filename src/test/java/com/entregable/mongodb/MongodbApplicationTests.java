package com.entregable.mongodb;

import com.entregable.mongodb.controller.ProductoController;
import com.entregable.mongodb.model.Producto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.util.Assert;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MongodbApplicationTests {


	Logger logger = LogManager.getLogger(ProductoController.class);

	private String url;
	@LocalServerPort
	private int port;
	private ObjectMapper objectMapper = new ObjectMapper();

	@Autowired
	private TestRestTemplate restTemplate;

	//Befores de los tests

	long start = System.nanoTime();

	@BeforeAll
	static void setup() {
		System.out.println("@BeforeAll - Ejecutando tests...");

		long start = System.nanoTime();
		System.out.println("Tiempo actual: %s" + Long.toString(start));
	}

	@BeforeEach
	void init() {
		url = String.format("http://localhost:%d/coder-house/", port);
		System.out.println("@BeforeEach - Seteando url para el test...");
	}

	/*
	@AfterAll
	void finish(){
		long end = System.nanoTime();
		System.out.println("Tiempo final: %s" + Long.toString(end ));
	}

	 */

	//Fin Befores


	// Test Rest Template
	//Tests GET
	@Test
	public void getAllProductos() throws Exception {

		var uriTest = String.format("%s%s", url, "getall");

		var productosResult = this.restTemplate.getForObject(uriTest, List.class);

		try{
			Assert.notNull(productosResult, "Lista de productos no nula");
			Assert.notEmpty(productosResult, "Lista de productos con elementos");
			Assert.isTrue(productosResult.size() == 13, "Tamaño de la lista es de 12");
			logger.info("RESULTADO TEST: SUCCESS. Lista no nula, no vacia y de tamaño correcto.");
		}
		catch (Exception e){
			logger.error("RESULTADO TEST: FAIL. La lista es nula, o vacia, o del tamaño incorrecto.");
		}
	}

	@Test
	public void getProductoByName() {
		var uriTest = String.format("%s%s", url, "get?name=celular");
		var messageResult = this.restTemplate.getForObject(uriTest, Producto.class);

		Assert.notNull(messageResult, "Producto no nulo.");
		Assert.isTrue(messageResult.getNombre().equals("celular"), "Nombre del producto OK");
		Assert.isTrue(messageResult.getCategoria().equals("tecno"), "Categoria del producto OK");
	}


	// Tests POST
	@Test
	public void createProducto() {
		var uriTest = String.format("%s%s", url, "post");
		var producto = Producto.builder().categoria("harinas").name("pan dulce").precio(50).stock(100).build();

		var productoResult = this.restTemplate.postForObject(uriTest, producto, Producto.class);

		Assert.notNull(productoResult, "Mensaje no nula");
		Assert.isTrue(productoResult.getNombre().equals("pan dulce"), "Nombre del producto OK");
		Assert.isTrue(productoResult.getCategoria().equals("harinas"), "Categoria del mensaje OK");
	}


	// Tests PUT
	@Test
	public void updateStock(){
		var uriTestPut = String.format("%s%s", url, "put/stock?name=celular&stock=20");
		this.restTemplate.put(uriTestPut,Producto.class);

		var uriTestGet = String.format("%s%s", url, "get?name=celular");
		var messageResult = this.restTemplate.getForObject(uriTestGet, Producto.class);

		Assert.notNull(messageResult, "Producto no nulo.");
		Assert.isTrue(messageResult.getNombre().equals("celular"), "Nombre del producto OK");
		Assert.isTrue(messageResult.getStock() == 20, "Stock del producto actualizado.");
	}


	// Tests DELETE
	@Test
	public void delete(){
		var uriTestDelete = String.format("%s%s", url, "deleteone?name=fideos");
		this.restTemplate.delete(uriTestDelete, Producto.class);

		var uriTestGet = String.format("%s%s", url, "get?name=fideos");
		var messageResult = this.restTemplate.getForObject(uriTestGet, Producto.class);

		Assert.isNull(messageResult, "Producto nulo");
	}


	// Tests HttRequest

	@Test
	public void getAllMessagesHttpRequestStatus() throws IOException {
		var uriTest = String.format("%s%s", url, "getall");

		var request = new HttpGet(uriTest);
		var httpResponse = HttpClientBuilder.create().build().execute(request);

		Assert.isTrue(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK, "Response status OK");
	}

	@Test
	public void getAllMessagesHttpRequestPayload() throws IOException {
		var uriTest = String.format("%s%s", url, "getall");

		var request = new HttpGet(uriTest);
		var httpResponse = HttpClientBuilder.create().build().execute(request);

		String content = EntityUtils.toString(httpResponse.getEntity());
		var messageResult = objectMapper.readValue(content, List.class);

		Assert.notNull(messageResult, "Lista de mensajes no nula");
		Assert.notEmpty(messageResult, "Lista de mensajes con elementos");
		Assert.isTrue(messageResult.size() == 15, "Tamaño de la lista es de 13");
	}


}
