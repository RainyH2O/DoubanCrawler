package demo.rainyh2o.spider;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Spider {
    private String Url;

    public static final ArrayList<Book> Books = new ArrayList<Book>();

    public Spider() {
    }

    public Spider(String Url) {
        this.Url = Url;
    }

    public void run() {
        String result = "";
        BufferedReader in = null;
        try {
            java.net.URL realUrl = new URL(Url);
            URLConnection connection = realUrl.openConnection();
            connection.connect();
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null)
            {
                result += line + "\n";
            }
        } catch (Exception e) {
            System.out.println("Get Error!" + e);
            e.printStackTrace();
        }
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        getBook(result);
    }

    public void setUrl(String url) {
        Url = url;
    }

    private void getBook(String s) {
        String reg1 = "<li class=\"subject-item[\\s\\S]*?title=[\\s\\S]*?>([\\s\\S]*?)<\\/a";                           // BookName, subBookName
        String reg2 = "<li class=\"subject-item[\\s\\S]*?title=[\\s\\S]*?star\\sclearfix\\\">([\\s\\S]*?)<\\/div";      // Rate, RaingNumber
        String reg3 = "<li class=\"subject-item[\\s\\S]*?pub\">\\s*(.*)";                                               // Author, Publisher, Date, Price
        Pattern p1 = Pattern.compile(reg1);
        Pattern p2 = Pattern.compile(reg2);
        Pattern p3 = Pattern.compile(reg3);
        Matcher m1 = p1.matcher(s);
        Matcher m2 = p2.matcher(s);
        Matcher m3 = p3.matcher(s);

        while (m1.find()) {
            Book book = new Book();
            String[] name = m1.group(1).trim().split("\\s*<.*?>\\s*");
            if (name.length == 2) {
                name[0] += name[1];
            }
            book.setBookName(name[0]);
            if (m2.find()) {
                //System.out.println(m2.group(1).trim());
                //System.out.println("######################################");
                String ratemsg = m2.group(1).trim();
                Pattern p = Pattern.compile("(\\d.*\\d)");
                Matcher m = p.matcher(ratemsg);
                m.find();
                m.group(1);
                ArrayList<String> rate = new ArrayList<String>();
                while (m.find()) {
                    rate.add(m.group(1));
                }
                if (!rate.isEmpty()){
                    book.setRate(Float.parseFloat(rate.get(0)));
                    book.setRatingNumber(Integer.parseInt(rate.get(1)));
                }
                //System.out.println("######################################");
            }
            if (m3.find()) {
                String[] msg = m3.group(1).split("\\s*\\/\\s*");
                book.setAuthor(msg[0]);
                if (msg.length >= 3) {
                    book.setPublisher(msg[msg.length-3]);
                    book.setDate(msg[msg.length-2]);
                    book.setPrice(msg[msg.length-1]);
                }
            }
            Books.add(book);
        }
    }
}
