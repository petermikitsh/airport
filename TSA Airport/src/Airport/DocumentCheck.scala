package Airport
import scala.actors.Actor
import scala.util.Random

class DocumentCheck (queues: List) extends Actor{
  
  //20% probability that the passenger will fail
  var failureRate = 20;
  // Which queue to hand the passenger off to next
  var queueNumber = 0;
  // Random generator for determining if the passenger fails
  var randNumber;
  // List for holding all the queues
  val queues = queues; 
  
  def act() {
    loop{
      receieve{
        case check: String =>
          if(passed()){
            logSentToQueue("Name");
             this.queues.get(this.queueNumber) ! "passenger"
             this.queueNumber++;
             if(this.queueNumber == this.queues.size())
               this.queueNumber = 0;
          }
          else{
            logTurnedAway("Passenger");
          }
        case close: String =>
          //loop through the actor queue and send close messages
          logClosing();
          
          
      }
    }
  }
  
  def passed(){
    var passed = true;
    randNumber = new Random(100);
    if(randNumber < failureRate)
      passed = false;
    
    return passed;
  }
  
  def logArrival (passenger: String){
    println("Document Check: Passenger name arrives"); // Somehow get passenger's name
  }
  
  def logTurnedAway(passenger: String){
    println("Document Check: Passenger name turned away"); // Get passenger name
  }
  
  def logSentToQueue(passenger: String){
    println("Document Check: Passenger name sent to line " + queueNumber);
  }
  
  def logClosing(){
    println("Document Check: Close sent");
    println("Document Check: Closed);
  }
}