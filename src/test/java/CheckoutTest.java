import org.junit.*;
import static org.junit.Assert.*;
import org.junit.Rule;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Date;

public class CheckoutTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void getduedate_returnsDueDateForABook_true() {
    Book myBook = new Book("Book", "Genre", 1);
    myBook.save();
    Patron myPatron = new Patron("John Smith");
    myPatron.save();
    myPatron.addBook(myBook);
    Checkout checkout = new Checkout();
    assertEquals(checkout.dueDate(myPatron.getId()), "09/24/2015");

  }


}
