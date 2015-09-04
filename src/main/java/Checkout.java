import java.util.Date;
import java.text.SimpleDateFormat;
import org.sql2o.*;

public class Checkout {

  private int book_id;
  private int patron_id;
  private Date checkoutDate;
  private Date duedate;

  public Checkout() {
  }

  public Date getCheckoutDate() {
    return checkoutDate;
  }

  public Date getDueDate() {
    return duedate;
  }

  public Date dueDate(int patron_id) {
  try(Connection con = DB.sql2o.open()) {
    String sql = "SELECT duedate FROM patrons JOIN checkouts ON (patrons.id = checkouts.patron_id)" +
    " JOIN books ON (checkouts.book_id = books.id) WHERE patrons.id = :patron_id";
      Date date = con.createQuery(sql)
      .addParameter("patron_id", patron_id)
      // .addParameter("book_id", book_id)
      .executeAndFetchFirst(Date.class);
      return date;
    }
  }

}
