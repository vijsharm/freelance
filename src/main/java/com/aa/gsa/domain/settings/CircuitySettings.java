package com.aa.gsa.domain.settings;

public class CircuitySettings {

	private final int from;
	
	private final int to;
	
	private final int percentage;
	
	public CircuitySettings(int from, int to, int percentage) {
		this.from = from;
		this.to = to;
		this.percentage = percentage;
	}

	public int getFrom() {
		return from;
	}

	public int getTo() {
		return to;
	}

	public int getPercentage() {
		return percentage;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + from;
		result = prime * result + percentage;
		result = prime * result + to;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CircuitySettings other = (CircuitySettings) obj;
		if (from != other.from)
			return false;
		if (percentage != other.percentage)
			return false;
		if (to != other.to)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CircuitySettings [from=" + from + ", to=" + to + ", percentage=" + percentage + "]";
	}	
}


