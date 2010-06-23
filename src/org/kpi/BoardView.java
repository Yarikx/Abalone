package org.kpi;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import mechanics.Board;
import mechanics.Cell;
import mechanics.Direction;
import mechanics.Game;
import mechanics.Group;
import mechanics.Layout;
import mechanics.Move;
import mechanics.MoveType;
import mechanics.Player;
import mechanics.Watcher;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class BoardView extends View implements Player, Watcher {

	static final float borderSize = 10;
	static final float SQRT3_2 = (float) Math.sqrt(3) / 2f;
	// FIXME get real size
	int size = 320;
	Paint defaultPaint, blackP, whiteP, emptyP;
	private Board board;
	boolean animation = false;

	boolean selected = false, selectionStarted = false, moveRequested = false;
	Cell startCell;
	Group selectedGroup;
	PointF testCircle = null;

	// getMove
	Object monitor;
	Move resultMove;
	// animation
	List<Ball> emptyBalls, animBals;
	final static int T = 50, time = 1000, N = time / T;

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
		monitor = new Object();
		Resources r = getResources();
		defaultPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		defaultPaint.setColor(r.getColor(R.color.defaultColor));
		blackP = new Paint(Paint.ANTI_ALIAS_FLAG);
		setBackgroundColor(Color.BLUE);
		blackP.setColor(Color.BLACK);
		whiteP = new Paint(Paint.ANTI_ALIAS_FLAG);
		whiteP.setColor(Color.WHITE);
		emptyP = new Paint(Paint.ANTI_ALIAS_FLAG);
		emptyP.setColor(Color.GRAY);
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
				// canvas.drawRect(ball.x, ball.y, ball.x + ballSize, ball.y
				// + ballSize, defaultPaint);

				drawBall(ball, canvas);

			}
		}

		if (animation) {
			for (Ball ball : emptyBalls) {

				drawBall(ball, canvas);

			}
			for (Ball ball : animBals) {

				drawBall(ball, canvas);

			}

		}

		if (testCircle != null) {
			canvas.drawCircle(testCircle.x, testCircle.y, 2 * ballSize,
					defaultPaint);
		}
	}

	private void drawBall(Ball ball, Canvas canvas) {
		Paint curPaint = null;
		switch (ball.state) {
		case Layout.B:
			curPaint = blackP;
			break;
		case Layout.W:
			curPaint = whiteP;
			break;
		case Layout.E:
			curPaint = emptyP;
			break;
		}
		canvas.drawCircle(ball.x, ball.y, ballSize / 2f, curPaint);
	}

	public void drawBoard(Board board) {
		// invalidate();
		this.board = board;

		balls = new ArrayList<Ball>();
		// FIXME get real size
		// size = 320;
		ballSize = ((float) size - 2 * borderSize) / 9f;
		for (int i = 1; i <= 9; i++) {
			float shift = (5f - i) * ballSize / 2f;
			float x, y;
			for (int j = 1; j <= 9; j++) {
				int state = board.getState(i, j);
				if (state != Layout.N) {
					x = borderSize + shift + (j - 1) * ballSize + ballSize / 2f;
					y = (float) (borderSize + (i - 1) * ballSize * SQRT3_2)
							+ ballSize / 2f;
					balls.add(new Ball(x, y, state));
				}
			}
		}

		postInvalidate();
	}

	@Override
	public boolean onTouchEvent(MotionEvent e) {
		// if (e.getAction() == MotionEvent.ACTION_DOWN){
		Cell cell = getCell(e.getX(), e.getY());
		testCircle = getPointByCell(cell);
		postInvalidate();
		// }
		if (moveRequested) {
			if (!selected) {
				if (e.getAction() == MotionEvent.ACTION_DOWN) {
					startCell = getCell(e.getX(), e.getY());
					Log.d("input", "startCell " + startCell.toString());
					selectionStarted = true;
				} else if (e.getAction() == MotionEvent.ACTION_MOVE) {

				} else if (e.getAction() == MotionEvent.ACTION_UP
						&& selectionStarted) {
					selectedGroup = new Group(startCell, getCell(e.getX(), e
							.getY()));

					selectionStarted = false;
					Log.d("input", "group " + selectedGroup.toString());

					if (selectedGroup.isValid()) {
						selected = true;
						Log.d("group", "group is valid");
						// TODO notification
					}else{
						Log.d("group", "group is not valid");
						selectedGroup = null;
					}
				}
				// if selected
			} else {
				if (e.getAction() == MotionEvent.ACTION_DOWN) {

				} else if (e.getAction() == MotionEvent.ACTION_MOVE) {

				} else if (e.getAction() == MotionEvent.ACTION_UP) {
					Move move = new Move(selectedGroup, Direction.North,
							Board.BLACK);

					MoveType moveType = board.getMoveType(move);
					if (moveType.getResult() != MoveType.NOMOVE) {
						resultMove = move;
						synchronized (monitor) {
							monitor.notify();
						}
						Log.d("group", "moved");
						// board.makeMove(move);
						// Log.d("input", "move");
						// selected = false;
						// drawBoard(board);
						moveRequested = false;
						selected = false;
						selectionStarted = false;
					} else {
						//moveRequested = true;
						Log.d("group", "NOMOVE");
						//TODO delete 
						selected = false;
					}

				}

			}
		} else {
			// if (e.getAction() == MotionEvent.ACTION_DOWN)
			// (new Thread(new Runnable() {
			//
			// @Override
			// public void run() {
			// Move mov = requestMove(null);
			// Log.d("move", mov.toString());
			//
			// }
			// })).start();
		}
		return true;
	}

	public PointF getPointByCell(Cell cell) {

		float shift = (5f - cell.getRow()) * ballSize / 2f;
		float x, y;

		x = borderSize + shift + (cell.getColumn() - 1) * ballSize + ballSize
				/ 2f;
		y = (float) (borderSize + (cell.getRow() - 1) * ballSize * SQRT3_2)
				+ ballSize / 2f;

		return new PointF(x, y);
	}

	public Cell getCell(float x, float y) {

		int row = (int) ((y - borderSize - (1 - SQRT3_2) * ballSize) / ((size
				- 2 * borderSize - 2 * (1 - SQRT3_2) * ballSize)
				* SQRT3_2 / 9f)) + 1;
		if (row > 9) {
			row = 9;
		} else if (row < 1) {
			row = 1;
		}
		int column = (int) ((x - borderSize - (5 - row) * ballSize / 2f)
				/ ballSize + 1);
		if (row <= 5) {
			if (column < 1)
				column = 1;
			else if (column > 4 + row) {
				column = 4 + row;
			}
		} else {
			if (column > 9) {
				column = 9;
			} else if (column < (row - 4)) {
				column = row - 4;
			}
		}
		Log.d("draw", "row = " + row);
		Log.d("draw", "column = " + column);
		return new Cell(row, column);
	}

	public Move requestMove(Game g) {
		// TODO getmove

		moveRequested = true;
		synchronized (monitor) {
			try {
				monitor.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return resultMove;
	}

	@Override
	public void updateView() {
		drawBoard();

	}

	private void drawBoard() {
		drawBoard(board);

	}

	@Override
	public void doAnimation(MoveType moveType, Direction direction) {
		double angle = 0;
		double PI = Math.PI;
		if (direction == Direction.East) {
			angle = 0;
		} else if (direction == Direction.SouthEast) {
			angle = PI / 3d;
		} else if (direction == Direction.South) {
			angle = 2d * PI / 3d;
		} else if (direction == Direction.West) {
			angle = PI;
		} else if (direction == Direction.NorthWest) {
			angle = -2d * PI / 3d;
		} else if (direction == Direction.North) {
			angle = -PI / 3d;
		}

		emptyBalls = new LinkedList<Ball>();
		animBals = new LinkedList<Ball>();
		for (Cell cell : moveType.getMovedCells().getCells()) {
			float x, y;
			PointF point = getPointByCell(cell);
			x = point.x;
			y = point.y;
			emptyBalls.add(new Ball(x, y, Layout.E));
			animBals.add(new Ball(x, y, board.getState(cell)));
		}

		animation = true;

		postInvalidate();

		for (int i = 0; i < N; i++) {
			for (Ball ball : animBals) {
				ball.x += (1d / (double) N) * Math.cos(angle) * ballSize;
				ball.y += (1d / (double) N) * Math.sin(angle) * ballSize;
			}

			postInvalidate();
			try {
				Thread.sleep(T);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		animation = false;

	}
}
