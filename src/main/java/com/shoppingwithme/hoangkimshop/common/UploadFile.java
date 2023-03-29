package com.shoppingwithme.hoangkimshop.common;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.core.io.ClassPathResource;
import org.springframework.web.multipart.MultipartFile;

public class UploadFile {

	public static boolean uploadImage(MultipartFile fileupload) {
		File filesave;
		try {
			filesave = new ClassPathResource("static/img").getFile();
			System.out.println("1 la: "+filesave.getAbsolutePath());
			System.out.println("2 la: "+File.separator);
			Path path= Paths.get(filesave.getAbsolutePath()+File.separator+fileupload.getOriginalFilename());
			System.out.println(path);
			Files.copy(fileupload.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			return true;
		} catch (IOException e) {
			
			e.printStackTrace();
			return false;
		}
		
	}
}
