package com.mab.meet.service;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.mab.meet.model.MeetVO;

public interface MeetFAO {

	MeetVO meet_image_fileupload(MeetVO mvo, MultipartHttpServletRequest mtfRequest, MultipartFile multipartFile_meet);

}
