package Airport
import scala.actors.Actor
import scala.util.Random

case class passenger(name: String)

class DocumentCheck (line: Array[Airport.Line]) extends Actor{
  
  //20% probability that the passenger will fail
  var failureRate = 20;
  // Which line to hand the passenger off to next
  var lineNumber = 0;
  // List for holding all the lines.
  var lines = line; 
  
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