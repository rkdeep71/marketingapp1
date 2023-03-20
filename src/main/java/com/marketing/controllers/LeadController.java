package com.marketing.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.marketing.dto.LeadData;
import com.marketing.entities.Lead;
import com.marketing.services.LeadService;
import com.marketing.util.EmailService;
import com.marketing.util.EmailServiceImpl;

@Controller
public class LeadController {
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private LeadService leadService;
	
		
	//handler methods
	
	//http://localhost:8181/create
	
	@RequestMapping("/create")
	public String viewCreateLeadForm() {
		return "create_lead";
	}
	
	
	//first way to read form data(directly copy the data into entity class object) 
	//http://localhost:8181/saveLead
	
//	@RequestMapping("/saveLead")
//	public String saveLead(Lead lead) {
//		System.out.println(lead.getFirstName());
//		System.out.println(lead.getLastName());
//		System.out.println(lead.getEmail());
//		System.out.println(lead.getMobile());
		
//		leadService.saveOneLead(lead);
//		return "create_lead";
//	}
	//--------------------------------------------------------------------
	//Second way to read form data(use @RequestParam to read data one-by-one)
	//http://localhost:8181/saveLead
	
//	@RequestMapping("/saveLead")
//	public String saveLead(@RequestParam("firstName") String firstName,@RequestParam("lastName") String lastName,@RequestParam("email") String email,@RequestParam("mobile") long mobile) {
//		System.out.println(firstName);
//		System.out.println(lastName);
//		System.out.println(email);
//		System.out.println(mobile);
		
//		Lead lead = new Lead();
//		lead.setFirstName(firstName);
//		lead.setLastName(lastName);
//		lead.setEmail(email);
//		lead.setMobile(mobile);
//		leadService.saveOneLead(lead);
//		return "create_lead";
//	}
	
	//-------------------------------------------------------------------------
	//third way to read form data(Data Transfer object where in the data from the form will directly go to the object.)
	
	@RequestMapping("/saveLead")
	public String saveLead(LeadData leadData, ModelMap model) {
//		System.out.println(leadData.getFirstName());
//		System.out.println(leadData.getLastName());
//		System.out.println(leadData.getEmail());
//		System.out.println(leadData.getMobile());
		
		Lead lead = new Lead();
		lead.setFirstName(leadData.getFirstName());
		lead.setLastName(leadData.getLastName());
		lead.setEmail(leadData.getEmail());
		lead.setMobile(leadData.getMobile());
		leadService.saveOneLead(lead);
		emailService.sendSimpleMail(leadData.getEmail(),"Test","Test Email");
		model.addAttribute("msg", "Lead is saved!!!");
		return "create_lead";
	}
	
	//http://localhost:8181/listall
	
	@RequestMapping("/listall")
	public String listLeads(ModelMap model){
		List<Lead> leads = leadService.getAllLeads();
		model.addAttribute("leads", leads);
		return "list_lead";
	}
	
	@RequestMapping("/deleteLead")
	public String deleteLead(@RequestParam("id") long id, ModelMap model) {
		leadService.deleteOneLead(id);
		List<Lead> leads = leadService.getAllLeads();
		model.addAttribute("leads", leads);
		return "list_lead";
	}
	
	
	@RequestMapping("/updateLead")
	public String updateLead(@RequestParam("id") long id, Model model) {
		Lead lead = leadService.getById(id);
		model.addAttribute("lead", lead);
		return "update_lead";
	}
	
	
	@RequestMapping("/updateOneLead")
	public String updateOneLead(@ModelAttribute("lead") Lead lead, Model model) {
		leadService.saveOneLead(lead);
		List<Lead> leads = leadService.getAllLeads();
		model.addAttribute("leads", leads);
		return "list_lead";
	}
	
}
