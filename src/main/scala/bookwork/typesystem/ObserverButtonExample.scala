// src/main/scala/bookwork/typesystem/ObserverButtonExample.scala

object ButtonObserverExample {
  def main(args: Array[String]) {
    import bookwork.typesystem.selftype._
    import bookwork.typesystem.selftype.ButtonSubjectObserver._

    val buttons = Vector(new ObservableButton("one"), new ObservableButton("two"))
    val observer = new ButtonClickObserver
    buttons foreach (_.addObserver(observer))
    for (i <- 0 to 2) buttons(0).click()
    for (i <- 0 to 4) buttons(1).click()
    println(observer.clicks)
  }
}
