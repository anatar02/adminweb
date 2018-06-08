package com.ads.web.admin.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ads.web.admin.CustomerAdminWebApplication;
import com.ads.web.admin.UserDataHelper;
import com.ads.web.admin.controller.model.TicketChartData;


@RestController
public class ApiController {
	@Autowired
	CustomerAdminWebApplication app;
	@Autowired
	private CommentMongoHelper commentHelper;
	@Autowired
	private UserDataHelper userHelper;
	
	@GetMapping("/api/ticket-status-chart")
	public List<IssueStatusDateCount> getChartStatusData() {
		List<TicketChartData> list = new ArrayList<TicketChartData>();
		list.add(new TicketChartData("2017-04-17", 1, 3, 10, 2));
		list.add(new TicketChartData("2017-04-18", 23, 33, 30, 12));
		list.add(new TicketChartData("2017-04-19", 41, 53, 10, 35));
		list.add(new TicketChartData("2017-04-20", 61, 13, 30, 2));
		List<IssueStatusDateCount> results = commentHelper.getIssueCountByDate(userHelper.getUser().get(getUserName()).getCompanyId());
		return results;
	}
	
	private String getUserName() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
		    String currentUserName = authentication.getName();
		    return currentUserName;
		}
		return null;
	
	}
}
