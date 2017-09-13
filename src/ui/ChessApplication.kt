package ui

import javafx.application.Application
import javafx.stage.Stage
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.thread
import kotlin.concurrent.withLock

class ChessApplication : Application() {
	
	companion object {
		
		private val _initLock = ReentrantLock()
		private val _initCondition = _initLock.newCondition()
		private var _instance: ChessApplication? = null
			set(value) {
				_initLock.withLock {
					field = value
					if (value != null) _initCondition.signalAll()
				}
			}
		private val instance: ChessApplication
			get() {
				_initLock.withLock {
					_instance?.let { return it }
					thread { Application.launch(ChessApplication::class.java) }
					_initCondition.await()
					return _instance!!
				}
			}
		fun launch():ChessApplication {
			return instance
		}
	}
	
	override fun start(primaryStage: Stage) {
		_instance = this
	}
	override fun stop() {
		_instance = null
	}
	
}
