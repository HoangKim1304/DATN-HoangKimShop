package com.shoppingwithme.hoangkimshop;

import javax.annotation.Resource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.shoppingwithme.hoangkimshop.service.FilesStorageService;


@SpringBootApplication
public class Application implements CommandLineRunner{

	@Resource
	FilesStorageService storageService;
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		storageService.init();
		
	}

}
