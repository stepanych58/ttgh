package com.stbegradleapp.fixer.storage;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import java.nio.file.Path;
import java.util.stream.Stream;
public interface StorageService {

	void init();

	void store(MultipartFile file);
	void store(MultipartFile file, String userId);

	Stream<Path> loadAll();

	Path load(String filename);

	Resource loadAsResource(String filename);

	void deleteAll();

}
