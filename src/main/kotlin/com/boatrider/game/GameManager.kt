package com.boatrider.game

object GameManager {
	var gameState = GameState.END

	fun startGame() {
		gameState = GameState.PLAYING
	}
	fun waitGame() {
		gameState = GameState.WAITING
	}
	fun stopGame() {
		gameState = GameState.END
	}

	fun isPlaying(): Boolean {
		return gameState == GameState.PLAYING
	}
	fun isWaiting(): Boolean {
		return gameState == GameState.WAITING
	}
	fun isEnd(): Boolean {
		return gameState == GameState.END
	}
}