package com.neon.vaadin.vconcertina;

public interface VConcertinaTabInterface {

	public VConcertinaTabHeaderInterface getTabHeader();

	public VConcertinaTabContent getTabContent();

	public void setClosable( boolean isClosable );

	public boolean isClosable();

	public void setMinimizable( boolean isMinimizable );

	public boolean isMinimizable();

	public void setMaximizable( boolean isMaximizable );

	public boolean isMaximizable();

	public void setCaption( String caption );

}
