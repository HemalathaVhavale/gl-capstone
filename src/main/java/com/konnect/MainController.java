package com.konnect;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.konnect.dao.error_finalRepository;
import com.konnect.dao.error_tblRepository;
import com.konnect.model.error_final;
import com.konnect.model.error_tbl;
import org.springframework.stereotype.Controller;
import com.konnect.integration.JiraClient;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@Controller
public class MainController {
	
	@Autowired
	private error_tblRepository error_tblRepository;
	@Autowired
	private error_finalRepository error_finalRepository;
	private JiraClient jiraclient = new JiraClient("jira","kamisama123","http://3.236.18.232:8080");
	int size=3;
	 
    @GetMapping("/konnecterror")
    public String error(final Model model) {
		 int page_num=0;
         Page<error_tbl> error_tbl = (Page<com.konnect.model.error_tbl>)error_tblRepository.findAllErrorsJiraFalse(PageRequest.of(page_num,size));
		 model.addAttribute("error_tbls", error_tbl);
		 model.addAttribute("msg", "This lists all the errors tabulated from customer audio");
		 model.addAttribute("page_num",page_num);
		 return "konnecterror";
    }	 
    
    @PostMapping("/konnecterror")
    public String errorPage(@RequestParam(value = "page_num", required = true) int page_num,final Model model) {
    	if (page_num<0)
    		page_num=0;
		
         Page<error_tbl> error_tbl = (Page<com.konnect.model.error_tbl>)error_tblRepository.findAllErrorsJiraFalse(PageRequest.of(page_num,size));
		 model.addAttribute("error_tbls", error_tbl);
		 model.addAttribute("msg", "This lists all the errors tabulated from customer audio");
		 model.addAttribute("page_num",page_num);
		 return "konnecterror";
    }
    
    @GetMapping("/fixed")
    public String fixed(final Model model) {
       	 int page_num=0;
		 Page<error_tbl> error_tbl = (Page<error_tbl>)error_tblRepository.findAllErrorsSolutionFalseJiraTrue(PageRequest.of(page_num,size));
		 model.addAttribute("error_tbls", error_tbl);
		 model.addAttribute("msg","This lists all the errors integrated with Jira.If fixed,please provide the solution");
		 model.addAttribute("page_num",page_num);
		 return "fixed";
   }
    
    @PostMapping("/fixed")
    public String fixed(@RequestParam(value = "page_num", required = true) int page_num,final Model model) {
    	if (page_num<0)
    		page_num=0;
    	
		 Page<error_tbl> error_tbl = (Page<error_tbl>)error_tblRepository.findAllErrorsSolutionFalseJiraTrue(PageRequest.of(page_num,size));
		 model.addAttribute("error_tbls", error_tbl);
		 model.addAttribute("msg","This lists all the errors integrated with Jira.If fixed,please provide the solution");
		 model.addAttribute("page_num",page_num);
		 return "fixed";
   }
    @GetMapping("/solution")
    public String solution(final Model model) {
  	  	 int page_num=0;
  	  	 Page<error_tbl> error_tbl = (Page<error_tbl>)error_tblRepository.findAllErrorsSolutionTrueJiraTrue(PageRequest.of(page_num,size));
  		 model.addAttribute("error_tbls", error_tbl);
  		 model.addAttribute("msg","This lists all the Fixed Errors with Solution. Use it if you want to change the solution");
  		 model.addAttribute("page_num",page_num);
  		 return "solution";
    }
    
  @PostMapping("/solution")
  public String solution(@RequestParam(value = "page_num", required = true) int page_num,final Model model) {
	  	if (page_num<0)
	  		page_num=0;
	  	 Page<error_tbl> error_tbl = (Page<error_tbl>)error_tblRepository.findAllErrorsSolutionTrueJiraTrue(PageRequest.of(page_num,size));
		 model.addAttribute("error_tbls", error_tbl);
		 model.addAttribute("msg","This lists all the Fixed Errors with Solution. Use it if you want to change the solution");
		 model.addAttribute("page_num",page_num);
		 return "solution";
  }
  
