package com.neon.vaadin.vconcertina;

import com.neon.vaadin.vconcertina.listener.VConcertinaTabActionListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

public class VConcertinaTabHeader extends HorizontalLayout implements VConcertinaTabHeaderInterface {

	private VConcertinaTabActionListener actionListener = null;

	private final Label labelCaption = new Label();

	private final HorizontalLayout layoutActions = new HorizontalLayout();

	private final Button buttonClose = new Button( "X" );
	private final Button buttonMinimize = new Button( "-" );
	private final Button buttonMaximize = new Button( "+" );


	public VConcertinaTabHeader() {
		this("");
	}

	public VConcertinaTabHeader( String caption ) {
		setupComponents();
		setupLayout();
		setupListeners();
		setCaption(caption);
		setClosable( false );
		setMinimizable( true );
		setMaximizable( true );
	}

	private void setupComponents() {
		this.addStyleName("v-concertina-tab-header");
		this.setSizeUndefined();
		this.setWidth("100%");

		layoutActions.setSizeUndefined();
		buttonClose.setSizeUndefined();
		buttonMaximize.setSizeUndefined();
		buttonMinimize.setSizeUndefined();

		labelCaption.setSizeUndefined();
	}

	private void setupLayout() {
		layoutActions.addComponent( buttonMinimize );
		layoutActions.addComponent( buttonMaximize );
		layoutActions.addComponent( buttonClose );
		layoutActions.setSpacing( true );

		this.addComponent( labelCaption );
		this.addComponent( layoutActions );
		this.setComponentAlignment( labelCaption, Alignment.MIDDLE_LEFT );
		this.setComponentAlignment( layoutActions, Alignment.MIDDLE_RIGHT );
	}

	private void setupListeners() {
		buttonClose.addListener( new Button.ClickListener() {
			@Override
			public void buttonClick(Button.ClickEvent event) {
				if ( actionListener != null ) {
					actionListener.closeTab();
				}
			}
		});
		buttonMaximize.addListener( new Button.ClickListener() {
			@Override
			public void buttonClick(Button.ClickEvent event) {
				if ( actionListener != null ) {
					actionListener.maximizeTab();
				}
			}
		});
		buttonMinimize.addListener( new Button.ClickListener() {
			@Override
			public void buttonClick(Button.ClickEvent event) {
				if ( actionListener != null ) {
					actionListener.minimizeTab();
				}
			}
		});
	}

	@Override
	public void setCaption(String caption) {
		labelCaption.setValue( caption );
	}

	public void setClosable(boolean isClosable) {
		buttonClose.setVisible(isClosable);
	}

	public void setMinimizable( boolean isMinimizable ) {
		buttonMinimize.setVisible(isMinimizable);
	}

	public void setMaximizable( boolean isMaximizable ) {
		buttonMaximize.setVisible(isMaximizable);
	}

	public void setActionListener(VConcertinaTabActionListener listener) {
		this.actionListener = listener;
	}

}
