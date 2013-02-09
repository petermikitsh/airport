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
      temp(i) = new Airport.Line(i);
      temp(i).start
    }
    return temp;
  }
  
  def passed():Boolean = {
    var randNumber = new Random(100)
    return randNumber.nextInt() < failureRate;
  }
  
  def logArrival (passenger: String){
    printf("Document Check: Passenger #%s arrives.\n", passenger);
  }
  
  def logTurnedAway(passenger: String){
    printf("Document Check: Passenger #%s turned away.\n", passenger);
  }
  
  def logSentToLine(passenger: String){
    printf("Document Check: Passenger #%s sent to line %d.\n", passenger, lineNumber);
  }
  
  def logClosing(){
    println("Document Check: Closed.");
  }
}