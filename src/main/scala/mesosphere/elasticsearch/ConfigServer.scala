package mesosphere.elasticsearch

import org.eclipse.jetty.server.{Request, Server}
import org.eclipse.jetty.server.handler.AbstractHandler
import javax.servlet.http.{HttpServletResponse, HttpServletRequest}
import java.io.{FileNotFoundException, File}
import scala.io.Source
import scala.collection.mutable
import java.net.URI

class ConfigServer(port: Int, configDir: String, scheduler: ElasticSearchScheduler) extends Logger {

  val server = new Server(port)
  server.setHandler(new ServeConfigHandler)
  server.start()

  def stop() {
    server.stop()
  }

  class ServeConfigHandler extends AbstractHandler {

    def handle(target: String, baseRequest: Request, request: HttpServletRequest, response: HttpServletResponse) = {

      info(s"Serving config resource: ${target}")

      val plainFilename = new File(new URI(target).getPath).getName // don't ask...
      val confFile = new File(s"${configDir}/${plainFilename}")

      if (!confFile.exists()){
        throw new FileNotFoundException(s"Couldn't file config file: ${configDir}/${plainFilename}. Please make sure it exists.")
      }

      val fileContent = Source.fromFile(confFile).getLines()
      val seedNodes = scheduler.taskSet.map(_.hostname)
      val substitutedContent = fileContent.map {
        _.replaceAllLiterally("${seedNodes}", seedNodes
          .mkString(","))
      }.mkString("\n")

      response.setContentType("application/octet-stream;charset=utf-8")
      response.setHeader("Content-Disposition", s"""attachment; filename="${plainFilename}" """)
      response.setHeader("Content-Transfer-Encoding", "binary")
      response.setHeader("Content-Length", substitutedContent.length.toString)

      response.setStatus(HttpServletResponse.SC_OK)
      baseRequest.setHandled(true)
      response.getWriter().println(substitutedContent)
    }

  }

}