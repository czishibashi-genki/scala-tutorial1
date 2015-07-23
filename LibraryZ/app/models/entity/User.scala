package models.entity

/**
 * Created by a13887 on 15/07/23.
 */
class User {

  var id: Option[Long] = null
  var name = ""
  var nfc_device_id = ""
  var chatwork_api_token = ""
  var image = ""
  var created_at = null
  var updated_at = null

  override def toString() = {
    s"""
      user
      id: ${this.id}
      name: ${this.name}
      nfc_device_id: ${this.nfc_device_id}
      chatwork_api_token: ${this.chatwork_api_token}
      image: ${this.image}
    """
  }
}
