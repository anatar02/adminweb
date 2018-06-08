package com.ads.web.admin;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface FeebackRepository extends MongoRepository<FeedbackEntity, String> {

	public List<FeedbackEntity> findByEmail(String email);
	public List<FeedbackEntity> findByCompanyIdAndStatus(String companyId, String status);
	public FeedbackEntity findById(String id);
}
