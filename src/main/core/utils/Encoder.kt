package utils

import net.bramp.ffmpeg.FFmpeg
import net.bramp.ffmpeg.FFmpegExecutor
import net.bramp.ffmpeg.FFprobe
import net.bramp.ffmpeg.builder.FFmpegBuilder
import net.bramp.ffmpeg.probe.FFmpegProbeResult
import java.io.File

class Encoder {

    fun encodeToMP4(sourceVideo: File): File {
        sourceVideo.let {
            val encodedVideoPath = it.path.split("\\.".toRegex())
                    .dropLastWhile { it.isEmpty() }.toTypedArray()[0] + ".mp4"
            val ffprobe = FFprobe()
            val executor = FFmpegExecutor(FFmpeg(), ffprobe)
            executor.createJob(createBuilder(ffprobe.probe(it.absolutePath),
                    encodedVideoPath)) { progress ->
                println(when (progress.isEnd) {
                    false -> "[Video Encoder] Encoding..."
                    else -> "[Video Encoder] Done - $encodedVideoPath"
                })
            }.run()
            it.delete()
            return File(encodedVideoPath)
        }
    }

    private fun createBuilder(probeResult: FFmpegProbeResult, output: String): FFmpegBuilder {
        return FFmpegBuilder()
                .setInput(probeResult)     // Filename, or a FFmpegProbeResult
                .overrideOutputFiles(true) // Override the output if it exists
                .addOutput(output)         // Filename for the destination
                .setFormat("mp4")          // Format is inferred from filename, or can be set
                .setTargetSize(500_000)    // Aim for a 500KB file
                .disableSubtitle()         // No subtitles
                .setAudioChannels(1)       // Mono audio
                .setAudioCodec("aac")      // using the aac codec
                .setAudioSampleRate(48_000)// at 48KHz
                .setAudioBitRate(32_768)   // at 32 kbit/s
                .setVideoCodec("libx264")  // Video using x264
                .setVideoFrameRate(24, 1)  // at 24 frames per second
                .setVideoResolution(1280, 720)
                .setStrict(FFmpegBuilder.Strict.EXPERIMENTAL) // Allow FFmpeg to use experimental specs
                .done()
    }
}
