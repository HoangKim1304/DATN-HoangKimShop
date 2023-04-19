package com.shoppingwithme.hoangkimshop.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shoppingwithme.hoangkimshop.common.ImageInfo;
import com.shoppingwithme.hoangkimshop.dto.request.FormProduct;
import com.shoppingwithme.hoangkimshop.dto.response.ProductResponseUpdate;
import com.shoppingwithme.hoangkimshop.dto.response.ProductSizeDtOResponse;
import com.shoppingwithme.hoangkimshop.entity.ProductEntity;
import com.shoppingwithme.hoangkimshop.entity.ProductSizeEntity;
import com.shoppingwithme.hoangkimshop.entity.SizeEntity;
import com.shoppingwithme.hoangkimshop.service.serviceImpl.FilesStorageServiceImpl;
import com.shoppingwithme.hoangkimshop.service.serviceImpl.ProductCategoryServiceImpl;
import com.shoppingwithme.hoangkimshop.service.serviceImpl.ProductServiceImpl;
import com.shoppingwithme.hoangkimshop.service.serviceImpl.ProductSizeServiceImpl;
import com.shoppingwithme.hoangkimshop.service.serviceImpl.SizeServiceImpl;
import com.shoppingwithme.hoangkimshop.service.serviceImpl.SupplierServiceImpl;

@Controller
public class ProductController {

	@Autowired
	private ProductServiceImpl productService;
	
	@Autowired
	private FilesStorageServiceImpl uploadServive;
	
	@Autowired
	private ProductSizeServiceImpl productSizeService;
	
	
	@Autowired
	private FilesStorageServiceImpl uploadService;
	
	@Autowired
	private SizeServiceImpl sizeSevice;
	
	@Autowired
	private SupplierServiceImpl supplierService;
	
