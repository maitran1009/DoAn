package com.pizza.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.pizza.model.input.ProductInput;
import com.pizza.model.output.ProductOutput;
import com.pizza.service.ProductService;

@Controller
@RequestMapping("/admin/product")
public class ProductController {
	@Autowired
	private ProductService productService;

	
	@GetMapping("list")
	public String productList(Model model) {
		return productService.productList(model);
	}

	@GetMapping("info")
	@ResponseBody
	public ProductOutput getProductInfo(@RequestParam int id) {
		return productService.getProductInfo(id);
	}

	@PostMapping(path = "create", produces = "application/json")
	@ResponseBody // Khi muốn trả về 1 object
	public boolean createProduct(@RequestBody ProductInput product) {
		return productService.create(product);
	}

	@GetMapping(path = "delete")
	@ResponseBody
	public boolean deleteProduct(@RequestParam int id) {
		return productService.deleteProduct(id);
	}

	@PostMapping("upload-file")
	@ResponseBody
	public void uploadFile(@ModelAttribute MultipartFile image) throws FileNotFoundException, IOException {
		File convFile = new File("src/main/resources/static/images/" + image.getOriginalFilename());
		if (convFile.createNewFile()) {
			FileOutputStream fos = new FileOutputStream(convFile);
			fos.write(image.getBytes());
			fos.close();
		}
	}
}
