package com.mab.meet.service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.mab.activity.model.ActivityVO;
import com.mab.meet.model.MeetVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class MeetFAOImpl implements MeetFAO {
	
	@Autowired
	ServletContext context;

	private String S3Bucket = "meet-a-bwa/meet"; // Bucket 이름

	@Autowired
	AmazonS3Client amazonS3Client;

	@Override
	public MeetVO meet_image_fileupload(MeetVO mvo, MultipartHttpServletRequest mtfRequest,
			MultipartFile multipartFile_meet) {
		log.info("{} byte", multipartFile_meet.getSize());

		if (multipartFile_meet.getSize() > 0) {
			log.info("filename : {}", multipartFile_meet.getOriginalFilename());
			List<MultipartFile> imgs = mtfRequest.getFiles("multipartFile_meet");
			
				String originFileName = UUID.randomUUID()+"."+StringUtils.getFilenameExtension(imgs.get(0).getOriginalFilename());
				long fileSize = imgs.get(0).getSize();

				System.out.println("originFileName : " + originFileName);
				System.out.println("fileSize : " + fileSize);

				ObjectMetadata objectMetaData = new ObjectMetadata();
				objectMetaData.setContentType(imgs.get(0).getContentType());
				objectMetaData.setContentLength(fileSize);
				try {

					amazonS3Client.putObject(
							new PutObjectRequest(S3Bucket, originFileName, imgs.get(0).getInputStream(), objectMetaData)
									.withCannedAcl(CannedAccessControlList.PublicRead));

					String imagePath = amazonS3Client.getUrl(S3Bucket, originFileName).toString();
					log.info("이미지 링크 : {}", imagePath);

					mvo.setMeet_image(originFileName);

				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

		} else {
			mvo.setMeet_image("default_image.jpg");
		}

		return mvo;
	}

}
