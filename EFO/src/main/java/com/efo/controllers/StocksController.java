package com.efo.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.efo.entity.Stocks;
import com.efo.service.StocksService;

@Controller
@RequestMapping("/accounting/")
public class StocksController {
	private final String pageLink = "/accounting/stockspaging";
	private PagedListHolder<Stocks> stocksList;
	
	@Autowired
	private StocksService stocksService;
	
	private SimpleDateFormat dateFormat;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}
	
	@RequestMapping("stockholderlist")
	public String stockholderList(Model model) {
		
		stocksList = stocksService.retrieveList();
		
		return "stockholderlist";
	}
	
	@RequestMapping(value = "stockspaging", method = RequestMethod.GET)
	public String handleAssetsRequest(@ModelAttribute("page") String page, Model model) throws Exception {
		int pgNum;

		pgNum = isInteger(page);

		if ("next".equals(page)) {
			stocksList.nextPage();
		} else if ("prev".equals(page)) {
			stocksList.previousPage();
		} else if (pgNum != -1) {
			stocksList.setPage(pgNum);
		}
		model.addAttribute("objectList", stocksList);
		model.addAttribute("pagelink", pageLink);

		return "stockholderlist";
	}

	/**************************************************************************************************************************************
	 * Used for both detecting a number, and converting to a number. If this
	 * routine returns a -1, the input parameter was not a number.
	 * 
	 **************************************************************************************************************************************/

	private int isInteger(String s) {
		int retInt;
		try {
			retInt = Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return -1;
		} catch (NullPointerException e) {
			return -1;
		}
		// only got here if we didn't return false
		return retInt;
	}

}
