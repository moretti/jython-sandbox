package moretti.jysandbox;

import com.google.gson.annotations.SerializedName;

public class Event {
	@SerializedName("Message")
	private String _message;
	@SerializedName("Delay")
	private long _delay;

	public Event(String message, long delay) {
		_message = message;
		_delay = delay;
	}

	public String getMessage() {
		return _message;
	}

	/**
	 * 
	 * @return The delay in nanoseconds (10**-9).
	 */
	public long getDelay() {
		return _delay;
	}
}
