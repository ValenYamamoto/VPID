package vpid;

public class Main {
	public static void main(String[] args) {
		double x1 = getRate(0, 1);
		double x2 = getRate(0.5, 1);
		double x3 = getRate(0.4, 1);
		double x4 = getRate(0.5, 0);
		
		setPower(0, returnPower(x1, 0, 1));
		setPower(0.5, returnPower(x2, 0.5, -1));
		setPower(0.4, returnPower(x3, 0.4, 1));
		setPower(0.5, returnPower(x4, 0.5, 0));
		
		System.out.println(returnPower(x2, 0.5, -1));
		
	}
	
	public static double function(double x, double y, double i) {
		x = Math.abs(x);
		y = Math.abs(y);
		
		double val = Math.sqrt((y*y)*(1-(i*i)/(x*x)));
		val = val * (x*Math.sqrt(1-(i*i)/(x*x))+Math.asin(i/x));
		val = val/(Math.sqrt(1-(i*i)/(x*x)));
		return val;
	}
	
	public static double getRate(double forward, double rotation) {
		double x2 = function(rotation, forward, rotation - 0.0000001);
		double x1 = function(rotation, forward, -rotation + 0.0000001);
		
		double rate = (x2-x1)/(2*rotation);
		
		return rate;
	}
	
	public static double getPower(double rate, double power) {
		return power * rate;
	}
	
	public static double checkPower(double power, double forward, double rotation) {
		if (Double.isNaN(power)) {
			if (rotation == 0 && forward == 0) {
				return 0;
			} else {
				return forward;
			}
		} if (power == 0) {
			return rotation;
		}
		
		if (rotation < 0) {
			return -power;
		}
		return power;
	}
	
	public static double returnPower(double x1, double forward, double rotation) {
		return checkPower(getPower(x1, forward), forward, rotation);
	}
	
	public static int insideOutside(double forward, double rotation) {
		if (rotation > 0) {
			return 1;
		}
		return 2;
	}
	
	public static void setPower(double x, double y) {
		
		double left;
		double right;
		
		if (insideOutside(Math.abs(x), Math.abs(y)) == 1) {
			if (x > y) {
				right = x;
				left = y;
			} else {
				right = y;
				left = x;
			}
		} else {
			if (x > y) {
				right = y;
				left = x;
			} else {
				right = x;
				left = y;
			}
		}
        double max = Math.max(1, Math.max(Math.abs(left), Math.abs(right)));
        right /= max;
        left /= max;
        if (right == 0 && left != 0) {
        	right = -left;
        } else if (left == 0 && right != 0) {
        	left = -right;
        }
        
        System.out.printf("Right Power: %f%nLeft Power: %f%n%n", right, left);
	}
}