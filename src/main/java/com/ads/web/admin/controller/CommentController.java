package com.ads.web.admin.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ads.web.admin.CustomerAdminWebApplication;
import com.ads.web.admin.FormSettingModel;
import com.ads.web.admin.UserDataHelper;
import com.ads.web.admin.model.CommentModel;
import com.ads.web.admin.model.IssueType;
import com.ads.web.admin.model.StatusModel;


@Controller
public class CommentController {
	@Autowired
	CustomerAdminWebApplication app;
	@Autowired
	private CommentMongoHelper commentHelper;
	@Autowired
	private UserDataHelper userHelper;
	
	
	@RequestMapping(value = "/comments/status/{status}", method = RequestMethod.GET)
	public String comments(@PathVariable("status") String status, Model model) {
		List<CommentModel> comments = commentHelper.findByStatus(userHelper.getUser().get(getUserName()).getCompanyId(), status);
		model.addAttribute("results", comments);
		return "comments-results";
	}
	
	@RequestMapping(value = "/comments", method = RequestMethod.GET)
	public String comments(Model model, @ModelAttribute CommentModel newComent) {
		String issueType = newComent.getIssueType();
		newComent.setIssueType(issueType);
		newComent.setCompanyId(userHelper.getUser().get(getUserName()).getCompanyId());
		model.addAttribute("newComment", newComent);
		if (StringUtils.isBlank(issueType)) {
			issueType = "COMPLAINT";
		}
		FormSettingModel formSettingModel = app.getIssueTypes().get(issueType);
		model.addAttribute("customForm", formSettingModel.getFragment());
		model.addAttribute("title", formSettingModel.getTitle());
		return "comments";
	}

	@RequestMapping(value = "/comments/{id}", method = RequestMethod.GET)
	public String comments(Model model, @PathVariable("id") String id) {
		CommentModel existingComment = commentHelper.getById(id);
		FormSettingModel formSettingModel = app.getIssueTypes().get(existingComment.getIssueType());
		model.addAttribute("customForm", formSettingModel.getFragment());
		model.addAttribute("title", formSettingModel.getTitle());
		model.addAttribute("newComment", existingComment);
		return "comments";
	}

	@RequestMapping(value = "/comments", method = RequestMethod.POST)
	public String newComments(Model model, @ModelAttribute CommentModel newComment) {
		commentHelper.creadeFeedback(newComment);
		model.addAttribute("newComment", new CommentModel());
		return "redirect:/";
	}
	private String getUserName() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
		    String currentUserName = authentication.getName();
		    return currentUserName;
		}
		return null;
	}

	@ModelAttribute("issueTypes")
	public List<IssueType> issueTypes() {
		List<IssueType> issuesTypes = new ArrayList<IssueType>();
		issuesTypes.add(new IssueType("COMPLAINT", "Complaint"));
		issuesTypes.add(new IssueType("ENQUIRY", "Enquiry"));
		issuesTypes.add(new IssueType("HOTEL_RESERVATION", "Hotel Reservation"));
		issuesTypes.add(new IssueType("TRAVEL_REQUEST", "Travel Request"));
		return issuesTypes;
	}
	@ModelAttribute("statusList")
	public List<StatusModel> statusList() {
		List<StatusModel> issuesTypes = new ArrayList<StatusModel>();
		issuesTypes.add(new StatusModel("OPEN", "Open"));
		issuesTypes.add(new StatusModel("CLOSED", "Closed"));
		issuesTypes.add(new StatusModel("INPROGRESS", "In Progress"));
		issuesTypes.add(new StatusModel("ESCALATING", "Escalating"));
		return issuesTypes;
	}
}
