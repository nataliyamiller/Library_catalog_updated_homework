import java.util.Arrays;
import org.junit.*;
import static org.junit.Assert.*;
import org.junit.Rule;
import java.util.List;
import java.util.ArrayList;

public class PatronTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void addBook_addsBookToPatron() {
    Book myBook = new Book ("War and Peace", "classic", 1);
    myBook.save();

    Patron myPatron = new Patron("John Smith");
    myPatron.save();


    myPatron.addBook(myBook, "tomorrow");
    Book savedBook = myPatron.getBooks().get(0);
    assertTrue(myBook.equals(savedBook));
  }

}
