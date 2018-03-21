package demo.rainyh2o.spider;

    public class Book implements Comparable<Book>{
        private String BookName;
        private float Rate;
        private int RatingNumber;
        private String Author;
        private String Publisher;
        private String Date;
        private String Price;

        public Book() {
        }

        public Book(String bookName, float rate, int ratingNumber, String author, String publisher, String date, String price) {
            BookName = bookName;
            Rate = rate;
            RatingNumber = ratingNumber;
            Author = author;
            Publisher = publisher;
            Date = date;
            Price = price;
        }

        public String getBookName() {
            return BookName;
        }

        public void setBookName(String bookName) {
            BookName = bookName;
        }

        public float getRate() {
            return Rate;
        }

        public void setRate(float rate) {
            Rate = rate;
        }

        public int getRatingNumber() {
            return RatingNumber;
        }

        public void setRatingNumber(int ratingNumber) {
            RatingNumber = ratingNumber;
        }

        public String getAuthor() {
            return Author;
        }

        public void setAuthor(String author) {
            Author = author;
        }

        public String getPublisher() {
            return Publisher;
        }

        public void setPublisher(String publisher) {
            Publisher = publisher;
        }

        public String getDate() {
            return Date;
        }

        public void setDate(String date) {
            Date = date;
        }

        public String getPrice() {
            return Price;
        }

        public void setPrice(String price) {
            Price = price;
        }

        @Override
        public String toString() {
            return "Book{" +
                    "BookName='" + BookName + '\'' +
                    '}';
        }

        public int compareTo(Book o) {
            if (Rate > o.Rate) {
                return -1;
            } else if (Rate < o. Rate) {
                return 1;
            } else
                return 0;
        }
    }
