package demo.rainyh2o.spider;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.PriorityQueue;

public class ExcelWrite {
    private static final int threshold = 1000;
    private static XSSFWorkbook WorkBook = null;

    private String FileName;
    private String SheetName;
    private String ColName[];

    public ExcelWrite() {
    }

    public ExcelWrite(String fileName, String sheetName, String[] colName) {
        FileName = fileName;
        SheetName = sheetName;
        ColName = colName;
    }

    public void createExcel() {
        WorkBook = new XSSFWorkbook();
        XSSFSheet sheet = WorkBook.createSheet(SheetName);
        FileOutputStream out = null;
        try {
            XSSFRow row = sheet.createRow(0);
            for (int i = 0; i < ColName.length; i++) {
                XSSFCell cell = row.createCell(i);
                cell.setCellValue(ColName[i]);
            }
            out = new FileOutputStream(FileName);
            WorkBook.write(out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void writeExcel(ArrayList<Book> books, int rownum) {
        PriorityQueue<Book> sortedbooks = new PriorityQueue<Book>(books);
        File file = new File(FileName);
        try {
            WorkBook = new XSSFWorkbook(new FileInputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileOutputStream out = null;
        XSSFSheet sheet = WorkBook.getSheet(SheetName);
        int colnum = sheet.getRow(0).getLastCellNum();
        try {
            for (int i = 0; i < rownum; i++) {
                ArrayList<String> abookinfo = getBookinfo(sortedbooks);
                while (abookinfo.isEmpty() && !sortedbooks.isEmpty()) {
                    abookinfo = getBookinfo(sortedbooks);
                    rownum--;
                }
                if (sortedbooks.isEmpty()) {
                    break;
                }
                XSSFRow newrow = sheet.createRow(i + 1);
                XSSFCell cell = newrow.createCell(0);
                cell.setCellValue(i + 1);
                for (int j = 1; j < colnum; j++) {
                    cell = newrow.createCell(j);
                    cell.setCellValue(abookinfo.get(j - 1));
                }
            }
            out = new FileOutputStream(FileName);
            WorkBook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                assert out != null;
                out.close();
            } catch (IOException ignored) {
            }
        }
    }

    private ArrayList<String> getBookinfo(PriorityQueue<Book> sortedbook) {
        ArrayList<String>  abookinfo = new ArrayList<String>();
        Book onebook = sortedbook.poll();
            if (onebook.getRatingNumber() >= threshold) {
                abookinfo.add(onebook.getBookName());
                abookinfo.add(onebook.getRate()+"");
                abookinfo.add(onebook.getRatingNumber()+"");
                abookinfo.add(onebook.getAuthor());
                abookinfo.add(onebook.getPublisher());
                abookinfo.add(onebook.getDate());
                abookinfo.add(onebook.getPrice());
        }
        return abookinfo;
    }
}
