package cn.itcast.bos.action.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import cn.itcast.bos.domain.base.Area;
import cn.itcast.bos.service.base.AreaService;
import cn.itcast.bos.utils.PinYin4jUtils;
@Controller
@Scope("prototype")
@ParentPackage("struts-default")
@Namespace("/")
public class AreaAction extends BaseAction<Area> {

	@Autowired
	private AreaService service;

	private File myFile;
	public File getMyFile() {
		return myFile;
	}
	public void setMyFile(File myFile) {
		this.myFile = myFile;
	}
	@Action(value="areaAction_importXls",
			results={@Result(name="success",location="/pages/base/area.html",type="redirect")})
	public String importXls() throws FileNotFoundException, IOException{
		System.out.println(myFile);
		HSSFWorkbook book = new HSSFWorkbook(new FileInputStream(myFile));
		HSSFSheet sheet = book.getSheetAt(0);
//		最后一行的索引
		int lastRowNum = sheet.getLastRowNum();
		HSSFRow row = null;
		Area area = null;
		List<Area> list = new ArrayList<Area>();
		for (int i = 1; i <= lastRowNum; i++) {
			area = new Area();
			row = sheet.getRow(i);
			String id = row.getCell(0).getStringCellValue();
			String province = row.getCell(1).getStringCellValue();
			String city = row.getCell(2).getStringCellValue();
			String district = row.getCell(3).getStringCellValue();
			String postcode = row.getCell(4).getStringCellValue();
			System.out.println(id+province+city+district+postcode);
			area.setId(id);
			area.setProvince(province);
			area.setCity(city);
			area.setDistrict(district);
			area.setPostcode(postcode);
			
			province = province.substring(0, province.length()-1);
			city = city.substring(0, city.length()-1);
			district = district.substring(0, district.length()-1);
//			城市编码    shijiazhuang
			String citycode = PinYin4jUtils.hanziToPinyin(city, "");
			area.setCitycode(citycode);
//			简码 
			String[] headByString = PinYin4jUtils.getHeadByString(province+city+district);
			String shortcode = StringUtils.join(headByString);
			area.setShortcode(shortcode);
			list.add(area);
			
		}
		service.save(list);
		return SUCCESS;
	}
	
	
	@Action("areaAction_findByPage")
	public void findByPage() throws IOException{
		Pageable pageable = new PageRequest(page-1, rows);
		Page<Area> page = service.findByPage(pageable);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", page.getTotalElements());
		map.put("rows", page.getContent());
		javaToJson(map, new String[]{"subareas"});
	}
	
	@Action("areaAction_findAll")
	public void findAll(){
		List<Area> list = service.findAll();
		javaToJson(list, new String[]{"subareas"});
		
	}
}
