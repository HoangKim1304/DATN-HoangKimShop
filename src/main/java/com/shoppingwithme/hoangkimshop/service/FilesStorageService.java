package com.shoppingwithme.hoangkimshop.service;

import java.nio.file.Path;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FilesStorageService {

	public void init();

	public void save(MultipartFile file);

	public boolean delete(String filename);

	public void deleteAll();

	public Stream<Path> loadAll();

	public Resource load(String filename);
}
