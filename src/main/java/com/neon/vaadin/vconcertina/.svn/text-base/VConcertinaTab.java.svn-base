package com.neon.vaadin.vconcertina;

import com.neon.vaadin.vconcertina.listener.VConcertinaTabActionListener;
import com.vaadin.event.LayoutEvents;
import com.vaadin.ui.Component;

public class VConcertinaTab implements VConcertinaTabInterface {

	private final VConcertina vConcertina;

	private final VConcertinaTabHeader tabHeader = new VConcertinaTabHeader();

	private final VConcertinaTabContent tabContent = new VConcertinaTabContent();

	private boolean closable = false;

	private boolean minimizable = true;

	private boolean maximizable = true;


	public VConcertinaTab( VConcertina vConcertina, Component component ) {
		this( vConcertina, null, component );
	}

	public VConcertinaTab( VConcertina vConcertina, String caption, Component component ) {
		this.vConcertina = vConcertina;

		if ( component != null ) {
			tabContent.addComponent( component );
		}
		tabHeader.setCaption( caption );
		tabHeader.setActionListener( new VConcertinaTabActionListener() {
			@Override
			public void closeTab() {
				VConcertinaTab.this.vConcertina.removeTab( VConcertinaTab.this );
			}

			@Override
			public void minimizeTab() {
				VConcertinaTab.this.vConcertina.minimize( VConcertinaTab.this );
			}

			@Override
			public void maximizeTab() {
				VConcertinaTab.this.vConcertina.maximize( VConcertinaTab.this );
			}
		});
		this.tabHeader.addListener( new LayoutEvents.LayoutClickListener() {
				@Override
				public void layoutClick(LayoutEvents.LayoutClickEvent event) {
					VConcertinaTab.this.vConcertina.toggle( VConcertinaTab.this );
				}
			});
	}

	@Override
	public VConcertinaTabHeaderInterface getTabHeader() {
		return tabHeader;
	}

	@Override
	public VConcertinaTabContent getTabContent() {
		return tabContent;
	}

	@Override
	public void setClosable(boolean isClosable) {
		this.closable = isClosable;
		tabHeader.setClosable( isClosable );
	}

	@Override
	public boolean isClosable() {
		return closable;
	}

	@Override
	public void setMinimizable(boolean isMinimizable) {
		this.minimizable = isMinimizable;
		tabHeader.setMinimizable( isMinimizable );
	}

	@Override
	public boolean isMinimizable() {
		return minimizable;
	}

	@Override
	public void setMaximizable(boolean isMaximizable) {
		this.maximizable = isMaximizable;
		tabHeader.setMaximizable( isMaximizable );
	}

	@Override
	public boolean isMaximizable() {
		return maximizable;
	}

	@Override
	public void setCaption(String caption) {
		tabHeader.setCaption( caption );
	}

}
