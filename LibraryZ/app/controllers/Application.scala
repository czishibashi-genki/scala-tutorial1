package controllers

import models.dao.{BookDao, UserDao}
import models.entity.{Book, User}
import play.api.mvc._

class Application extends Controller {

  def index = Action {

//    // insert
//    var user = new User
//    user.name = "Tanaka"
//    user.nfc_device_id = "nfc_2"
//    user.chatwork_api_token = "cwuser1234"
//    //    user.image = null
//    //    user.image = Option("aiueo")
//    user = UserDao.insert(user)
//    println(user.toString)
//
//    // update
//    user.name = "HANAKO_KAI"
//    UserDao.update(user)
//    println(user.toString)
//
//    // delete
//    //        UserDao.delete(user)
//    // select
//    val user1 = UserDao.findByNFCId("nfc_2")
//    println(user1.toString())
//
//
//    val user2 = UserDao.findById(21)
//    println("user" + user2.toString)
//
//    // insert
//    var book = new Book()
//    book.name = "book_name"
//    book.isbn = "9784873115030"
//    book.publisher = "オライリー・ジャパン"
//    book.category = Option("オライリー")
//    book.category_id = Option(1)
//    book.place = "F.O.X"
//    book.image = Option("image_url")
//    BookDao.insert(book)
//
//    // update
//    book.name = "book_name_kai"
//    book.isbn = "90000000000"
//    book.publisher = "やふー"
//    book.category = Option("yahuo-")
//    book.category_id = Option(1)
//    book.place = "OPENREC"
//    book.image = Option("image_url_kai")
//    BookDao.update(book)
//
//    val book1 = BookDao.findById(4)
//    println(book1)
    //     delete
    //    BookDao.delete(book)


    Ok(views.html.index("Your new application is ready."))
  }

}
