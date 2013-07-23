package moretti.jysandbox;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

@SuppressWarnings("serial")
public class PySandboxServlet extends HttpServlet {

	public void init() throws ServletException {
		PyConsole.init();
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");

		String source = req.getParameter("body");
		if (source == null) {
			return;
		}

		SandboxResult result = new SandboxResult();
		EventWriter eventWriter = new EventWriter();
		try {
			PyConsole.exec(source, eventWriter);
		} catch (Exception ex) {
			result.Errors = ex.getMessage() != null ? ex.getMessage() : ex
					.toString();
		}
		result.Events = eventWriter.getEvents();

		PrintWriter writer = resp.getWriter();
		writer.print(new Gson().toJson(result));
	}
}
