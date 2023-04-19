package com.shoppingwithme.hoangkimshop.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.shoppingwithme.hoangkimshop.common.ImageInfo;
import com.shoppingwithme.hoangkimshop.dto.request.FormSearchDTO;
import com.shoppingwithme.hoangkimshop.dto.response.ProductDTOResponseUser;
import com.shoppingwithme.hoangkimshop.entity.ProductEntity;
import com.shoppingwithme.hoangkimshop.entity.ProductSizeEntity;
import com.shoppingwithme.hoangkimshop.security.MyUserDeatils;
import com.shoppingwithme.hoangkimshop.service.serviceImpl.CartItemServiceImpl;
import com.shoppingwithme.hoangkimshop.service.serviceImpl.FilesStorageServiceImpl;
import com.shoppingwithme.hoangkimshop.service.serviceImpl.ProductLikeServiceImpl;
import com.shoppingwithme.hoangkimshop.service.serviceImpl.ProductServiceImpl;
import com.shoppingwithme.hoangkimshop.service.serviceImpl.ProductSizeServiceImpl;
import com.shoppingwithme.hoangkimshop.service.serviceImpl.SizeServiceImpl;
import com.shoppingwithme.hoangkimshop.service.serviceImpl.SupplierServiceImpl;

@Controller
public class HomeController {

	@Autowired
	private ProductServiceImpl productService;
	
	
	@Autowired
	private FilesStorageServiceImpl uploadService;
	
	@Autowired
	private CartItemServiceImpl cartService;
	
	@Autowired
	private SizeServiceImpl sizeService;
	
	@Autowired
	private SupplierServiceImpl supplierService;
	
	@Autowired
	private ProductSizeServiceImpl productSizeService;
	
	@Autowired
	private ProductLikeServiceImpl productLikeService;
	
	
	@GetMapping("/")
	public String homePage(Model model, 
							HttpSession session, 
							@AuthenticationPrincipal MyUserDeatils user,
							@RequestParam(defaultValue = "1") int page,
							@RequestParam(required = false) String keyword) {
		
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
//		listProduct=productService.findByStatus(0);
		List<ProductDTOResponseUser>listProductshow=new ArrayList<>();
		 Pageable pageable = PageRequest.of(page - 1, 8);
		 Page<ProductEntity> pageTuts;
		    if (keyword == null) {
		        pageTuts = productService.findAll(pageable);
		      } else {
		        pageTuts = productService.findByNameContainingIgnoreCase(keyword, pageable);
		        model.addAttribute("keyword", keyword);
		      }
		
		    listProduct=pageTuts.getContent();
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
			
			session.setAttribute("userId", user.getInforUser().getId());
			session.setAttribute("username", user.getUsername());
			System.out.println("username: "+user.getUsername());
			
//				List<CartItemEntity> listCartItem=cartService.findByUserid(user.getInforUser().getId());
			try {
//				System.out.println("Ma nguoi dung: "+user.getInforUser().getId());
				Integer sumproductincart=cartService.getSumTotalQunatityByUserId(user.getInforUser().getId());
				int sumproductlike=productLikeService.countByUserid(user.getInforUser().getId());
				
					
//					model.addAttribute("sizecart", sumproductincart);
//					model.addAttribute("sizeproductlike", sumproductlike);
					session.setAttribute("sizecart", sumproductincart);
					session.setAttribute("sizeproductlike", sumproductlike);
				
					
//					model.addAttribute("sizecart", 0);
				
			} catch (Exception e) {
				return "404";
			}
			
				
			
			
				
			
			
			
		}else {
			session.setAttribute("username", "");
		}
		model.addAttribute("currentPage", pageTuts.getNumber() + 1);
	      model.addAttribute("totalItems", pageTuts.getTotalElements());
	      model.addAttribute("totalPages", pageTuts.getTotalPages());
	      model.addAttribute("pageSize", 8);
		model.addAttribute("listProductshow", listProductshow);
		return "index";
	}
	
	@GetMapping("/shopping-with-me/woment")
	public String getPageWoment(Model model) {
		
		List<ImageInfo> listImgSupplier = uploadService.loadAll().map(path -> {
		      String filename = path.getFileName().toString();
		      String url = MvcUriComponentsBuilder
		          .fromMethodName(SupplierController.class, "getImage", path.getFileName().toString()).build().toString();

		      return new ImageInfo(filename, url);
		    }).collect(Collectors.toList());
		
		
		
		List<ProductEntity>listProduct=productService.findByCategory_id(Long.valueOf(2));
		
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
		model.addAttribute("listSize", sizeService.loadAllSize());
		model.addAttribute("listSupplier",supplierService.listAllSupplierEntity());
		model.addAttribute("maxPrice",productService.getMaxPriceByCategoryId(Long.valueOf(2)));
		model.addAttribute("minPrice",productService.getMinPriceByCategoryId(Long.valueOf(2)));
		return "shop_women";
	}
	
	@PostMapping("/shopping-with-me/woment123")
	public String searchProduct(@ModelAttribute (name = "search")FormSearchDTO search, Model model) {
		System.out.println(search);
		List<String> listsupplier=Arrays.asList(search.getListsupplier().split(","));
		List<Long>idsupplier=new ArrayList<>();
		List<ProductEntity>listProduct= productService.findByFormSearch(Long.valueOf(2), Double.valueOf(search.getMaxprice()), Double.valueOf(search.getMinprice()));
		if(listsupplier.size() >0 && !listsupplier.contains("")) {
			for(String s : listsupplier) idsupplier.add(Long.valueOf(s));
			listProduct=productService.findByFormSearch(Long.valueOf(2), Double.valueOf(search.getMaxprice()), Double.valueOf(search.getMinprice()), idsupplier);
		}
		List<String> listsize=Arrays.asList(search.getListsize().split(","));
		List<Long>idsize=new ArrayList<>();
		if(listsize.size() !=0 && !listsize.contains("")) {
			for(String s : listsize) idsize.add(Long.valueOf(s));
			
		}
		
		List<ProductDTOResponseUser>listProductshow=new ArrayList<>();
		List<ImageInfo> listImgSupplier = uploadService.loadAll().map(path -> {
		      String filename = path.getFileName().toString();
		      String url = MvcUriComponentsBuilder
		          .fromMethodName(SupplierController.class, "getImage", path.getFileName().toString()).build().toString();

		      return new ImageInfo(filename, url);
		    }).collect(Collectors.toList());
		
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
			
		
		
		if(idsize.size() >0) {
			
			for(int i=0;i<listProductshow.size();i++) {
				List<ProductSizeEntity>listsizeavaiable=productSizeService.findByProductid(listProduct.get(i).getId());
				int check=0;
				for(int j=0;j<listsizeavaiable.size();j++) {
					if( idsize.contains(listsizeavaiable.get(j).getSize_id())) {
						check++;
					}
				}
				if(check==0) {
					listProductshow.remove(i);
				}
			}
			
		}
		
		
		model.addAttribute("listProductshow", listProductshow);
		model.addAttribute("listSize", sizeService.loadAllSize());
		model.addAttribute("listSupplier",supplierService.listAllSupplierEntity());
		model.addAttribute("maxPrice",productService.getMaxPriceByCategoryId(Long.valueOf(2)));
		model.addAttribute("minPrice",productService.getMinPriceByCategoryId(Long.valueOf(2)));
		return "shop_women";
	}
	
	
}
