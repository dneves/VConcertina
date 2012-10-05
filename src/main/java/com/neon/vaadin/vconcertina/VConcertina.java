package com.neon.vaadin.vconcertina;

import com.neon.vaadin.vconcertina.listener.VConcertinaListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.vaadin.jouni.animator.AnimatorProxy;
import org.vaadin.jouni.animator.client.ui.VAnimatorProxy;

public class VConcertina extends CustomComponent {

	/**
	 * default maximize tab animation.
	 */
	private static final VAnimatorProxy.AnimType TAB_ANIMATION_SHOW = VAnimatorProxy.AnimType.ROLL_DOWN_OPEN;

	/**
	 * default minimize tab animation.
	 */
	private static final VAnimatorProxy.AnimType TAB_ANIMATION_HIDE = VAnimatorProxy.AnimType.ROLL_UP_CLOSE_REMOVE;

	/**
	 * animator add-on, responsible for widget animations, i.e. opening/closing tabs
	 */
	private final AnimatorProxy animatorProxy = new AnimatorProxy();

	/**
	 * list of listeners
	 */
	private final List< VConcertinaListener > LISTENERS = Collections.synchronizedList( new LinkedList< VConcertinaListener >() );

	/**
	 * the widget main layout
	 */
	private final CssLayout layout = new CssLayout();

	/**
	 * the list of current tabs
	 */
	private final List< VConcertinaTabInterface > tabs = new ArrayList<VConcertinaTabInterface>();

	/**
	 * mapping tabs to the actual container added the the widget layout.
	 * needed for the animator to add/remove the tabContent to this componentContainer
	 */
	private final Map< VConcertinaTabInterface, ComponentContainer > components = new HashMap<VConcertinaTabInterface, ComponentContainer>();

	/**
	 * is a tab maximized or minimized ?
	 */
	private final Map< VConcertinaTabInterface, Boolean > tabStatus = new HashMap<VConcertinaTabInterface, Boolean>();

	/**
	 * allow only 1 tab open at a time
	 */
	private boolean single = false;


	public VConcertina() {
		setCompositionRoot( layout );
		animatorProxy.addListener( new AnimatorProxy.AnimationListener() {
			@Override
			public void onAnimation(AnimatorProxy.AnimationEvent animationEvent) {
				AnimatorProxy.Animation animation = animationEvent.getAnimation();
				VAnimatorProxy.AnimType animationType = animation.getType();
				Component component = animation.getTarget();

				if ( component instanceof VConcertinaTabContent ) {
					VConcertinaTabContent tabContent = ( VConcertinaTabContent ) component;
					if ( TAB_ANIMATION_SHOW.equals( animationType ) ) {
						fireTabMaximized( tabContent.getTabInterface() );
					} else if ( TAB_ANIMATION_HIDE.equals( animationType ) ) {
						fireTabMinimized( tabContent.getTabInterface() );
					}
				}
			}
		});
		layout.addComponent( animatorProxy );
		addStyleName( "v-concertina" );
	}

	/**
	 * Adds a new tab to the widget, using the default implementations for the actual tab and its tab header.
	 *
	 * @param component    the new tab content component
	 * @return	the newly added tab
	 */
	public VConcertinaTabInterface addTab( Component component ) {
		return addTab( null, component );
	}

	/**
	 * Adds a new tab to the widget, using the default implementations for the actual tab and its tab header and setting its caption.
	 *
	 * @param caption      the new tab caption
	 * @param component		the new tab content component
	 * @return the newly added tab
	 */
	public VConcertinaTabInterface addTab( String caption, Component component ) {
		VConcertinaTabInterface tabInterface = new VConcertinaTab( this, component );
		tabInterface.getTabHeader().setCaption( caption );
		return addTab( tabInterface );
	}

