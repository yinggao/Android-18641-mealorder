package ws.local;

public class Accelerometer {

	public boolean exceedXCriteria(double currentXAcce) {
		if (Math.abs(currentXAcce) > 14 ) {
			return true;
		}
		return false;
	}
	
	public boolean exceedYCriteria(double currentYAcce) {
		if (Math.abs(currentYAcce) > 14 ) {
			return true;
		}
		return false;
	}
	
	public boolean exceedZCriteria(double currentZAcce) {
		if (Math.abs(currentZAcce) > 14 ) {
			return true;
		}
		return false;
	}
}
