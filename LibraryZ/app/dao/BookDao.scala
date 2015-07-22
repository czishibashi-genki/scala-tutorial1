package dao

import anorm._
import play.api.Play.current
import play.api.db.DB

/**
 * Created by a13887 on 15/07/22.
 */
class BookDao {

  var id: Option[Long] = null
  var name: String = ""
  var isbn: String = ""
  var publisher: String = ""
  var category: Option[String] = null
  var category_id: Option[Int] = null
  var place: String = ""
  var image: Option[String] = null

  def insert() = {

    // TODO: トランザクション対応
    DB.withConnection { implicit c =>

      // TODO: SQLのテンプレート化
      val user_id = SQL(
        """
          INSERT INTO book(
            name, isbn, publisher, category,
            category_id, place, image,
            created_at, updated_at
          ) VALUES(
            {name}, {isbn}, {publisher}, {category},
            {category_id}, {place}, {image},
            NOW(), NOW()
          )
        """
      ).on(
          'name -> this.name,
          'isbn -> this.isbn,
          'publisher -> this.publisher,
          'category -> this.category,
          'category_id -> this.category_id,
          'place -> this.place,
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
          UPDATE book SET
            name = {name},
            isbn = {isbn},
            publisher = {publisher},
            category = {category},
            category_id = {category_id},
            place = {place},
            image = {image},
            updated_at = NOW()
          WHERE id = {id}
        """
      ).on(
          'name -> this.name,
          'isbn -> this.isbn,
          'publisher -> this.publisher,
          'category -> this.category,
          'category_id -> this.category_id,
          'place -> this.place,
          'image -> this.image,
          'id -> this.id
        ).executeUpdate()
    }
  }

  def delete() = {

    DB.withConnection { implicit c =>

      SQL(
        """
          UPDATE book SET deleted_at = NOW() WHERE id = {id}
        """
      ).on('id -> this.id).executeUpdate()

    }
  }

  def findById(id: Int) = {

    val selectQuery = SQL("SELECT * FROM book WHERE id = {id} AND deleted_at is null;").on('id -> id)
    // TODO: 冗長なマッピングを関数化切り出し
    DB.withConnection { implicit c =>
      selectQuery().map { row =>
        this.id = row[Option[Long]]("id")
        this.name = row[String]("name")
        this.publisher = row[String]("publisher")
        this.category = row[Option[String]]("category")
        this.category_id = row[Option[Int]]("category_id")
        this.place = row[String]("place")
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
        this.publisher = row[String]("publisher")
        this.category = row[Option[String]]("category")
        this.category_id = row[Option[Int]]("category_id")
        this.place = row[String]("place")
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
      publisher: ${this.publisher}
      category: ${this.category}
      category_id: ${this.category_id}
      place: ${this.place}
      image: ${this.image}
    """
  }
}
