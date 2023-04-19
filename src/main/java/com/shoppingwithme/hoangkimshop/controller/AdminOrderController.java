package com.shoppingwithme.hoangkimshop.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.shoppingwithme.hoangkimshop.common.ImageInfo;
import com.shoppingwithme.hoangkimshop.dto.response.OrderDTO;
import com.shoppingwithme.hoangkimshop.dto.response.OrderDetailDTO;
import com.shoppingwithme.hoangkimshop.entity.DetailOrderEntity;
import com.shoppingwithme.hoangkimshop.entity.OrderDeliveryEntity;
import com.shoppingwithme.hoangkimshop.entity.OrderEntity;
import com.shoppingwithme.hoangkimshop.entity.ProductEntity;
import com.shoppingwithme.hoangkimshop.entity.ShippingUnitEntity;
import com.shoppingwithme.hoangkimshop.entity.SizeEntity;
import com.shoppingwithme.hoangkimshop.entity.StatusOrderEntity;
import com.shoppingwithme.hoangkimshop.entity.VochourEntity;
import com.shoppingwithme.hoangkimshop.service.serviceImpl.DetailOrderServiceImpl;
import com.shoppingwithme.hoangkimshop.service.serviceImpl.FilesStorageServiceImpl;
import com.shoppingwithme.hoangkimshop.service.serviceImpl.OrderDeliveryServiceImpl;
import com.shoppingwithme.hoangkimshop.service.serviceImpl.OrderServiceImpl;
import com.shoppingwithme.hoangkimshop.service.serviceImpl.ProductServiceImpl;
import com.shoppingwithme.hoangkimshop.service.serviceImpl.ShippingUnitServiceImpl;
import com.shoppingwithme.hoangkimshop.service.serviceImpl.SizeServiceImpl;
import com.shoppingwithme.hoangkimshop.service.serviceImpl.StatusOrderServiceImpl;
import com.shoppingwithme.hoangkimshop.service.serviceImpl.VoucherServiceImpl;

@Controller
public class AdminOrderController {

	@Autowired
	private OrderServiceImpl orderService;
	
//	@Autowired
//	private UserDeatilsServiceImpl userService;
	
	@Autowired
	private DetailOrderServiceImpl detailOrderService;

	@Autowired
	private VoucherServiceImpl voucherService;
	
	@Autowired
	private ProductServiceImpl productService;
	
	@Autowired
	private SizeServiceImpl sizeService;
	
	@Autowired 
	private StatusOrderServiceImpl statusOrderService;
	
	@Autowired
	private ShippingUnitServiceImpl  shippingUnitService;
	
	@Autowired
	private OrderDeliveryServiceImpl orderDeliveryService;
	
	@Autowired
	private FilesStorageServiceImpl uploadService;
	
