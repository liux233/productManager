package com.primeton.ranxing.productapi.web;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.primeton.ranxing.productapi.entity.Product;
import com.primeton.ranxing.productapi.server.IProduct;

@RestController
@RequestMapping(value = "/api")
public class PController {

	@Autowired
	IProduct productServer;

	@RequestMapping(value = "/products/{pID}", method = RequestMethod.POST)
	String createProduct(@PathVariable("pID") Long pID, @RequestBody Product product) {
		JSONObject json = new JSONObject();

		if (productServer.addProduct() == 1) {
			return JSON.toJSONString(productServer.findOne(pID));
		} else {
			json.put("error", "create a product error");
			return json.toJSONString();
		}
	}

	@RequestMapping(value = "/products", method = RequestMethod.GET)
	Object getProducts(@RequestParam(value = "product_name", required = false) String name,
			@RequestParam(value = "product_id", required = false) Long pid) {
		JSONObject json = new JSONObject();
		json.put("status", HttpStatus.SC_OK);
		json.put("message", "ok");
		json.put("result", productServer.findAllProduct());
		return json.toJSONString();
	}

	@RequestMapping(value = "/products", method = RequestMethod.GET, params = { "product_id" })
	Object getProductById(@RequestParam("product_id") Long id) {
		JSONObject json = new JSONObject();
		json.put("status", HttpStatus.SC_OK);
		json.put("message", "ok");
		json.put("result", productServer.findOne(id));
		return json.toJSONString();
	}

	@RequestMapping(value = "/products", method = RequestMethod.GET, params = { "product_name" })
	Object getProductByName(@RequestParam("product_name") String name) {
		JSONObject json = new JSONObject();
		json.put("status", HttpStatus.SC_OK);
		json.put("message", "ok");
		json.put("result", productServer.getProductByName(name));
		return json.toJSONString();
	}

	@RequestMapping(value = "/products/{id}", method = RequestMethod.GET)
	String findById(@PathVariable("id") Long id) {
		JSONObject json = new JSONObject();
		json.put("status", HttpStatus.SC_OK);
		json.put("message", "ok");
		json.put("result", productServer.findOne(id));
		return json.toJSONString();
	}

	@RequestMapping(value = "/products/{id}", method = RequestMethod.DELETE)
	String deleteProduct(@PathVariable("id") long id) {
		productServer.delete(id);
		return "";
	}
}
