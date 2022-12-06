package com.mab.activity.repository;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.mab.activity.model.ActivityVO;

public interface ActivityFAO {

	ActivityVO activity_fileupload(ActivityVO avo, MultipartHttpServletRequest mtfRequest,
			MultipartFile multipartFile_activity);

}
