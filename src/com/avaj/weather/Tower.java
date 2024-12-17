package com.avaj.weather;

import com.avaj.aircraft.Flyable;
import java.util.ArrayList;
import java.util.List;

public class Tower {
	private final List<Flyable> observers = new ArrayList<>();

	public void register(Flyable p_flayable) {
		if (!observers.contains(p_flayable)) {
			observers.add(p_flayable);
		}
	}

	public void unregister(Flyable p_flayable) {
		observers.remove(p_flayable);
	}

	protected void conditionChanged() {
		for (Flyable flyable : new ArrayList<>(observers)) {
			flyable.updateConditions();
		}
	}
}
