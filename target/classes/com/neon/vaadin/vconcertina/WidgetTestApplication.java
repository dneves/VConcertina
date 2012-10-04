package com.neon.vaadin.vconcertina;

import com.vaadin.Application;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

@SuppressWarnings("serial")
public class WidgetTestApplication extends Application {
    @Override
    public void init() {
		Window window = new Window( "VConcertina Widget Test App" );
		setMainWindow(window);


	    VConcertina vConcertina = new VConcertina();
	    vConcertina.setSingle( true );
	    window.addComponent( vConcertina );

	    for ( int i = 1; i <= 10; i++ ) {
		    vConcertina.addTab( createTab( vConcertina, i ) );
	    }

    }

	private VConcertinaTabInterface createTab( VConcertina vConcertina, int index ) {
		VerticalLayout layout = new VerticalLayout();
		layout.setSpacing( true );
		layout.setMargin( true );

		final VConcertinaTabInterface tabInterface = new VConcertinaTab( vConcertina, "Tab Title #" + index, layout );

		Label label = new Label( "Tab Content" + index );
		layout.addComponent( label );
		layout.setComponentAlignment( label, Alignment.MIDDLE_CENTER );

		Button button = new Button( "TOGGLE MAXIMIZABLE" );
		button.addListener( new Button.ClickListener() {
			@Override
			public void buttonClick(Button.ClickEvent event) {
				tabInterface.setMaximizable( ! tabInterface.isMaximizable() );
			}
		});
		Button button1 = new Button( "TOGGLE MINIMIZABLE" );
		button1.addListener( new Button.ClickListener() {
			@Override
			public void buttonClick(Button.ClickEvent event) {
				tabInterface.setMinimizable(!tabInterface.isMinimizable());
			}
		});
		Button button2 = new Button( "TOGGLE CLOSABLE" );
		button2.addListener( new Button.ClickListener() {
			@Override
			public void buttonClick(Button.ClickEvent event) {
				tabInterface.setClosable(!tabInterface.isClosable());
			}
		});

		HorizontalLayout horizontalLayout = new HorizontalLayout();
		horizontalLayout.setSpacing( true );
		horizontalLayout.addComponent( button );
		horizontalLayout.addComponent( button1 );
		horizontalLayout.addComponent( button2 );

		layout.addComponent( horizontalLayout );

		return tabInterface;
	}

}
