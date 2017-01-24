package tools;

import java.awt.Point;
import java.awt.geom.Point2D;

public class CustomPolygon {

	public Point[] points;
	
	public CustomPolygon(Point[] p){
		points = p;
	}
	public static CustomPolygon[] removePoint(Point[] p, int index){
		Point[] p2 = new Point[p.length-1];
		for(int i = 0 ; i < index;i++){
			p2[i] = p[i];
		}
		for(int i = index ; i < p2.length;i++){
			p2[i] = p[i + 1];
		}
		Point base1;
		if(index > 0){
			base1 = p[index - 1];
		}
		else{
			base1 = p[p.length - 1];
		}
		Point vertex = p[index];
		Point base2;
		if(index == p.length - 1){
			base2 = p[0];
		}
		else{
			base2 = p[index + 1];
		}
		CustomPolygon c1 = new CustomPolygon(new Point[]{base1,vertex,base2});
		CustomPolygon c2 = new CustomPolygon(p2);
		return new CustomPolygon[]{c1,c2};
	}
	
}
