package adventofcode.twenty18

import java.io.File

/**
 * @author Martin Trollip
 * @since 2018/12/07 07:07
 */
val STEPS_REGEX = "Step ([A-Z]) must be finished before step ([A-Z]) can begin.".toRegex()

const val DAY7_INPUT = "src/res/day7_input"

const val BASE_COST = 60
const val WORKERS = 5

fun main(args: Array<String>) {
    var steps = LinkedHashMap<String, Step>()

    File(DAY7_INPUT).readLines().map {
        val matchResult = STEPS_REGEX.find(it)
        val (step, nextStep) = matchResult!!.destructured
        steps.add(step, nextStep)
    }

    println(steps)
    println("The instructions are " + instructions(steps))

    var workers = List(WORKERS, { Worker() })
    println()
    println("The work will take ${work(steps, workers)} seconds")
}

fun LinkedHashMap<String, Step>.add(step: String, next: String) {
    var thisStep = get(step)

    if (thisStep != null) {
        thisStep.addNext(next)
    } else {
        var cost = BASE_COST + step.toCharArray().first().toLowerCase().toInt() - 96
        put(step, Step(step, mutableListOf(next), cost = cost))
    }

    var nextStep = get(next)
    if (nextStep != null) {
        nextStep.addRequired(step)
    } else {
        var cost = BASE_COST + next.toCharArray().first().toLowerCase().toInt() - 96
        put(next, Step(next, requiredSteps = mutableListOf(step), cost = cost))
    }
}

var completedSteps = ArrayList<String>()

fun instructions(steps: LinkedHashMap<String, Step>): String {
    var newInstructions = ""

    while (completedSteps.size < steps.size) {
        var readiness = ArrayList<String>()
        for (step in steps.values) {
            if (!completedSteps.contains(step.name)) {
                if (step.isReady(completedSteps)) {
                    readiness.add(step.name)
                }
            }
        }
        readiness.sort()



        newInstructions += readiness.first()
        completedSteps.add(readiness.first())
    }
    return newInstructions
}

fun List<Worker>.printStatus(): String {
    var status = ""

    for (worker in this) {
        if (worker.busy) {
            status += worker.work.name.padStart(10)
        } else {
            status += "idle".padStart(10)
        }
    }
    return status
}

fun List<Worker>.getAvailable(): List<Worker> {
    return this.filter { !it.busy }
}

fun List<Worker>.getInProgress(): List<Step> {
    return this.filter { it.busy }.map { it.work }
}

fun List<Worker>.workWork(): List<Step> {
    var workDone = ArrayList<Step>()
    for (worker in this.filter { it.busy }) {
        worker.doUnitOfWork()
        if (!worker.busy) {
            workDone.add(worker.work)
        }
    }

    return workDone
}

fun Int.toc(): Int {
    return this + 1
}

var completedWork = ArrayList<String>()

fun work(steps: LinkedHashMap<String, Step>, workers: List<Worker>): Int {
    var tic = 0

    var readiness = ArrayList<String>()
    while (completedWork.size < steps.size) {
        for (step in steps.values) {
            if (!workers.getInProgress().contains(step) && !completedWork.contains(step.name) && !readiness.contains(step.name)) {
                if (step.isReady(completedWork)) {
                    readiness.add(step.name)
                }
            }
        }
        readiness.sort()
        println("$tic ${workers.printStatus()} ready=$readiness done=${completedWork}")

        while (workers.getAvailable().isNotEmpty() && readiness.isNotEmpty()) {
            workers.getAvailable().first().assignWork(steps.get(readiness.first())!!)
            readiness.remove(readiness.first())
        }

        val workDone = workers.workWork()

        if (workDone.isNotEmpty()) {
            for (step in workDone) {
                completedWork.add(step.name)
            }
        }
        tic = tic.toc()
    }

    return tic
}

data class Step(var name: String, var nextSteps: MutableList<String> = ArrayList(), var requiredSteps: MutableList<String> = ArrayList(), var cost: Int = -610) {
    fun addNext(step: String) {
        nextSteps.add(step)
        nextSteps.sort()
    }

    fun addRequired(step: String) {
        requiredSteps.add(step)
        requiredSteps.sort()
    }

    fun isReady(completedSteps: ArrayList<String>): Boolean {
        return requiredSteps.isEmpty() || completedSteps.containsAll(requiredSteps)
    }

    fun doUnitOfWork() {
        cost--
    }
}

data class Worker(var busy: Boolean = false, var work: Step = Step(".")) {

    fun assignWork(work: Step) {
        this.work = work
        this.busy = true
    }

    fun doUnitOfWork() {
        work.doUnitOfWork()
        if (work.cost <= 0) {
            busy = false
        }
    }
}
