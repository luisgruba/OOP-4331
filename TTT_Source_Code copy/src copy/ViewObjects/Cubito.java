package ViewObjects;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Cubito extends StackPane{
	private Rectangle rect;
	private int row;
	private int col;
	private boolean isActive;
	
	public Cubito(int n) {
		rect = new Rectangle(n*49.2,n*49.2);
		rect.setFill(Color.CORNFLOWERBLUE);
		isActive = true;
	}
	
	public int getRow() {
		return row;
	}
	
	public void setRow(int row) {
		this.row = row;
	}
	
	public void setCol(int col) {
		this.col = col;
	}
	
	public int getCol() {
		return col;
	}
	
	public void whatDo() {
		if(isActive)
			pickedColor();
	}
	
	public void setActive(boolean occasionally) {
		isActive = occasionally;
	}
	
	public Rectangle getRectangle() {
		return rect;
	}
	
	public void pickedColor() {
		this.getRectangle().setFill(Color.ORANGE);
	}
	
	public void openColor() {
		this.getRectangle().setFill(Color.CORNFLOWERBLUE);
	}
}
