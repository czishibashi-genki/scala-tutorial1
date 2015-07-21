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
  var nfc_id = ""
  var cw_user_id = ""
  var image = ""
  var created_at = null
  var updated_at = null

  def insert() = {

    DB.withConnection { implicit c =>

      val user_id = SQL(
        """
          INSERT INTO user(
            name, nfc_id, cw_user_id, image,
            created_at, updated_at
          ) VALUES(
            {name}, {nfc_id}, {cw_user_id}, {image},
            NOW(), NOW()
          )

        """
      ).on(
          'name -> this.name,
          'nfc_id -> this.nfc_id,
          'cw_user_id -> this.cw_user_id,
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
            nfc_id = {nfc_id},
            cw_user_id = {cw_user_id},
            image = {image},
            updated_at = NOW()
          WHERE id = {id}
        """
      ).on(
          'id -> this.id,
          'name -> this.name,
          'nfc_id -> this.nfc_id,
          'cw_user_id -> this.cw_user_id,
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
}
