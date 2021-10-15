package com.employee.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.employee.model.Employee;
import com.employee.services.EmployeeService;

@Controller
public class LoginController {

	@Autowired
	private EmployeeService employeeService;
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@GetMapping("/")
	public String home(Model model ) {
		
//		model.addAttribute("listEmployee",employeeService.getallEmployee());
//		return "home";
		
//      For Paging
		return FindPAgenaated(1,"firstname","asc", model);
		}
	
	@GetMapping("/newEmployeeForm")
	public String ShowNewEmployeeForm(Model model) {
		
		Employee employee=new Employee();
		model.addAttribute("employee",employee);
		
		return "Add_Employee";
	}
	
	@PostMapping("/saveEmployee")
	public String saveEmployee(@ModelAttribute("employee") Employee employee) {
		employeeService.saveEMployee(employee);
		return "redirect:/";	
	}
	
	@GetMapping("/showFormForUpdate/{id}")
	public String ShowFornForUpdate(@PathVariable(value="id") Long id,Model model) {
	
	Employee employeeById = employeeService.getEmployeeById(id);	
	model.addAttribute("employee", employeeById);
		return "Update_Employee";
	}
	
	@GetMapping("/deleteEmployee/{id}")
	public String DeleteEmployee(@PathVariable(value="id") Long id) {
		employeeService.DeleteEmplyeeById(id);
		
		return "redirect:/";
	}
	
	@GetMapping("/page/{pageno}")
	public String FindPAgenaated(@PathVariable(value = "pageno") int pageNo,@RequestParam("sortField") String sortField,
			@RequestParam("sortDir") String sortDir,Model model) {
		
		int pageize=5;
		
		Page<Employee> page=employeeService.findPaginated(pageNo, pageize,sortField,sortDir);
		List<Employee> listEmployee=page.getContent();
		
		model.addAttribute("currentpages",pageNo);
		model.addAttribute("totalPages",page.getTotalPages());
		model.addAttribute("TotalItems",page.getTotalElements());
		model.addAttribute("listEmployee", listEmployee);
		
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

		return "home";
	}
}

