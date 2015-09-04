import java.util.List;
import org.sql2o.*;
import java.util.ArrayList;


  public class Patron {
    private int id;
    private String patron_name;
    private String username;
    private String password;


    public int getId() {
      return id;
    }

    public String getPatronName() {
      return patron_name;
    }

    public String getUsername() {
      return username;
    }

    public String getPassword() {
      return password;
    }

    public Patron (String patron_name) {
      this.patron_name = patron_name;
    }

    @Override
    public boolean equals(Object otherPatron) {
      if(!(otherPatron instanceof Patron )) {
        return false;
      } else {
        Patron newPatron = (Patron) otherPatron;
        return this.getPatronName().equals(newPatron.getPatronName());
      }
    }

  public static List<Patron> all() {
      String sql = "SELECT * FROM patrons";
      try(Connection con = DB.sql2o.open()) {
          return con.createQuery(sql).executeAndFetch(Patron.class);
        }
    }

    public void save() {
      try(Connection con = DB.sql2o.open()) {
        String sql ="INSERT INTO patrons (patron_name) values (:patron_name)";
        this.id = (int) con.createQuery(sql, true)
          .addParameter("patron_name", this.patron_name)
          .executeUpdate()
          .getKey();
      }
    }

    public static Patron find(int id) {
      try(Connection con = DB.sql2o.open()){
        String sql ="SELECT * FROM patrons WHERE id=:id";
        Patron patron = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Patron.class);
        return patron;
      }
    }

    public void update(String patron_name) {
      this.patron_name = patron_name;

      try(Connection con = DB.sql2o.open()){
        String sql = "UPDATE patrons SET patron_name=:patron_name WHERE id=:id";
        con.createQuery(sql) //course_name is not this.course_name due to parameters?
          .addParameter("patron_name", patron_name)
          .addParameter("id", id)
          .executeUpdate();
      }
    }

    public void addBook(Book book) { //comes from html page
      try(Connection con = DB.sql2o.open()) {
        String sql = "INSERT INTO checkouts (patron_id, book_id) VALUES (:patron_id, :book_id)";
        con.createQuery(sql)
          .addParameter("patron_id", id)
          .addParameter("book_id", book.getId())
          .executeUpdate();
        }
    }

    public ArrayList<Book> getBooks() {
      try(Connection con = DB.sql2o.open()) {
        String sql = "SELECT book_id FROM checkouts WHERE patron_id =:patron_id";
        List<Integer> bookIds = con.createQuery(sql)
          .addParameter("patron_id", this.getId())
          .executeAndFetch(Integer.class);

          ArrayList<Book> books = new ArrayList<Book>();

        for(Integer index : bookIds) {
          String booksQuery = "SELECT * FROM books WHERE id=:index";
          Book book = con.createQuery(booksQuery)
            .addParameter("index", index)
            .executeAndFetchFirst(Book.class);
          books.add(book);
        }return books;
      }
    }

    public void delete() {
      try(Connection con = DB.sql2o.open()) {
        String deleteQuery = "DELETE FROM patrons WHERE id=:id";
        con.createQuery(deleteQuery)
        .addParameter("id", id)
        .executeUpdate();

        String joinDeleteQuery = "DELETE from checkouts WHERE patron_id=:patron_id";
        con.createQuery(joinDeleteQuery)
          .addParameter("patron_id", this.getId())
          .executeUpdate();
      }
    }
}//end Patrons class
