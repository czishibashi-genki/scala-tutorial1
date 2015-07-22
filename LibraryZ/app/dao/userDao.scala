package dao

import anorm._
import play.api.Play.current
import play.api.db.DB

/**
 * Created by a13887 on 15/07/21.
 */
class UserDao {

  var id: Option[Long] = null
  var name = ""
  var nfc_device_id = ""
  var chatwork_api_token = ""
  var image: Option[String] = null
  var created_at = null
  var updated_at = null

  def insert() = {

    // TODO: トランザクション対応
    DB.withConnection { implicit c =>

      // TODO: SQLのテンプレート化
      val user_id = SQL(
        """
          INSERT INTO user(
            name, nfc_device_id, chatwork_api_token, image,
            created_at, updated_at
          ) VALUES(
            {name}, {nfc_device_id}, {chatwork_api_token}, {image},
            NOW(), NOW()
          )
        """
      ).on(
          'name -> this.name,
          'nfc_device_id -> this.nfc_device_id,
          'chatwork_api_token -> this.chatwork_api_token,
          'image -> this.image
        ).executeInsert()

      this.id = user_id
    }

    // TODO: anormのエラーハンドリング
  }

  def update() = {

    DB.withConnection { implicit c =>

      SQL(
        """
          UPDATE user SET
            name = {name},
            nfc_device_id = {nfc_device_id},
            chatwork_api_token = {chatwork_api_token},
            image = {image},
            updated_at = NOW()
          WHERE id = {id}
        """
      ).on(
          'id -> this.id,
          'name -> this.name,
          'nfc_device_id -> this.nfc_device_id,
          'chatwork_api_token -> this.chatwork_api_token,
          'image -> this.image
        ).executeUpdate()
    }
  }

  def delete() = {

    DB.withConnection { implicit c =>

      SQL(
        """
          UPDATE user SET deleted_at = NOW() WHERE id = {id}
        """
      ).on('id -> this.id).executeUpdate()

    }
  }

  def findById(id: Int) = {

    val selectQuery = SQL("SELECT * FROM user WHERE id = {id} AND deleted_at is null;").on('id -> id)
    // TODO: 冗長なマッピングを関数化切り出し
    DB.withConnection { implicit c =>
      selectQuery().map { row =>
        this.id = row[Option[Long]]("id")
        this.name = row[String]("name")
        this.nfc_device_id = row[String]("nfc_device_id")
        this.chatwork_api_token = row[String]("chatwork_api_token")
        this.image = row[Option[String]]("image")
      }
    }
    // TODO: 該当するidがなかった場合の処理
  }

  /** nfc_device_idが最初に一致するユーザー取得
    *
    * @param nfc_device_id
    */
  def findByNFCId(nfc_device_id: String) = {

    val selectQuery = SQL("SELECT * FROM user WHERE nfc_device_id = {nfc_device_id} AND deleted_at is null;").on('nfc_device_id -> nfc_device_id)
    DB.withConnection { implicit c =>
      selectQuery().map { row =>
        this.id = row[Option[Long]]("id")
        this.name = row[String]("name")
        this.nfc_device_id = row[String]("nfc_device_id")
        this.chatwork_api_token = row[String]("chatwork_api_token")
        this.image = row[Option[String]]("image")
      }
    }
    // TODO: 該当するnfc_device_idがなかった場合の処理
  }


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
