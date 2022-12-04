package com.mab.activity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.mab.activity.model.ActivityVO;
import com.mab.activity.repository.ActivityFAO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ActiivityFileService {

	@Autowired
	ActivityFAO fao;

	public ActivityVO activity_image_upload(ActivityVO avo, MultipartHttpServletRequest mtfRequest,
			MultipartFile multipartFile_activity) {
		return fao.backoffice_fileupload(avo,mtfRequest,multipartFile_activity);
	}

}
