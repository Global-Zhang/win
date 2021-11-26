package com.hereWeGo.manager.service;

import com.hereWeGo.common.result.FileResult;

import java.io.InputStream;

/**
 * 上传服务
 */
public interface UploadService {
	/**
	 * 上传服务
	 * @param inputStream
	 * @param fileName
	 * @return
	 */
	FileResult upload(InputStream inputStream, String fileName);
}