	@GetMapping("/admin/ordres")
	public String getListOrderByUserId(@RequestParam(required = false, name = "status")int status,  Model model) {
		
		List<ImageInfo> listImgSupplier = uploadService.loadAll().map(path -> {
		      String filename = path.getFileName().toString();
		      String url = MvcUriComponentsBuilder
		          .fromMethodName(SupplierController.class, "getImage", path.getFileName().toString()).build().toString();

		      return new ImageInfo(filename, url);
		    }).collect(Collectors.toList());
		
//		int userid=Integer.valueOf(session.getAttribute("userId").toString());
		List<OrderDTO> listorder=new ArrayList<>();
//		List<OrderEntity> listorderDB=orderService.findByUserid(userid);
		List<OrderEntity>listorderDB=orderService.findAll();
		for(int i=0;i<listorderDB.size();i++) {
			
			
			
			OrderDTO order= new OrderDTO();
			order.setOrderdate(listorderDB.get(i).getOrderdate().toString());
			order.setIsonline(listorderDB.get(i).isIsonline());
			order.setMoney(listorderDB.get(i).getMoney());
			order.setSumprice(listorderDB.get(i).getSumprice());
			order.setSumquantity(listorderDB.get(i).getSumquantity());
			order.setIdstatusorder(listorderDB.get(i).getIdstatusorder());
			order.setIdorder(listorderDB.get(i).getId());
			if(Long.valueOf(listorderDB.get(i).getVoucherid()) !=null && listorderDB.get(i).getVoucherid()!=0)
			{
				order.setVoucherid(listorderDB.get(i).getVoucherid());
				VochourEntity vochour=voucherService.findById(listorderDB.get(i).getVoucherid());
				order.setVouchercode(vochour.getIdcode());
				
			}
			switch (listorderDB.get(i).getIdstatusorder()) {
			case 1:
				order.setNamestatusorder("Chờ xác nhận");
				
				break;
			case 2:
				order.setNamestatusorder("Chờ lấy hàng");
				break;
			case 3:
				order.setNamestatusorder("Đang giao");
				break;
			case 4:
				order.setNamestatusorder("Đã giao");
				break;
			case 5:
				order.setNamestatusorder("Đã hủy");
				break;
			default:
				order.setNamestatusorder("Không xác định");
				break;
			}
			
			
			
			List<DetailOrderEntity>orderdetailDB=detailOrderService.findByOrderid(listorderDB.get(i).getId());
			List<OrderDetailDTO> orderdetail=new ArrayList<>();
			for(int j=0;j<orderdetailDB.size();j++) {
				
				ProductEntity product=productService.findById(orderdetailDB.get(j).getProductid());
				SizeEntity sizeEntity=sizeService.findById(orderdetailDB.get(j).getSizeid());
				
				
				OrderDetailDTO detailDTO = new OrderDetailDTO();
				detailDTO.setIdproduct(product.getId());
				detailDTO.setProductname(product.getName());
				
				detailDTO.setPrice_per_product(Float.valueOf(product.getPrice().toString()));
				detailDTO.setSizeid(Integer.valueOf(sizeEntity.getId().toString()));
				detailDTO.setSizename(sizeEntity.getName());
				detailDTO.setQuantity(orderdetailDB.get(j).getQuantity());
				if(product.getThumbnail() !=null) {
					ImageInfo img=new ImageInfo(product.getThumbnail(), "");
					if(listImgSupplier.indexOf(img)!=  -1) {
						int index=listImgSupplier.indexOf(img);
						detailDTO.setThumbnail(listImgSupplier.get(index).getUrl());
					}
				}
				
				orderdetail.add(detailDTO);
				
				
				
			}
			order.setListproduct(orderdetail);
			listorder.add(order);
			
			
			
		}
		List<OrderDTO> listorderForStatus=new ArrayList<>();
		if(status !=0) {
			for(int i=0;i<listorder.size();i++) {
				if(listorder.get(i).getIdstatusorder()==status) {
					listorderForStatus.add(listorder.get(i));
				}
			}
			model.addAttribute("listorders", listorderForStatus);
		}else {
			model.addAttribute("listorders", listorder);
		}
		
		
		return "admin/list_order_by_admin";
	}
	
