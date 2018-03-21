package demo.rainyh2o.spider;

/**
 * Main
 *
 */
public class Main
{
    public static void main( String[] args )
    {
        final int GAP = 2000;
        String file = "Result.xlsx";
        String sheet = "result";
        String[] col = {"序号", "书名", "评分", "评价人数", "作者", "出版社", "出版日期", "价格"};
        String oriurl = "https://book.douban.com/tag/%E7%BC%96%E7%A8%8B?start=";
        int page = 0;
        Spider spider = new Spider();

        for (int i = 0; i < 50; i++) {
            String url = oriurl + page;
            spider.setUrl(url);
            spider.run();
            page += 20;
            try {
                Thread.sleep(GAP);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        ExcelWrite excelwrite = new ExcelWrite(file, sheet, col);
        excelwrite.createExcel();
        excelwrite.writeExcel(Spider.Books, Spider.Books.size());
    }
}