	@Autowired
	private ProductCategoryServiceImpl categoryService;
	
	
	@PostMapping("/admin/product/save")
	public String saveProduct(@ModelAttribute(name = "product") FormProduct product
			,@RequestParam MultipartFile mainImg
			,@RequestParam MultipartFile img1
			,@RequestParam MultipartFile img2
			,@RequestParam MultipartFile img3
			,@RequestParam MultipartFile img4) {
		System.out.println("Product data: "+product);
		
		
		if(mainImg.isEmpty())
			return "ErrorPage";
		else {
			product.setThumbnail(mainImg.getOriginalFilename());
			uploadServive.save(mainImg);
//			System.out.println("Main img not null");
		}
		if(! img1.isEmpty() )
		{
			product.setImage1(img1.getOriginalFilename());
			uploadServive.save(img1);
//			System.out.println("Main img1 not null");
		}
		if(!img2.isEmpty())
		{
			product.setImage2(img2.getOriginalFilename());
			uploadServive.save(img2);
//			System.out.println("Main img2 not null");
		}
		if(!img3.isEmpty())
		{
			product.setImage3(img3.getOriginalFilename());
			uploadServive.save(img3);
//			System.out.println("Main img3 not null");
		}
		if(!img4.isEmpty())
		{
			product.setImage4(img4.getOriginalFilename());
			uploadServive.save(img4);
//			System.out.println("Main img4 not null");
		}
		product.setCreateDate(new Date());
		product.setModifierDate(new Date());
		product.setStatus(0);
		
		ModelMapper mapper= new ModelMapper();
		ProductEntity entity=mapper.map(product, ProductEntity.class);
		ProductEntity saveProduct=productService.saveProduct(entity);
		if(saveProduct.getId()==0) {
			return "ErrorPage";
		}else {
			int productId=saveProduct.getId();
			for(int i=0;i<product.getListSize().size();i++) {
				if(product.getListSize().get(i).getSize_id()==null) {
					continue;
				}else {
					if(product.getListSize().get(i).getQuantity()==0) {
						continue;
					}else {
						ProductSizeEntity size= new ProductSizeEntity();
						size.setProductid(productId);
						size.setQuantity(product.getListSize().get(i).getQuantity());
						size.setSize_id(product.getListSize().get(i).getSize_id());
						size.setDatesale(new Date());
						size.setquantitysold(0);
						productSizeService.saveProductSizeEntity(size);
						
					}
				}
			}
		}
		
		return "redirect:/admin/products/form";
	}
	
	
	@GetMapping("/admin/product-update/{id}")
	public String getFromUpdateProduct(@PathVariable(name = "id") int id, Model model) {
		
		ProductEntity  productEntity= productService.findById(id);
		
		List<ImageInfo> listImgSupplier = uploadService.loadAll().map(path -> {
		      String filename = path.getFileName().toString();
		      String url = MvcUriComponentsBuilder
		          .fromMethodName(SupplierController.class, "getImage", path.getFileName().toString()).build().toString();

		      return new ImageInfo(filename, url);
		    }).collect(Collectors.toList());
		
		for(int i=0;i<5;i++) {
			
			if(productEntity.getThumbnail() !=null) {
				ImageInfo img=new ImageInfo(productEntity.getThumbnail(), "");
				if(listImgSupplier.indexOf(img)!=  -1) {
					int index=listImgSupplier.indexOf(img);
					productEntity.setThumbnail(listImgSupplier.get(index).getUrl());
				}
			}
			
			if(productEntity.getImage1() !=null) {
				ImageInfo img=new ImageInfo(productEntity.getImage1(), "");
				if(listImgSupplier.indexOf(img)!=  -1) {
					int index=listImgSupplier.indexOf(img);
					productEntity.setImage1(listImgSupplier.get(index).getUrl());
				}
			}
			if(productEntity.getImage2() !=null) {
				ImageInfo img=new ImageInfo(productEntity.getImage2(), "");
				if(listImgSupplier.indexOf(img)!=  -1) {
					int index=listImgSupplier.indexOf(img);
					productEntity.setImage2(listImgSupplier.get(index).getUrl());
				}
			}
			if(productEntity.getImage3() !=null) {
				ImageInfo img=new ImageInfo(productEntity.getImage3(), "");
				if(listImgSupplier.indexOf(img)!=  -1) {
					int index=listImgSupplier.indexOf(img);
					productEntity.setImage3(listImgSupplier.get(index).getUrl());
				}
			}
			if(productEntity.getImage4() !=null) {
				ImageInfo img=new ImageInfo(productEntity.getImage4(), "");
				if(listImgSupplier.indexOf(img)!=  -1) {
					int index=listImgSupplier.indexOf(img);
					productEntity.setImage4(listImgSupplier.get(index).getUrl());
				}
			}
			
		}
		ProductResponseUpdate product= new ProductResponseUpdate();
		ModelMapper mapper= new ModelMapper();
		
		product=mapper.map(productEntity, ProductResponseUpdate.class);
//		System.out.println("URL hinh dai dien la: "+product.getThumbnail());
//		System.out.println("URL hinh IMG3: "+product.getImage3());
//		System.out.println("URL hinh IMG1: "+product.getImage1());
//		System.out.println("URL hinh IMG2: "+product.getImage2());
//		System.out.println("URL hinh IMG4: "+product.getImage4());
		
		List<SizeEntity> listSize=sizeSevice.loadAllSize();
		
		List<ProductSizeEntity>listSizeEntity= productSizeService.findByProductid(id);
		
		List<ProductSizeDtOResponse> listSizeResponse= new ArrayList<>();
		for(int i=0;i<listSize.size();i++) {
			
			ProductSizeDtOResponse dtoReponse = new ProductSizeDtOResponse();
			dtoReponse.setSize_id(listSize.get(i).getId());
			dtoReponse.setProduct_id(id);
			dtoReponse.setNameSize(listSize.get(i).getName());
			ProductSizeEntity  entity= new ProductSizeEntity();
			entity.setSize_id(listSize.get(i).getId());
			
			if(listSizeEntity.indexOf(entity) != -1) {
				
				int index=listSizeEntity.indexOf(entity);
				dtoReponse.setQuantity(listSizeEntity.get(index).getQuantity());
				dtoReponse.setId(listSizeEntity.get(index).getId());
				
			}else {
				
				dtoReponse.setQuantity(0);
			}
			
			
			listSizeResponse.add(dtoReponse);
		}
		 
		product.setListSize(listSizeResponse);
		model.addAttribute("listSize", listSizeResponse);
		model.addAttribute("listSupplier", supplierService.listAllSupplierEntity());
		model.addAttribute("listCategory", categoryService.getAllCategory());
		
		model.addAttribute("product", product);
		return "admin/form_update_product_last_version";
	}
	
