package com.boatrider.util

import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Player

object Utils {
	fun findFallbackLocation(player: Player, range: Int = 50): Location? {
		val world = player.world
		val playerLoc = player.location

		val startX = playerLoc.blockX
		val startY = playerLoc.blockY
		val startZ = playerLoc.blockZ

		for (y in startY..world.maxHeight) {
			for (dx in -range..range) {
				for (dz in -range..range) {
					val x = startX + dx
					val z = startZ + dz

					val block = world.getBlockAt(x, y, z)
					if (block.type == Material.ICE) {
						return Location(world, x + 0.5, y + 2.0, z + 0.5)
					}
				}
			}
		}

		return null
	}
}