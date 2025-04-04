package com.boatrider.util

import com.boatrider.Plugin.instance
import org.bukkit.Bukkit
import org.bukkit.boss.BarColor
import org.bukkit.boss.BarStyle
import org.bukkit.boss.BossBar
import org.bukkit.scheduler.BukkitRunnable

object Counter {
	fun createBossBarCountdown(title: String, seconds: Int, perSecond: (sec: Int) -> Unit): BossBar {
		val bar = Bukkit.createBossBar("${title}: ${seconds}", BarColor.GREEN, BarStyle.SOLID)
		bar.setProgress(1.0)
		bar.setVisible(true)

		val porcentage = bar.progress / seconds
		var second = seconds

		object : BukkitRunnable() {
			override fun run() {
				second -= 1

				if (bar.progress - porcentage < 0.0) {
					this.cancel()
					bar.removeAll()
				} else {
					bar.progress -= porcentage
					bar.setTitle("${title}: ${second}")
				}

				perSecond(second)
			}
		}.runTaskTimer(instance, 0, 20)

		return bar
	}
}