package com.ioffeivan.videoplayer.video.utils

import java.time.Duration
import java.util.Locale

fun formatDuration(seconds: Int,): String {
    val duration = Duration.ofSeconds(seconds.toLong())
    return when {
        duration.toDays() > 0 -> {
            String.format(
                Locale.getDefault(),
                "%d:%02d:%02d:%02d",
                duration.toDays(),
                duration.toHours() % 24,
                duration.toMinutes() % 60,
                duration.seconds % 60,
            )
        }

        duration.toHours() > 0 -> {
            String.format(
                Locale.getDefault(),
                "%d:%02d:%02d",
                duration.toHours() % 24,
                duration.toMinutes() % 60,
                duration.seconds % 60,
            )
        }

        else -> String.format(
            Locale.getDefault(),
            "%d:%02d",
            duration.toMinutes() % 60,
            duration.seconds % 60,
        )
    }
}