	@GetMapping("/admin/ordres/detail/{id}")
	public String getDetailOrder(@PathVariable(name = "id")int id, Model model) {
		System.out.println("ID ORDER: "+id);
		if(Integer.valueOf(id) !=null && id !=0) {
			
			OrderEntity order=orderService.findById(id);
			List<DetailOrderEntity>listproduct=detailOrderService.findByOrderid(id);
			OrderDTO orderdto= new OrderDTO();
			orderdto.setOrderdate(order.getOrderdate().toString());
			orderdto.setIsonline(order.isIsonline());
			orderdto.setMoney(order.getMoney());
			orderdto.setSumprice(order.getSumprice());
			orderdto.setSumquantity(order.getSumquantity());
			orderdto.setIdstatusorder(order.getIdstatusorder());
			orderdto.setPhonecustomer(order.getPhonecustomer());
			orderdto.setNamecustomer(order.getNamecustomer());
			orderdto.setEmailrecever(order.getEmailrecever());
			orderdto.setAddressship(order.getAddressship());
			orderdto.setMoney(order.getMoney());
			orderdto.setNote(order.getNote());
			
			List<StatusOrderEntity> liststatusorder=statusOrderService.findListById();
			List<StatusOrderEntity> liststatusordershow=new ArrayList<>();
			for(int i=0;i<liststatusorder.size();i++) {
				
				if(liststatusorder.get(i).getId()>=order.getIdstatusorder()) {
					liststatusordershow.add(liststatusorder.get(i));
				}
			}
			if(liststatusordershow.size()>0) {
				
				orderdto.setListStatus(liststatusordershow);
			}
			if(Long.valueOf(order.getVoucherid()) !=null && order.getVoucherid()!=0)
			{
				order.setVoucherid(order.getVoucherid());
				VochourEntity vochour=voucherService.findById(order.getVoucherid());
				orderdto.setVouchercode(vochour.getIdcode());
				
			}
			
			
			
			
			
			List<OrderDetailDTO> orderdetail=new ArrayList<>();
			for(int j=0;j<listproduct.size();j++) {
				
				ProductEntity product=productService.findById(listproduct.get(j).getProductid());
				SizeEntity sizeEntity=sizeService.findById(listproduct.get(j).getSizeid());
				
				
				OrderDetailDTO detailDTO = new OrderDetailDTO();
				detailDTO.setIdproduct(product.getId());
				detailDTO.setProductname(product.getName());
				
				detailDTO.setPrice(Double.valueOf(product.getPrice().toString()));
				detailDTO.setSizeid(Integer.valueOf(sizeEntity.getId().toString()));
				detailDTO.setSizename(sizeEntity.getName());
				detailDTO.setQuantity(listproduct.get(j).getQuantity());
				
				
				orderdetail.add(detailDTO);
				
				
				
			}
			OrderDeliveryEntity orderdelivery= orderDeliveryService.findByOrderId(order.getId());
			if(orderdelivery !=null) {
				orderdto.setPhoneshipper(orderdelivery.getPhoneshipper());
				orderdto.setNameshipper(orderdelivery.getNameshipper());
				orderdto.setIdshippunit(orderdelivery.getIdshippunit());
				ShippingUnitEntity ship=shippingUnitService.findById(orderdelivery.getIdshippunit());
				orderdto.setNameshippunit(ship.getName());
			}else {
				orderdto.setPhoneshipper("");
				orderdto.setNameshipper("");
				orderdto.setIdshippunit(0);
				orderdto.setNameshippunit("");
			}
			
			orderdto.setIdorder(order.getId());
			orderdto.setListproduct(orderdetail);
			System.out.println("ID trang thai don hang la: ");
			model.addAttribute("listShipp", shippingUnitService.findAll());
			model.addAttribute("listStatus", liststatusordershow);
			model.addAttribute("order", orderdto);
			
		}else {
			model.addAttribute("order", null);
		}
		
		
		
		return "admin/update_order";
	}
	
	@PostMapping("/admin/update/order")
	public String updateOrder(@ModelAttribute(name="order") OrderDTO order) {
		System.out.println("Trang thai don hang can update: "+order.getIdstatusorder());
		System.out.println("Ten nguoi giao hang: "+order.getNameshipper());
		System.out.println("So dien thoai nguoi giao hang: "+order.getPhoneshipper());
		System.out.println("Ma do hang: "+order.getIdorder());
		System.out.println("Mã don vi van chuyen la: "+order.getIdshippunit());
		try {
			int orderId=order.getIdorder();
			 
				OrderEntity ordersave=orderService.findById(orderId);
				if(ordersave !=null) {
					if(order.getIdstatusorder() == 1 || order.getIdstatusorder() ==2) {
						ordersave.setIdstatusorder(order.getIdstatusorder());
						orderService.saveOrder(ordersave);
					}else {
						if(order.getIdstatusorder() ==3 ) {
							OrderDeliveryEntity orderdelivery=new OrderDeliveryEntity();
							orderdelivery.setDateship(new Date());
							orderdelivery.setIdshippunit(order.getIdshippunit());
							orderdelivery.setIdorder(orderId);
							orderdelivery.setNameshipper(order.getNameshipper());
							orderdelivery.setPhoneshipper(order.getPhoneshipper());
							orderdelivery.setDeliverycode("deliverycode");
							ordersave.setIdstatusorder(order.getIdstatusorder());
							orderService.saveOrder(ordersave);
							OrderDeliveryEntity orderdeliverysave=orderDeliveryService.saveOrderDelivery(orderdelivery);
							if(orderdeliverysave !=null) {
								orderdeliverysave.setDeliverycode("deliverycode"+orderdeliverysave.getId());
								orderDeliveryService.saveOrderDelivery(orderdeliverysave);
							}else {
								return "505";
							}
						}
					}
				}
				return "redirect:/admin/ordres?status=0";
			
		} catch (Exception e) {
			return "505";
		}
		
		
		
		
		
		
	}
}
