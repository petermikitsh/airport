package Airport
import scala.actors.Actor
import scala.util.Random

case class passenger(name: String)

class DocumentCheck extends Actor {

  
  //20% probability that the passenger will fail
  var failureRate = 20;
  // Which line to hand the passenger off to next
  var lineNumber = 0;
  // List for holding all the lines.

  var airportLines: Array[Airport.Line] = makeLines(5)

  
  def act() {
    loop{
      receive{
        case passenger(name: String) =>
          logArrival(name)
          if(passed()){
             logSentToLine(name)
             airportLines(lineNumber) ! name;
             this.lineNumber += 1
             if(lineNumber == airportLines.length)
               this.lineNumber = 0
          }
          else{
            logTurnedAway(name);
          }
        case close: String =>
          //loop through the actor lines and send close messages.
          for (l <- airportLines){
            l ! "stop"
          }
          logClosing()
          exit
      }
    }
  }
  
  def makeLines(numLines : Int):Array[Airport.Line] = {
    var temp:Array[Airport.Line] = new Array[Airport.Line](numLines);
    for (i <- 0 to numLines-1) {
      temp(i) = new Airport.Line;
      temp(i).start
    }
    return temp;
  }
  
  def passed():Boolean = {
    var passed = true;
    var randNumber = new Random(100)
    if(randNumber.nextInt() < failureRate)
      passed = false
    
    return passed;
  }
  
  def logArrival (passenger: String){
    println("Document Check: Passenger name arrives"); // Somehow get passenger's names
  }
  
  def logTurnedAway(passenger: String){
    println("Document Check: Passenger name turned away"); // Get passenger name
  }
  
  def logSentToLine(passenger: String){
    println("Document Check: Passenger name sent to line " + lineNumber);
  }
  
  def logClosing(){
    println("Document Check: Close sent");
    println("Document Check: Closed");
  }
}

object Main extends App {
  val docCheck = new DocumentCheck
  docCheck.start
  docCheck ! passenger("pass1")
  docCheck ! passenger("pass2")
  docCheck ! passenger("pass3")
  docCheck ! passenger("pass4")
  docCheck ! passenger("pass5")
  docCheck ! passenger("pass6")
  docCheck ! passenger("pass7")
  docCheck ! passenger("pass8")
  docCheck ! passenger("pass9")
  docCheck ! passenger("pass10")
  docCheck ! passenger("pass11")
  docCheck ! passenger("pass12")
  
  
  
  
  
}