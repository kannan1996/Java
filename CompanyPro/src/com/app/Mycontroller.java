package com.app;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class Mycontroller {
	
	@Autowired
	HibernateTemplate hibernatetemplate;
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@RequestMapping("/")
public  String indexpge()
	{
		return "login";
		
	}

@RequestMapping("/userLogin")
public String userLogin(HttpServletRequest req)
{
	String username=req.getParameter("username");
	String password=req.getParameter("password");
	String viewName="";
	Map<String,Object> map=jdbcTemplate.queryForMap("select count(*) from company where userid=? and password=?",new String[]{username,password});
    if(map.size()>0)
      {
	    viewName="main";
      }
   else
      {
    	viewName="error";
      }
	return viewName;
}
@RequestMapping("/insert")
public String insertComapanyRecord()
{
	return "insertcompanydetails";
}
@RequestMapping("/insertuser")
public String insertuser(HttpServletRequest request)
{
	String name=request.getParameter("name");
	String compname=request.getParameter("compname");
	String dept=request.getParameter("dept");
	String designation=request.getParameter("designation");
	
	Companydetails com=new Companydetails();
	com.setName(name);
	com.setCompname(compname);
	com.setDept(dept);
	com.setDesignation(designation);
	hibernatetemplate.persist(com);
	return "main";
}
@RequestMapping("/show")
public String ShowCompanyRecord()
{
	return "showcompany";
}
@RequestMapping("/showUser")
public ModelAndView getAllcompany()
{
	List com=hibernatetemplate.find("select * from companydet")
	ModelAndView mav=new ModelAndView();
	mav.setViewName("List");
	mav.addObject("List",com);
	return mav;
}
@RequestMapping("delete")
public String deleteCompanyRecord()
{
	return "deletestudent";
}
@RequestMapping("/deleteUser")
public String deleteUser(HttpServletRequest request)
{
	String viewname="";
	String name=request.getParameter("name");
	Map<String,Object> map=jdbcTemplate.queryForMap("delete * from companydet where name=?",new String[]{name});
	if(map.size()>0)
	{
		viewname="";
		
	}
	else
	{
		viewname="error";
	}
	return viewname;
}
}
