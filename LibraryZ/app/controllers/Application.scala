package controllers

import dao.UserDao
import play.api.mvc._

class Application extends Controller {

  def index = Action {

    // insert
    var user = new UserDao()
    user.name = "Tanaka"
    user.nfc_id = "nfc_7"
    user.cw_user_id = "cwuser1234"
    user.image = null
    user.insert()

    // update
    user.name = "HANAKO_KAI"
    user.update()

    // delete
    user.delete()

    Ok(views.html.index("Your new application is ready."))
  }

}
