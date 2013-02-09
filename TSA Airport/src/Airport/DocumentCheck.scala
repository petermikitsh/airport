package Airport
import scala.actors.Actor
import scala.util.Random

case class passenger(name: String)

class DocumentCheck (queue: Array[Airport.Line]) extends Actor{
  
  //20% probability that the passenger will fail
  var failureRate = 20;
  // Which queue to hand the passenger off to next
  var queueNumber = 0;
  // List for holding all the queues.
  var queues = queue; 
  
  def act() {
    loop{
      receive{
        case passenger(name: String) =>
          logArrival(name)
          if(passed()){
            logSentToQueue(name)
             queues(queueNumber) ! "passenger"
             this.queueNumber += 1
             if(queueNumber == queues.length)
               this.queueNumber = 0
          }
          else{
            logTurnedAway(name);
          }
        case close: String =>
          //loop through the actor queue and send close messages.
          for (q <- queues){
            q ! "stop"
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
  
  def logSentToQueue(passenger: String){
    println("Document Check: Passenger name sent to line " + queueNumber);
  }
  
  def logClosing(){
    println("Document Check: Close sent");
    println("Document Check: Closed");
  }
}