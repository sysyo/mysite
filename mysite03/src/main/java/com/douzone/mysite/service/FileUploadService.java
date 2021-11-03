package com.douzone.mysite.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.douzone.mysite.exception.FileUploadException;

@Service
public class FileUploadService {
	private static String SAVE_PATH = "/upload-mysite"; // C드라이브 저장 경로
	private static String URL_BASE = "/upload/images";	// 가상 url 만들어서 연결 
	
	public String restoreImage(MultipartFile file) throws FileUploadException {
		try {
			File uploadDirectory = new File(SAVE_PATH);
			if(!uploadDirectory.exists()) {
				uploadDirectory.mkdir();
			}
			
			if(file.isEmpty()) {
				throw new FileUploadException("file upload error: image empty");
			}
			
			String originFilename = file.getOriginalFilename();
			// png 만 짜르기
			String extName = originFilename.substring(originFilename.lastIndexOf('.')+1);
			String saveFilename = generateSaveFilename(extName);
			
			byte[] data = file.getBytes(); // 파일을 바이트 크기로 변경한다.
			// 실제 C 드라이브에 저장되는 경로
			// SAVE_PATH = "/update-images" 에 저장된다.
			OutputStream os = new FileOutputStream(SAVE_PATH + "/" + saveFilename);
			os.write(data);
			os.close();

			// 근데 SAVE_PATH로는  C 드라이브에 접근이 안되서 가상 url을 만들어서 연결시켜준다.
			return URL_BASE + "/" + saveFilename;
			
			 // c 드라이브 밑에 savepath 밑에 이미지가 저장되고 가상 url 까지 만든다  
			// 이거 두개를 연결시켜 주는 역할은 spring-servlet.xml에서 적어준다.
//			<!-- mvc url-resource mapping -->
//			<mvc:resources mapping="/upload/images/**" location="file:/upload-mysite/" />
	
		} catch(IOException ex) {
			throw new FileUploadException("file upload error:" + ex);
		}
	}
	
	private String generateSaveFilename(String extName) {
		String filename = "";
		
		Calendar calendar = Calendar.getInstance();
		
		filename += calendar.get(Calendar.YEAR);
		filename += calendar.get(Calendar.MONTH);
		filename += calendar.get(Calendar.DATE);
		filename += calendar.get(Calendar.HOUR);
		filename += calendar.get(Calendar.MINUTE);
		filename += calendar.get(Calendar.SECOND);
		filename += calendar.get(Calendar.MILLISECOND);
		filename += ("." + extName);
		
		return filename;
	}	
}
