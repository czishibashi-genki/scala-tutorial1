package models.dao

import anorm._
import models.entity.Book
import play.api.Play.current
import play.api.db.DB

/**
 * Created by a13887 on 15/07/22.
 */
object BookDao {

  def insert(book: Book): Book = {

    // TODO: トランザクション対応
    DB.withConnection { implicit c =>

      // TODO: SQLのテンプレート化
      val book_id = SQL(
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
          'name -> book.name,
          'isbn -> book.isbn,
          'publisher -> book.publisher,
          'category -> book.category,
          'category_id -> book.category_id,
          'place -> book.place,
          'image -> book.image
        ).executeInsert()

      book.id = book_id
    }

    return book

    // TODO: anormのエラーハンドリング
  }

  def update(book: Book) = {

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
          'name -> book.name,
          'isbn -> book.isbn,
          'publisher -> book.publisher,
          'category -> book.category,
          'category_id -> book.category_id,
          'place -> book.place,
          'image -> book.image,
          'id -> book.id
        ).executeUpdate()
    }
  }

  def delete(book: Book) = {

    DB.withConnection { implicit c =>

      SQL(
        """
          UPDATE book SET deleted_at = NOW() WHERE id = {id}
        """
      ).on('id -> book.id).executeUpdate()

    }
  }

  def findById(id: Int): Book = {

    val selectQuery = SQL("SELECT * FROM book WHERE id = {id} AND deleted_at is null;").on('id -> id)
    // TODO: 冗長なマッピングを関数化切り出し
    DB.withConnection { implicit c =>
      var book = new Book()
      selectQuery().map { row =>
        book.id = row[Option[Long]]("id")
        book.name = row[String]("name")
        book.publisher = row[String]("publisher")
        book.category = row[Option[String]]("category")
        book.category_id = row[Option[Int]]("category_id")
        book.place = row[String]("place")
        book.image = row[Option[String]]("image")
      }

      return book
    }

    // TODO: 該当するidがなかった場合の処理
  }

  // 本の名前で検索
  def findsByName(name: String): List[Book] = {

    val selectQuery = SQL(s"SELECT * FROM book WHERE name LIKE '%$name%' AND deleted_at is null;")

    var books = List[Book]()
    // TODO: 冗長なマッピングを関数化切り出し
    val result = DB.withConnection { implicit c =>

      selectQuery().map { row =>
        var book = new Book()
        book.id = row[Option[Long]]("id")
        book.name = row[String]("name")
        book.publisher = row[String]("publisher")
        book.category = row[Option[String]]("category")
        book.category_id = row[Option[Int]]("category_id")
        book.place = row[String]("place")
        book.image = row[Option[String]]("image")
        books = book :: books
      }.toList

    }

    return books
  }

  // 本の名前で検索
  def findsByIsbn(isbn: String): List[Book] = {

    val selectQuery = SQL("SELECT * FROM book WHERE isbn = {isbn} AND deleted_at is null;").on('isbn -> isbn)

    var books = List[Book]()
    // TODO: 冗長なマッピングを関数化切り出し
    val result = DB.withConnection { implicit c =>

      selectQuery().map { row =>
        var book = new Book()
        book.id = row[Option[Long]]("id")
        book.name = row[String]("name")
        book.publisher = row[String]("publisher")
        book.category = row[Option[String]]("category")
        book.category_id = row[Option[Int]]("category_id")
        book.place = row[String]("place")
        book.image = row[Option[String]]("image")
        books = book :: books
      }.toList

    }

    return books
  }
}
