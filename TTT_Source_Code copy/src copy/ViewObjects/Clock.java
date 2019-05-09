package ViewObjects;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.util.Duration;
import javafx.scene.*;

public class Clock extends StackPane{

	private Timeline animation;
	private int time;
	private String timer = "";
	
	Label label;
	
	public Clock(int time) {
		this.time = time;
		label = new Label(time+"");
		label.setFont(Font.font(30));
		
		getChildren().add(label);
		
	}
	
	public void getTimer() {
		
		if(time > 0)
			time--;
		timer = time+"";
		label.setText(timer);
	}
	
	public void setAnimation(Timeline tl) {
		animation = tl;
		animation.setCycleCount(Timeline.INDEFINITE);
	}
	
}











































