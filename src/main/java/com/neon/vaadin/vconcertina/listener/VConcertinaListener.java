package com.neon.vaadin.vconcertina.listener;

import com.neon.vaadin.vconcertina.VConcertinaTabInterface;

public interface VConcertinaListener {

	/**
	 * Called whenever a new tab is added to the widget.
	 *
	 * @param tabInterface    the added tab component
	 */
	public void tabAdded( VConcertinaTabInterface tabInterface );

	/**
	 * Called whenever a tab is removed from the widget.
	 *
	 * @param tabInterface    the removed tab component
	 */
	public void tabRemoved( VConcertinaTabInterface tabInterface );

	/**
	 * Called whenever a tab is minimized.
	 *
	 * @param tabInterface    the minimized tab component
	 */
	public void tabMinimized( VConcertinaTabInterface tabInterface );

	/**
	 * Called whenever a tab is maximized.
	 *
	 * @param tabInterface    the maximized tab component
	 */
	public void tabMaximized( VConcertinaTabInterface tabInterface );

}
