package com.shoppingwithme.hoangkimshop.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import com.shoppingwithme.hoangkimshop.common.ImageInfo;
import com.shoppingwithme.hoangkimshop.dto.response.OrderDTO;
import com.shoppingwithme.hoangkimshop.dto.response.OrderDetailDTO;
import com.shoppingwithme.hoangkimshop.dto.response.VoucherDTO;
import com.shoppingwithme.hoangkimshop.entity.CartItemEntity;
import com.shoppingwithme.hoangkimshop.entity.DetailOrderEntity;
import com.shoppingwithme.hoangkimshop.entity.OrderEntity;
import com.shoppingwithme.hoangkimshop.entity.ProductEntity;
import com.shoppingwithme.hoangkimshop.entity.ProductSizeEntity;
import com.shoppingwithme.hoangkimshop.entity.SizeEntity;
import com.shoppingwithme.hoangkimshop.entity.VochourEntity;
import com.shoppingwithme.hoangkimshop.payment.PayPallServiceImpl;
import com.shoppingwithme.hoangkimshop.service.serviceImpl.CartItemServiceImpl;
import com.shoppingwithme.hoangkimshop.service.serviceImpl.DetailOrderServiceImpl;
import com.shoppingwithme.hoangkimshop.service.serviceImpl.FilesStorageServiceImpl;
import com.shoppingwithme.hoangkimshop.service.serviceImpl.OrderServiceImpl;
import com.shoppingwithme.hoangkimshop.service.serviceImpl.ProductServiceImpl;
import com.shoppingwithme.hoangkimshop.service.serviceImpl.ProductSizeServiceImpl;
import com.shoppingwithme.hoangkimshop.service.serviceImpl.SizeServiceImpl;
import com.shoppingwithme.hoangkimshop.service.serviceImpl.VoucherServiceImpl;

@Controller
public class OrderController {

	@Autowired
	private CartItemServiceImpl cartItemService;
	
	@Autowired
	private ProductServiceImpl productService;
	
	@Autowired
	private SizeServiceImpl sizeService;
	
	@Autowired
	private VoucherServiceImpl voucherService;
	
	@Autowired
	private OrderServiceImpl orderService;
	
	@Autowired
	private DetailOrderServiceImpl detailOrderService;
	
	@Autowired
	private ProductSizeServiceImpl productSizeService;
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private FilesStorageServiceImpl uploadService;
	
	@Autowired
	private PayPallServiceImpl paypallService;
	
	public static final String SUCCESS_URL = "pay/success";
	public static final String CANCEL_URL = "pay/cancel";
	
