package moretti.jysandbox;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class EventWriter extends StringWriter {
	private long _startTime;
	private long _endTime;
	private List<Event> _events;

	public EventWriter() {
		super();
		_events = new ArrayList<Event>();
	}

	public List<Event> getEvents() {
		return _events;
	}

	@Override
	public void write(String str) {
		_endTime = System.nanoTime();

		super.write(str);

		long delay;
		if (_startTime == 0) {
			delay = 0;
		} else {
			delay = _endTime - _startTime;
		}
		_startTime = System.nanoTime();
		_events.add(new Event(str, delay));
	}
}
