package com.shoppingwithme.hoangkimshop.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shoppingwithme.hoangkimshop.common.ExcelExporter;
import com.shoppingwithme.hoangkimshop.dto.request.FormSearchDoanhThuDTO;
import com.shoppingwithme.hoangkimshop.dto.response.RevenueStatisticsDTO;
import com.shoppingwithme.hoangkimshop.service.serviceImpl.OrderServiceImpl;
import com.shoppingwithme.hoangkimshop.service.serviceImpl.ProductCategoryServiceImpl;
import com.shoppingwithme.hoangkimshop.service.serviceImpl.StatisticalServiceImpl;
import com.shoppingwithme.hoangkimshop.service.serviceImpl.SupplierServiceImpl;

@Controller
public class RevenueStatisticsController {

	@Autowired
	private OrderServiceImpl orderService;
	
	@Autowired
	private ProductCategoryServiceImpl categoryService;
	
	@Autowired
	private SupplierServiceImpl supplierService;
	
	@Autowired
	private StatisticalServiceImpl statisticalService;
	
	@Autowired
	private HttpSession session;
	
	
	@GetMapping("/admin/revenue_statistics/{key}")
	public String getPage(Model model,HttpServletResponse response,RedirectAttributes ra, @PathVariable(name = "key", required = false)String key) throws IOException {
		System.out.println("Key la gi? "+key);
		List<RevenueStatisticsDTO> listStatisticalSearch=new ArrayList<>();
		List<RevenueStatisticsDTO> listStatistical=statisticalService.getListToStatistical();
		FormSearchDoanhThuDTO searchform=(FormSearchDoanhThuDTO)model.asMap().get("searchform");
		if(searchform !=null && key.equals("filter")) {
			for(RevenueStatisticsDTO item: listStatistical) {
				if(item.getCategory_id()==searchform.getCategoryid()&& item.getSupplier_id()==searchform.getSupplierid()&& item.getQuy()==searchform.getQuy()&& item.getYear1()==searchform.getYear()) 
				{
					
					listStatisticalSearch.add(item);
				}
			}
			
		}
		if(listStatisticalSearch.size()>0  && listStatisticalSearch !=null) {
			session.setAttribute("listStatistical_search", listStatisticalSearch);
			model.addAttribute("listStatistical", listStatisticalSearch);
			listStatistical=null;
		}
		if(key.equals("all") || searchform==null){
			model.addAttribute("listStatistical", listStatistical);
//			ra.addFlashAttribute("listStatistical_search",null);
			listStatisticalSearch=null;
//			session.setAttribute("listStatistical_search", listStatisticalSearch);
		}
		
		
		model.addAttribute("listYear", orderService.getListYear());
		model.addAttribute("listCategory", categoryService.getAllCategory());
		model.addAttribute("listSupplier", supplierService.findByDeleteflagIsFalse());
		if(key.equals("download")) {
			return "redirect:/admin/download/revenue_statistics";
			
		}
		
		return "admin/revenue_statistics";
	}
	@PostMapping("/admin/revenue_statistics/search")
	public String getResultSearch(RedirectAttributes model, @ModelAttribute(name="searchform")FormSearchDoanhThuDTO searchform) {
		System.out.println("Gia tri cua form: "+searchform);
		model.addFlashAttribute("searchform", searchform);
		return "redirect:/admin/revenue_statistics/filter";
	}
	
	@GetMapping("/admin/download/revenue_statistics")
	public void downloadList(HttpServletResponse response, Model model) throws IOException {
		response.reset();
		response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=doanhthu.xls");
        List<RevenueStatisticsDTO> listStatistical=(List<RevenueStatisticsDTO>)session.getAttribute("listStatistical_search");
        if(listStatistical==null) {
        	
        	
        	listStatistical=statisticalService.getListToStatistical();
    		
        		
        	
        }
        ByteArrayInputStream stream = ExcelExporter.contactListToExcelFile(listStatistical);
        if(stream !=null){
        	session.setAttribute("listStatistical_search", null);
        }
		IOUtils.copy(stream, response.getOutputStream());
		
	}
}
