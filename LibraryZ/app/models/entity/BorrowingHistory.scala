package models.entity

import org.joda.time.DateTime


/**
 * Created by a13887 on 15/07/23.
 */
class BorrowingHistory {

  var id: Option[Long] = null
  var userId: Int = -1
  var bookId: Int = -1
  var returnDate: java.util.Date = null
//  var returnDate: DateTime = null
  var chatworkTaskId: Option[String] = null
  var finish: Boolean = false

  override def toString() = {
    s"""
        Borrowing history
        id: ${this.id}
        userId: ${this.userId}
        bookId: ${this.bookId}
        returnDate: ${this.returnDate}
        chatworkTaskId: ${this.chatworkTaskId}
        finish: ${this.finish}
      """
  }
}
