package com.shoppingwithme.hoangkimshop.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.shoppingwithme.hoangkimshop.common.ImageInfo;
import com.shoppingwithme.hoangkimshop.dto.FormSupplier;
import com.shoppingwithme.hoangkimshop.dto.response.SupplierDTOResponse;
import com.shoppingwithme.hoangkimshop.entity.SupplierEntity;
import com.shoppingwithme.hoangkimshop.service.serviceImpl.FilesStorageServiceImpl;
import com.shoppingwithme.hoangkimshop.service.serviceImpl.ProductServiceImpl;
import com.shoppingwithme.hoangkimshop.service.serviceImpl.SupplierServiceImpl;

@RestController
public class SupplierController {

	@Autowired
	private SupplierServiceImpl supplierService;

	@Autowired
	private FilesStorageServiceImpl uploadService;
	
	
	@Autowired
	private ProductServiceImpl productService;

	@GetMapping("/admin/supplier")
	public List<SupplierDTOResponse> getListSupplier() {
		
		List<SupplierEntity> listSupplier=supplierService.listAllSupplierEntity();
		List<SupplierDTOResponse> listSupplierShow= new ArrayList<>();
		listSupplier=supplierService.findByDeleteflagIsFalse();
		
		List<ImageInfo> listImgSupplier = uploadService.loadAll().map(path -> {
		      String filename = path.getFileName().toString();
		      String url = MvcUriComponentsBuilder
		          .fromMethodName(SupplierController.class, "getImage", path.getFileName().toString()).build().toString();

		      return new ImageInfo(filename, url);
		    }).collect(Collectors.toList());
		
		for(int i=0;i<listSupplier.size();i++) {
			
		
			ImageInfo img=new ImageInfo(listSupplier.get(i).getLogo(), listSupplier.get(i).getUrl());
			
			
			SupplierDTOResponse supplierResponse= new SupplierDTOResponse();
			supplierResponse.setId(listSupplier.get(i).getId());
			supplierResponse.setAddress(listSupplier.get(i).getAddress());
			supplierResponse.setName(listSupplier.get(i).getName());
			supplierResponse.setPhonenumber(listSupplier.get(i).getPhonenumber());
			supplierResponse.setNummberQuantityProduct(productService.countBySupplier_id(supplierResponse.getId()));
			if(listImgSupplier.indexOf(img)!=  -1) {
				
				int index=listImgSupplier.indexOf(img);
//				listSupplier.get(i).setUrl(listImgSupplier.get(index).getUrl());
				supplierResponse.setUrl(listImgSupplier.get(index).getUrl());
			}
			listSupplierShow.add(supplierResponse);
			
		}
		
		
		return listSupplierShow;
	}
	
	
	@GetMapping("/admin/supplier/not-working")
	public List<SupplierDTOResponse> listSupplierDoNotWork() {
		
		List<SupplierEntity> listSupplier=supplierService.listAllSupplierEntity();
		listSupplier=supplierService.findByDeleteflagIsTrue();
		List<SupplierDTOResponse> listSupplierShow= new ArrayList<>();
		
		List<ImageInfo> listImgSupplier = uploadService.loadAll().map(path -> {
		      String filename = path.getFileName().toString();
		      String url = MvcUriComponentsBuilder
		          .fromMethodName(SupplierController.class, "getImage", path.getFileName().toString()).build().toString();

		      return new ImageInfo(filename, url);
		    }).collect(Collectors.toList());
		
		for(int i=0;i<listSupplier.size();i++) {
			ImageInfo img=new ImageInfo(listSupplier.get(i).getLogo(), listSupplier.get(i).getUrl());
			SupplierDTOResponse supplierResponse= new SupplierDTOResponse();
			supplierResponse.setId(listSupplier.get(i).getId());
			supplierResponse.setAddress(listSupplier.get(i).getAddress());
			supplierResponse.setName(listSupplier.get(i).getName());
			supplierResponse.setPhonenumber(listSupplier.get(i).getPhonenumber());
			supplierResponse.setNummberQuantityProduct(productService.countBySupplier_id(supplierResponse.getId()));
			if(listImgSupplier.indexOf(img)!=  -1) {
				int index=listImgSupplier.indexOf(img);
//				listSupplier.get(i).setUrl(listImgSupplier.get(index).getUrl());
				supplierResponse.setUrl(listImgSupplier.get(index).getUrl());
			}
			listSupplierShow.add(supplierResponse);
		}
		
		
		return listSupplierShow;
	}
	
	
	
	
	
	@GetMapping("/images/{filename:.+}")
	  public ResponseEntity<Resource> getImage(@PathVariable String filename) {
	    Resource file = uploadService.load(filename);

	    return ResponseEntity.ok()
	        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
	  }

	@PostMapping("/admin/supplier-save")
	public ResponseEntity<?> saveSupplier(@RequestParam MultipartFile fileupload, @ModelAttribute FormSupplier supplier,
			Model model) throws IOException {

		String message = "";
		if(fileupload==null)
			System.out.println("File is null");
		if(supplier==null)
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Cann't add supplier");
		
		try {
				ModelMapper mapper=new ModelMapper();
				SupplierEntity entity=null;
				entity=mapper.map(supplier, SupplierEntity.class);
				if(fileupload !=null)
				{
					entity.setLogo(fileupload.getOriginalFilename());
					uploadService.save(fileupload);
				}
				supplierService.saveSupplier(entity);
				return ResponseEntity.ok("Uploaded file && save supplier successfully.");
	
		} catch (Exception e) {
			
			message = "Could not upload the image: " + fileupload.getOriginalFilename() + ". Error: " + e.getMessage();
			model.addAttribute("message", message);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Cann't add supplier");
			
		}

		
	}
	

	@PutMapping("/admin/supplier/change-status/{supplier_id}")
	public ResponseEntity<?> changeStatus(@PathVariable(name = "supplier_id") String supplier_id) throws IOException {

		SupplierEntity supplier=supplierService.getSupplierById(Long.parseLong(supplier_id)).get();
		if(supplier==null) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Cann't change status supplier");
		}else {
			boolean status=supplier.isFlag();
			supplier.setFlag(!status);
			supplierService.saveSupplier(supplier);
			return ResponseEntity.ok("Change status supplier successfully.");
		}
//		System.out.println("ID cua supplier change status: "+supplier_id);
//		System.out.println("Result cua supplier change status: "+result);
//		SupplierEntity entity=null;
//		if(supplier_id==null)
//			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Cann't change status supplier");
//		
//		try {
//				
//				if(result == 1)
//					entity=supplierService.changeStatusOffSupplier(Long.parseLong(supplier_id));
//				else if(result==0)
//					entity=supplierService.changeStatusOnSupplier(Long.parseLong(supplier_id));
//				if(entity !=null)
//				return ResponseEntity.ok("Change status supplier successfully.");
//				else
//					return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Cann't change status supplier");
//	
//		} catch (Exception e) {
//			
//			
//			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Cann't change status supplier");
//			
//		} 

		
	}


}
