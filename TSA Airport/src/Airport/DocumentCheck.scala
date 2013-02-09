package Airport
import scala.actors.Actor
import scala.util.Random

case class passenger(name: String)

<<<<<<< HEAD
class DocumentCheck (line: Array[String]) extends Actor{
=======
class DocumentCheck extends Actor {
>>>>>>> 81c7a43ea9cd7a01a2a4f2014da6324572276a3d
  
  //20% probability that the passenger will fail
  var failureRate = 20;
  // Which line to hand the passenger off to next
  var lineNumber = 0;
  // List for holding all the lines.
<<<<<<< HEAD
  var lines = line; 
  var airportLine = new Line()
=======
  var lines:Array[Airport.Line] = makeLines(5)
>>>>>>> 81c7a43ea9cd7a01a2a4f2014da6324572276a3d
  
  def act() {
    loop{
      receive{
        case passenger(name: String) =>
          logArrival(name)
          if(passed()){
             logSentToLine(name)
             lines(lineNumber) ! name;
             this.lineNumber += 1
             if(lineNumber == lines.length)
               this.lineNumber = 0
          }
          else{
            logTurnedAway(name);
          }
        case close: String =>
          //loop through the actor lines and send close messages.
          for (l <- lines){
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
  
  
  
}