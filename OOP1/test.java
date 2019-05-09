package main.course.oop.tictactoe.util;
public class test{
	
	public static void main(String[] args){

		TwoDArray a = new TwoDArray(8,8,-1);

		System.out.println(a.getArrayDisplay());
		System.out.println(a.getArrayDetails());

		System.out.println(a.insertInt(0, 0, -1));
		a.insertInt(1, 1, 2);
		a.insertInt(2, 2, 3);
		a.insertInt(3, 3, 4);
		a.insertInt(4, 4, 5);
		a.insertInt(5, 5, 6);
		a.insertInt(6, 6, 7);
		
		System.out.println(a.getArrayDisplay());
		System.out.println(a.getArrayDetails());
	}
}