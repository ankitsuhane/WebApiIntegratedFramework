package com.orgname.test.stepdefinitions;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;



public class ExcelUtils extends GlobalConstants {
	private XSSFWorkbook xWorkbook =null;
	private XSSFSheet xSheet=null;
	private FileInputStream fis =null;
	private FileOutputStream fos =null;
	private String [] returnCellValues=null;
	private static ExcelUtils eu;
	private String filename=null;

	public ExcelUtils(String filePath, String workBook){

		try {

			workBook= appendFileExtension(workBook,GFILEEXT);
			this.filename=filePath+"\\"+workBook;
			this.fis = new FileInputStream(filename);
			this.xWorkbook = new XSSFWorkbook(fis);
			this.xSheet = xWorkbook.getSheetAt(0);

		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	private ExcelUtils(String workBook) {

		this(GHOMEPATH+GTESTDATAPATH, workBook);
	}

	private ExcelUtils() {

		this(GHOMEPATH+GTESTDATAPATH.toUpperCase(), GTESTDATAFILE.toUpperCase());

	}

	/**
	 * ExcelUtils getInstance
	 * ExcelUtils getInstance is written to initialize the filePath and WorkBook for the Excel file. ExcelUtils getInstance is an overloaded method.
	 * When a method wants to fetch data from the Default TestData file with Default location, it can be used without any parameter.  
	 * When a method wants to fetch data from any other file but with Default location, then it can be used with a WorkBook parameter.
	 * When a method wants to fetch data from any other file any any other location, then it can be used with a WorkBook and filePath parameter.
	 *
	 * @author MSP6962
	 */
	public static synchronized  ExcelUtils getInstance(){

		if(eu == null){
			eu = new ExcelUtils();
		}
		return eu; 
	}

	/**
	 * ExcelUtils getInstance
	 * ExcelUtils getInstance is written to initialize the filePath and WorkBook for the Excel file. ExcelUtils getInstance is an overloaded method.
	 * When a method wants to fetch data from the Default TestData file with Default location, it can be used without any parameter.  
	 * When a method wants to fetch data from any other file but with Default location, then it can be used with a WorkBook parameter.
	 * When a method wants to fetch data from any other file any any other location, then it can be used with a WorkBook and filePath parameter.
	 *
	 * @author MSP6962
	 */

	public static synchronized  ExcelUtils getInstance(String workBook){

		if(eu == null){
			eu = new ExcelUtils(workBook);
		}
		return eu; 
	}
	/**
	 * ExcelUtils getInstance
	 * ExcelUtils getInstance is written to initialize the filePath and WorkBook for the Excel file. ExcelUtils getInstance is an overloaded method.
	 * When a method wants to fetch data from the Default TestData file with Default location, it can be used without any parameter.  
	 * When a method wants to fetch data from any other file but with Default location, then it can be used with a WorkBook parameter.
	 * When a method wants to fetch data from any other file any any other location, then it can be used with a WorkBook and filePath parameter.
	 *
	 * @author MSP6962
	 * @param filePath is the path of the file from it is stored
	 */
	public static synchronized  ExcelUtils getInstance(String filePath, String workBook){

		if(eu == null){
			eu = new ExcelUtils(filePath, workBook);
		}
		return eu; 
	}


	private String appendFileExtension(String workBook, String ext){

		boolean flag=  workBook.endsWith(ext);

		if (flag != true)
			workBook =workBook+ext;
		return workBook;
	}

	/**
	 * getExcelValue method
	 * This method is to get a value from Excel sheet based on below parameters.
	 * This is an overloaded method written so that not everyone has to keen on giving test case number for each time you use function
	 * @param sheetName: Sheet Name
	 * @param key: Column Name
	 * @throws IOException
	 */
	public synchronized String getExcelValue(String sheetName, String key) throws  IOException{

		String currentScenarioName;
//		currentScenarioName = PropUtils.getPropLoad("Scenario");
		currentScenarioName = "Scenario";
		return getExcelValue(sheetName, currentScenarioName, key);
	}

	/**
	 * getHeaderArray method
	 * This method will return entire header in a list collection 
	 * @param sheetName: Sheet Name
	 * @return header array 
	 */
	public List<String> getHeaderArray(String sheetName) {
		LinkedList<String> header = new LinkedList<String>();
		xSheet = xWorkbook.getSheet(sheetName);
		for (int i = xSheet.getRow(0).getFirstCellNum(); i < xSheet.getRow(0)
				.getLastCellNum(); i++) {
			header.add(xSheet.getRow(0).getCell(i).getStringCellValue());
		}
		return header;
	}

	/**
	 * getExcelValue method
	 * This method is to get a value from Excel sheet based on below parameters.
	 * This is an overloaded method written so that not everyone has to keen on giving test case number for each time you use function 
	 * @param sheetName: Sheet Name
	 * @param key: Column Name
	 */
	public synchronized String getExcelValue(String sheetName, String TestCaseNo, String key){
		sheetName = sheetName.trim();
		TestCaseNo = TestCaseNo.trim();
		key = key.trim();	
		String returnCellValue = null;

		try{
			//log.info("getExcelValue method has been executed");

			int[] arrayRowCol= getRowCell(sheetName,TestCaseNo,key);
			if ((xSheet.getRow(arrayRowCol[0]).getCell(arrayRowCol[1]))!=null){
				returnCellValue=  returnCellTypeValue(arrayRowCol[0], arrayRowCol[1]);
			}
			else {
				//log.debug("Requested Cell has no value" + "sheetName: "+ sheetName+" "+"TestCaseNo: "+ TestCaseNo+"Key: "+ key);
			}
		}
		catch(Exception e){
			//log.error("getExcelValue method got into exception", e);
		}
		return returnCellValue;
	}

	/**
	 * returnCellTypeValue method
	 * This method is to get a value from Excel sheet based on below parameters.
	 * This is an overloaded method written so that not everyone has to keen on giving test case number for each time you use function 
	 * @param row: Sheet row number 
	 * @param Col: Sheet Column number
	 * @return cellText in string format. Later developer can change based on its expectations
	 */
	private String returnCellTypeValue(int row, int Col){


		Cell cell = xSheet.getRow(row).getCell(Col, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);

		if(cell.getCellType() == CellType.STRING )
			return xSheet.getRow(row).getCell(Col).getStringCellValue();
		else if(cell.getCellType()==CellType.NUMERIC || cell.getCellType()==CellType.FORMULA ){

			String cellText  = String.valueOf(cell.getNumericCellValue());
			if (DateUtil.isCellDateFormatted(cell)){
				double d = cell.getNumericCellValue();

				Calendar cal =Calendar.getInstance();
				cal.setTime(DateUtil.getJavaDate(d));
				cellText =
						(String.valueOf(cal.get(Calendar.YEAR))).substring(2);
				cellText = cal.get(Calendar.DAY_OF_MONTH) + "/" +
						cal.get(Calendar.MONTH)+1 + "/" + 
						cellText;

			}
			return cellText;
		}
		else if(cell.getCellType()==CellType.BLANK)
			return ""; 
		else 
			return String.valueOf(cell.getBooleanCellValue());
	}

	/**
	 * setExcelValue method
	 * This method is to set a value into Excel sheet based on below parameters.
	 * @param sheetName: Sheet name
	 * @param key: Key
	 * @param TestCaseNo: Name of the test case or scenario for which you want to set value
	 * @param cellValue: value that you want to set
	 * @return cellText in string format. Later developer can change based on its expectations
	 */
	public synchronized boolean setExcelValue(String sheetName, String TestCaseNo, String key, String cellValue) {

		try{
			//log.info("setExcelValue method has been executed");
			sheetName = sheetName.trim();
			TestCaseNo = TestCaseNo.trim();
			key = key.trim();

			int[] arrayRowCol= getRowCell(sheetName,TestCaseNo, key);
			System.out.println("arrayRowCol: "+ arrayRowCol[0] + " "+ arrayRowCol[1]);
			if (arrayRowCol!=null){

				if ((xSheet.getRow(arrayRowCol[0]).getCell(arrayRowCol[1]))!=null){
					xSheet.getRow(arrayRowCol[0]).getCell(arrayRowCol[1]).setCellValue(cellValue);
				}
				else {
					xSheet.getRow(arrayRowCol[0]).createCell(arrayRowCol[1]);
					xSheet.getRow(arrayRowCol[0]).getCell(arrayRowCol[1]).setCellValue(cellValue);
				}
				fis.close();

				fos = new FileOutputStream(filename);

				xWorkbook.write(fos);
				fos.close();
			}
			else{
				//log.debug("value Sheet or cell or row found"+ "sheetName: "+ sheetName+" "+"TestCaseNo: "+ TestCaseNo+"Key: "+ key);
				return false;
			}
		}
		catch(Exception e){
			//log.error("setExcelValue method got into exception", e);
			return false;
		}
		return true;
	}

	/**
	 * setExcelValue method
	 * This method is overloaded method for setExcelValue
	 * This is an overloaded method written so that not everyone has to keen on giving test case number for each time you use function 
	 * @param sheetName: Sheet name
	 * @param key: Key
	 * @param cellValue: value that you want to set
	 * @return callout of another method
	 */
	public synchronized boolean setExcelValue(String sheetName, String key, String cellValue) throws IOException{
		String currentScenarioName;
//		currentScenarioName = PropUtils.getPropLoad("Scenario");
		currentScenarioName = "Scenario";
		return setExcelValue(sheetName, currentScenarioName, key, cellValue);
	}

	/**
	 * getRowCell method
	 * This method is to return the Row and cell number based on below parameters
	 * @param sheetName: Sheet name
	 * @param key: Key
	 * @param TestCaseNo: expected test case number for which you want to get row and cell value
	 * @return arrayReturns: cell and row number in an array for expected test case  
	 */
	private int[] getRowCell(String sheetName, String TestCaseNo, String key){

		int[] arrayReturns =null;
		try{
			arrayReturns = new int[2];
			//log.info("getRowCell method has been executed");
			sheetName = sheetName.toUpperCase();
			this.xSheet = xWorkbook.getSheet(sheetName);
			if(isSheetExist(sheetName)){
				int rowSearch=0;
				int colSearch=0;
				int firstColumn=0;

				rowSearch= getTestCaseRowNum(firstColumn, TestCaseNo);
				colSearch= getKeyColNum(key);

				arrayReturns[0]= rowSearch;
				arrayReturns[1]= colSearch;
			}
			else
				return arrayReturns;

		}
		catch(Exception e){
			//log.debug("getRowCell method got into exception", e.getMessage());
		}
		return  arrayReturns;
	}
	/**
	 * getTestCaseRowNum method
	 * This method is to return the Row number for a test case based on below parameters
	 * @param firstColumn: first column where test case or scenario name are stored
	 * @param TestCaseNo: test case or Scenario name
	 * @param TestCaseNo: expected test case number for which you want to get row and cell value
	 * @return rowSearch: row number for expected test case  
	 */
	private int getTestCaseRowNum(int firstColumn, String TestCaseNo){
		int rowSearch=0;

		try{
			//log.info("getTestCaseRowNum method has been executed");
			for (rowSearch=0; rowSearch<=xSheet.getLastRowNum(); rowSearch++){
				if (xSheet.getRow(rowSearch).getCell(firstColumn).getStringCellValue().trim().equalsIgnoreCase(TestCaseNo))
					break;
			}
			if (rowSearch==xSheet.getLastRowNum()){
				throw new Exception(); 
			}


		}catch(Exception e){
			//log.debug("getTestCaseRowNum method got into exception", e.getMessage());
		}
		return rowSearch;
	}

	/**
	 * getKeyColNum method
	 * This method is written to key Column number
	 * @param key
	 * @return colSearch: Column number for expected key  
	 */
	private int getKeyColNum(String key){

		int lastColumn= xSheet.getRow(0).getLastCellNum();
		int Row=0;
		int colSearch = 0;	
		try{
			//log.info("getKeyColNum method has been executed");
			if(key!=null){
				key= key.toUpperCase();
				for (colSearch=0; colSearch<lastColumn;colSearch++){
					if (xSheet.getRow(Row).getCell(colSearch).getStringCellValue().equalsIgnoreCase(key)){
						break;
					}
					else{
						//	do nothing
					}
				}
			}
		}catch(Exception e){
			//log.error("getKeyColNum method got into exception", e);
		}
		return colSearch;
	}

	/**
	 * getTestRowArray method
	 * This method is written to get the test case entire row values 
	 * @param sheetName
	 * @param TestCaseNo
	 * @exception IOException
	 * @return returnCellValues: Collection of entire row for a test case  
	 */
	public synchronized String[] getTestRowArray(String sheetName, String TestCaseNo) throws IOException{


		try{

			//log.info("getExcelArray method has been executed");
			int[] arrayRowCol= getRowCell(sheetName, TestCaseNo, null);
			int lastColumn= xSheet.getRow(0).getLastCellNum();
			returnCellValues =  new String[lastColumn];

			for(int columnLoop = 0; columnLoop  < lastColumn; columnLoop ++){
				if ((xSheet.getRow(arrayRowCol[0]).getCell(columnLoop))!=null)
					returnCellValues[columnLoop] = returnCellTypeValue(arrayRowCol[0], columnLoop); 
			}

			this.xWorkbook.close();
			this.fis.close();
			this.xSheet= null;
			this.xWorkbook = null;
		}
		catch(Exception e){
			//log.error("getExcelArray method got into exception", e);
		}
		return returnCellValues;

	}

	/**
	 * getTestRowArray method
	 * This method is overloaded method for method getTestRowArray
	 * @param sheetName
	 * @exception IOException
	 * getTestRowArray(sheetName, TestCaseNo)
	 */

	public synchronized String[] getTestRowArray(String sheetName) throws IOException{
		String currentScenarioName;
//		currentScenarioName = PropUtils.getPropLoad("Scenario");
		currentScenarioName = "Scenario";
		return getTestRowArray(sheetName, currentScenarioName);
	}

	/**
	 * isSheetExist method
	 * This method is written to verify whether Sheet exist before doing any operation 
	 * @param sheetName
	 * @exception FileNotFoundException
	 * @exception IOException
	 * getTestRowArray(sheetName, TestCaseNo)
	 */
	private boolean isSheetExist(String sheetName){

		int index = xWorkbook.getSheetIndex(sheetName);
		if(index==-1){
			index=xWorkbook.getSheetIndex(sheetName.toUpperCase());
			if(index==-1)
				return false;
			else
				return true;
		}
		else
			return true;
	}

	/**
	 * getDataProviderArray method
	 * This method is get the data in form of Data provider   
	 * @param sheetName
	 * @exception IOException
	 * @return returnCellArray 
	 */
	public synchronized Object[][] getDataProviderArray(String sheetName) throws IOException{

		Object [][] returnCellArray = null;


		try{
			//log.info("getDataProviderArray method has been executed");
			xSheet = xWorkbook.getSheet(sheetName);

			int lastColumn= xSheet.getRow(0).getLastCellNum();
			int lastRow= xSheet.getLastRowNum();
			returnCellArray = new Object[lastRow][lastColumn]; 

			for (int rowLoop=0; rowLoop< lastRow;rowLoop++){
				for(int columnLoop = 0; columnLoop  < lastColumn; columnLoop ++){
					if ((xSheet.getRow(rowLoop+1).getCell(columnLoop+1))!=null)
						returnCellArray[rowLoop][columnLoop] = returnCellTypeValue(rowLoop+1, columnLoop+1); 
				}
			}
			this.xWorkbook.close();
			this.fis.close();
			this.xSheet= null;
			this.xWorkbook = null;
		}
		catch(Exception e){
			//log.error("getExcelArray method got into exception", e);
		}
		return returnCellArray;

	}

	/**
	 * getExcelColumnArray method
	 * This method is get the entire Column data for any given Key 
	 * @param sheetName
	 * @param key
	 * @exception IOException
	 * @return returnCellArray 
	 */
	public List<String> getExcelColumnArray(String sheetName, String key) throws IOException{

		ArrayList<String> returnCellArray = new ArrayList<>();
		key = key.trim();



		try{
			//log.info("getExcelColumnArray method has been executed");
			xSheet = xWorkbook.getSheet(sheetName);

			int lastRow= xSheet.getLastRowNum();
			int colNumber= getKeyColNum(key);

			for (int rowLoop=0; rowLoop< lastRow;rowLoop++){
				if ((xSheet.getRow(rowLoop+1).getCell(colNumber+1))!=null)
					returnCellArray.add(returnCellTypeValue(rowLoop+1, colNumber+1)); 
			}
			this.xWorkbook.close();
			this.fis.close();
			this.xSheet= null;
			this.xWorkbook = null;
		}
		catch(Exception e){
			//log.error("getExcelColumnArray method got into exception", e);
		}
		return Collections.unmodifiableList(returnCellArray);

	}

	public List<String> getPassedNonDependentScenarios(String sheetName, String key, String dependentScenarioName, String conditionColumnKey, String condition) throws IOException{

		ArrayList<String> returnCellArray = new ArrayList<>();
		key = key.trim();

		try{
			//log.info("getExcelColumnArrayScenarioDependsOn method has been executed");
			xSheet = xWorkbook.getSheet(sheetName);

			int lastRow= xSheet.getLastRowNum();
			int colNumber= getKeyColNum(key);
			int dependentScenarioCellNumber = getKeyColNum(dependentScenarioName);
			int conditionColumnNumber = getKeyColNum(conditionColumnKey);

			//			System.out.println("colNumber: "+colNumber + "dependentScenarioCellNumber: "+dependentScenarioCellNumber +"conditionColumnNumber: "+conditionColumnNumber);

			for (int rowLoop=1; rowLoop<= lastRow;rowLoop++){

				if (returnCellTypeValue(rowLoop, dependentScenarioCellNumber)=="" && xSheet.getRow(rowLoop).getCell(colNumber)!=null)
					returnCellArray.add(returnCellTypeValue(rowLoop, colNumber).trim()); 

				/*				System.out.println("xSheet.getRow(rowLoop).getCell(dependentScenarioCellNumber).getStringCellValue: "+xSheet.getRow(rowLoop).getCell(dependentScenarioCellNumber).getStringCellValue());
				System.out.println("xSheet.getRow(rowLoop).getCell(colNumber): "+xSheet.getRow(rowLoop).getCell(colNumber));
				 */				
/*				else if (xSheet.getRow(rowLoop).getCell(dependentScenarioCellNumber).getStringCellValue()!=null && xSheet.getRow(rowLoop).getCell(colNumber)!=null
						&& getRowNumberScenarioDependson (rowLoop, lastRow, colNumber, dependentScenarioCellNumber, conditionColumnNumber,condition))
					returnCellArray.add(returnCellTypeValue(rowLoop, colNumber).trim()); 
*/				
				else if (returnCellTypeValue(rowLoop, dependentScenarioCellNumber)!=null && xSheet.getRow(rowLoop).getCell(colNumber)!=null
						&& getRowNumberScenarioDependson (rowLoop, lastRow, colNumber, dependentScenarioCellNumber, conditionColumnNumber,condition))
					returnCellArray.add(returnCellTypeValue(rowLoop, colNumber).trim()); 
				else
					setExcelValue(sheetName, conditionColumnKey, "Skipped as parent scenario is not Passed");
			}
			this.xWorkbook.close();
			this.fis.close();
			this.xSheet= null;
			this.xWorkbook = null;
		}
		catch(Exception e){
			//log.error("getExcelColumnArrayScenarioDependsOn method got into exception", e);
		}
		return Collections.unmodifiableList(returnCellArray);

	}

	public List<String> getPassedNonDependentScenarios(String sheetName) throws IOException{

		String key=GlobalConstants.GSCENARIONAMEKEY;	
		String dependentScenarioName= GlobalConstants.GDEPENDENTSCENARIONAMEKEY;
		String conditionColumnKey = GlobalConstants.GEXECUTEDSELENIUM;
		String condition= GlobalConstants.GSCENARIONAMEPASSED;

		return getPassedNonDependentScenarios(sheetName, key, dependentScenarioName, conditionColumnKey, condition);
	}

	private boolean getRowNumberScenarioDependson(int rowLoop, int lastRow, int colNumber, int dependentScenarioCellNumber, int conditionColumnNumber, String condition ){
		boolean flag= false;
		String  dependentScenarioCellValue= xSheet.getRow(rowLoop).getCell(dependentScenarioCellNumber).getStringCellValue();

		for (int loopRow=1; loopRow<= lastRow;loopRow++ ){
			if (returnCellTypeValue(loopRow, colNumber).equalsIgnoreCase(dependentScenarioCellValue) && 
					returnCellTypeValue(loopRow, conditionColumnNumber).equalsIgnoreCase(condition)){
				flag= true;
				break;
			}
		}
		return flag;
	}

	
	
	//Not yet tested	
/*	public List<String> getPassedScenariosOnly(String sheetName, String key, String conditionColumnKey, String condition) throws IOException{

		ArrayList<String> returnCellArray = new ArrayList<>();
		key = key.trim();

		try{
			//log.info("getPassedScenariosOnly method has been executed");
			xSheet = xWorkbook.getSheet(sheetName);

			int lastRow= xSheet.getLastRowNum();
			int colNumber= getKeyColNum(key);
			int conditionColumnNumber = getKeyColNum(conditionColumnKey);

			for (int rowLoop=1; rowLoop<= lastRow;rowLoop++){

				if (returnCellTypeValue(rowLoop, dependentScenarioCellNumber)=="" && xSheet.getRow(rowLoop).getCell(colNumber)!=null)
					returnCellArray.add(returnCellTypeValue(rowLoop, colNumber).trim()); 

				else if (returnCellTypeValue(rowLoop, dependentScenarioCellNumber)!=null && xSheet.getRow(rowLoop).getCell(colNumber)!=null
						&& getRowNumberScenarioDependson (rowLoop, lastRow, colNumber, dependentScenarioCellNumber, conditionColumnNumber,condition))
					returnCellArray.add(returnCellTypeValue(rowLoop, colNumber).trim()); 
				else
					setExcelValue(sheetName, conditionColumnKey, "Skipped as parent scenario is not Passed");
			}
			this.xWorkbook.close();
			this.fis.close();
			this.xSheet= null;
			this.xWorkbook = null;
		}
		catch(Exception e){
			//log.error("getPassedScenariosOnly method got into exception", e);
		}
		return Collections.unmodifiableList(returnCellArray);

	}*/

	/**
	 * setExcelColArray method
	 * This method is written if you want to set same value across entire sheet irrespective to any test case or scenario Name   
	 * @param sheetName
	 * @param key
	 * @param cellValue: cell value or list of cell values. If you want to set blank to entire column send null. 
	 * if you want to set same value across the key cell column then you can send one value in array format and that value will be set to entire key column
	 * if you want to set different values across the key cell column then you can send collection of strings whose length is same as total number of scenarios mentioned in the sheet. 
	 * if any of above criteria not matches then this method will not work 
	 * @exception FileNotFoundException
	 * @exception IOException
	 * @return returnCellArray 
	 */
	public synchronized void setExcelColArray(String sheetName, String key, String[] cellValue) {
		sheetName = sheetName.trim();
		key = key.trim();

		try{
			//log.info("setExcelArray method has been executed for sheetName: "+sheetName+" key: "+ key);

			int colNumber= getKeyColNum(key);
			int lastRow= xSheet.getLastRowNum();

			if (cellValue== null){
				for(int rowLoop = 1; rowLoop   <= lastRow; rowLoop++){
					xSheet.getRow(rowLoop).createCell(colNumber);
					xSheet.getRow(rowLoop).getCell(colNumber).setCellType(CellType.BLANK);
				}
			} 

			else{
				int arrLength= cellValue.length;
				if (colNumber!= 0 && arrLength== lastRow){

					for(int rowLoop = 0; rowLoop   < lastRow; rowLoop++){

						xSheet.getRow(rowLoop).createCell(colNumber);
						xSheet.getRow(rowLoop).getCell(colNumber).setCellValue(cellValue[rowLoop]);
					}
				}
				else if (colNumber!= 0 && arrLength== 1){
					for(int rowLoop = 0; rowLoop   < lastRow; rowLoop++){
						xSheet.getRow(rowLoop).createCell(colNumber);
						xSheet.getRow(rowLoop).getCell(colNumber).setCellValue(cellValue[0]);
					}
				}

			}
			fis.close();

			fos = new FileOutputStream(filename);

			xWorkbook.write(fos);
			fos.close();

		}
		catch(Exception e){
			//log.error("setExcelArray method got into exception", e);
		}

	}
}