	@PostMapping("/shopping-with-me/order")
	public String getPageOrder(@ModelAttribute(name = "idsArray") String idsArray, Model model) {
		System.out.println("I'am here");
		System.out.println(idsArray);
		
		OrderDTO order= new OrderDTO();
		List<Long> listId = new ArrayList<Long>();
		
		List<String>ids=Arrays.asList(idsArray.split(","));
		for(int i=0;i<ids.size();i++) {
			listId.add(Long.valueOf(ids.get(i)));
		}

		List<CartItemEntity> listCartItem=cartItemService.findAllByListId(listId);
		System.out.println("oSo luong p: "+listCartItem.size());
		
		int sumquantity=0;
		int sumprice=0;
		List<OrderDetailDTO> listorder=new ArrayList<>();
		for(int i=0;i< listCartItem.size();i++) {
			
			int productid=listCartItem.get(i).getProductId();
			ProductEntity product=productService.findById(productid);
			
			Long sizeid=listCartItem.get(i).getProductsizeid();
			SizeEntity size=sizeService.findById(sizeid);

			
			OrderDetailDTO orderetail= new OrderDetailDTO();
			
			orderetail.setQuantity(listCartItem.get(i).getQuantity());

			orderetail.setIdproduct(listCartItem.get(i).getProductId());
			orderetail.setSizeid(Integer.valueOf(listCartItem.get(i).getProductsizeid().toString()));
			
			orderetail.setPrice(product.getPrice()*orderetail.getQuantity());
			orderetail.setProductname(product.getName());
			orderetail.setSizename(size.getName());
			orderetail.setCartid(listCartItem.get(i).getId());
			orderetail.setPrice_per_product(Float.valueOf(product.getPrice().toString()));
			System.out.println(orderetail);
			listorder.add(orderetail);
			sumquantity+=orderetail.getQuantity();
			sumprice+=orderetail.getPrice();
		}
		order.setListproduct(listorder);
		List<VochourEntity>listvoucher= voucherService.getAllVoucherAvailable(sumprice);
		
		List<VoucherDTO>listvoucheravailable=new ArrayList<>();
		System.out.println("So luong voucher la: "+listvoucher.size());
		for(int i=0;i< listvoucher.size();i++) {
			VoucherDTO voucher=new VoucherDTO();
			voucher.setId(listvoucher.get(i).getId());
			voucher.setEnddate(listvoucher.get(i).getEndate());
			voucher.setQuantityWasUsed(orderService.getSumVoucherId(listvoucher.get(i).getId()));
			voucher.setStatus(listvoucher.get(i).getStatus());
			voucher.setIspayonline(listvoucher.get(i).isIspayonline());
			voucher.setQuantity(listvoucher.get(i).getQuuantity());
			voucher.setDiscout(listvoucher.get(i).getDiscout());
			voucher.setIdcode(listvoucher.get(i).getIdcode());
			voucher.setMaxpricediscout(listvoucher.get(i).getMaxpricediscout());
			voucher.setMaxpriceoder(listvoucher.get(i).getMaxpriceoder());
			listvoucheravailable.add(voucher);
		}
		
		model.addAttribute("listvoucher", listvoucheravailable);
		model.addAttribute("order", order);
		model.addAttribute("sumquantity", sumquantity);
		model.addAttribute("sumprice", sumprice);
		model.addAttribute("listorder", listorder);
		return "checkout";
	}
	
