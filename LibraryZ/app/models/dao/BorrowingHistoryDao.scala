package models.dao

import anorm._
import models.entity.BorrowingHistory
import play.api.Play.current
import play.api.db.DB

/**
 * Created by a13887 on 15/07/23.
 */
object BorrowingHistoryDao {

  def insert(borrowingHistory: BorrowingHistory): BorrowingHistory = {

    // TODO: トランザクション対応
    DB.withConnection { implicit c =>

      // TODO: SQLのテンプレート化
      val borrowingHistory_id = SQL(
        """
            INSERT INTO borrowing_history(
              user_id, book_id, return_date, chatwork_task_id, finish,
              created_at, updated_at
            ) VALUES(
              (SELECT id FROM user WHERE id={user_id}), (SELECT id FROM book WHERE id={book_id}), {return_date}, {chatwork_task_id}, {finish},
              NOW(), NOW()
            )
        """
      ).on(
          'user_id -> borrowingHistory.userId,
          'book_id -> borrowingHistory.bookId,
          'return_date -> borrowingHistory.returnDate,
          'chatwork_task_id -> borrowingHistory.chatworkTaskId,
          'finish -> borrowingHistory.finish
        ).executeInsert()

      borrowingHistory.id = borrowingHistory_id
    }

    return borrowingHistory

    // TODO: anormのエラーハンドリング
  }

  def update(borrowingHistory: BorrowingHistory) = {

    DB.withConnection { implicit c =>

      SQL(
        """
            UPDATE borrowing_history SET
              user_id = (SELECT id FROM user WHERE id={user_id}),
              book_id = (SELECT id FROM book WHERE id={book_id}),
              return_date = {return_date},
              chatwork_task_id = {chatwork_task_id},
              finish = {finish},
              updated_at = NOW()
            WHERE id = {id}
        """
      ).on(
          'user_id -> borrowingHistory.userId,
          'book_id -> borrowingHistory.bookId,
          'return_date -> borrowingHistory.returnDate,
          'chatwork_task_id -> borrowingHistory.chatworkTaskId,
          'finish -> borrowingHistory.finish,
          'id -> borrowingHistory.id
        ).executeUpdate()
    }
  }

  def delete(borrowingHistory: BorrowingHistory) = {

    DB.withConnection { implicit c =>

      SQL(
        """
            UPDATE borrowing_history SET deleted_at = NOW() WHERE id = {id}
        """
      ).on('id -> borrowingHistory.id).executeUpdate()

    }
  }

  def findById(id: Int): BorrowingHistory = {

    val selectQuery = SQL("SELECT * FROM borrowing_history WHERE id = {id} AND deleted_at is null;").on('id -> id)
    // TODO: 冗長なマッピングを関数化切り出し
    DB.withConnection { implicit c =>
      var borrowingHistory = new BorrowingHistory()
      selectQuery().map { row =>
        borrowingHistory.id = row[Option[Long]]("id")
        borrowingHistory.userId = row[Int]("user_id")
        borrowingHistory.bookId = row[Int]("book_id")
        borrowingHistory.returnDate = row[java.util.Date]("return_date")
        borrowingHistory.chatworkTaskId = row[Option[String]]("chatwork_task_id")
        borrowingHistory.finish = row[Boolean]("finish")
      }

      borrowingHistory
    }
  }

  def findByUserIdAndBookId(userId: Int, bookId: Int): BorrowingHistory = {

    val selectQuery = SQL(s"SELECT * FROM borrowing_history WHERE user_id = $userId AND $bookId AND deleted_at is null;")
    // TODO: 冗長なマッピングを関数化切り出し
    DB.withConnection { implicit c =>
      var borrowingHistory = new BorrowingHistory()
      selectQuery().map { row =>
        borrowingHistory.id = row[Option[Long]]("id")
        borrowingHistory.userId = row[Int]("user_id")
        borrowingHistory.bookId = row[Int]("book_id")
        borrowingHistory.returnDate = row[java.util.Date]("return_date")
        borrowingHistory.chatworkTaskId = row[Option[String]]("chatwork_task_id")
        borrowingHistory.finish = row[Boolean]("finish")
      }

      borrowingHistory
    }
  }
}
