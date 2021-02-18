package com.bluetail.aero.automation;

import java.io.FileInputStream;

import java.io.FileOutputStream;
import java.util.Calendar;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * @description This class contains method to get the current Time Stamp
 * @createdAt 02/17/2020
 * @modifiedAt
 * @Modifiedby
 * @author Ashok.
 */

public class MSExcelAutomation {
	
	public String path;
	public FileInputStream fis = null;
	public FileOutputStream fileOut = null;
	private XSSFWorkbook workbook = null;
	private XSSFSheet sheet  = null;
	private XSSFRow row = null;
	private XSSFCell cell = null;
	
	public MSExcelAutomation(String path) {
		
		this.path = path;
		try{
			fis = new FileInputStream(path);
			workbook = new XSSFWorkbook(fis);
			sheet = workbook.getSheetAt(0);
			fis.close();
			
		}catch(Exception e){
			
			e.printStackTrace();
		}
	}
	
	
	
	public int getRowCount(String sheetName) {
		
		int index = workbook.getSheetIndex(sheetName);
		
		if(index == -1){
			
			return 0;
			
		}else{
			
			sheet  = workbook.getSheetAt(index);
			int number = sheet.getLastRowNum()+1;
			return number;
		}
		
	}
	
	
	
	public String getCellData(String sheetName, String colName, int rowNum) {
		
		try{
			if(rowNum <= 0){
				
				return "";
			}
			
			int index = workbook.getSheetIndex(sheetName);
			int colNum = -1;
			if(index == -1){
				
				return "";
			}
			
			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(0);
			
			for(int i=0; i<row.getLastCellNum(); i++){
				
				if(row.getCell(i).getStringCellValue().trim().equals(colName.trim())){
					
					colNum = i;
				}
			
			}
			
			if(colNum == -1){
				
				return "";
			}
			
			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(rowNum - 1);
			if(row == null){
				
				return "";
			}
			
			cell = row.getCell(colNum);
			if(cell == null){
				
				return "";
			}
			
			if(cell.getCellType() == Cell.CELL_TYPE_STRING) {
				
				return cell.getStringCellValue();
				
			}else if((cell.getCellType() ==  Cell.CELL_TYPE_NUMERIC) || (cell.getCellType() == Cell.CELL_TYPE_FORMULA) ) {
				
				int intCellText = (int) cell.getNumericCellValue();
				String cellText = Integer.toString(intCellText);
				
				if(HSSFDateUtil.isCellDateFormatted(cell)){
					
					double d = cell.getNumericCellValue();
					Calendar cal = Calendar.getInstance();
					cal.setTime(HSSFDateUtil.getJavaDate(d));
					cellText = (String.valueOf(cal.get(Calendar.YEAR)));
					cellText = cal.get(Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.MONTH)+1 + "/" + cellText;
				}
				
				return cellText;
				
			}else if(cell.getCellType() == Cell.CELL_TYPE_BLANK){
				
				return "";
				
			}else{
				
				return String.valueOf(cell.getBooleanCellValue());
			}
			
			
		}catch(Exception e){
			
			e.printStackTrace();
			return "row "+rowNum+ " or column "+colName + " does not exist in xlsx";
		}
	}
	
	
	
	public String getCellData(String sheetName, int colNum, int rowNum){
		
		try{
			if(rowNum <= 0){
				
				return "";
			}
			
			int index = workbook.getSheetIndex(sheetName);
			if(index == -1){
				
				return "";
			}
			
			sheet =  workbook.getSheetAt(index);
			row = sheet.getRow(rowNum-1);
			
			if(row == null){
				
				return "";
			}
			
			cell = row.getCell(colNum);
			if(cell == null){
				
				return "";
			}
			
			if(cell.getCellType() == Cell.CELL_TYPE_STRING){
				
				return cell.getStringCellValue();
				
			}else if((cell.getCellType() == Cell.CELL_TYPE_NUMERIC) || (cell.getCellType() == Cell.CELL_TYPE_FORMULA)){
				
				int intCellText = (int) cell.getNumericCellValue();
				String cellText = Integer.toString(intCellText);
				if(HSSFDateUtil.isCellDateFormatted(cell)){
					
					double d = cell.getNumericCellValue();
					Calendar cal = Calendar.getInstance();
					cal.setTime(HSSFDateUtil.getJavaDate(d));
					
					cellText = (String.valueOf(cal.get(Calendar.YEAR)));
					cellText = cal.get(Calendar.MONTH)+1 + "/" + cal.get(Calendar.DAY_OF_MONTH) + "/" + cellText;
				}
				
				return cellText;
				
			}else if(cell.getCellType() == Cell.CELL_TYPE_BLANK){
				
				return "";
				
			}else{
				
				return String.valueOf(cell.getBooleanCellValue());
				
			}
			
		}catch(Exception e){
			
			e.printStackTrace();
			return "row "+rowNum+" or column "+colNum+" does not exist in xls";
			
		}
	}
	
	
	
	public boolean addSheet(String sheetname){
		
		FileOutputStream fileOut;
		try{
			workbook.createSheet(sheetname);
			fileOut = new FileOutputStream(path);
			workbook.write(fileOut);
			
			fileOut.close();
			
		}catch(Exception e){
			
			e.printStackTrace();
			return false;
			
		}
		
		return true;
	}
	
	
	
	
	public boolean removeSheet(String sheetname){
		
		int index = workbook.getSheetIndex(sheetname);
		if(index == -1){
			
			return false;
		}
		
		FileOutputStream fileOut;
		
		try{
			workbook.removeSheetAt(index);
			fileOut = new FileOutputStream(path);
			workbook.write(fileOut);
			
			fileOut.close();
			
		}catch(Exception e){
			
			e.printStackTrace();
			return false;
			
		}
		
		return true;
		
	}
	
	
	
	public boolean isSheetExist(String sheetname){
		
		int index = workbook.getSheetIndex(sheetname);
		if(index == -1){
			
			index = workbook.getSheetIndex(sheetname.toUpperCase());
			if(index == -1){
				
				return false;
				
			}else{
				
				return true;
			}
			
			
		}else{
			
			return true;
		}
		
	}
	
	
	
	
	public int getColumnCount(String sheetname){
		
		if(!isSheetExist(sheetname)){
			
			return -1;
		}
		
		sheet = workbook.getSheet(sheetname);
		row = sheet.getRow(0);
		
		if(row == null){
			
			return -1;
		}
		
		return row.getLastCellNum();
		
	}
	
	
	
	
	public int getCellRowNum(String sheetName, String colName, String cellValue){
		
		for(int i=2; i<=getRowCount(sheetName); i++){
			
			if(getCellData(sheetName, colName, i).equalsIgnoreCase(cellValue)){
				
				return i;
			}
		}
		
		return -1;
		
	}

}
