package com.example.controller;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.model.TinDetails;
import com.example.service.ScrapeDataService;

@Controller
@RequestMapping(value = "index")
public class AssignmentController {
	private Logger LOGGER = LoggerFactory.getLogger(AssignmentController.class);


	@Autowired
	ScrapeDataService scrapeDataService;

	/**
	 * Loads the Index Page 
	 * @return String (index.html)
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String index(Model model){
		model.addAttribute("invalidTin", false);
		return "index";
	}

	/**
	 * Processes the Tin Details Request and Scrapes through the TIN Website
	 * @param tinNo
	 * @param model
	 * @return String (index.html) with appropriate data and messages
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(method = RequestMethod.POST)
	public String index(@RequestParam("tinNo") String tinNo, Model model) throws Exception {
		if(! validTinNo(tinNo)){
			model.addAttribute("invalidTin", true);
			return "index"; 
		}

		LOGGER.info("++ Valid TIN Request ++ TIN No "+tinNo);

		String url = "http://mahavat.gov.in/Tin_Search/TinSearch";

		Collection<TinDetails> tinDetails = scrapeDataService.fetchScrapedData(url, tinNo);

		if(null != tinDetails && tinDetails.size() > 0){
			model.addAttribute("results", true);
			model.addAttribute("tinDetailsList", tinDetails);
		}
		else {
			model.addAttribute("results", false);
			model.addAttribute("invalidTin", false);
		}

		return "index";
	}

	private boolean validTinNo(String tinNo) {
		boolean validTin = false;

		if(null != tinNo && tinNo.trim().length() > 0){
			validTin = true;
		}
		return validTin;
	}

}