package com.mab.activity.repository;

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

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class ActivityFAOImpl implements ActivityFAO {

	@Autowired
	ServletContext context;

	private String S3Bucket = "meet-a-bwa/activity"; // Bucket 이름

	@Autowired
	AmazonS3Client amazonS3Client;

	@Override
	public ActivityVO activity_fileupload(ActivityVO avo, MultipartHttpServletRequest mtfRequest,
			MultipartFile multipartFile_activity) {
		
		log.info("{} byte", multipartFile_activity.getSize());
		
		if (multipartFile_activity.getSize() > 0) {
			log.info("filename : {}", multipartFile_activity.getOriginalFilename());
			List<MultipartFile> imgs = mtfRequest.getFiles("multipartFile_activity");

			String originFileName = UUID.randomUUID() + "."
					+ StringUtils.getFilenameExtension(imgs.get(0).getOriginalFilename());
			long fileSize = imgs.get(0).getSize();

			log.info("originFileName : {}", originFileName);
			log.info("fileSize : {}", fileSize);

			ObjectMetadata objectMetaData = new ObjectMetadata();
			objectMetaData.setContentType(imgs.get(0).getContentType());
			objectMetaData.setContentLength(fileSize);
			try {

				amazonS3Client.putObject(
						new PutObjectRequest(S3Bucket, originFileName, imgs.get(0).getInputStream(), objectMetaData)
								.withCannedAcl(CannedAccessControlList.PublicRead));

				String imagePath = amazonS3Client.getUrl(S3Bucket, originFileName).toString();
				log.info("이미지 링크 : {}", imagePath);

				avo.setActivity_image(originFileName);

			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		} else {
			avo.setActivity_image("default-image.jpg");
		}

		return avo;
	}

}
