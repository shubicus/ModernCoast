package ke.co;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.tinylog.Logger;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class MyFileWriter {

    private MyFileWriter() {
    }

    public static void writeToExcel(List<Fare> fareList) {
        Logger.info("[TEST] == Writing data to File.");

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("MyData");

        AtomicInteger rowIndex = new AtomicInteger();
        Collections.synchronizedList(fareList).forEach(fare -> {
            Row row = sheet.createRow(rowIndex.getAndIncrement());

            int colNum = 0;
            for (int i = 0; i < 7; i++) {
                Cell cell = row.createCell(colNum++);

                switch (colNum) {
                    case 1:
                        cell.setCellValue(fare.getBus());
                        break;
                    case 2:
                        cell.setCellValue(fare.getFromTo());
                        break;
                    case 3:
                        cell.setCellValue(fare.getReportingTime());
                        break;
                    case 4:
                        cell.setCellValue(fare.getDepartureTime());
                        break;
                    case 5:
                        cell.setCellValue(fare.getPriceFrom());
                        break;
                    case 6:
                        cell.setCellValue(fare.getAvailableSeats());
                        break;
                    case 7:
                        cell.setCellValue(fare.getLink());
                        break;
                    default:
                        cell.setCellValue("XXXXXXXXXXXXXX");
                }
            }

        });

        writeWorkbookToFile(workbook);
    }

    private static void writeWorkbookToFile(Workbook workbook) {
        FileOutputStream fileOut = null;
        try {
            fileOut = new FileOutputStream("./src/main/resources/OutputJsoup.xlsx");
        } catch (FileNotFoundException e) {
            Logger.error(e, e.getMessage());
        }
        try {
            workbook.write(fileOut);
        } catch (IOException e) {
            Logger.error(e.getCause(), e.getMessage());
        }
        try {
            workbook.close();
        } catch (IOException e) {
            Logger.error(e, e.getMessage());
        }
        try {
            if (fileOut != null)
                fileOut.close();
        } catch (IOException e) {
            Logger.error(e, e.getMessage());
        }

        Logger.info("[TEST] == Done.");
    }

}
