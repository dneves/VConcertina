package com.neon.vaadin.vconcertina;

import com.vaadin.ui.Component;
import java.util.Iterator;
import java.util.List;

public class VConcertinaComponentIterator implements Iterator<Component> {

	private final Iterator< VConcertinaTabInterface > iterator;

	public VConcertinaComponentIterator( List< VConcertinaTabInterface > tabInterfaceList ) {
		iterator = tabInterfaceList == null ? null : tabInterfaceList.iterator();
	}

	@Override
	public boolean hasNext() {
		return iterator == null ? false : iterator.hasNext();
	}

	@Override
	public Component next() {
		if ( hasNext() ) {
			VConcertinaTabInterface tabInterface = iterator.next();
			VConcertinaTabContent tabContent = tabInterface == null ? null : tabInterface.getTabContent();
			return tabContent == null ? null : tabContent.getComponent();
		}
		return null;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

}
