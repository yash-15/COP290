
public class Physics {

	static int Sin[]={0,1,0,-1},Cos[]={1,0,-1,0};
	
	/*
	 * CCW 90 degree rotation
	 */
	public static double[] Rotate(int count, double x0, double y0){
		double[] res = {0.0,0.0};
		res[0]=x0*Cos[count]+y0*Sin[count];
		res[1]=y0*Cos[count]-x0*Sin[count];
		return res;
	}
	
	/*
	 * CCW theta radian rotation
	 */
	public static double[] RotateDouble(double theta, double x0, double y0){
		double[] res = {0.0,0.0};
		res[0]=x0*Math.cos(theta)+y0*Math.sin(theta);
		res[1]=y0*Math.cos(theta)-x0*Math.sin(theta);
		return res;
	}

}