	@PostMapping("/shopping-with-me/order/payment")
	public String order(@ModelAttribute(name = "order") OrderDTO order) throws NumberFormatException, PayPalRESTException {
		System.out.println("Gia tri cua order: ");
		System.out.println(order);
		int userid=Integer.valueOf(session.getAttribute("userId").toString());
		OrderEntity ordersave= new OrderEntity();
		ordersave.setAddressship(order.getAddressship());
		ordersave.setEmailrecever(order.getEmailrecever());
		ordersave.setIdstatusorder(0);
		ordersave.setIsonline(order.isIsonline());
		ordersave.setModifidate(new Date());
		ordersave.setMoney(order.getMoney());
		ordersave.setNamecustomer(order.getNamecustomer());
		ordersave.setNameuser(order.getNamesender());
		ordersave.setNote(order.getNote());
		ordersave.setOrderdate(new Date());
		ordersave.setPhonecustomer(order.getPhonecustomer());
		ordersave.setSumprice(order.getSumprice());
		ordersave.setSumquantity(order.getSumquantity());
		if(Integer.valueOf(userid)!=null) {
			ordersave.setUserid(userid);
			
		}
		
		
		if(Long.valueOf(order.getVoucherid()) !=null && order.getVoucherid() !=0) {
			ordersave.setVoucherid(order.getVoucherid());
		}
		
		
		
		
		OrderEntity ordersaved=orderService.saveOrder(ordersave);
		
		for(int i=0;i< order.getListproduct().size();i++) {
			
			DetailOrderEntity detailorder= new DetailOrderEntity();
			detailorder.setOrderid(ordersaved.getId());
			detailorder.setQuantity(order.getListproduct().get(i).getQuantity());
			detailorder.setProductid(order.getListproduct().get(i).getIdproduct());
			detailorder.setSizeid(Long.valueOf(order.getListproduct().get(i).getSizeid()));
//			detailorder.setPrice_per_product( order.getListproduct().get(i).getPrice_per_product());
			detailOrderService.saveDetailOrderEntity(detailorder);
			
			
			if(order.getListproduct().get(i).getCartid() !=null) {
				
					CartItemEntity cartItem=cartItemService.findById(order.getListproduct().get(i).getCartid());
					cartItemService.deleteById(cartItem.getId());
					ProductSizeEntity productSize=productSizeService.findByProductidAndSizeid(order.getListproduct().get(i).getIdproduct(), Integer.valueOf(order.getListproduct().get(i).getSizeid()));
					if(productSize !=null) {
					int quantity=productSize.getQuantity();
					productSize.setQuantity(quantity-detailorder.getQuantity());
					productSize.setquantitysold(detailorder.getQuantity());
					productSizeService.saveProductSizeEntity(productSize);
					System.out.println("Cart Item can xoa la: "+cartItem.getId());
					
				}
			}

			
		}
		if(Long.valueOf(ordersaved.getVoucherid()) !=null && ordersaved.getVoucherid() !=0) {
			VochourEntity vochour=voucherService.findById(ordersaved.getVoucherid());
			if(vochour !=null) {
				int quantityvoucher=vochour.getQuuantity();
				vochour.setQuuantity(quantityvoucher-1);
				voucherService.addVoucher(vochour);
			}
		}
		if(ordersave.isIsonline()){
			Payment payment=paypallService.createPaymet(Double.valueOf(ordersave.getSumprice()), 
					"USD", 
					"paypal", 
					"ORDER" ,
					ordersave.getNote(), 
					"http://localhost:8080/"+CANCEL_URL, 
					"http://localhost:8080/"+SUCCESS_URL);
			for(Links link:payment.getLinks()) {
				if(link.getRel().equals("approval_url")) {
					return "redirect:"+link.getHref();
				}
			}
			
			
		}
//		return "redirect:/demopaypall";
		return "redirect:/shopping-with-me/orders";
		
	}
	
	@GetMapping("/shopping-with-me/orders")
	public String getListOrderByUserId(Model model) {
		
		List<ImageInfo> listImgSupplier = uploadService.loadAll().map(path -> {
		      String filename = path.getFileName().toString();
		      String url = MvcUriComponentsBuilder
		          .fromMethodName(SupplierController.class, "getImage", path.getFileName().toString()).build().toString();

		      return new ImageInfo(filename, url);
		    }).collect(Collectors.toList());
		
		int userid=Integer.valueOf(session.getAttribute("userId").toString());
		List<OrderDTO> listorder=new ArrayList<>();
		List<OrderEntity> listorderDB=orderService.findByUserid(userid);
		
		for(int i=0;i<listorderDB.size();i++) {
			
			OrderDTO order= new OrderDTO();
			order.setOrderdate(listorderDB.get(i).getOrderdate().toString());
			order.setIsonline(listorderDB.get(i).isIsonline());
			order.setMoney(listorderDB.get(i).getMoney());
			order.setSumprice(listorderDB.get(i).getSumprice());
			order.setSumquantity(listorderDB.get(i).getSumquantity());
			order.setIdstatusorder(listorderDB.get(i).getIdstatusorder());
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
		model.addAttribute("listorders", listorder);
		return "list_order_by_userid";
	}
	@GetMapping(value = CANCEL_URL)
    public String cancelPay() {
		return "redirect:/shopping-with-me/cart";
        
    }

    @GetMapping(value = SUCCESS_URL)
    public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) {
        try {
            Payment payment = paypallService.executePayment(paymentId, payerId);
            System.out.println(payment.toJSON());
            if (payment.getState().equals("approved")) {
            	return "redirect:/shopping-with-me/orders";
            }
        } catch (PayPalRESTException e) {
         System.out.println(e.getMessage());
        }
        return "redirect:/";
    }
	
}
