/**
 * @author ly
 * Excel数据导入数据库
 * @version 1.0
 */
package cn.sina.elec.utils.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;



import jxl.Cell;
import jxl.CellType;
import jxl.DateCell;
import jxl.Sheet;
import jxl.Workbook;

public class GenerateSqlFromExcel {

	
	/**
	 * 导入报表Excel数据，生成用户表的数据库导入语句
	 * @param formFile
	 * @return list ArrayList
	 * @throws Exception
	 */
	public static ArrayList<String[]> generateUserSql(File formFile)
			throws Exception {
		InputStream in = null;
		Workbook wb = null;   //jxl的核心对象
		ArrayList<String[]> list = new ArrayList<String[]>();
		
		try {
			if (formFile == null) {
				throw new Exception("文件为空！");
			}

			in = new FileInputStream(formFile);//将文件读入到输入流中
			
			wb = Workbook.getWorkbook(in);//从输入流中获取WorkBook对象，加载选中的excel文件
			
			Sheet sheet[] = wb.getSheets();//通过workbook对象获取sheet对象，此时sheet对象是一个数组
			if (sheet != null) {
				for (int i = 0; i < sheet.length; i++) {
					int count = i+1;
					if (!sheet[i].getName().equalsIgnoreCase("User"+count)) {					
						throw new Exception("指定文件中不包含名称为User的sheet,请重新指定！");
					}
					for (int j = 1; j < sheet[i].getRows(); j++) {//使用sheet对象用来获取每1行，从1开始表示要去掉excel的标题
						String[] valStr = new String[9];//用数组来存放每一行的数据，9表示每一行的数据不能超过9，可以<=9
						for (int k = 0; k < sheet[i].getColumns(); k++) {//使用sheet对象用来获取每1列，从0开始表示从第1列开始
							Cell cell = sheet[i].getCell(k, j);//k表示列的号，j表示行的号
							String content = "";
							if (cell.getType() == CellType.DATE) {
								DateCell dateCell = (DateCell) cell;
								java.util.Date importdate = dateCell.getDate();/**如果excel是日期格式的话需要减去8小时*/
								long eighthour = 8*60*60*1000;
								SimpleDateFormat simpledate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
								/**在当前日期上减8小时*/
								long time = importdate.getTime()-eighthour; 
								java.util.Date date = new java.util.Date();
								date.setTime(time);
								content = simpledate.format(date); 
							} else {
								String tempcontent = (cell.getContents() == null ? "" : cell.getContents());
								content = tempcontent.trim();
							}
							valStr[k] = content;//将excel获取到的值赋值给String类型的数组
							
						} 
						list.add(valStr);
					}
				}
			}
			
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (wb != null) {
				wb.close();
			}
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
