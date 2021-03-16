package com.company.temp;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.temp.service.impl.EmpMapper;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

@Controller
public class ExcelPdfController {
	
	@Autowired EmpMapper empMapper;
	@Autowired DataSource datasource;

	@RequestMapping("/getEmpExcel")
	public String getEmpExcel(Model model) {
		List<Map<String,Object>> list = empMapper.getEmpList();
		System.out.println(list.get(0));
		model.addAttribute("filename","empList" );
		//대문자로 컬럼명이 들어오기 때문에 대문자로 작성한것.
		model.addAttribute("headers", new String[] {"firstName", "email"});
		model.addAttribute("datas", empMapper.getEmpList());
		return "commonExcelView";
		
	}
	
	
	@RequestMapping("/pdf/empList")
	public String getEmpListPdf(Model model) {
		model.addAttribute("filename", "/reports/empList.jasper");
		return "pdfView";
	}
	
	
	@RequestMapping("/pdf/empList2")
	public String getEmpList2Pdf(Model model, @RequestParam int deptId) {
		/*파라미터 값*/
		HashMap<String,Object> map = new HashMap<>(); 
		map.put("P_DEPARTMENT_ID", deptId);

		model.addAttribute("filename", "/reports/empList2.jasper");
		model.addAttribute("param", map);
		return "pdfView";
	}
	
	@RequestMapping("/pdf/empList4")
	public String getEmpList4Pdf(Model model) {
		model.addAttribute("filename", "/reports/empList4.jasper");
		return "pdfView";
	}
	
	@RequestMapping("/pdf/empList4jrxml")
	public void getEmpList4jrxml(HttpServletResponse response) throws Exception {
		
		Connection conn = datasource.getConnection();
		//소스파일 컴파일하여 저장 : compileReportToFile
		String jrxmlFile = getClass().getResource("/reports/empList4.jrxml").getFile();
		String jasperFile = JasperCompileManager.compileReportToFile( jrxmlFile );
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperFile, null, conn);
		JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
	}
	
		//chart 출력 핸들러
		@RequestMapping("/getDailySaleList")
		@ResponseBody
		public List<Map<String, Object>> getDailySaleList(){
			return empMapper.getDailySaleList();
		}
		
		@RequestMapping("/getMonthSaleList")
		@ResponseBody
		public List<Map<String, Object>> getMonthSaleList(){
			return empMapper.getMonthSaleList();
		}
}
