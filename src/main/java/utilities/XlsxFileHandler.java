package utilities;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class XlsxFileHandler {
	//This method store the list of strings passed on an xlsx file
		public static void createFile(String fileName, String sheetName, String headerName, List<String> data) throws IOException, InterruptedException
		{
			Workbook workbook = new XSSFWorkbook();
			Sheet sheet = workbook.createSheet(sheetName);
			Row header = sheet.createRow(0);
			Cell headerCell = header.createCell(0);
			headerCell.setCellValue(headerName);
			
			for (int i = 0; i<data.size();i++)
			{
				Row row = sheet.createRow(i+1);
				Cell cell = row.createCell(0);
				cell.setCellValue(data.get(i));
			}

			String fileLocation = Utility.getCurrentPath().concat(String.format("target\\_outputData\\%s.xlsx",fileName));
			FileOutputStream outputStream = new FileOutputStream(fileLocation);
			workbook.write(outputStream);
			workbook.close();
		}

}
