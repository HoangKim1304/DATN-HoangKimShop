package com.shoppingwithme.hoangkimshop.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shoppingwithme.hoangkimshop.common.ImageInfo;
import com.shoppingwithme.hoangkimshop.dto.response.ProductDTOResponseUser;
import com.shoppingwithme.hoangkimshop.entity.ProductEntity;
import com.shoppingwithme.hoangkimshop.entity.ProductLikeEntity;
import com.shoppingwithme.hoangkimshop.service.serviceImpl.FilesStorageServiceImpl;
import com.shoppingwithme.hoangkimshop.service.serviceImpl.ProductLikeServiceImpl;
import com.shoppingwithme.hoangkimshop.service.serviceImpl.ProductServiceImpl;

@Controller
public class ProductLikeController {

	@Autowired
	private ProductLikeServiceImpl productLikeService;
	
	@Autowired ProductServiceImpl productService;
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private FilesStorageServiceImpl uploadService;
	
	@GetMapping("/shopping-with-me/likeproduct")
	public String getPageListProductLike(RedirectAttributes model) {
		if(session.getAttribute("userId") !=null ) {
			List<ImageInfo> listImgSupplier = uploadService.loadAll().map(path -> {
			      String filename = path.getFileName().toString();
			      String url = MvcUriComponentsBuilder
			          .fromMethodName(SupplierController.class, "getImage", path.getFileName().toString()).build().toString();

			      return new ImageInfo(filename, url);
			    }).collect(Collectors.toList());
			int userid=Integer.valueOf(session.getAttribute("userId").toString());
			List<ProductLikeEntity> listproductlike=productLikeService.findByUserid(userid);
			List<Integer> listids=new ArrayList<>();
			for(ProductLikeEntity item: listproductlike) {
				listids.add(item.getProductid());
			}
			List<ProductEntity>listProduct=productService.findById(listids);
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
			model.addAttribute("listProductshow", listProductshow);
			return "product_like_by_user_id";
		}
		else {
			model.addFlashAttribute("error","Bạn phải đăng nhập");
			return "redirect:/";
		}
		
	}
}
