package cn.itcast.bos.service.base;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import cn.itcast.bos.domain.base.SubArea;

public interface SubAreaService {

	public void save(SubArea model);

	public Page<SubArea> findByPage(Pageable pageable);

	void export(FileInputStream in, OutputStream out) throws IOException;

	public List showChart();

}
