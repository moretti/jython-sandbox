package moretti.jysandbox;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.logging.Logger;

class PyConsoleClassLoader extends URLClassLoader {
	private static final String[] RESTRICTED_PACKAGES = { "com.google" };
	private static final Logger log = Logger
			.getLogger(PyConsoleClassLoader.class.getName());

	public PyConsoleClassLoader(URL[] urls) {
		super(urls);
	}

	public PyConsoleClassLoader(URL[] urls, ClassLoader parent) {
		super(urls, parent);
	}

	public PyConsoleClassLoader(ClassLoader parent) {
		super(new URL[0], parent);
	}

	@Override
	protected void addURL(URL url) {
		throw new RuntimeException("Cannot extend classloader at runtime");
	}

	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		for (String packageName : RESTRICTED_PACKAGES)
			if (name.startsWith(packageName)) {
				log.warning("Attempt to access restricted class: " + name);
				throw new ClassNotFoundException(name);
			}
		return super.findClass(name);
	}
}
