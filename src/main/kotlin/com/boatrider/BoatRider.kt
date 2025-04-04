package com.boatrider

import com.boatrider.command.commands.Commands
import com.boatrider.event.EventListener
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents
import org.bukkit.plugin.java.JavaPlugin

class BoatRider : JavaPlugin() {
	override fun onEnable() {
		Plugin.instance = this

		lifecycleManager.registerEventHandler(LifecycleEvents.COMMANDS) { commands ->
			for (command in Commands.createCommands()) {
				commands.registrar().register(command.build())
			}
		}
		server.pluginManager.registerEvents(EventListener(), this)
	}

	override fun onDisable() {
		// Plugin shutdown logic
	}
}