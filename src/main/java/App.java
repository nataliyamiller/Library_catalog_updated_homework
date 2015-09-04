import java.util.HashMap;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;
import java.util.List;
import java.util.Arrays;
import java.util.Set;

 public class App {
    public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    //Admin page for Adding/Searching/Editing Catalog
    get("/adminhome", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/admin.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/my-account", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/patron.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/add-author", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/newauthor.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());


    post("/add-author", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      String name = request.queryParams("name");
      Author newAuthor = new Author(name);
      newAuthor.save();
      response.redirect("/all-authors");
      return null;
    });

    get("/add-book", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/newbook.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());


    post("/add-book", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      String title = request.queryParams("title");
      String genre = request.queryParams("genre");
      int copies = Integer.parseInt(request.queryParams("copies"));
      Book newBook = new Book(title, genre, copies);
      newBook.save();
      response.redirect("/all-books");
      return null;
    });

    get("/all-authors", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("authors", Author.all());
      model.put("template", "templates/all-authors.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/all-books", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("books", Book.all());
      model.put("template", "templates/all-books.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/all-authors/:id", (request,response) ->{
      HashMap<String, Object> model = new HashMap<String, Object>();
      int id = Integer.parseInt(request.params("id"));
      Author author = Author.find(id);
      model.put("author", author);
      model.put("books", Book.all());
      model.put("template", "templates/author.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("all-authors/:id/addbooks", (request, response) -> {
      int bookId = Integer.parseInt(request.queryParams("book_id"));
      int authorId = Integer.parseInt(request.queryParams("author_id"));
      Author author = Author.find(authorId);
      Book book = Book.find(bookId);
      author.addBook(book);
      response.redirect("/all-authors/" + authorId);
      return null;
    });

    get("/create-account", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      // model.put("patron_name", request.session().attribute("patron_name"));
      model.put("template", "templates/create-account.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());


    post("/create-account", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      String name = request.queryParams("patron_name");
      // request.session().attribute("patron_name", patron_name);
      Patron patron = new Patron(name);
      patron.save();
      response.redirect("/patron/" + patron.getId());
      return null;
    });

    get("/patron/:id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      int id = Integer.parseInt(request.params(":id"));
      Patron patron = Patron.find(id);
      Checkout checkout = new Checkout();
      model.put("checkout", checkout);
      model.put("patron", patron);
      model.put("name", patron.getPatronName());
      model.put("books", Book.all());
      model.put("template", "templates/patron.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/patron/:id/checkout-books", (request, response) -> {
        int bookId = Integer.parseInt(request.queryParams("book_id"));
        int patronId = Integer.parseInt(request.queryParams("patron_id"));
        Patron patron = Patron.find(patronId);
        Book book = Book.find(bookId);
        patron.addBook(book);
        response.redirect("/patron/" + patronId);
        return null;
      });









    // //gets Add Book form
    // get("/add-book", (request, response) -> {
    //   HashMap<String, Object> model = new HashMap<String, Object>();
    //   model.put("template", "templates/newbook.vtl");
    //   return new ModelAndView(model, layout);
    // }, new VelocityTemplateEngine());
    //
    // post("/add-book", (request, response) -> {
    //   HashMap<String, Object> model = new HashMap<String, Object>();
    //   String title = request.queryParams("title");
    //   String genre = request.queryParams("genre");
    //   int copies = Integer.parseInt(request.queryParams("copies"));
    //
    //   Book newBook = new Book(title, genre, copies);
    //   newBook.save();
    //
    //   model.put("template", "templates/newbook.vtl");
    //   return new ModelAndView(model, layout);
    // }, new VelocityTemplateEngine());
    //
    // //gets Add Author form



    // get("/authors/:id/addbook", (request, response) -> {
    //   HashMap<String, Object> model = new HashMap<String, Object>();
    //
    //   Author author = Author.find(Integer.parseInt(request.params(":id")));
    //
    //   model.put("author", author);
    //   model.put("template", "template/newauthors.vtl");
    //   return new ModelAndView(model, layout);
    // }, new VelocityTemplateEngine());
    //
    // post("/authors/:id/addbook", (request, response) -> {
    //   HashMap<String, Object> model = new HashMap<String, Object>();
    //   Author author = Author.find(Integer.parseInt(request.params(":id")));
    //   int bookId = Integer.parseInt(request.queryParams("book_id"));
    //   Book newBook = Book.find(bookId);
    //   author.addBook(newBook);
    //
    //   response.redirect("/authors/" + author.getId() + "/addbook");
    //   return null;
    // });
    //








  }//end of main

}//end of app
