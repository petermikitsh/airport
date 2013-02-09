package Airport

object Driver extends App {
  val docCheck = new DocumentCheck
  docCheck.start
  for (i <- 0 to 10) {
    docCheck ! passenger(i + "")
  }
  docCheck ! "close"
}