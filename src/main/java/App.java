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


    // //Get page to view and update authors in db
    // get("/all-authors", (request, response) -> {
    //   HashMap<String, Object> model = new HashMap<String, Object>();
    //   model.put("authors", Author.all());
    //   model.put("template", "templates/all-authors.vtl");
    //   return new ModelAndView(model, layout);
    // }, new VelocityTemplateEngine());
    //
    // get("/all-books", (request, response) -> {
    //   HashMap<String, Object> model = new HashMap<String, Object>();
    //   model.put("books", Book.all());
    //   model.put("template", "templates/all-books.vtl");
    //   return new ModelAndView(model, layout);
    // }, new VelocityTemplateEngine());
    //
    //
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
