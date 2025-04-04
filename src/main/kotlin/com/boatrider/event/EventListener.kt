package com.boatrider.event

import com.boatrider.game.GameManager
import net.kyori.adventure.identity.Identity
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.title.Title
import org.bukkit.GameMode
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.event.vehicle.VehicleExitEvent
import org.bukkit.event.vehicle.VehicleMoveEvent
import org.bukkit.util.Vector

class EventListener : Listener {
	val onlinePlayers = mutableListOf<Player>()
	val fallbackLoc = mutableMapOf<Identity, Location>()

	@EventHandler
	fun onPlayerFall(event: VehicleMoveEvent) {
		if (!GameManager.isPlaying()) return

		val vehicle = event.vehicle
		val passenger = vehicle.passengers.firstOrNull() as? Player ?: return
		val underBlock = passenger.location.subtract(0.0, 1.0, 0.0).block

		if (
			passenger.gameMode != GameMode.SPECTATOR &&
			underBlock.type == Material.RED_CONCRETE
		) {
			val fallbackLoc = fallbackLoc[passenger.identity()]

			if (fallbackLoc != null) {
				passenger.showTitle(
					Title.title(
						Component.text(
							"OUT OF TRACK!",
							NamedTextColor.RED
						),
						Component.text(
							"트랙으로 되돌아갑니다!"
						)
					)
				)

				val newBoat = vehicle.world.spawnEntity(
					fallbackLoc,
					EntityType.BAMBOO_RAFT,
				)
				newBoat.addPassenger(passenger)
				newBoat.velocity = Vector(0.0, 0.0, 0.0)
				vehicle.remove()
			}
		}
	}

	@EventHandler
	fun onBoatLandOnIce(event: VehicleMoveEvent) {
		if (!GameManager.isPlaying()) return

		val vehicle = event.vehicle
		val passenger = vehicle.passengers.firstOrNull() as? Player ?: return
		val underBlock = passenger.location.subtract(0.0, 1.0, 0.0).block

		if (
			underBlock.type == Material.EMERALD_BLOCK
		) {
			passenger.showTitle(
				Title.title(
					Component.text(
						"CLEAR!",
						NamedTextColor.GREEN
					),
					Component.text(
						""
					)
				)
			)
		}
	}

	@EventHandler
	fun onLeaveBoat(event: VehicleExitEvent) {
		if (!GameManager.isPlaying()) return

		val player = event.exited as? Player ?: return
		val vehicle = event.vehicle

		vehicle.remove()

		/*if (player.gameMode != GameMode.SPECTATOR) {
			event.isCancelled = true

			/*player.sendMessage(
				Component.text(
					"경기 도중 보트에서 내릴 수 없습니다!",
					NamedTextColor.RED
				)
			)*/
		} else {
			vehicle.remove()
		}*/
	}

	@EventHandler
	fun savePlayerFallbackLoc(event: VehicleMoveEvent) {
		if (!GameManager.isPlaying()) return

		val vehicle = event.vehicle
		val passenger = vehicle.passengers.firstOrNull() as? Player ?: return
		val underBlock = passenger.location.subtract(0.0, 1.0, 0.0).block

		if (underBlock.type == Material.ICE) {
			fallbackLoc[passenger.identity()] = passenger.location
		}
	}

	@EventHandler
	fun onPlayerJoin(event: PlayerJoinEvent) {
		val player = event.player

		onlinePlayers.add(player)
	}

	@EventHandler
	fun onPlayerQuit(event: PlayerQuitEvent) {
		val player = event.player

		onlinePlayers.remove(player)
	}
}