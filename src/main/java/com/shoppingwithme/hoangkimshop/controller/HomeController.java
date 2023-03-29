package com.shoppingwithme.hoangkimshop.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.shoppingwithme.hoangkimshop.common.ImageInfo;
import com.shoppingwithme.hoangkimshop.dto.response.ProductDTOResponseUser;
import com.shoppingwithme.hoangkimshop.entity.ProductEntity;
import com.shoppingwithme.hoangkimshop.security.MyUserDeatils;
import com.shoppingwithme.hoangkimshop.service.serviceImpl.FilesStorageServiceImpl;
import com.shoppingwithme.hoangkimshop.service.serviceImpl.ProductServiceImpl;

@Controller
public class HomeController {

	@Autowired
	private ProductServiceImpl productService;
	
	
	@Autowired
	private FilesStorageServiceImpl uploadService;
	
	
	@GetMapping("/")
	public String homePage(Model model, HttpSession session, @AuthenticationPrincipal MyUserDeatils user) {
		
		int women=productService.countByCategory_id(Long.parseLong("2"));
		int men=productService.countByCategory_id(Long.parseLong("3"));
		int kid=productService.countByCategory_id(Long.parseLong("5"));
		int cosmetics=productService.countByCategory_id(Long.parseLong("6"));
		int accessories=productService.countByCategory_id(Long.parseLong("9"));
		model.addAttribute("women", women);
		model.addAttribute("men", men);
		model.addAttribute("kid", kid);
		model.addAttribute("cosmetics", cosmetics);
		model.addAttribute("accessories", accessories);
		
		List<ImageInfo> listImgSupplier = uploadService.loadAll().map(path -> {
		      String filename = path.getFileName().toString();
		      String url = MvcUriComponentsBuilder
		          .fromMethodName(SupplierController.class, "getImage", path.getFileName().toString()).build().toString();

		      return new ImageInfo(filename, url);
		    }).collect(Collectors.toList());
		
		System.out.println("Img size: "+listImgSupplier.size());
		List<ProductEntity>listProduct=new ArrayList<>();
		listProduct=productService.findByStatus(0);
		List<ProductDTOResponseUser>listProductshow=new ArrayList<>();
		for(int i=0;i<listProduct.size();i++) {
			
			
			
			ProductDTOResponseUser product=  new ProductDTOResponseUser();
			ImageInfo img=new ImageInfo(listProduct.get(i).getThumbnail(),listProduct.get(i).getThumbnail());
			
			product.setId(listProduct.get(i).getId());
			product.setName(listProduct.get(i).getName());
			product.setPrice(listProduct.get(i).getPrice());
			
			if(listImgSupplier.indexOf(img)!=  -1) {
				int index=listImgSupplier.indexOf(img);
				product.setThumbnail(listImgSupplier.get(index).getUrl());
			}
			listProductshow.add(product);
			
		}
		if(user !=null) {
			session.setAttribute("username", user.getUsername());
		}else {
			session.setAttribute("username", "");
		}
		model.addAttribute("listProductshow", listProductshow);
		return "index";
	}
}
