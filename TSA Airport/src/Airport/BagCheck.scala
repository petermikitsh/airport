package Airport
import scala.actors.Actor
import scala.util.Random



class BagCheck () extends Actor{
  var randNumber = new Random(100);
  var percentage = 50;
	def act () {
	  loop {
	    receive{
	      case "Tester" => 
	      	if (randNumber.nextInt() < percentage)
	      	  System.out.println("I passed");
	      	else 
	      	  System.out.println("I failed");
	      case "Stop" => 
	        sender ! "Stop"
	        exit()
	    }
	    
	    	
	  }
		  
	}
}
