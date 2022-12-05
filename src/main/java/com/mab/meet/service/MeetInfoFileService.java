/**
 * @author 최진실
 */
package com.mab.meet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.mab.meet.model.MeetVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MeetInfoFileService {

	@Autowired
	MeetFAO fao;

	public MeetVO meet_image_upload(MeetVO mvo, MultipartHttpServletRequest mtfRequest,
			MultipartFile multipartFile_meet) {
		return fao.meet_image_fileupload(mvo,mtfRequest,multipartFile_meet);
	}

}
