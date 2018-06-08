package com.ads.web.admin.controller;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.sort;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.Fields;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Component;

import com.ads.web.admin.FeebackRepository;
import com.ads.web.admin.FeedbackEntity;
import com.ads.web.admin.model.CommentModel;
import com.ads.web.admin.model.TravelerModel;

@Component
public class CommentMongoHelper {
	@Autowired
	private FeebackRepository repo;
	@Autowired
	private MongoTemplate mongoTemplate;

	public List<IssueStatusCount> getHostingCount(String companyId) {
		
		Criteria c = new Criteria();
		Aggregation agg = newAggregation(match(c.andOperator(Criteria.where("status").exists(true), Criteria.where("companyId").is(companyId))),
				group("status").count().as("count"), 
				project("count").and("status").previousOperation(),
				sort(Sort.Direction.DESC, "count")
		);

		// Convert the aggregation result into a List
		AggregationResults<IssueStatusCount> groupResults = mongoTemplate.aggregate(agg, FeedbackEntity.class,
				IssueStatusCount.class);
		List<IssueStatusCount> result = groupResults.getMappedResults();

		return result;

	}
public List<IssueStatusDateCount> getIssueCountByDate(String companyId) {
		
		Criteria c = new Criteria();
		Aggregation agg = newAggregation(match(c.andOperator(Criteria.where("status").exists(true), Criteria.where("companyId").is(companyId))),
				group(Fields.fields("status").and("lastUpdateDate")).count().as("count"),
				project().andExpression("dateToString(lastUpdateDate)").as("day")
				.and("status").previousOperation()
//				sort(Sort.Direction.DESC, "count")
		);

		// Convert the aggregation result into a List
		AggregationResults<IssueStatusDateCount> groupResults = mongoTemplate.aggregate(agg, FeedbackEntity.class,
				IssueStatusDateCount.class);
		List<IssueStatusDateCount> result = groupResults.getMappedResults();

		return result;

	}

	public CommentModel getById(String id) {
		return translateEntityToModel(repo.findById(id));
	}

	public List<CommentModel> findByStatus(String companyId, String status) {

		List<FeedbackEntity> entities = repo.findByCompanyIdAndStatus(companyId, status);
		List<CommentModel> models = new ArrayList<CommentModel>(entities.size());
		for (FeedbackEntity fe : entities) {
			CommentModel m = translateEntityToModel(fe);
			models.add(m);
		}
		return models;
	}
	

	private CommentModel translateEntityToModel(FeedbackEntity entity) {
		CommentModel model = new CommentModel();
		model.setId(entity.getId());
		model.setEmail(entity.getEmail());
		model.setIssueType(entity.getIssueType());
		model.setFirstName(entity.getFirstName());
		model.setLastName(entity.getLastName());
		model.setComments(entity.getComments());
		model.setSubject(entity.getSubject());
		model.setPhone(entity.getPhone());
		model.setCompanyId(entity.getCompanyId());
		model.setStatus(entity.getStatus());
		entity.setCreateDate(entity.getCreateDate());
		entity.setLastUpdateDate(entity.getLastUpdateDate());
		if (entity.getTraveler() != null) {
			TravelerModel tm = new TravelerModel();
			tm.setTravelersDepartment(entity.getTraveler().getTravelersDepartment());
			tm.setTravelersPurpose(entity.getTraveler().getTravelersPurpose());
			tm.setTravelersFromDate(entity.getTraveler().getTravelersFromDate());
			tm.setTravelersToDate(entity.getTraveler().getTravelersToDate());
			model.setTravelerModel(tm);
		}
		return model;
	}

	public String creadeFeedback(CommentModel newComment) {
		FeedbackEntity e = new FeedbackEntity();

		e.setEmail(newComment.getEmail());
		e.setIssueType(newComment.getIssueType());
		e.setFirstName(newComment.getFirstName());
		e.setLastName(newComment.getLastName());
		e.setComments(newComment.getComments());
		e.setSubject(newComment.getSubject());
		e.setPhone(newComment.getPhone());
		e.setStatus(newComment.getStatus());
		e.setId(newComment.getId());
		e.setCompanyId(newComment.getCompanyId());
		e.setLastUpdateDate(Calendar.getInstance().getTime());
		
		if (StringUtils.isBlank(newComment.getId())) {
			e.setId(UUID.randomUUID().toString());
			e.setStatus("OPEN");
			e.setCreateDate(Calendar.getInstance().getTime());
			
		}
		repo.save(e);
		return e.getId();
	}

}
