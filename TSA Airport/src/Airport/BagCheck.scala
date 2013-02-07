package Airport
import scala.actors.Actor
import scala.util.Random


/**
 * Takes in percentage of pass/fail rate and number of lines this actor should have
 */
class BagCheck (percentage: Int, lines: Int) extends Actor{
  var randNumber = new Random(100); // number between 1 and 100 to determine pass/fail
  var openLines = lines // how many lines are available for this one 
	def act () {
	  loop {
	    receive{
	      case check: String =>
	        openLines = openLines - 1 
	        // if stop is received exit the loop
	        if (check == "stop")
	          exit
	          
	        // Check to see if there is an open line
	        if (openLines > 0){
	        	Thread.sleep(100) // some sort of time passes while checking
	        	if (randNumber.nextInt < percentage)
	        		sender ! check + "passed"
	        	else
	        		sender ! check + "failed"
	        }else{
	          sender ! "busy" // I will send busy to Line if there are no lines available
	        }
	        openLines = openLines + 1 // reopen a line
	        
	        
	      // this is the old stuff 
//	      case "Tester" => 
//	      	if (randNumber.nextInt() < percentage)
//	      	  System.out.println("I passed");
//	      	else 
//	      	  System.out.println("I failed");
//	      case "Stop" => 
//	        sender ! "Stop"
//	        exit()
	    }
	    
	    	
	  }
		  
	}
}
