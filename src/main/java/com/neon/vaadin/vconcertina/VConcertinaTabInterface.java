package com.neon.vaadin.vconcertina;

public interface VConcertinaTabInterface {

	/**
	 *
	 * @return
	 */
	public VConcertinaTabHeaderInterface getTabHeader();

	/**
	 *
	 * @return
	 */
	public VConcertinaTabContent getTabContent();

	/**
	 *
	 * @param isClosable
	 */
	public void setClosable( boolean isClosable );

	/**
	 *
	 * @return
	 */
	public boolean isClosable();

	/**
	 *
	 * @param isMinimizable
	 */
	public void setMinimizable( boolean isMinimizable );

	/**
	 *
	 * @return
	 */
	public boolean isMinimizable();

	/**
	 *
	 * @param isMaximizable
	 */
	public void setMaximizable( boolean isMaximizable );

	/**
	 *
	 * @return
	 */
	public boolean isMaximizable();

	/**
	 * Defines the tab caption.
	 *
	 * @param caption    the new tab caption
	 */
	public void setTitle( String caption );

	/**
	 * Gets the tab component caption.
	 *
	 * @return	the tab caption
	 */
	public String getTitle();

}
