package com.shoppingwithme.hoangkimshop.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.shoppingwithme.hoangkimshop.common.ImageInfo;
import com.shoppingwithme.hoangkimshop.dto.request.FormProduct;
import com.shoppingwithme.hoangkimshop.dto.response.ProductDTOResponse;
import com.shoppingwithme.hoangkimshop.entity.ProductCategoryEntity;
import com.shoppingwithme.hoangkimshop.entity.ProductEntity;
import com.shoppingwithme.hoangkimshop.entity.SupplierEntity;
import com.shoppingwithme.hoangkimshop.service.serviceImpl.FilesStorageServiceImpl;
import com.shoppingwithme.hoangkimshop.service.serviceImpl.ProductCategoryServiceImpl;
import com.shoppingwithme.hoangkimshop.service.serviceImpl.ProductServiceImpl;
import com.shoppingwithme.hoangkimshop.service.serviceImpl.ProductSizeServiceImpl;
import com.shoppingwithme.hoangkimshop.service.serviceImpl.SizeServiceImpl;
import com.shoppingwithme.hoangkimshop.service.serviceImpl.SupplierServiceImpl;


@Controller
public class PageController {

	@Autowired
	private SizeServiceImpl sizeSevice;
	
	@Autowired
	private SupplierServiceImpl supplierService;
	
	@Autowired
	private ProductCategoryServiceImpl categoryService;
	
	@Autowired
	private ProductServiceImpl productService;
	
	@Autowired
	private ProductSizeServiceImpl productsizeService;
	
	@Autowired
	private FilesStorageServiceImpl uploadService;
	
	@GetMapping("/images1/{filename:.+}")
	  public ResponseEntity<Resource> getImage123(@PathVariable String filename) {
	    Resource file = uploadService.load(filename);

	    return ResponseEntity.ok()
	        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
	  }
	
	@GetMapping("/admin/suppliers")
	public String getPageSupplier() {
		return "admin/ManageSupplier";
	}
	
	@GetMapping("/admin/products/form")
	public String getPageAddProduct(Model model) {
		
		
		model.addAttribute("listSize", sizeSevice.loadAllSize());
		
		model.addAttribute("listSupplier", supplierService.listAllSupplierEntity());
		model.addAttribute("listCategory", categoryService.getAllCategory());
	
		model.addAttribute("product", new FormProduct());
		return "admin/form_add_product_last_version";
	}
	
	@GetMapping("/admin/products/list")
	public String getListProduct(Model model, @RequestParam(required = false) String keyword,
		 @RequestParam(defaultValue = "1") int page,
	     @RequestParam(defaultValue = "6") int size,
	     @RequestParam(defaultValue = "id,desc") String[] sort
	)
	{
		Long category_id=Long.parseLong("2");
		System.out.println("So luong product co ma id duoc truyen vo: "+productService.countByCategory_id(category_id));
		List<ProductEntity> products = new ArrayList<ProductEntity>();
		String sortField = sort[0];
		System.out.println("I need to known file: "+sort[0]);
	    String sortDirection = sort[1];
	    System.out.println("I need to known sort: "+sort[1]);
	    Direction direction = sortDirection.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
	    Order order = new Order(direction, sortField);
	    Pageable pageable = PageRequest.of(page - 1, size, Sort.by(order));
	    Page<ProductEntity> pageTuts;
	    if (keyword == null) {
	        pageTuts = productService.findAll(pageable);
	      } else {
	        pageTuts = productService.findByNameContainingIgnoreCase(keyword, pageable);
	        model.addAttribute("keyword", keyword);
	      }

	    products = pageTuts.getContent();
	    
	    int sumQuantityByProductId=0;
	    int sumQuantitySoldeByProductId;
	    List<ProductDTOResponse> productsResponse = new ArrayList<ProductDTOResponse>();
	    for(int i=0;i<products.size();i++) {
	    	
	    	int productId=products.get(i).getId();
	    	
	    	ProductDTOResponse product= new ProductDTOResponse();
	    	ProductCategoryEntity category=categoryService.getCategorybyId(products.get(i).getCategory_id()).get();
	    	product.setName(products.get(i).getName());
	    	product.setOriganalprice(products.get(i).getOriganalprice());
	    	product.setPrice(products.get(i).getPrice());
	    	product.setCategory(category.getName());
	    	product.setId(productId);
	    	
	    	SupplierEntity supplier=supplierService.getSupplierById(products.get(i).getSupplier_id()).get();
	    	
	    	sumQuantityByProductId=productsizeService.getQuantityByProduct(productId);
	    	product.setSumQuantityByProductId(sumQuantityByProductId);
	    	
	    	sumQuantitySoldeByProductId=productsizeService.getQuantitySoldByProduct(productId);
	    	product.setSumQuantitySoldeByProductId(sumQuantitySoldeByProductId);
	    	product.setSupplier(supplier.getName());
	    	productsResponse.add(product);
	    	
	    }
	      model.addAttribute("products", productsResponse);
	      model.addAttribute("currentPage", pageTuts.getNumber() + 1);
	      model.addAttribute("totalItems", pageTuts.getTotalElements());
	      model.addAttribute("totalPages", pageTuts.getTotalPages());
	      model.addAttribute("pageSize", size);
	      model.addAttribute("sortField", sortField);
	      model.addAttribute("sortDirection", sortDirection);
	      model.addAttribute("reverseSortDirection", sortDirection.equals("asc") ? "asc" : "desc");
		return "admin/list_product";
	}
	
	@GetMapping("/admin/supplier/{id}")
	public String getSupplierById(@PathVariable(name = "id" )Long id, Model model) {
		SupplierEntity entity=supplierService.getSupplierById(id).get();
		
		List<ImageInfo> listImgSupplier = uploadService.loadAll().map(path -> {
		      String filename = path.getFileName().toString();
		      String url = MvcUriComponentsBuilder
		          .fromMethodName(SupplierController.class, "getImage", path.getFileName().toString()).build().toString();

		      return new ImageInfo(filename, url);
		    }).collect(Collectors.toList());
		
		ImageInfo img=new ImageInfo(entity.getLogo(), entity.getUrl());
		if(listImgSupplier.indexOf(img)!=  -1) {
			int index=listImgSupplier.indexOf(img);
			entity.setUrl(listImgSupplier.get(index).getUrl());
		}
		model.addAttribute("supplier", entity);
		System.out.println("Thong tin ve nha san xuat update la: "+entity.getName()+", url image: "+entity.getUrl());
		return "admin/update_supplier";
	}
	
	
	
	
}
