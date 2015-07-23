package models.entity

/**
 * Created by a13887 on 15/07/23.
 */
class Book {

  var id: Option[Long] = null
  var name: String = ""
  var isbn: String = ""
  var publisher: String = ""
  var category: Option[String] = null
  var category_id: Option[Int] = null
  var place: String = ""
  var image: Option[String] = null

  override def toString() = {
    s"""
      book
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