	@PostMapping("/admin/product/update")
	public String updateProduct(@ModelAttribute(name = "product") ProductResponseUpdate product
			,@RequestParam MultipartFile mainImg
			,@RequestParam MultipartFile img1
			,@RequestParam MultipartFile img2
			,@RequestParam MultipartFile img3
			,@RequestParam MultipartFile img4
			,RedirectAttributes model) {
		
		ProductEntity entity=productService.findById(product.getId());
//		System.out.println("Product data: "+product);
		
		
		if(mainImg.isEmpty() && entity.getThumbnail()==null)
			return "ErrorPage";
		else {
			if(! mainImg.isEmpty())
			{
				entity.setThumbnail(mainImg.getOriginalFilename());
				uploadServive.save(mainImg);
			}
		}
		if(! img1.isEmpty() )
		{
			entity.setImage1(img1.getOriginalFilename());
			uploadServive.save(img1);
		}
		if(!img2.isEmpty())
		{
			entity.setImage2(img2.getOriginalFilename());
			uploadServive.save(img2);
		}
		if(!img3.isEmpty())
		{
			entity.setImage3(img3.getOriginalFilename());
			uploadServive.save(img3);
		}
		if(!img4.isEmpty())
		{
			entity.setImage4(img4.getOriginalFilename());
			uploadServive.save(img4);
		}
		try {
			entity.setModifierDate(new Date());
			entity.setStatus(0);
			entity.setName(product.getName());
			entity.setCategory_id(product.getCategory_id());
			entity.setSupplier_id(product.getSupplier_id());
			entity.setOriganalprice(product.getOriganalprice());
			entity.setPrice(product.getPrice());
			entity.setShortdescription(product.getShortdescription());
			entity.setLongdescription(product.getLongdescription());
			ProductEntity saveProduct=productService.saveProduct(entity);
			int productId=saveProduct.getId();
			for(int i=0;i<product.getListSize().size();i++) {
				System.out.println("product id la: "+productId);
				Long sizeid=product.getListSize().get(i).getSize_id();
				int sizeidint=0;
				if(sizeid !=null) {
					sizeidint=sizeid.intValue();
					
				}
				int quantity=product.getListSize().get(i).getQuantity();
				
				
					if(sizeidint !=0) {
						ProductSizeEntity size= productSizeService.findByProductidAndSizeid(productId,sizeidint );
						if(size ==null) {
							size= new ProductSizeEntity();
							size.setProductid(productId);
							size.setQuantity(product.getListSize().get(i).getQuantity());
							size.setSize_id(product.getListSize().get(i).getSize_id());
							size.setDatesale(new Date());
							size.setquantitysold(0);
							productSizeService.saveProductSizeEntity(size);
						}else {
							size.setQuantity(quantity);
							productSizeService.saveProductSizeEntity(size);
						}
					}else {
						if(product.getListSize().get(i).getId() !=0 && Integer.valueOf(product.getListSize().get(i).getId()) !=null) {
							ProductSizeEntity size= productSizeService.findById(product.getListSize().get(i).getId());
							size.setQuantity(0);
							productSizeService.saveProductSizeEntity(size);
						}
					}

				
			}
			model.addFlashAttribute("error_update",true);
			return "redirect:/admin/product-update/"+productId;
			
		} catch (Exception e) {
			model.addFlashAttribute("error_update",false);
			return "redirect:/admin/products/list";
		}
		

			
		}
	
