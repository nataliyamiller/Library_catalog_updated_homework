import java.util.Arrays;
import org.junit.*;
import static org.junit.Assert.*;
import org.junit.Rule;
import java.util.List;
import java.util.ArrayList;

public class BookTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Book.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfBooksAreTheSame() {
    Book firstBook = new Book ("Book", "Genre", 1);
    Book  secondBook = new Book ("Book", "Genre", 1);
    assertTrue(firstBook.equals(secondBook));
  }

  @Test
  public void save_savesIntoDatabase_true () {
    Book newBook = new Book ("Book", "Genre", 1);
    newBook.save();
    assertTrue(Book.all().get(0).equals(newBook));
  }

  @Test
  public void find_findsBookInDatabase_true() {
    Book myBook = new Book("War and Peace", "Genre", 1);
    myBook.save();
    Book savedBook = Book.find(myBook.getId());
    assertTrue(myBook.equals(savedBook));
  }

  @Test
  public void update_updatesBookTitleAndGenreInDatabase_true() {
    Book myBook = new Book("War and Peace", "Drama", 1);
    myBook.save();
    String title = "Day";
    String genre = "Comedy";
    int copies = 3;
    myBook.update(title, genre, copies);
    assertTrue(Book.all().get(0).getTitle().equals(title));
    assertTrue(Book.all().get(0).getGenre().equals(genre));
    assertTrue(Book.all().get(0).getCopies() == copies);
  }

  @Test
  public void getAuthors_returnsAllAuthors_List() {
    Author myAuthor = new Author("Tolstoy");
    myAuthor.save();
    assertTrue(myAuthor.equals(Author.all().get(0)));
  }

  @Test
  public void delete_deletesBookAndListAssociations() {
    Book myBook = new Book("War and Peace", "Drama", 1);
    myBook.save();

    Author myAuthor = new Author("Tolstoy");
    myAuthor.save();

    myAuthor.addBook(myBook);
    myBook.delete();
    assertEquals(myBook.getAuthors().size(), 0);
  }























}//end BookTest class
