package com.shoppingwithme.hoangkimshop.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingwithme.hoangkimshop.entity.ProductEntity;
import com.shoppingwithme.hoangkimshop.entity.ProductLikeEntity;
import com.shoppingwithme.hoangkimshop.service.serviceImpl.ProductLikeServiceImpl;
import com.shoppingwithme.hoangkimshop.service.serviceImpl.ProductServiceImpl;

@RestController
public class ProductLikeRestController {

	@Autowired
	private HttpSession session;
	
	@Autowired
	private ProductServiceImpl productService;
	
	@Autowired
	private ProductLikeServiceImpl productLikeService;
	@PostMapping("/shopping-with-me/likeproduct/{productid}")
	public int likeProductByUserId(@PathVariable(name = "productid") int productid) {
		int userid=0;
		if(session.getAttribute("userId")==null || session.getAttribute("userId").toString()=="") {
			return -2;
			
		}else {
			userid=Integer.valueOf(session.getAttribute("userId").toString());
			if(userid==0) {
				return -2;
			}
			else {
				ProductEntity product=productService.findById(productid);
				if(product==null) {
					return -1;
				}else {
					ProductLikeEntity isExists=productLikeService.findByUseridAndProductid(userid, productid);
					if(isExists !=null) {
						return -3;
					}
					ProductLikeEntity productLike= new ProductLikeEntity();
					productLike.setUserid(userid);
					productLike.setProductid(productid);
					productLikeService.saveProductLikeByUserId(productLike);
					return 0;
				}
			}
			
		}
		
	}
}
