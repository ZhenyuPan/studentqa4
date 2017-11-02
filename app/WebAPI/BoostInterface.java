package WebAPI;

import PeerEvaluation.PeerEvaluationBoost;
import play.mvc.Controller;
import play.mvc.Result;
public class BoostInterface extends Controller {
	public Result BoostApi()
	{
		PeerEvaluationBoost PB = new PeerEvaluationBoost();
		PB.BoostAll();
		return ok("Finished");
	}
}
