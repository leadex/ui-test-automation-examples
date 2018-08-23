package utils

import com.codeborne.selenide.Screenshots
import io.qameta.allure.Allure
import utils.Constants.HTML
import utils.Constants.JSON
import utils.Constants.MP4
import utils.Constants.PNG
import java.io.File
import java.io.InputStream

interface Attachment {

    fun attachSelenideScreenshot() {
        val target = Screenshots.takeScreenShotAsFile()
        target?.let { Allure.addAttachment("Screenshot", PNG, it.inputStream(), "png") }
    }

    fun attachJson(title: String, input: InputStream?) = input?.let { Allure.addAttachment(title, JSON, input, "json") }

    fun attachVideo(content: File) = content.let { Allure.addAttachment("Video", MP4, content.inputStream(), "mp4") }

    fun attachVideoHtml(videoUUID: String, host: String) = videoUUID.let {
        Allure.addAttachment("Video", HTML, "<html><body><video width='100%'"
                + " height='100%' controls autoplay><source src='"
                + "$host/video/video_$videoUUID.mp4"
                + "' type='video/mp4'></video></body></html>", ".html")
    }
}
