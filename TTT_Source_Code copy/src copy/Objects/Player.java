package Objects;

public class Player {

	private String name;
	private String marker;
	private int wins;
	private int matches;
	private int position;
	
	public Player(String name, String marker) {
		this.name = name;
		this.marker = marker;
		wins = 0;
		matches = 0;
		position = -1;
	}
	
	public void addMatch() {
		matches++;
	}
	
	public void addWin() {
		wins++;
	}
	
	public void setMarker(String s) {
		marker = s;
	}
	
	public String getMarker() {
		return marker;
	}
	
	public String getName() {
		return name;
	}
	
	public String getRecord() {
		return wins+"-"+matches;
	}
	
	public void setPosition(int order) {
		position = order;
	}
	
	public int getPosition() {
		return position;
	}
}
