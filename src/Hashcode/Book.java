public class Book{

    int id;
    int score;
    
    public static int idCounter;

    public Book(int score){
        this.id = idCounter++;
        this.score = score;
    }
}