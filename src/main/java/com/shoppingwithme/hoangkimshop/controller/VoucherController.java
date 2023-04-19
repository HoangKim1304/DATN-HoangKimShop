package com.shoppingwithme.hoangkimshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.shoppingwithme.hoangkimshop.dto.request.FormVoucherDTO;
import com.shoppingwithme.hoangkimshop.entity.VochourEntity;
import com.shoppingwithme.hoangkimshop.service.serviceImpl.VoucherServiceImpl;

@Controller
public class VoucherController {

	@Autowired
	private VoucherServiceImpl voucherService;
	@GetMapping("/admin/voucher")
	public String getPageManageVoucher( Model model) {
		model.addAttribute("listVoucher", voucherService.findByStatus(0));
		return "admin/manage_vouchor";
	}
	
	@PostMapping("/admin/vochour/save")
	public String addVourcher(@ModelAttribute(name = "voucher") FormVoucherDTO voucher) throws Exception {
		
		VochourEntity vochourEntity= new VochourEntity();

		vochourEntity.setEndate(voucher.getStartdate());
		vochourEntity.setStartdate(voucher.getEndate());
		vochourEntity.setDiscout(voucher.getDiscout());
		vochourEntity.setStatus(0);
		vochourEntity.setMaxpricediscout(voucher.getMaxpricediscout());
		vochourEntity.setQuuantity(voucher.getQuuantity());
		vochourEntity.setMaxpriceoder(voucher.getMaxpriceoder());
		
		VochourEntity voucherSave=voucherService.addVoucher(vochourEntity);
		if(voucherSave !=null) {
			vochourEntity.setIdcode("voucher"+voucherSave.getId());
			return "redirect:/admin/voucher";
		}else {
			System.out.println("Ua sao lai NULL vay");
			return "admin/404";
		}
		
		
		
	}
	
	@GetMapping("/admin/vourcher/update/{id}")
	public String getPageUpdateVoucher( @PathVariable(name = "id") int id, Model model) {
		
		model.addAttribute("voucher", voucherService.findById(id));
		
		return "admin/form_update_voucher";
	}
	
	@PostMapping("/admin/vochour/update")
	public String updateVoucher( @ModelAttribute(name = "voucher") VochourEntity voucher) {
		
		VochourEntity entity=voucherService.findById(voucher.getId());
		if(entity==null) {
			return "admin/404";
		}else {
			voucherService.addVoucher(voucher);
			return "redirect:/admin/voucher";
		}
		
		
	}
	
	@GetMapping("/admin/vourcher/delete/{id}")
	public String deleteVoucher( @PathVariable(name = "id") int id) {
		VochourEntity entity=voucherService.findById(id);
		
		if(entity==null) {
			return "admin/404";
		}else {
			entity.setStatus(1);
			voucherService.addVoucher(entity);	
				return "redirect:/admin/voucher";
			
			
		}
		
		
		
	}
}
