package com.neon.vaadin.vconcertina;

import com.neon.vaadin.vconcertina.listener.VConcertinaListener;
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
		final Window window = new Window( "VConcertina Widget Test App" );
		setMainWindow(window);

	    final VConcertina vConcertina = new VConcertina();
	    vConcertina.setSingle( true );
		vConcertina.addListener( new VConcertinaListener() {
			@Override
			public void tabAdded( VConcertinaTabInterface tabInterface ) {
				window.showNotification( "tab added : " + tabInterface.getTitle(), Window.Notification.TYPE_HUMANIZED_MESSAGE );
			}

			@Override
			public void tabRemoved( VConcertinaTabInterface tabInterface ) {
				window.showNotification( "tab removed : " + tabInterface.getTitle(), Window.Notification.TYPE_HUMANIZED_MESSAGE );
			}

			@Override
			public void tabMinimized( VConcertinaTabInterface tabInterface ) {
				window.showNotification( "tab minimized : " + tabInterface.getTitle(), Window.Notification.TYPE_HUMANIZED_MESSAGE );
			}

			@Override
			public void tabMaximized( VConcertinaTabInterface tabInterface ) {
				window.showNotification( "tab maximized : " + tabInterface.getTitle(), Window.Notification.TYPE_HUMANIZED_MESSAGE );
			}
		});
		for ( int i = 1; i <= 10; i++ ) {
			vConcertina.addTab( createTab( vConcertina, i ) );
		}

		Button toggleSingle = new Button( "TOGGLE SINGLE" );
		toggleSingle.addListener( new Button.ClickListener() {
			@Override
			public void buttonClick( Button.ClickEvent event ) {
				vConcertina.setSingle( ! vConcertina.isSingle() );
				window.showNotification( "single = " + vConcertina.isSingle(), Window.Notification.TYPE_HUMANIZED_MESSAGE );
			}
		});
		Button addTab = new Button( "ADD NEW" );
		addTab.addListener( new Button.ClickListener() {
			@Override
			public void buttonClick( Button.ClickEvent event ) {
				vConcertina.addTab( createTab( vConcertina, vConcertina.getComponentCount() + 1 ) );
			}
		} );
		Button enable = new Button( "TOGGLE ENABLE" );
		enable.addListener( new Button.ClickListener() {
			@Override
			public void buttonClick( Button.ClickEvent event ) {
				vConcertina.setEnabled( ! vConcertina.isEnabled() );
			}
		});

		HorizontalLayout horizontalLayout = new HorizontalLayout();
	    horizontalLayout.setSpacing( true );
		horizontalLayout.addComponent(toggleSingle);
		horizontalLayout.addComponent(addTab);
		horizontalLayout.addComponent( enable );

		window.addComponent( horizontalLayout );
	    window.addComponent( vConcertina );
    }

	private VConcertinaTabInterface createTab( final VConcertina vConcertina, int index ) {
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
		Button button3 = new Button( "TOGGLE ENABLE" );
		button3.addListener( new Button.ClickListener() {
			@Override
			public void buttonClick( Button.ClickEvent event ) {
				vConcertina.setEnabled( tabInterface, ! vConcertina.isEnabled( tabInterface ) );
			}
		});

		HorizontalLayout horizontalLayout = new HorizontalLayout();
		horizontalLayout.setSpacing( true );
		horizontalLayout.addComponent( button );
		horizontalLayout.addComponent( button1 );
		horizontalLayout.addComponent( button2 );
		horizontalLayout.addComponent( button3 );

		layout.addComponent( horizontalLayout );

		return tabInterface;
	}

}
