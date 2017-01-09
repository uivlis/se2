package com.unicorncoding.a2s2t

import akka.actor.ActorSystem
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.Toast
import com.typesafe.config.ConfigFactory
import com.unicorncoding.a2s2t.fragments.DemoFragment
import akka.actor.Actor
import akka.actor.ActorRef
import akka.actor.ActorSystem
import akka.actor.PoisonPill
import akka.actor.Props
import akka.actor.Terminated
import akka.pattern.ask
import akka.util.Timeout
import com.typesafe.config.ConfigFactory
import scala.io.StdIn.readLine
import Command._

class DemoActivity extends Activity
  with AkkaUtils
  with AndroidUtils {

  override def TAG: String = "DemoActivity"

  override val actorSystemName: String = "ClusterSystem"

  var frontend: Option[ActorRef] = None
  
  override def onCreate(savedInstanceState: Bundle): Unit = {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.main)
	frontend =  Some(actorSystem.actorOf(Props(DemoActivityActor(this)), name = "frontend"))
    initDemoFragment()
  }

  override def onBackPressed(): Unit = {
    actorSystem.shutdown()

    super.onBackPressed()
  }
  
  def initDemoFragment(): Unit = {
    val demoFragment = new DemoFragment
    getFragmentManager
      .beginTransaction()
      .add(R.id.fragment_container, demoFragment)
      .commit()
  }

  def execOnUi(f: => Unit): Unit = {
    runOnUiThread(new Runnable {
      override def run(): Unit = f
    })
  }
}

trait AkkaUtils {
  self: Activity ⇒
  val actorSystemName: String
  lazy val actorSystem = ActorSystem(
    actorSystemName,
    ConfigFactory.load()
  )
}

trait AndroidUtils {

  def TAG: String

  def logInfo(msg: String): Unit = {
    Log.i(TAG, msg)
  }

  def logWarn(msg: String): Unit = {
    Log.w(TAG, msg)
  }

  def logError(msg: String): Unit = {
    Log.e(TAG, msg)
  }

  def mkOnClickListener(f: => Unit): OnClickListener = {
    new OnClickListener {
      override def onClick(v: View): Unit = f
    }
  }

  def mkToast(context: Context, msg: String, length: Int = Toast.LENGTH_SHORT): Toast = {
    Toast.makeText(context, msg, length)
  }

}

case class DemoActivityActor(parent: Activity) extends Actor {
	val activity = parent.asInstanceOf[DemoActivity]
	
	def receive = {
		case r: ResultImg =>
		 activity.execOnUi({
			println("It's " + r.img.condMeteo)
      })
	}
}