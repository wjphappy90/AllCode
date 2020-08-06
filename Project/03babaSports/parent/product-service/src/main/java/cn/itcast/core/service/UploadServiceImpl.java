package cn.itcast.core.service;

import org.springframework.stereotype.Service;

import cn.itcast.core.common.FastDFSUtils;

@Service("uploadServiceImpl")
public class UploadServiceImpl implements UploadService {

	@Override
	public String uploadPic(byte[] pic, String name, long size) throws Exception {
		String path = FastDFSUtils.uploadPic(name, pic, size);
		return path;
	}

}
