package com.jordan.project.interfaces;


import com.jordan.project.widget.GridChart;

public interface ITouchEventNotify {

	public void notifyEventAll(GridChart chart);
	
	public void addNotify(ITouchEventResponse notify);
	
	public void removeNotify(int i);
	
	public void removeAllNotify();
}
