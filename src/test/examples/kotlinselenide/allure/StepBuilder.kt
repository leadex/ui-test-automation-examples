package kotlinselenide.allure

import com.codeborne.selenide.logevents.LogEvent
import com.codeborne.selenide.logevents.LogEventListener
import io.qameta.allure.Allure
import io.qameta.allure.model.Status
import io.qameta.allure.model.StatusDetails
import io.qameta.allure.model.StepResult
import io.qameta.allure.util.ResultsUtils
import java.util.*

object StepBuilder : LogEventListener, TitleReceiver {

    private val lifecycle = Allure.getLifecycle()
    private var elementTitle: String? = null
    private var currentAction: String? = null

    override fun onReceive(title: String) {
        elementTitle = title
        currentAction = null
    }

    override fun onEvent(currentLog: LogEvent?) {
        with(currentLog.toString()) {
            currentAction = when {
                contains("click") -> "Pressing"
                contains("send") -> "Filling"
                contains("wait until") -> "Checking"
                else -> null
            }
        }
        step(currentLog)
    }

    private fun step(event: LogEvent?) {
        if (!elementTitle.isNullOrEmpty() && !currentAction.isNullOrEmpty()) {
            val name = "$currentAction $elementTitle"
            println(name)

            with(lifecycle) {
                val stepUUID = UUID.randomUUID().toString()
                currentTestCase.ifPresent {
                    startStep(stepUUID, StepResult()
                            .withName(name)
                            .withStatus(Status.PASSED))
                }

                updateStep { stepResult -> stepResult.start = stepResult.start - event!!.duration }

                if (LogEvent.EventStatus.FAIL == event!!.status) {
                    updateStep { stepResult ->
                        val details = ResultsUtils.getStatusDetails(event.error)
                                .orElse(StatusDetails())
                        stepResult.status = Status.FAILED
                        stepResult.statusDetails = details
                    }
                }
                stopStep(stepUUID)
            }
            elementTitle = null
            currentAction = null
        }
    }
}