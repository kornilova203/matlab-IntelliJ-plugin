package com.github.korniloval.matlab.debug

import com.github.korniloval.matlab.debug.MatlabDebugProcess.Companion.DEBUG_PROMPT
import com.intellij.execution.process.ProcessOutputTypes
import com.intellij.openapi.application.ApplicationManager
import java.util.concurrent.LinkedBlockingDeque
import java.util.concurrent.atomic.AtomicBoolean

class DebuggerCommandsQueue(private val debugProcess: MatlabDebugProcess) : Runnable {
    val queue = LinkedBlockingDeque<Command>()
    private val messagesQueue = LinkedBlockingDeque<String>()
    var stop = AtomicBoolean(false)

    override fun run() {
        debugProcess.addTextListener(ProcessOutputTypes.STDOUT) { text ->
            messagesQueue.add(text)
            true
        }

        while (!stop.get()) {
            val command = queue.peek()
            if (command == null) {
                messagesQueue.clear()
                Thread.sleep(10)
            } else {
                if (command.started) {
                    val message = messagesQueue.poll() ?: continue
                    if (message == DEBUG_PROMPT) {
                        ApplicationManager.getApplication().invokeLater {
                            command.end()
                        }
                        queue.poll()
                    } else {
                        ApplicationManager.getApplication().invokeLater {
                            command.process(message)
                        }
                    }
                } else {
                    messagesQueue.clear()
                    command.started = true
                    debugProcess.run(command.cmd)
                }
            }
        }
    }
}

abstract class Command(val cmd: String) {
    var started = false

    abstract fun process(text: String)
    abstract fun end()
}
