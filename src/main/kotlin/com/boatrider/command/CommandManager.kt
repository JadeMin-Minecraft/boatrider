/*package com.boatrider.command

import com.boatrider.command.commands.Commands
import io.papermc.paper.plugin.bootstrap.BootstrapContext
import io.papermc.paper.plugin.bootstrap.PluginBootstrap
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents

class CommandManager : PluginBootstrap {
	override fun bootstrap(context: BootstrapContext) {
		context.lifecycleManager.registerEventHandler(LifecycleEvents.COMMANDS) { commands ->
			for (command in Commands.createCommands()) {
				commands.registrar().register(command.build())
			}
		}
	}
}*/