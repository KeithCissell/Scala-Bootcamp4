// src/main/scala/bookwork/typesystem/SubjectObserver.scala
package bookwork.typesystem.selftype

abstract class SubjectObserver {
  type S <: Subject
  type O <: Observer

  trait Subject {
    self: S =>
    private var observers = List[O]()

    def addObserver(observer: O) = observers ::= observer

    def notifyObservers() = observers.foreach(_.receiveUpdate(self))
  }

  trait Observer {
    def receiveUpdate(subject: S)
  }
}
