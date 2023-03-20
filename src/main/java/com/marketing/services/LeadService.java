package com.marketing.services;

import java.util.List;
import com.marketing.entities.Lead;

public interface LeadService {
	
	public void saveOneLead(Lead lead);
	public List<Lead> getAllLeads();
	public void deleteOneLead(long id);
	public Lead getById(long id);
	
}
