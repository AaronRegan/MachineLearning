/*	Aaron Regan
 * 	13446668
 * 	4BP1
 * 	Assignment 3- Machine Learning
 * 	Implementation of the ID3 Algorithm for data classification
 */

package dt;

public class Rules {
	
	private double threshold;
	private int attribute;
	private String left;
	private String right;
	public Rules(double value, int attribute, String left, String right)
    {
    	this.threshold = value;
    	this.attribute = attribute;
    	this.left = left;
    	this.right = right;
    }
	public double getThreshold() {
		return threshold;
	}
	public int getAttribute() {
		return attribute;
	}
	public String getLeft() {
		return left;
	}
	public String getRight() {
		return right;
	}
	@Override
	public String toString() {//toString override to print rules held in Rules object//
	  return "Threshold: "+threshold +" Attribute: "+ attribute+"="+" Owl Type: "+ left + "]\n";
	}
}
