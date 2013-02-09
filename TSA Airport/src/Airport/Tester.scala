package Airport
import scala.actors.Actor
import scala.actors.Actor._



case object Stop

class Tester(number: Integer, line: Actor) extends Actor{
	def act(){
		var numLeft = number
		loop{
		  numLeft = numLeft - 1
		  if (numLeft > 0)
				line ! "Tester"
			else {
				line ! "Stop"
				receive {
		    case check(name, result)  =>
		      if (result == "passed")
		        
		        
		      exit()
		  }
			}
		  
			
		}
		
	}

}