	@GetMapping("admin/product-deatil/{id}")
	public String getDeatilProduct(@PathVariable(name = "id") int id, Model model) {
		ProductEntity  productEntity= productService.findById(id);
		
		List<ImageInfo> listImgSupplier = uploadService.loadAll().map(path -> {
		      String filename = path.getFileName().toString();
		      String url = MvcUriComponentsBuilder
		          .fromMethodName(SupplierController.class, "getImage", path.getFileName().toString()).build().toString();

		      return new ImageInfo(filename, url);
		    }).collect(Collectors.toList());
		
		for(int i=0;i<5;i++) {
			
			if(productEntity.getThumbnail() !=null) {
				ImageInfo img=new ImageInfo(productEntity.getThumbnail(), "");
				if(listImgSupplier.indexOf(img)!=  -1) {
					int index=listImgSupplier.indexOf(img);
					productEntity.setThumbnail(listImgSupplier.get(index).getUrl());
				}
			}
			
			if(productEntity.getImage1() !=null) {
				ImageInfo img=new ImageInfo(productEntity.getImage1(), "");
				if(listImgSupplier.indexOf(img)!=  -1) {
					int index=listImgSupplier.indexOf(img);
					productEntity.setImage1(listImgSupplier.get(index).getUrl());
				}
			}
			if(productEntity.getImage2() !=null) {
				ImageInfo img=new ImageInfo(productEntity.getImage2(), "");
				if(listImgSupplier.indexOf(img)!=  -1) {
					int index=listImgSupplier.indexOf(img);
					productEntity.setImage2(listImgSupplier.get(index).getUrl());
				}
			}
			if(productEntity.getImage3() !=null) {
				ImageInfo img=new ImageInfo(productEntity.getImage3(), "");
				if(listImgSupplier.indexOf(img)!=  -1) {
					int index=listImgSupplier.indexOf(img);
					productEntity.setImage3(listImgSupplier.get(index).getUrl());
				}
			}
			if(productEntity.getImage4() !=null) {
				ImageInfo img=new ImageInfo(productEntity.getImage4(), "");
				if(listImgSupplier.indexOf(img)!=  -1) {
					int index=listImgSupplier.indexOf(img);
					productEntity.setImage4(listImgSupplier.get(index).getUrl());
				}
			}
			
		}
		ProductResponseUpdate product= new ProductResponseUpdate();
		ModelMapper mapper= new ModelMapper();
		
		product=mapper.map(productEntity, ProductResponseUpdate.class);
		System.out.println("URL hinh dai dien la: "+product.getThumbnail());
		System.out.println("URL hinh IMG3: "+product.getImage3());
		System.out.println("URL hinh IMG1: "+product.getImage1());
		System.out.println("URL hinh IMG2: "+product.getImage2());
		System.out.println("URL hinh IMG4: "+product.getImage4());
		
		List<SizeEntity> listSize=sizeSevice.loadAllSize();
		
		List<ProductSizeEntity>listSizeEntity= productSizeService.findByProductid(id);
		
		List<ProductSizeDtOResponse> listSizeResponse= new ArrayList<>();
		for(int i=0;i<listSize.size();i++) {
			
			ProductSizeDtOResponse dtoReponse = new ProductSizeDtOResponse();
			dtoReponse.setSize_id(listSize.get(i).getId());
			dtoReponse.setProduct_id(id);
			dtoReponse.setNameSize(listSize.get(i).getName());
			ProductSizeEntity  entity= new ProductSizeEntity();
			entity.setSize_id(listSize.get(i).getId());
			
			if(listSizeEntity.indexOf(entity) != -1) {
				
				int index=listSizeEntity.indexOf(entity);
				dtoReponse.setQuantity(listSizeEntity.get(index).getQuantity());
				
			}else {
				
				dtoReponse.setQuantity(0);
			}
			
			
			listSizeResponse.add(dtoReponse);
		}
		 String nameCategory=categoryService.getCategorybyId(product.getCategory_id()).get().getName();
		 String nameSupplier=supplierService.getSupplierById(product.getSupplier_id()).get().getName();
		product.setListSize(listSizeResponse);
		model.addAttribute("nameCategory", nameCategory);
		model.addAttribute("nameSupplier", nameSupplier);
		model.addAttribute("listSize", listSizeResponse);
		model.addAttribute("listSupplier", supplierService.listAllSupplierEntity());
		model.addAttribute("listCategory", categoryService.getAllCategory());
		
		model.addAttribute("product", product);
		return "admin/form_detail_product";
		
	}

}
