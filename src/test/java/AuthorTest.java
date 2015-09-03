import org.junit.*;
import static org.junit.Assert.*;
import org.junit.Rule;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

public class AuthorTest {

     @Rule
     public DatabaseRule database = new DatabaseRule();

     @Test
     public void all_emptyAtFirst() {
       assertEquals(Author.all().size(), 0);
     }

     @Test
     public void equals_returnTrueIfAuthorsAreTheSame() {
       Author newAuthor = new Author("Shakespeare");
       Author secondAuthor = new Author("Shakespeare");
       assertTrue(newAuthor.equals(secondAuthor));
     }

     @Test
     public void save_savesIntoDatabase_true() {
       Author newAuthor = new Author("Shakespeare");
       newAuthor.save();
       assertTrue(Author.all().get(0).equals(newAuthor));
     }

     @Test
     public void find_findsAuthorInDatabase_true() {
       Author myAuthor = new Author("Tolstoy");
       myAuthor.save();
       Author savedAuthor = Author.find(myAuthor.getId());
       assertTrue(myAuthor.equals(savedAuthor));
     }

     @Test
     public void update_changesAuthorNameAndEnrollmentInDatabase_true() {
       Author myAuthor = new Author ("Tolstoy");
       myAuthor.save();
       String name = "Doestoevsky";
       myAuthor.update(name);
       assertTrue(Author.all().get(0).getName().equals(name));
     }

     @Test
     public void addBook_addsBookToAuthor() {
       Book myBook = new Book ("War and Peace", "classic", 1);
       myBook.save();

       Author myAuthor = new Author("Tolstoy");
       myAuthor.save();

       myAuthor.addBook(myBook);
       Book savedBook = myAuthor.getBooks().get(0);
       assertTrue(myBook.equals(savedBook));
     }

     @Test
     public void getAuthors_returnsAllBooks_ArrayList() {
       Book myBook = new Book ("War and Peace", "classic", 1);
       myBook.save();

       Author myAuthor = new Author("Tolstoy");
       myAuthor.save();

       myAuthor.addBook(myBook);
       List savedBooks = myAuthor.getBooks();
       assertEquals(savedBooks.size(), 1);
     }

     @Test
     public void delete_deletesAllAuthorsAndListAssociation () {
       Book myBook = new Book ("War and Peace", "classic", 1);
       myBook.save();

       Author myAuthor = new Author("Tolstoy");
       myAuthor.save();

       myAuthor.addBook(myBook);
       myAuthor.delete();
       assertEquals(myBook.getAuthors().size(), 0);
     }

 }//end AuthorTest class