 @PostMapping("/updatesolutionfixed") 
 public String updatesolutionfixed( 
     @RequestParam(value = "error_number", required = true) int error_number,@RequestParam(value = "page_num", required = true) int page_num,
     @RequestParam(value = "target_version", required = true) String target_version,
     @RequestParam(value = "Solution", required = true) String solution,Model model)
  {    
	error_tbl error_tbl = error_tblRepository.findById(error_number).get(); 
	error_tbl.solution = solution; 
	error_tbl.solution_flag=1;
	error_tbl.target_version=target_version;
	error_tblRepository.save(error_tbl);
	Page<error_tbl> error_tbl1 = (Page<error_tbl>)error_tblRepository.findAllErrorsSolutionFalseJiraTrue(PageRequest.of(page_num,size));
	model.addAttribute("error_tbls", error_tbl1);
	model.addAttribute("msg", "Successful updated Solution for eror_id:" + error_number + " solution:" + solution);
	model.addAttribute("page_num",page_num);
    return "fixed";
  }
 
 @PostMapping("/updatejiraerror") 
 public String updatejiraerror( 
     @RequestParam(value = "error_number", required = true) int error_number, @RequestParam(value = "page_num", required = true) int page_num,
     @RequestParam(value = "error_description", required = true) String error_description,
     Model model) throws Exception
  {    
	 
  /*  String error_numberS=Integer.toString(error_number);
    //String result = jiraclient.createIssue("SMARTKONNE",10100L,error_numberS,error_description);*/
    String result="true";
    if(result!=null){
    	error_tbl error_tbl = error_tblRepository.findById(error_number).get(); 
    	error_tbl.jira_flag=1;
    	error_tblRepository.save(error_tbl);
    	model.addAttribute("msg", "Successful updated Jira for error_number: " + error_number + " & Jira_id:" + " " + result);
    }else{
    	model.addAttribute("msg", "UnSuccessful updated Jira for error_number:" + error_number);
    }
   
    Page<error_tbl> error_tbl1 = (Page<error_tbl>)error_tblRepository.findAllErrorsJiraFalse(PageRequest.of(page_num,size));
	model.addAttribute("error_tbls", error_tbl1);
	model.addAttribute("page_num",page_num);
	return "konnecterror";
  }
 
 @PostMapping("/updatesolutionsolution") 
 public String updatesolutionsolution( 
     @RequestParam(value = "error_number", required = true) int error_number,@RequestParam(value = "page_num", required = true) int page_num,
     @RequestParam(value = "error_code", required = true) String error_code,
     @RequestParam(value = "error_source", required = true) String error_source,
     @RequestParam(value = "error_description", required = true) String error_description,
     @RequestParam(value = "error_category", required = true) String error_category,
     @RequestParam(value = "solution", required = true) String solution,
     @RequestParam(value = "source_version", required = true) String source_version,
     @RequestParam(value = "target_version", required = true) String target_version,
     @RequestParam(value = "created_date", required = true) String created_date,
     @RequestParam(value = "product_id", required = true) String product_id,
     Model model)
  {    
	
	/* Update into RDS */
	error_tbl error_tbl = error_tblRepository.findById(error_number).get(); 
	error_tbl.solution = solution; 
	error_tbl.target_version = target_version; 
	error_tbl.solution_flag=1;
	error_tblRepository.save(error_tbl);
	
	/*update into Master DB */
	error_final error_final = new error_final(); 
	error_final.error_number = error_number;  
	error_final.error_source=error_source;
	error_final.error_code =error_code; 
	error_final.error_description = error_description; 
	error_final.error_category = error_category; 
	error_final.solution = solution; 
	error_final.source_version = source_version; 
	error_final.target_version = target_version; 
	error_final.product_id = product_id;
	Date date = new Date();
	error_final.created_date =date;
	error_finalRepository.save(error_final);

	
	/* Display the results */
	Page<error_tbl> error_tbl1 = (Page<error_tbl>)error_tblRepository.findAllErrorsSolutionTrueJiraTrue(PageRequest.of(page_num,size));
	model.addAttribute("error_tbls", error_tbl1);
	model.addAttribute("msg", "Successful updated Solution for error_number:" + error_number + " solution:" + solution);
	model.addAttribute("page_num",page_num);
    return "solution";
  }
 
 @PostMapping("/deletesolution") 
 public String deletesolution(@RequestParam(value = "page_num", required = true) int page_num, 
	     @RequestParam(value = "error_number", required = true) int error_number,
	     Model model)
{    
	error_tblRepository.deleteById(error_number);
	Page<error_tbl> error_tbl1 = (Page<error_tbl>)error_tblRepository.findAllErrorsSolutionTrueJiraTrue(PageRequest.of(page_num,size));
	model.addAttribute("error_tbls", error_tbl1);
	model.addAttribute("msg", "Successful deleted error_number:" + error_number);
	model.addAttribute("page_num",page_num);
    return "solution";
 }
 
 @GetMapping("/about")
 public String about() {
	return "about";
 }	 
}

