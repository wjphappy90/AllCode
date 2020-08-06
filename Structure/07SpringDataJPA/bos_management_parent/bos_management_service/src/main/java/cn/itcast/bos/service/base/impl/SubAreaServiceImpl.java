package cn.itcast.bos.service.base.impl;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.UUID;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.base.SubAreaDao;
import cn.itcast.bos.domain.base.SubArea;
import cn.itcast.bos.service.base.SubAreaService;

@Service
@Transactional
public class SubAreaServiceImpl implements SubAreaService {
	@Autowired
	private SubAreaDao dao;

	@Override
	public void save(SubArea model) {
		if(model.getId()==null){ //如果id为空表示是一个保存操作，赋ID
			model.setId(UUID.randomUUID().toString());
		}
		dao.save(model);
	}

	@Override
	public Page<SubArea> findByPage(Pageable pageable) {
		return dao.findAll(pageable);
	}
	
	
//	分区的导出
	@Override
	public void export(FileInputStream in,OutputStream out) throws IOException{
		HSSFWorkbook book = new HSSFWorkbook(in);
		HSSFSheet sheet = book.getSheetAt(0);
		List<SubArea> list = dao.findAll();
//		获取到提前设置好的样式
		HSSFCellStyle cellStyle = book.getSheetAt(1).getRow(0).getCell(0).getCellStyle();
		
		
		int rowIndex = 2;
		HSSFRow row = null;
		HSSFCell cell = null;
		for (SubArea subArea : list) {
			row = sheet.createRow(rowIndex);
//			分拣编号	省	市	区	关键字	起始号	终止号	单双号	辅助关键字
			cell = row.createCell(0);
			cell.setCellValue(subArea.getId());
			cell.setCellStyle(cellStyle);
			
			cell = row.createCell(1);
			cell.setCellValue(subArea.getArea().getProvince());
			cell.setCellStyle(cellStyle);
			
			cell = row.createCell(2);
			cell.setCellValue(subArea.getArea().getCity());
			cell.setCellStyle(cellStyle);
			
			cell = row.createCell(3);
			cell.setCellValue(subArea.getArea().getDistrict());
			cell.setCellStyle(cellStyle);
			
			cell = row.createCell(4);
			cell.setCellValue(subArea.getKeyWords());
			cell.setCellStyle(cellStyle);
			
			cell = row.createCell(5);
			cell.setCellValue(subArea.getStartNum());
			cell.setCellStyle(cellStyle);
			
			cell = row.createCell(6);
			cell.setCellValue(subArea.getEndNum());
			cell.setCellStyle(cellStyle);
			
			cell = row.createCell(7);
			cell.setCellValue(subArea.getSingle());
			cell.setCellStyle(cellStyle);
			
			cell = row.createCell(8);
			cell.setCellValue(subArea.getAssistKeyWords());
			cell.setCellStyle(cellStyle);
			
			rowIndex++;
		}
		book.write(out);
	}

	@Override
	public List showChart() {
		return dao.showChart();
	}

}
