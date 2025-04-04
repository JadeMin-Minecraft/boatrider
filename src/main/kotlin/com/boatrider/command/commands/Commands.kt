package com.boatrider.command.commands

import com.boatrider.game.GameManager
import com.boatrider.util.Counter.createBossBarCountdown
import com.mojang.brigadier.Command
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import io.papermc.paper.command.brigadier.CommandSourceStack
import io.papermc.paper.command.brigadier.Commands
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.title.Title
import org.bukkit.Bukkit

object Commands {
	fun createCommands(): List<LiteralArgumentBuilder<CommandSourceStack>> {
		return listOf(
			Commands.literal("bt")
				.then(Commands.literal("start")
					.executes {
						gameStart(it)
					}
				)
				.then(Commands.literal("stop")
					.executes {
						gameStop(it)
					}
				)
		)
	}


	fun gameStart(ctx: CommandContext<CommandSourceStack>): Int {
		val player = ctx.source.sender

		player.sendMessage("Game started!")
		GameManager.startGame()

		val counter = createBossBarCountdown(
			title = "게임 시작",
			seconds = 10,
			perSecond = { sec ->
				for (player in Bukkit.getOnlinePlayers()) {
					if (sec == 0) {
						player.showTitle(
							Title.title(
								Component.text(
									"게임 시작!",
									NamedTextColor.GREEN
								),
								Component.text(
									""
								)
							)
						)
					} else {
						player.showTitle(
							Title.title(
								Component.text(
									"게임 시작까지 ${sec}초 남았습니다!",
									NamedTextColor.YELLOW
								),
								Component.text(
									""
								)
							)
						)
					}
				}
			}
		)
		for (player in Bukkit.getOnlinePlayers()) {
			counter.addPlayer(player)
		}

		return Command.SINGLE_SUCCESS
	}
	fun gameStop(ctx: CommandContext<CommandSourceStack>): Int {
		val player = ctx.source.sender

		player.sendMessage("Game stopped!")
		GameManager.stopGame()

		return Command.SINGLE_SUCCESS
	}
}