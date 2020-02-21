import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;

import src.Hashcode.Book;

public class Library{

    public int id;
    public int bookNumber;
    public int signupTime;
    public int shipCapacity;

    public List<Book> books;
    public List<Book> chosenBooks;
    
    public int totalScore;


    public static int idCounter;

    public Library(int bookNumber, int signupTime, int shipCapacity){
        this.id = idCounter++;
        this.bookNumber = bookNumber;
        this.signupTime = signupTime;
        this.shipCapacity = shipCapacity;
        this.books = new ArrayList<Book>();
        this.chosenBooks = new ArrayList<Book>();
    }

    public void calculateScore(){
        
        int score = 0;

        for(Book book : this.books){
            score += book.score;
        }
        
        this.totalScore = score;
    }

    public void sortBooks(){

        books.sort(new Comparator<Book>(){
            @Override
            public int compare(Book a, Book b){
                return b.score - a.score;
            }
        });
    }
}