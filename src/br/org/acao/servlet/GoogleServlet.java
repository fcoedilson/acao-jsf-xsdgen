package br.org.acao.servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.org.acao.util.DateUtil;
import br.org.acao.util.VelocityUtil;
import br.org.acao.util.ZipUtil;


public class GoogleServlet extends HttpServlet {

	private static final long serialVersionUID = 7909683619955293507L;
	private static final Log logger = LogFactory.getLog(GoogleServlet.class);

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String sid = request.getParameter("id");
		String tmpIds = request.getParameter("ids");
		String sdataInicio = request.getParameter("di");
		String svelocidadeMaxima = request.getParameter("vm");
		String sautoCamera = request.getParameter("ac");
		String sexibirRastro = request.getParameter("er");
		String sexibirPontosRastro = request.getParameter("epr");

		int id = sid != null ? Integer.parseInt(sid) : 0;
		float velocidadeMaxima = svelocidadeMaxima != null ? Float.parseFloat(svelocidadeMaxima) : 60;
		boolean autoCamera = sautoCamera != null ? Boolean.parseBoolean(sautoCamera) : false;
		boolean exibirRastro = sexibirRastro != null ? Boolean.parseBoolean(sexibirRastro) : false;
		boolean exibirPontosRastro = sexibirPontosRastro != null ? Boolean.parseBoolean(sexibirPontosRastro) : false;
		Date dataInicio = DateUtil.getDateTime(DateUtil.getDateNow(), sdataInicio);
		String[] sids = tmpIds != null ? tmpIds.split(",") : new String[0];

		List<Integer> veiculoIds = new ArrayList<Integer>();
		for (String tId : sids) {
			veiculoIds.add(Integer.parseInt(tId));
		}

		try {
			byte[]  content = VelocityUtil.merge("route.vm",
					new String[]{"exibirRota","exibirPontosRota","autoCamera"},
					new Object[]{ exibirRastro , exibirPontosRastro , autoCamera});
			content = ZipUtil.zip("sgf.kml", content);
			download(content, response);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		request.getSession().invalidate();
	}

	private void download(byte[] content, HttpServletResponse response) throws IOException {

		response.setHeader("Content-Disposition", "attachment; filename=sgf.kmz");
		response.setHeader("Content-Transfer-Encoding", "binary");
		response.setContentType("application/vnd.google-earth.kmz");
		response.setContentLength(content.length);
		OutputStream out = response.getOutputStream();
		out.write(content, 0, content.length);
		out.close();
	}
	
	 protected void service(HttpServletRequest request, HttpServletResponse response)
		      throws ServletException, IOException {

		    response.setContentType("text/html;charset=UTF-8");
		    PrintWriter out = response.getWriter();

		    // Print out the IP address of the caller
		    out.println("174.142.214.78");

		  }
}