package com.neon.vaadin.vconcertina;

import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.vaadin.jouni.animator.AnimatorProxy;
import org.vaadin.jouni.animator.client.ui.VAnimatorProxy;

public class VConcertina extends CustomComponent {

	private final AnimatorProxy animatorProxy = new AnimatorProxy();

	private final CssLayout layout = new CssLayout();

	private final List< VConcertinaTabInterface > tabs = new ArrayList<VConcertinaTabInterface>();

	private final Map< VConcertinaTabInterface, ComponentContainer > components = new HashMap<VConcertinaTabInterface, ComponentContainer>();

	private final Map< VConcertinaTabInterface, Boolean > tabStatus = new HashMap<VConcertinaTabInterface, Boolean>();

	private boolean single = false;


	public VConcertina() {
		setCompositionRoot( layout );
		animatorProxy.addListener( new AnimatorProxy.AnimationListener() {
			@Override
			public void onAnimation(AnimatorProxy.AnimationEvent animationEvent) {
				System.out.println( "Animation Ended: " + animationEvent.getAnimation() );
			}
		});
		layout.addComponent( animatorProxy );
		addStyleName( "v-concertina" );
	}

	public VConcertinaTabInterface addTab( Component component ) {
		return addTab( null, component );
	}

	public VConcertinaTabInterface addTab( String caption, Component component ) {
		VConcertinaTabInterface tabInterface = new VConcertinaTab( this, component );
		tabInterface.getTabHeader().setCaption( caption );
		return addTab( tabInterface );
	}

	public VConcertinaTabInterface addTab( VConcertinaTabInterface tabInterface ) {
		tabs.add(tabInterface);

		VConcertinaTabHeaderInterface tabHeaderInterface = tabInterface.getTabHeader();
		tabHeaderInterface.addStyleName( "v-concertina-tab-header" );
		VConcertinaTabContent tabContent = tabInterface.getTabContent();
		tabContent.addStyleName( "v-concertina-tab-content" );

		CssLayout cssLayout = new CssLayout();
		cssLayout.addStyleName( "v-concertina-tab" );
		cssLayout.addComponent( tabHeaderInterface );
		// tabContent will be added/removed by the animator
//		cssLayout.addComponent( tabContent );

		layout.addComponent( cssLayout );
		components.put( tabInterface, cssLayout );
		return tabInterface;
	}

	public VConcertinaTabInterface getTab( int index ) {
		return tabs.get( index );
	}

	public VConcertinaTabInterface removeTab( int index ) {
		return removeTab( getTab( index ) );
	}

	public VConcertinaTabInterface removeTab( VConcertinaTabInterface tabInterface ) {
		if ( tabInterface.isClosable() ) {
			ComponentContainer componentContainer = components.remove( tabInterface );
			if ( componentContainer != null ) {
				tabs.remove( tabInterface );
				layout.removeComponent( componentContainer );
				return tabInterface;
			}
		}
		return null;
	}

	@Override
	public int getComponentCount() {
		return tabs.size();
	}

	public void maximizeAll() {
		for ( VConcertinaTabInterface tabInterface : tabs ) {
			maximize( tabInterface );
		}
	}

	public void minimizeAll() {
		for ( VConcertinaTabInterface tabInterface : tabs ) {
			minimize( tabInterface );
		}
	}

	public void toggle( VConcertinaTabInterface tabInterface ) {
		if ( isOpen( tabInterface ) ) {
			minimize( tabInterface );
		} else {
			maximize( tabInterface );
		}
	}

	public boolean isOpen( VConcertinaTabInterface tabInterface ) {
		Boolean status = tabStatus.get( tabInterface );
		return status != null && status;
	}

	public void maximize( VConcertinaTabInterface tabInterface ) {
		if ( tabInterface.isMaximizable() && ! isOpen( tabInterface ) ) {
			if ( isSingle() ) {
				minimizeAll();
			}

			Component animateComponent = tabInterface.getTabContent();
			ComponentContainer componentContainer = components.get( tabInterface );
			if ( animateComponent != null && ( animateComponent.getParent() == null || animateComponent.getParent() != componentContainer ) ) {
				componentContainer.addComponent( animateComponent );
			}
			animatorProxy.animate( animateComponent, VAnimatorProxy.AnimType.ROLL_DOWN_OPEN_POP ).setDuration( 250 );
			tabStatus.put( tabInterface, true );
		}
	}

	public void minimize( VConcertinaTabInterface tabInterface ) {
		if ( tabInterface.isMinimizable() && isOpen( tabInterface ) ) {
			animatorProxy.animate( tabInterface.getTabContent(), VAnimatorProxy.AnimType.ROLL_UP_CLOSE_REMOVE ).setDuration( 250 );
			tabStatus.put( tabInterface, false );
		}
	}

	public boolean isSingle() {
		return single;
	}

	public void setSingle(boolean single) {
		this.single = single;
	}

	@Override
	public void setSizeFull() {
		super.setSizeFull();
		layout.setSizeFull();
	}

	@Override
	public void setSizeUndefined() {
		super.setSizeUndefined();
		layout.setSizeUndefined();
	}

}
