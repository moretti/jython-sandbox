package moretti.jysandbox;

import java.io.Writer;
import java.util.Properties;

import org.python.core.PySystemState;
import org.python.util.PythonInterpreter;

import com.google.appengine.api.utils.SystemProperty;

public class PyConsole {
	private static final String pythonLibraryPath = "WEB-INF/python-lib/Lib.zip";

	public static void init() {
		if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Development) {
			// This is a workaround for a hack:
			// http://hoteljavaopensource.blogspot.com/2013/01/google-app-engine-and-jython.html
			System.getProperties().remove("file.encoding");
		}

		Properties system = (Properties) System.getProperties().clone();
		Properties post = new Properties();
		post.setProperty("python.path", pythonLibraryPath);
		PythonInterpreter.initialize(system, post, new String[0]);
	}

	/**
	 * Evaluate the Python source and write the output into the stream.
	 * 
	 * @param source
	 * @param out
	 */
	public static void exec(String source, Writer out) {
		PySystemState state = new PySystemState();
		PyConsoleClassLoader classLoader = new PyConsoleClassLoader(Thread
				.currentThread().getContextClassLoader());
		state.setClassLoader(classLoader);
		PythonInterpreter interpreter = new PythonInterpreter(null, state);
		interpreter.setOut(out);
		interpreter.setErr(out);
		interpreter.exec(source);
	}
}
