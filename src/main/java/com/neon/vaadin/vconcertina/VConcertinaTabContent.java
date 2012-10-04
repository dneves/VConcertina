package com.neon.vaadin.vconcertina;

import com.vaadin.ui.CssLayout;

public class VConcertinaTabContent extends CssLayout {

	private final VConcertinaTabInterface tabInterface;

	public VConcertinaTabContent( VConcertinaTabInterface tabInterface ) {
		if ( tabInterface == null ) {
			throw new IllegalArgumentException( "tab content parent is null" );
		}
		this.tabInterface = tabInterface;
		this.addStyleName( "v-concertina-tab-content" );
	}

	/**
	 *
	 * @return	the tab component where this content belongs
	 */
	public VConcertinaTabInterface getTabInterface() {
		return tabInterface;
	}
}
