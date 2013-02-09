package Airport
import scala.actors.Actor
import scala.actors.Actor._

class Line(id: Int) extends Actor{

  /*
   *  Map Structure:
   *  
   *  Key(String) : Value(Array[Boolean])
   *  Name => [bagCheckResult, bodyCheckResult]
   */
  private val map: Map[String, Array[Boolean]] = Map()
  private var bagCheck = new Airport.Scanner(20, 10)
  private var bodyCheck = new Airport.Scanner(20, 10)
  
  case class BagCheck(name: String, result: Boolean);
  case class BodyCheck(name: String, result: Boolean);
  
	def act(){
	  bagCheck.start
	  bodyCheck.start
	  loop {
	    receive {
	      case BagCheck (name: String, bagCheckResult: Boolean) =>
	        map.get(name) match {
	          case Some(i) =>
	            i(0) = bagCheckResult;
	            onArrayComplete(name);
	          case None => 
	          	val result:Array[Boolean] = new Array[Boolean](2);
	          	result(0) = bagCheckResult;
	            map + (name -> result);
	        }
	      case BodyCheck (name: String, bodyCheckResult: Boolean) =>
	        map.get(name) match {
		      case Some(j) =>
		        j(1) = bodyCheckResult;
		        onArrayComplete(name);
		      case None => 
		        val result:Array[Boolean] = new Array[Boolean](2);
		        result(1) = bodyCheckResult;
		        map + (name -> result);
	        }
	      case name : String =>
	        if (name == "stop") {
	          bagCheck ! "stop"
	          printf("Line %d: Closing bag check.\n", id)
	          bodyCheck ! "stop"
	          printf("Line %d: Closing body check.\n", id)
	          printf("Line %d: Stopped.\n", id)
	          exit
	        } else {
	          bagCheck ! name
	          printf("Line %d: Passenger #%s bags enter the bag check.\n", id, name)
	          bodyCheck ! name
	          printf("Line %d: Passenger #%s enters the body check.\n", id, name)
	        }
	    }
	  }
	}
	
	def onArrayComplete(name: String) {
	  map.get(name) match {
	  	case Some(k) =>
	  	  	if (k(1) && k(2)) {
	  	  	  printf("Line %d: Passenger #%s cleared all scans.\n", id, name);
	  	  	} else {
	  	  	  printf("Line %d: Passenger #%s failed one or more checks and goes to jail.\n", id, name)
	  	  	}
	  	case None => println("ERROR: Passenger not found! (onArrayComplete).")
	  }
	}
	
}