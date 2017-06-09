// src/main/scala/modulework/list-implimentation

sealed trait List[+A] {
  override def toString = {
    def toScalaList(t: List[A]): scala.List[A] = t match {
      case Empty => Nil
      case Cons(h, t) => h :: toScalaList(t)
    }
    toScalaList(this).toString
  }
}
final case object Empty extends List[Nothing]
final case class Cons[A](h: A, t: List[A]) extends List[A]

object List {
  def foldRight[A, B](as: List[A], b: B, f: (A, B) => B): B = as match {
    case Empty => b
    case Cons(h, t) => f(h, foldRight(t, b, f))
  }

  def foldLeft[A, B](as: List[A], b: B, f: (B, A) => B): B = as match {
    case Empty => b
    case Cons(h, t) => foldLeft(t, f(b, h), f)
  }

  def reduceRight[A](as: List[A], f: (A, A) => A): A = as match {
    case Empty => error("bzzt. reduceRight on empty list")
    case Cons(h, t) => foldRight(t, h, f)
  }

  def reduceLeft[A](as: List[A], f: (A, A) => A): A = as match {
    case Empty => error("bzzt. reduceLeft on empty list")
    case Cons(h, t) => foldLeft(t, h, f)
  }

  def unfold[A, B](b: B, f: B => Option[(A, B)]): List[A] = f(b) match {
    case Some((a, b)) => Cons(a, unfold(b, f))
    case scala.None => Empty
  }
}

sealed trait Natural {
  override def toString = {
    def toInt(n: Natural): Int = n match {
      case Zero => 0
      case Succ(x) => 1 + toInt(x)
    }
    toInt(this).toString
  }
}
final case object Zero extends Natural
final case class Succ(c: Natural) extends Natural

object Exercises {

  def add(x: Natural, y: Natural): Natural = {
    def toInt(n: Natural): Int = n match {
      case Zero => 0
      case Succ(x) => 1 + toInt(x)
    }
    var r = y
    for (i <- 0 to toInt(x)) r = Succ(r)
    r
  }

  def sum(is: List[Int]): Int = is match {
    case Empty      => 0
    case Cons(h, t) => h + sum(t)
  }

  def length[A](as: List[A]): Int = as match {
    case Empty      => 0
    case Cons(h, t) => 1 + length(t)
  }

  def map[A, B](as: List[A], f: A => B): List[B] = as match {
    case Empty      => Empty
    case Cons(h, t) => Cons(f(h), map(t, f))
  }

  def filter[A](as: List[A], f: A => Boolean): List[A] = as match {
    case Empty => Empty
    case Cons(h, t) if f(h) => Cons(h, filter(t, f))
    case Cons(h, t)         => filter(t, f)
  }

  def append[A](x: List[A], y: List[A]): List[A] = x match {
    case Empty      => y
    case Cons(h, t) => Cons(h, append(t, y))
  }

  def flatten[A](as: List[List[A]]): List[A] = as match {
    case Empty      => Empty
    case Cons(h, t) => append(h, flatten(t))
  }

  def flatMap[A, B](as: List[A], f: A => List[B]): List[B] = as match {
    case Empty      => Empty
    case Cons(h, t) => append(f(h), flatMap(t, f))
  }

  def maximum(is: List[Int]): Int = {
    def _initialize(is: List[Int]): Int = is match {
      case Empty      => error("bzzt. Tried 'maximum' on an empty list")
      case Cons(h, t) => _maximum(t, h)
    }
    def _maximum(is: List[Int], max: Int):Int = is match {
      case Empty => max
      case Cons(h, t) if h > max  => _maximum(t, h)
      case Cons(h, t)             => _maximum(t, max)
    }
    _initialize(is)
  }

  def reverse[A](as: List[A]): List[A] = as match {
    case Empty      => Empty
    case Cons(h, t) if t == Empty => Cons(h, Empty)
    case Cons(h, t)               => append(reverse(t), Cons(h, Empty))
  }

    def main(args: Array[String]) { }
}
