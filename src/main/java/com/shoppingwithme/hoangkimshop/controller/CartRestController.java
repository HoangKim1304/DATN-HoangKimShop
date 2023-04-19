package com.shoppingwithme.hoangkimshop.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingwithme.hoangkimshop.entity.CartItemEntity;
import com.shoppingwithme.hoangkimshop.service.serviceImpl.CartItemServiceImpl;

@RestController
public class CartRestController {

	@Autowired
	private HttpSession session;
	
	@Autowired
	private CartItemServiceImpl cartItemService;
	
	@PostMapping("/shopping-with-me/cart/add/{productid}/{sizeid}/{quantity}")
	public boolean addProductToCart(@PathVariable(name = "productid")int productid, 
									@PathVariable(name = "sizeid")int sizeid,
									@PathVariable(name = "quantity") int quantity) {
		String userId=session.getAttribute("userId").toString();
		System.out.println("Product id la ma de them : "+productid);
		System.out.println("Size id la: "+sizeid);
		System.out.println("Quantity id la: "+quantity);
		if(userId==null)
		{
			return false;
		}else {
			CartItemEntity cartItem=new CartItemEntity();
			cartItem.setProductId(productid);
			cartItem.setUserid(Integer.parseInt(userId));
			cartItem.setQuantity(quantity);
			cartItem.setProductsizeid(Long.valueOf(sizeid));
			cartItemService.addCart(cartItem, Integer.parseInt(userId));
			return true;
		}
		
		
	}
	
	@PostMapping("/shopping-with-me/cart/update/{cartItemId}/{sizeId}/{Quantity}")
	public boolean updateCart(@PathVariable(name = "cartItemId")int cartItemId, 
			@PathVariable(name = "sizeId")int sizeId,
			@PathVariable(name = "Quantity") int Quantity) {
		System.out.println("cartItemId: "+cartItemId);
		System.out.println("SizeId: "+sizeId);
		System.out.println("quantity: "+Quantity);
		String userId=session.getAttribute("userId").toString();
		System.out.println("ID nguoi dung update la: "+userId);
		if(userId==null || cartItemId==0 )
		{
			return false;
		}else {
			CartItemEntity cartItem=cartItemService.findById(Long.valueOf(cartItemId));
			if(cartItem==null) {
				System.out.println("Ma item cart nay kho ton tai");
				return false;
			}else {
				System.out.println("Ma item cart ton tai");
//				cartItem=cartItemService.fin
				cartItem.setQuantity(Quantity);
				cartItem.setProductsizeid(Long.valueOf(sizeId));
				boolean isupdate= cartItemService.updateCartItem(cartItem, Integer.parseInt(userId));
				return isupdate;
			}
			
			
			
		}
		
		
		
	}
	
	@PostMapping("/shopping-with-me/cart/delete/{cartItemId}")
	public boolean deleteCartItem(@PathVariable(name = "cartItemId")int cartItemId) {
		String userId=session.getAttribute("userId").toString();
		if(userId==null || cartItemId==0 )
		{
			return false;
		}else {
			CartItemEntity cartItem=cartItemService.findById(Long.valueOf(cartItemId));
			if(cartItem==null) {
				return false;
			}else {
				
				boolean check=cartItemService.deleteById(Long.valueOf(cartItemId));
				return check;
				
			}
			
			
			
		}
	}
}
