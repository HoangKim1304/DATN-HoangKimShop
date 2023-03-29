package com.shoppingwithme.hoangkimshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.shoppingwithme.hoangkimshop.entity.SupplierEntity;
import com.shoppingwithme.hoangkimshop.service.serviceImpl.FilesStorageServiceImpl;
import com.shoppingwithme.hoangkimshop.service.serviceImpl.SupplierServiceImpl;

@Controller
public class SupplierRestController {

	@Autowired
	private SupplierServiceImpl supplierService;
	
	@Autowired
	private FilesStorageServiceImpl uploadsFiles;
	
	@PostMapping("admin/supplier-update")
	public String updateSupplier(@RequestParam MultipartFile fileupload, @ModelAttribute SupplierEntity supplier,
			Model model) {
		if( supplier==null) {
			model.addAttribute("error_update_supplier", "Failure to update supplier");
		}else {
			
			if(! fileupload.isEmpty())
			{
				supplier.setLogo(fileupload.getOriginalFilename());
				uploadsFiles.save(fileupload);
				
			}
			System.out.println("Thong tin cua supplier update la: ");
			System.out.println(supplier.getName()+", "+supplier.getAddress()+", "+supplier.getPhonenumber());
			 supplierService.saveSupplier(supplier); 
		}
			
		return "admin/ManageSupplier";
	}
		
			
		
		
	
	
	

}
