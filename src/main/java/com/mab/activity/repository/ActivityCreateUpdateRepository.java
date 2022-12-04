package com.mab.activity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.mab.activity.model.ActivityVO;

public interface ActivityCreateUpdateRepository extends JpaRepository<ActivityVO, Object> {

	@Modifying
	@Transactional
	@Query(nativeQuery = true, value="INSERT INTO activity(activity_no, activity_image, activity_name, activity_info, activity_city, activity_county, activity_interest, activity_gender, activity_nop, activity_age, activity_date, recruitment_stime, recruitment_etime, activity_stime, activity_etime, user_no, meet_no, private_state) "
			+ "values('A'||seq_activity.nextval, :#{#avo?.activity_image}, :#{#avo?.activity_name}, :#{#avo?.activity_info}, :#{#avo?.activity_city}, :#{#avo?.activity_county}, :#{#avo?.activity_interest}, :#{#avo?.activity_gender},:#{#avo?.activity_nop}, :#{#avo?.activity_age}, current_date ,:#{#avo?.recruitment_stime}, :#{#avo?.recruitment_etime}, :#{#avo?.activity_stime}, :#{#avo?.activity_etime}, :#{#avo?.user_no}, :#{#avo?.meet_no}, :#{#avo?.private_state})")
	public int activity_insert(ActivityVO avo);

}
