package com.neon.vaadin.vconcertina.component;

import com.vaadin.data.Property;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;

public class ClickableLabel extends CssLayout {

	private final Label label;

	public ClickableLabel() {
		this( "" );
	}

	public ClickableLabel(String content) {
		this.label = new Label( content );
		setupLayout();
	}

	public ClickableLabel(Property contentSource) {
		this.label = new Label(contentSource);
		setupLayout();
	}

	public ClickableLabel(String content, int contentMode) {
		this.label = new Label(content, contentMode);
		setupLayout();
	}

	public ClickableLabel(Property contentSource, int contentMode) {
		this.label = new Label(contentSource, contentMode);
		setupLayout();
	}

	private void setupLayout() {
		this.addComponent( label );
	}

	@Override
	public void setSizeFull() {
		super.setSizeFull();
		label.setSizeFull();
	}

	@Override
	public void setSizeUndefined() {
		super.setSizeUndefined();
		label.setSizeUndefined();
	}
}
