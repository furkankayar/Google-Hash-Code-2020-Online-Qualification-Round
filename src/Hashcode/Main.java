import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;

public class Main{

    public static int totalBookNumber = 0;
    public static int totalLibraryNumber = 0;
    public static int deadline = 0;
    public static List<Book> books = new ArrayList<Book>();
    public static List<Library> libraries = new ArrayList<Library>();

    public static List<Library> chosenLibraries = new ArrayList<Library>();

    public static void main(String[] args){

        readFile(args[0]);

        libraries.sort(new Comparator<Library>(){
            @Override
            public int compare(Library a, Library b){
                return  (b.totalScore / b.signupTime) - (a.totalScore / a.signupTime);
            }
        });

        int totalSignup = 0;
        int index = 0;
        while(index < libraries.size()){
            
            if(totalSignup + libraries.get(index).signupTime > deadline){
                break;
            }
            totalSignup += libraries.get(index).signupTime;
            chosenLibraries.add(libraries.get(index));
            index++;
        }


        int progress = 0;

        for(Library lib : chosenLibraries){

            progress += lib.signupTime;
            if(deadline - progress > 0 && lib.shipCapacity > 0){
                int bookSendCapacity = (deadline - progress) * lib.shipCapacity;
            
                for(int i = 0 ; i < lib.books.size() && i <= bookSendCapacity ; i++){
                    lib.chosenBooks.add(lib.books.get(i));
                }
            }
        }

        /*for(Library lib : chosenLibraries){
            System.out.print(lib.id + " ");
            for(Book book : lib.chosenBooks){
                System.out.print(book.id + " ");
            } 
            System.out.println();
        }*/
        writeFile(args[1]);
    }

    public static void readFile(String fileName){

        try{
            File inputFile = new File(fileName);
            BufferedReader br = new BufferedReader(new FileReader(inputFile));

            String[] info = br.readLine().split(" ");
            totalBookNumber = Integer.parseInt(info[0]);
            totalLibraryNumber = Integer.parseInt(info[1]);
            deadline = Integer.parseInt(info[2]);

            String[] bookInfo = br.readLine().split(" ");
            for(int i = 0 ; i < bookInfo.length ; i++){
                books.add(new Book(Integer.parseInt(bookInfo[i])));
            }
            
            for(int i = 0 ; i < totalLibraryNumber ; i++){
                
                String[] libInfo = br.readLine().split(" ");
                Library lib = new Library(Integer.parseInt(libInfo[0]), Integer.parseInt(libInfo[1]), Integer.parseInt(libInfo[2]));
                String[] libBookInfo = br.readLine().split(" ");

                for(int j = 0 ; j < lib.bookNumber ; j++){
                    lib.books.add(books.get(Integer.parseInt(libBookInfo[j])));
                }
                lib.calculateScore();
                lib.sortBooks();
                libraries.add(lib);
            }

            br.close();
        }
        catch(FileNotFoundException ex){
            System.out.println("File not found!");
        }
        catch(IOException ex){
            System.out.println("Error!");
        }
        catch(NumberFormatException ex){
            System.out.println("Can not be parsed!");
        }
    }
    
    public static void writeFile(String fileName){
        File file = new File(fileName);
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            bw.write(String.valueOf(chosenLibraries.size() + "\n"));
            
            for(Library lib : chosenLibraries){

                if(lib.chosenBooks.size() == 0)
                    continue; 

                bw.write(String.valueOf(lib.id) + " " + String.valueOf(lib.chosenBooks.size()) + "\n");
                for(Book book : lib.chosenBooks){
                    bw.write(String.valueOf(book.id) + " ");
                }
                bw.write("\n");
            }

            bw.close();
        }
        catch(IOException ex){
            System.out.println("Error!");
        }
    }
}