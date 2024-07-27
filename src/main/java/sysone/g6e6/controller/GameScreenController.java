package sysone.g6e6.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import sysone.g6e6.service.GameScreenService;

public class GameScreenController {
	GameScreenService gameScreenService = new GameScreenService();
	@FXML
	public void createGameMap(ActionEvent e){
		try{
			gameScreenService.createGame(1, 8);
		}catch (Exception ex){
			ex.printStackTrace();
		}
	}
}
