package com.github.emraxxor.fstack.demo.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.annotation.PostConstruct;

import com.github.emraxxor.fstack.demo.storage.StorageProperties;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Base64Utils;

import com.github.emraxxor.fstack.demo.data.type.DefaultFileInfo;
import com.github.emraxxor.fstack.demo.data.type.FileInfo;
import com.github.emraxxor.fstack.demo.data.type.ImageData;
import lombok.SneakyThrows;
import lombok.Synchronized;
import lombok.extern.log4j.Log4j2;

/**
 * 
 * @author Attila Barna
 *
 * @param <P>
 */
@Log4j2
public abstract class BaseStorageService<P extends StorageProperties> {

	@Autowired
	private P properties;
	
	@PostConstruct
	@SneakyThrows
	public void init() {
		var fold = new File( properties.getStorage() );
		if ( !fold.exists() )
			FileUtils.forceMkdir(fold);
	}
	
	private File storeFile(byte[] data) {
		File f = generateRandomFileName();
		FileOutputStream fos = null;
		try {
			f.createNewFile();
			fos = new FileOutputStream(f);
			fos.write(data);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		} finally {
			if ( fos != null ) {
				try {
					fos.close();
				} catch (IOException e) {
					log.error(e.getMessage(),e);
				}
			}
		}
		return f;
	}
	
	private File generateRandomFileName() {
		String path = properties.getStorage();
		String randomFileName = RandomStringUtils.random(50, true, true);
		File f;
		
		while( ((f=new File(path + "/" + randomFileName )).exists())  ) 
			randomFileName = RandomStringUtils.random(50, true, true);
		
		return f;
	}

	@Synchronized
	public Boolean deleteFile(String fileName) {
		try {
			FileUtils.forceDelete(new File(properties.getStorage() + '/' + fileName));
			return true;
		} catch (IOException e) {
			log.error(e.getMessage(),e);
		}
		return false;
	}

	@Synchronized
	public ImageData file(String name) throws IOException {
		return new ImageData(Base64Utils.encodeToString( FileUtils.readFileToByteArray( new File(properties.getStorage() + "/" + name) ) ) );
	}
	
	@Synchronized
	@SneakyThrows
	public boolean remove(String name) {
		return new File(this.properties+"/"+name).delete();
	}
	
	@Synchronized
	public FileInfo storeFile(String base64data) {
		return new DefaultFileInfo( storeFile(Base64Utils.decodeFromString(base64data)) );
	}
}
