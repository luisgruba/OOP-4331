package Objects;

public class Cell {

	private int row;
	private int col;
	private String marker;
	private boolean isOccupied;
	
	public Cell(int row, int col) {
		this.row = row;
		this.col = col;
		marker = ".";
		isOccupied = false;
	}
	
	public int getRow() {
		return row;
	}
	
	public int getCol() {
		return col;
	}
	
	public String getMarker() {
		return marker;
	}
	
	public void setMarker(String s) {
		marker = s;
	}
	
	public boolean isOccupied() {
		return isOccupied;
	}
	
	public void setOccupied(Player player) {
		setMarker(player.getMarker());
		isOccupied = true;
	}
	
	public void setOccupied() {
		isOccupied = true;
	}
	
	public void printCell() {
		System.out.print("|\t"+ marker + "\t|");
	}
}
