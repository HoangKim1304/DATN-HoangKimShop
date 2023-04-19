package com.shoppingwithme.hoangkimshop.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.shoppingwithme.hoangkimshop.common.ImageInfo;
import com.shoppingwithme.hoangkimshop.dto.response.CartItemDTO;
import com.shoppingwithme.hoangkimshop.entity.CartItemEntity;
import com.shoppingwithme.hoangkimshop.entity.ProductEntity;
import com.shoppingwithme.hoangkimshop.entity.SizeEntity;
import com.shoppingwithme.hoangkimshop.security.MyUserDeatils;
import com.shoppingwithme.hoangkimshop.service.serviceImpl.CartItemServiceImpl;
import com.shoppingwithme.hoangkimshop.service.serviceImpl.FilesStorageServiceImpl;
import com.shoppingwithme.hoangkimshop.service.serviceImpl.ProductServiceImpl;
import com.shoppingwithme.hoangkimshop.service.serviceImpl.ProductSizeServiceImpl;
import com.shoppingwithme.hoangkimshop.service.serviceImpl.SizeServiceImpl;

@Controller
public class CartController {

	
	
	@Autowired
	private FilesStorageServiceImpl uploadService;
	
	@Autowired
	private CartItemServiceImpl cartService;
	
	@Autowired
	private ProductServiceImpl productService;
	
	@Autowired
	private ProductSizeServiceImpl productSizeService;
	
	@Autowired
	private SizeServiceImpl sizeService;
	
	@Secured("USER")
	@GetMapping("/shopping-with-me/cart")
	public String gePageListCart(HttpSession session, Model model,@AuthenticationPrincipal MyUserDeatils user) {
		
		List<CartItemEntity> listCartItem=cartService.findByUserid(user.getInforUser().getId());

		List<ImageInfo> listImgSupplier = uploadService.loadAll().map(path -> {
		      String filename = path.getFileName().toString();
		      String url = MvcUriComponentsBuilder
		          .fromMethodName(SupplierController.class, "getImage", path.getFileName().toString()).build().toString();

		      return new ImageInfo(filename, url);
		    }).collect(Collectors.toList());
		
		List<CartItemDTO>listCartItems=new ArrayList<>();
		for(int i=0;i<listCartItem.size();i++) {
			
			ProductEntity product=productService.findById(listCartItem.get(i).getProductId());
			
			SizeEntity sizeSelected=sizeService.findById(listCartItem.get(i).getProductsizeid());
			
			List<SizeEntity> listSizeByProductId=new ArrayList<>();
			
			
			ImageInfo img=new ImageInfo(product.getThumbnail(), "");
			
			CartItemDTO cartItem= new CartItemDTO();
			cartItem.setId(listCartItem.get(i).getId());
			cartItem.setPrice(product.getPrice()*listCartItem.get(i).getQuantity());
			cartItem.setPricePerProduct(product.getPrice());

			cartItem.setSizeid(listCartItem.get(i).getProductsizeid().intValue());
			cartItem.setProductid(product.getId());
			cartItem.setQuantity(listCartItem.get(i).getQuantity());
			cartItem.setNameProduct(product.getName());
			cartItem.setSizeid(sizeSelected.getId().intValue());
			cartItem.setNameSize(sizeSelected.getName());
			
			for(int j=0;j<productSizeService.findByProductid(product.getId()).size();j++) {
				
				SizeEntity size=sizeService.findById(productSizeService.findByProductid(product.getId()).get(j).getSize_id());
				if(! listSizeByProductId.contains(size)) {
					listSizeByProductId.add(size);
				}
				
			}
			cartItem.setListSizeByProductId(listSizeByProductId);
			if(listImgSupplier.indexOf(img)!=  -1) {
							
				int index=listImgSupplier.indexOf(img);
				cartItem.setUrl(listImgSupplier.get(index).getUrl());
				listCartItems.add(cartItem);

			}

		}
		model.addAttribute("listCartItem", listCartItems);

		return "shop-cart";
	}
	

	
}
