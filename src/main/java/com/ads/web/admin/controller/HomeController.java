package com.ads.web.admin.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ads.web.admin.CustomerAdminWebApplication;
import com.ads.web.admin.StatusUIModel;
import com.ads.web.admin.UserDataHelper;
import com.ads.web.admin.model.DashboardItem;


@Controller
public class HomeController {
	@Autowired
	CustomerAdminWebApplication app;
	@Autowired
	private CommentMongoHelper commentHelper;
	@Autowired
	private UserDataHelper userHelper;
	@RequestMapping("/")
	public ModelAndView index() {
		List<IssueStatusCount> result = commentHelper.getHostingCount(userHelper.getUser().get(getUserName()).getCompanyId());
		Iterator<IssueStatusCount> iter = result.iterator();
		
		ModelAndView mav = new ModelAndView("home");
		List<DashboardItem> dashboardElements = new ArrayList<DashboardItem>();
		while(iter.hasNext()) {
			IssueStatusCount issueCount = iter.next();
			StatusUIModel statusUI = app.getStatus().get(issueCount.getStatus());
			dashboardElements.add(new DashboardItem(issueCount.getStatus(), issueCount.getCount()+"", statusUI.getDisplay(), statusUI.getStyle()));
		}
	
		mav.addObject("dashboadElements", dashboardElements);

		return mav;
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
