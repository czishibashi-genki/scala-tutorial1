package controllers

import dao.BookDao
import play.api.mvc._

class Application extends Controller {

  def index = Action {

    // insert
    //    var user = new UserDao()
    //    user.name = "Tanaka"
    //    user.nfc_device_id = "nfc_2"
    //    user.chatwork_api_token = "cwuser1234"
    //    user.image = null
    //    user.insert()

    // update
    //    user.name = "HANAKO_KAI"
    //    user.update()

    // delete
    //    user.delete()
    // select
    //    user.findByNFCId("nfc_7")
    //    println(user.toString())


    //      val user = User
    //      user.findById(3)
    //      println("user" + user.toString)

    // insert
    var book = new BookDao()
    book.name = "book_name"
    book.isbn = "9784873115030"
    book.publisher = "オライリー・ジャパン"
    book.category = Option("オライリー")
    book.category_id = Option(1)
    book.place = "F.O.X"
    book.image = Option("image_url")
    book.insert()

    // update
    book.name = "book_name_kai"
    book.isbn = "90000000000"
    book.publisher = "やふー"
    book.category = Option("yahuo-")
    book.category_id = Option(1)
    book.place = "OPENREC"
    book.image = Option("image_url_kai")
    book.update()

    // delete
//    book.delete()


    Ok(views.html.index("Your new application is ready."))
  }

}
