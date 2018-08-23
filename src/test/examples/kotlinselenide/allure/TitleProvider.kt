package kotlinselenide.allure

import com.codeborne.selenide.SelenideElement

interface  TitleProvider {

    companion object {
        private val titleReceivers = HashSet<TitleReceiver>()
    }

    fun register(receiver: TitleReceiver) {
        titleReceivers.add(receiver)
    }

    infix fun SelenideElement.title(title: String): SelenideElement {
        for (receiver in titleReceivers)
            receiver.onReceive(title)
        return this
    }
}