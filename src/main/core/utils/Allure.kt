package utils

import com.automation.remarks.junit.VideoRule
import com.automation.remarks.video.recorder.VideoRecorder
import com.codeborne.selenide.Configuration
import com.google.common.io.Resources
import org.junit.AssumptionViolatedException
import org.junit.runner.Description
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.*

object Allure : VideoRule(), Attachment {

    override fun skipped(e: AssumptionViolatedException?, description: Description?) {
        failed(e, description!!)
    }

    override fun failed(e: Throwable?, d: Description) {
        super.failed(e, d)
        attachSelenideScreenshot()
        attachVideo(Encoder().encodeToMP4(VideoRecorder.getLastRecording()))
    }

    override fun finished(description: Description?) {
        mkdirs("${System.getProperty("user.dir")}/build/allure-results")
        writeProperties()
        copyCategories()
    }

    private fun writeProperties() {
        val file = createNewFile(File("${System.getProperty("user.dir")}/build/allure-results", "environment.properties"))
        FileOutputStream(file, false).use { fos ->
            createProperties().store(fos, null)
            fos.flush()
            fos.close()
        }
    }

    private fun createProperties(): Properties {
        with(Properties()) {
            setProperty("OS", System.getProperty("os.name"))
            setProperty("Env", Configuration.baseUrl)
            setProperty("User", System.getProperty("user.name"))
            return this
        }
    }

    private fun copyCategories() {
        FileInputStream(File(Resources.getResource("categories.json").toURI())).channel.use { source ->
            FileOutputStream("${System.getProperty("user.dir")}/build/allure-results/categories.json").channel
                    .use { destination -> destination.transferFrom(source, 0, source.size()) }
        }
    }

    fun mkdirs(dirs: String) {
        with(File(dirs)) {
            if (!exists()) mkdirs()
        }
    }

    fun createNewFile(file: File): File {
        with(file) {
            if (!exists()) createNewFile()
            return this
        }
    }
}