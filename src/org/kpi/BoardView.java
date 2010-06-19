package org.kpi;

import java.util.ArrayList;

import mechanics.Board;
import mechanics.Layout;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class BoardView extends View {

	static final int borderSize = 5;
	int size = 0;
	Paint defaultPaint, blackP, whiteP, emptyP;
	private Board board;
	boolean animation = false;

	class Ball {
		float x, y;
		int state;

		public Ball(float x, float y, int state) {
			this.x = x;
			this.y = y;
			this.state = state;
		}
	}

	ArrayList<Ball> balls;

	private float ballSize;

	public BoardView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public BoardView(Context context) {
		super(context);
		init();
	}

	private void init() {
		setFocusable(true);
		Resources r = getResources();
		defaultPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		defaultPaint.setColor(r.getColor(R.color.defaultColor));
		
	}

	private int measure(int measureSpec) {
		int result = 0;

		// Decode the measurement specifications.
		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);

		if (specMode == MeasureSpec.UNSPECIFIED) {
			// Return a default size of 200 if no bounds are specified.
			result = 200;
		} else {
			// As you want to fill the available space
			// always return the full available bounds.
			result = specSize;
		}
		return result;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int measuredWidth = measure(widthMeasureSpec);
		int measuredHeight = measure(heightMeasureSpec);

		int d = Math.min(measuredWidth, measuredHeight);

		setMeasuredDimension(d, d);
		size = d;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// canvas.drawLine(0, 0, 100, 100, defaultPaint);
		Log.d("draw", "height = " + getHeight());
		Log.d("draw", "width = " + getWidth());

		// TODO boar edges

		// TODO cells
		if (balls != null) {
			for (Ball ball : balls) {
				canvas.drawRect(ball.x, ball.y, ball.x + ballSize, ball.y
						+ ballSize, defaultPaint);
			}
		}
	}

	public void drawBoard(Board board) {
		invalidate();
		this.board = board;

		balls = new ArrayList<Ball>();
		int h = 320;
		ballSize = ((float)h - 2 * borderSize) / 9f;
		for (int i = 1; i <= 9; i++) {
			float shift = (5f - i) * ballSize / 2f;
			float x, y;
			for (int j = 1; j <= 9; j++) {
				int state = board.getState(i, j);
				if (state != Layout.N) {
					x = borderSize + shift + (j - 1) * ballSize;
					y = borderSize + (i - 1) * ballSize;
					balls.add(new Ball(x, y, state));
				}
			}
		}

		invalidate();
	}

}
