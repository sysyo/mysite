package com.douzone.mysite.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.douzone.mysite.exception.GalleryServiceException;
import com.douzone.mysite.repository.GalleryRepository;
import com.douzone.mysite.vo.GalleryVo;

@Service
public class GalleryService {
   private static String SAVE_PATH = "/upload-mysite";    //c드라이브 저장
   private static String URL_BASE = "/gallery/images";      // 가상 url
   
   @Autowired
   private GalleryRepository galleryRepository;

   //select
   public List<GalleryVo> getImages() {
      return galleryRepository.findAll();
   }
   
   //delete
   public Boolean removeImage(Long no) {
      return galleryRepository.delete(no);
   }

   // image file upload
   public void saveImage(MultipartFile file, String comments) throws GalleryServiceException {
      try {
         File uploadDirectory = new File(SAVE_PATH);
         
         if(!uploadDirectory.exists()) {
            uploadDirectory.mkdir();
         }
         
         if(file.isEmpty()) {
            throw new GalleryServiceException("file upload error: image empty");
         }
         
         String originFilename = file.getOriginalFilename();
         String extName = originFilename.substring(originFilename.lastIndexOf('.')+1);
         // 파일 이름에서 png 부분만 자르는 역할
         String saveFilename = generateSaveFilename(extName);
         
         
         byte[] data = file.getBytes(); // 바이트로 변경
         OutputStream os = new FileOutputStream(SAVE_PATH + "/" + saveFilename); // 실제 내c드라이브에 저장되는 경로
         os.write(data);
         os.close();
         
         // 가상 url을 만들어서 연결시켜준다  db에 넣어줌
         GalleryVo vo = new GalleryVo();
         vo.setUrl(URL_BASE + "/" + saveFilename);
         vo.setComments(comments);
         
         galleryRepository.insert(vo);
         //
      } catch(IOException ex) {
         throw new GalleryServiceException("file upload error:" + ex);
      }
   }
   
   // 새로운 이름 만드는 구조
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