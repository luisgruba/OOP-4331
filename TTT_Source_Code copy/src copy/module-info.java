/**
 * 
 */
/**
 * @author luisgruber
 *
 */
module TTTFinal {
	requires javafx.graphics;
	requires javafx.controls;
	requires javafx.media;
	requires javafx.web;
	exports View;
	opens View to javafx.graphics;
	exports Main;
	opens Main to javafx.graphics;
	
}