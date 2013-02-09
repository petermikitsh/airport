package Airport
import scala.actors.Actor
import scala.actors.Actor._

class Line (bagCheck: Airport.Scanner, bodyCheck: Airport.Scanner) extends Actor{

  /*
   *  Map Structure:
   *  
   *  Key(String) : Value(Array[Boolean])
   *  Name => [bagCheckResult, bodyCheckResult]
   */
  private val map: Map[String, Array[Boolean]] = Map()
  
  case class BagCheck(name: String, result: Boolean);
  case class BodyCheck(name: String, result: Boolean);
  
	def act(){
	  loop {
	    receive {
	      case BagCheck (name: String, bagCheckResult: Boolean) =>
	        map.get(name) match {
	          case Some(i) =>
	            i(0) = bagCheckResult;
	            // send message based on completed array
	          case None => 
	          	val result:Array[Boolean] = new Array[Boolean](2);
	          	result(0) = bagCheckResult;
	            map + (name -> result);
	        }
	      case BodyCheck (name: String, bodyCheckResult: Boolean) =>
	        map.get(name) match {
		        case Some(j) =>
		            j(1) = bodyCheckResult;
		            // send message based on completed array
		          case None => 
		          	val result:Array[Boolean] = new Array[Boolean](2);
		          	result(1) = bodyCheckResult;
		            map + (name -> result);
	        }
	      case name : String =>
	        bagCheck ! name;
	        bodyCheck ! name;
	    }
	  }
	}
	
}