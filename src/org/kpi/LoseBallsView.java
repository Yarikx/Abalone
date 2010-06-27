package org.kpi;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

public class LoseBallsView extends View{
	
	private byte side;
	private static final int borderSize = 5, vJap = 5,hJap = 5;
	
	
	public LoseBallsView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		
		
	}

	public void setSide(byte side) {
		this.side = side;
	}

	public byte getSide() {
		return side;
	}
	
	

}
