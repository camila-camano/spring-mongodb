package com.entregable.mongodb;

import com.entregable.mongodb.controller.ProductoController;
import com.entregable.mongodb.model.Producto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.util.Assert;

import java.util.List;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MongodbApplicationTests {


	Logger logger = LogManager.getLogger(ProductoController.class);

	private String url;
	@LocalServerPort
	private int port;
	private ObjectMapper objectMapper = new ObjectMapper();

	@Autowired
	private TestRestTemplate restTemplate;

	//Before y After de los tests
	@BeforeAll
	static void setup() {
		System.out.println("@BeforeAll - Ejecutando tests...");
	}

	@BeforeEach
	void init() {
		url = String.format("http://localhost:%d/coder-house/", port);
		System.out.println("@BeforeEach - Seteando url para el test...");
	}
	//Fin Befores


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
		var uriTest = String.format("%s%s", url, "get?name=fideos");
		var messageResult = this.restTemplate.getForObject(uriTest, Producto.class);

		Assert.notNull(messageResult, "Producto no nulo.");
		Assert.isTrue(messageResult.getNombre().equals("fideos"), "Nombre del producto OK");
		Assert.isTrue(messageResult.getCategoria().equals("harinas"), "Categoria del producto OK");
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
		var uriTestPut = String.format("%s%s", url, "put/stock?name=fideos&stock=20");
		this.restTemplate.put(uriTestPut,Producto.class);

		var uriTestGet = String.format("%s%s", url, "get?name=fideos");
		var messageResult = this.restTemplate.getForObject(uriTestGet, Producto.class);

		Assert.notNull(messageResult, "Producto no nulo.");
		Assert.isTrue(messageResult.getNombre().equals("fideos"), "Nombre del producto OK");
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



}
