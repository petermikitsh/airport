package Airport
import scala.actors.Actor
import scala.actors.Actor._

class Line extends Actor{

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
	        bagCheck ! name;
	        printf("Passenger %s's bags enter the bag check.\n", name);
	        bodyCheck ! name;
	        printf("Passenger %s enters the body check.\n", name)
	    }
	  }
	}
	
	def onArrayComplete(name: String) {
	  map.get(name) match {
	  	case Some(k) =>
	  	  	if (k(1) && k(2)) {
	  	  	  printf("Passenger %s cleared all scans.\n", name);
	  	  	} else {
	  	  	  printf("Passenger %s failed one or more checks and goes to jail.\n", name)
	  	  	}
	  	case None => println("ERROR: Passenger not found! (onArrayComplete)")
	  }
	}
	
}