	/**
	 * Adds a new tab to the widget.
	 *
	 * @param tabInterface    the new tab component
	 * @return the newly added tab
	 */
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
		fireTabAdded( tabInterface );
		return tabInterface;
	}

	/**
	 * Returns the tab component at the given position (0 based).
	 *
	 * @param index    the tab index
	 * @return	the tab at the given index
	 */
	public VConcertinaTabInterface getTab( int index ) {
		return tabs.get( index );
	}

	/**
	 * Removes a tab from the widget. Note that if the tab is not closable, it wont be removed.
	 *
	 * @param index    the tab index to remove (0 based)
	 * @return	the removed tab or null if the tab does not exist or is not closable
	 */
	public VConcertinaTabInterface removeTab( int index ) {
		return removeTab( getTab( index ) );
	}

	/**
	 * Removes a tab from the widget. Note that if the tab is not closable, it wont be removed.
	 *
	 * @param tabInterface    the tab to remove
	 * @return the removed tab or null if the tab does not exist or is not closable
	 */
	public VConcertinaTabInterface removeTab( VConcertinaTabInterface tabInterface ) {
		if ( tabInterface.isClosable() ) {
			ComponentContainer componentContainer = components.remove( tabInterface );
			if ( componentContainer != null ) {
				tabs.remove( tabInterface );
				layout.removeComponent( componentContainer );
				fireTabRemoved( tabInterface );
				return tabInterface;
			}
		}
		return null;
	}

	/**
	 * Returns the currently maximized tabs.
	 *
	 * @return	a list of maximized tabs
	 */
	public List< VConcertinaTabInterface > getSelectedTabs() {
		List< VConcertinaTabInterface > result = new LinkedList<VConcertinaTabInterface>();
		for ( VConcertinaTabInterface tabInterface : tabs ) {
			if ( isOpen( tabInterface ) ) {
				result.add( tabInterface );
			}
		}
		return result;
	}

	/**
	 *
	 * @return	the number of tabs
	 */
	@Override
	public int getComponentCount() {
		return tabs.size();
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

	/**
	 * Should enable/disable the entire concertina.
	 *
	 * @param enabled    true to enable; false to disable
	 */
	@Override
	public void setEnabled( boolean enabled ) {
		super.setEnabled( enabled );
	}

	/**
	 * Should enable/disable a tab component.
	 *
	 * @param tabInterface    the tab component to enable/disable
	 * @param enabled	true to enable; false to disable
	 */
	public void setEnabled( VConcertinaTabInterface tabInterface, boolean enabled ) {
		ComponentContainer componentContainer = components.get( tabInterface );
		if ( componentContainer != null ) {
			componentContainer.setEnabled( enabled );
		}
	}

	public boolean isEnabled( VConcertinaTabInterface tabInterface ) {
		boolean result = false;
		ComponentContainer componentContainer = components.get( tabInterface );
		if ( componentContainer != null ) {
			result = componentContainer.isEnabled();
		}
		return result;
	}

	/**
	 *
	 * @return true if only one tab maximized allowed; false otherwise. Defaults to false.
	 */
	public boolean isSingle() {
		return single;
	}

	/**
	 * Sets the single state of the widget. Defaults to false.
	 *
	 * @param single    true to allow only one tab maximized; false otherwise. Defaults to false.
	 */
	public void setSingle(boolean single) {
		this.single = single;
	}

	/**
	 * Maximize all maximizable and not maximized tabs.
	 * If the single state of the widget is set to true, this method has no effect.
	 */
	public void maximizeAll() {
		if ( ! isSingle() ) {
			for ( VConcertinaTabInterface tabInterface : tabs ) {
				maximize( tabInterface );
			}
		}
	}

	/**
	 * Minimize all minimizable and not minimized tabs.
	 */
	public void minimizeAll() {
		for ( VConcertinaTabInterface tabInterface : tabs ) {
			minimize( tabInterface );
		}
	}

	/**
	 * Toggles the state of the given tab, maximizing or minimizing accordingly.
	 *
	 * @param tabInterface    the tab component to toggle
	 */
	public void toggle( VConcertinaTabInterface tabInterface ) {
		if ( isOpen( tabInterface ) ) {
			minimize( tabInterface );
		} else {
			maximize( tabInterface );
		}
	}

	/**
	 * Checks if the given tab component is maximized.
	 *
	 * @param tabInterface    the tab component
	 * @return	true if the given tab is maximized; false otherwise
	 */
	public boolean isOpen( VConcertinaTabInterface tabInterface ) {
		Boolean status = tabStatus.get( tabInterface );
		return status != null && status;
	}

	/**
	 * Maximizes the given tab, if its maximizable and is not already maximized.
	 * If the widget single state is set to true, all other tabs will be minimized.
	 *
	 * @param tabInterface    the tab component to maximize
	 */
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
			animatorProxy.animate( animateComponent, VAnimatorProxy.AnimType.ROLL_DOWN_OPEN ).setDuration( 500 );
			tabStatus.put( tabInterface, true );
		}
	}

	/**
	 * Minimizes the given tab, if its minimizable and is not already minimized.
	 *
	 * @param tabInterface
	 */
	public void minimize( VConcertinaTabInterface tabInterface ) {
		if ( tabInterface.isMinimizable() && isOpen( tabInterface ) ) {
			animatorProxy.animate( tabInterface.getTabContent(), VAnimatorProxy.AnimType.ROLL_UP_CLOSE_REMOVE ).setDuration( 250 );
			tabStatus.put( tabInterface, false );
		}
	}

	/**
	 * Register a new widget listener.
	 *
	 * @param listener    the listener implementation
	 */
	public void addListener( VConcertinaListener listener ) {
		if ( listener != null ) {
			LISTENERS.add( listener );
		}
	}

	/**
	 * Unregister a widget listener.
	 *
	 * @param listener    the listener implementation
	 */
	public void removeListener( VConcertinaListener listener ) {
		if ( listener != null ) {
			LISTENERS.remove( listener );
		}
	}

	/**
	 * Fires an event when a new tab is added to the widget.
	 *
	 * @param tabInterface    the added tab component
	 */
	private void fireTabAdded( final VConcertinaTabInterface tabInterface ) {
		for ( VConcertinaListener listener : LISTENERS ) {
			listener.tabAdded( tabInterface );
		}
	}

	/**
	 * Fires an event when a tab is removed from the widget.
	 *
	 * @param tabInterface    the removed tab component
	 */
	private void fireTabRemoved( final VConcertinaTabInterface tabInterface ) {
		for ( VConcertinaListener listener : LISTENERS ) {
			listener.tabRemoved( tabInterface );
		}
	}

	/**
	 * Fires an event when a tab is minimized.
	 *
	 * @param tabInterface    the minimized tab component
	 */
	private void fireTabMinimized( final VConcertinaTabInterface tabInterface ) {
		for ( VConcertinaListener listener : LISTENERS ) {
			listener.tabMinimized( tabInterface );
		}
	}

	/**
	 * Fires an event when a tab is maximized.
	 *
	 * @param tabInterface    the maximized tab component
	 */
	private void fireTabMaximized( final VConcertinaTabInterface tabInterface ) {
		for ( VConcertinaListener listener : LISTENERS ) {
			listener.tabMaximized( tabInterface );
		}
	}

}
