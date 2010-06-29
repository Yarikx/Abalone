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
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class BoardView extends View implements Player, Watcher {

	static final float borderSize = 10;
	static final float SQRT3_2 = (float) Math.sqrt(3) / 2f,
			PROP = (8 * SQRT3_2 + 1f) / 9f;
	int size = 0;
	private Activity parent;

	public void setParent(Activity parent) {
		this.parent = parent;
	}

	Paint defaultPaint, blackP, whiteP, emptyP, highlightedP, selectedP,
			boardP;
	private Board board;
	boolean animation = false;

	boolean selected = false, selectionStarted = false, moveRequested = false;
	Cell startCell;
	Group selectedGroup;

	// getMove
	Object monitor;
	Move resultMove;
	// animation
	List<Ball> emptyBalls, animBals;
	final static int T = 20, time = 400, N = time / T;
	// highlight
	boolean highlight = false;

	Drawable blackBall, whiteBall, emptyBall;

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
	private Game game;
	private MoveType currentMoveType;
	private Group currentGroup;
	private Resources r;
	private RectF boardRect;
	private Rect dst;

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
		r = getResources();
		blackBall = getResources().getDrawable(R.drawable.black_ball);
		whiteBall = getResources().getDrawable(R.drawable.white_ball);
		emptyBall = getResources().getDrawable(R.drawable.hole);
		// TODO move to xml
		defaultPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		defaultPaint.setColor(r.getColor(R.color.defaultColor));
		blackP = new Paint(Paint.ANTI_ALIAS_FLAG);
		blackP.setColor(Color.BLACK);
		whiteP = new Paint(Paint.ANTI_ALIAS_FLAG);
		whiteP.setColor(Color.WHITE);
		emptyP = new Paint(Paint.ANTI_ALIAS_FLAG);
		emptyP.setColor(Color.GRAY);
		highlightedP = new Paint(Paint.ANTI_ALIAS_FLAG);
		highlightedP.setColor(Color.RED);
		highlightedP.setAlpha(100);

		selectedP = new Paint(Paint.ANTI_ALIAS_FLAG);
		selectedP.setColor(Color.GREEN);
		selectedP.setAlpha(100);

		boardP = new Paint(Paint.ANTI_ALIAS_FLAG);
		boardP.setColor(r.getColor(R.color.board));
		dst = new Rect();

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
		Log.d("screen", "result=" + result);
		return result;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int measuredWidth = measure(widthMeasureSpec);
		int measuredHeight = measure(heightMeasureSpec);

		// (size - 2 * borderSize - 2 * (1 - SQRT3_2) * ballSize)*SQRT3_2+2 *
		// borderSize)

		// int d = (int) Math.min((measuredWidth-2*borderSize)*PROP,
		// measuredHeight);

		if ((measuredWidth - 2 * borderSize) * PROP + 2 * borderSize < measuredHeight) {
			measuredHeight = (int) (((measuredWidth - 2 * borderSize) * PROP) + 2 * borderSize);

		} else {
			measuredWidth = (int) (((measuredHeight - 2 * borderSize) / PROP) + 2 * borderSize);
		}
		Log.d("screen", measuredWidth + "x" + measuredHeight);
		setMeasuredDimension(measuredWidth, measuredHeight);
		size = measuredWidth;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// canvas.drawLine(0, 0, 100, 100, defaultPaint);
//		Log.d("draw", "height = " + getHeight());
//		Log.d("draw", "width = " + getWidth());

		// TODO boar edges
		canvas.save();

		

		for (int i = 1; i <= 6; i++) {
			canvas.drawRect(boardRect, boardP);
			canvas.rotate(60, getWidth() / 2, getHeight() / 2);
		}

		canvas.restore();
		// TODO cells

		if (balls != null) {
			for (Ball ball : balls) {
				// canvas.drawRect(ball.x, ball.y, ball.x + ballSize, ball.y
				// + ballSize, defaultPaint);

				drawBall(ball, canvas);

			}
		}

		// TODO delete
		Log.d("draw", "refresh anim=" + animation + " " + animBals);

		if (animation) {
			if (emptyBall != null) {
				for (Ball ball : emptyBalls) {

					drawBall(ball, canvas);

				}
			}
			if (animBals != null) {
				for (Ball ball : animBals) {

					drawBall(ball, canvas);

				}
			}

		}

		if (selectionStarted) {
			if (currentGroup != null) {
				drawSelected(currentGroup, canvas);
			}
		}
		if (selected) {
			if (selectedGroup != null) {
				drawSelected(selectedGroup, canvas);
			}
		}

		if (highlight) {
			Group highlitedCells = currentMoveType.getHighlightedCells();
			if (highlitedCells != null) {
				drawHighlight(highlitedCells, canvas);
			} else {
				drawHighlight(selectedGroup, canvas);
			}
		}

	}

	private void drawBall(Ball ball, Canvas canvas) {

		dst.set((int) (ball.x - ballSize / 2),
				(int) (ball.y - ballSize / 2), (int) (ball.x + ballSize / 2),
				(int) (ball.y + ballSize / 2));

		switch (ball.state) {
		case Layout.B:
			blackBall.setBounds(dst);
			blackBall.draw(canvas);
			break;
		case Layout.W:
			whiteBall.setBounds(dst);
			whiteBall.draw(canvas);
			break;
		case Layout.E:
			emptyBall.setBounds(dst);
			emptyBall.draw(canvas);
			// canvas.drawCircle(ball.x, ball.y, ballSize / 2f, curPaint);
			break;
		}

	}

	private void drawHighlight(Group hGroup, Canvas canvas) {
		PointF p;
		for (Cell cell : hGroup.getCells()) {
			p = getPointByCell(cell);
			canvas.drawCircle(p.x, p.y, 0.5f * ballSize, highlightedP);
		}
	}

	private void drawSelected(Group hGroup, Canvas canvas) {
		PointF p;
		for (Cell cell : hGroup.getCells()) {
			p = getPointByCell(cell);
			canvas.drawCircle(p.x, p.y, 0.5f * ballSize, selectedP);
		}
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		// TODO Auto-generated method stub
		super.onSizeChanged(w, h, oldw, oldh);

		measure(MeasureSpec.AT_MOST);
		// size = h;
		drawBoard();
		Log.d("screen", "screen changed " + h + " " + w);
	}

	public void drawBoard(Board board) {
		// invalidate();
		this.board = board;

//		Log.d("screen", getHeight() + "");
//		Log.d("screen", getMeasuredHeight() + "");
		ballSizeRecalc();
		Log.d("draw", "ball size="+ballSize);

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
		

		// postInvalidate();
		animBals = emptyBalls = null;
		animation = false;

		postInvalidate();
		// (new Thread(new Runnable() {
		//
		// @Override
		// public void run() {
		// postInvalidate();
		//
		// }
		// },"postInvalidateThread")).start();
	}

	public void ballSizeRecalc() {
		boardRect = new RectF(getWidth() / 4f - 1, 1,
				3f * getWidth() / 4f + 1, getWidth() / 2);
		balls = new ArrayList<Ball>();
		ballSize = ((float) size - 2 * borderSize) / 9f;
	}

	@Override
	public boolean onTouchEvent(MotionEvent e) {

		postInvalidate();
		if (moveRequested) {
			if (!selected) {
				if (e.getAction() == MotionEvent.ACTION_DOWN) {
					startCell = getCell(e.getX(), e.getY());
					Log.d("input", "startCell " + startCell.toString());
					selectionStarted = true;
				} else if (e.getAction() == MotionEvent.ACTION_MOVE) {
					currentGroup = board
							.getUsableGroup(
									startCell,
									getCell(e.getX(), e.getY()),
									getDirectionFromCell(e.getX(), e.getY(),
											startCell), game.getSide());
				} else if (e.getAction() == MotionEvent.ACTION_UP
						&& selectionStarted) {
					selectedGroup = board
							.getUsableGroup(
									startCell,
									getCell(e.getX(), e.getY()),
									getDirectionFromCell(e.getX(), e.getY(),
											startCell), game.getSide());

					selectionStarted = false;
					currentGroup = null;
					// Log.d("input", "group " + selectedGroup.toString());

					if (selectedGroup != null) {
						selected = true;
						Log.d("group", "group is valid");

					} else {
						Log.d("group", "group is not valid");
						// TODO notification
					}
				}
				// if selected
			} else {
				if (e.getAction() == MotionEvent.ACTION_DOWN) {

					currentMoveType = board.getMoveType(new Move(selectedGroup,
							getDirection(e.getX(), e.getY()), game.getSide()));
					highlight = true;

				} else if (e.getAction() == MotionEvent.ACTION_MOVE) {

					currentMoveType = board.getMoveType(new Move(selectedGroup,
							getDirection(e.getX(), e.getY()), game.getSide()));

				} else if (e.getAction() == MotionEvent.ACTION_UP) {
					highlight = false;
					if (selectedGroup
							.isCellInGroup(getCell(e.getX(), e.getY()))) {
						selected = false;
						selectionStarted = false;
					} else {
						Move move = new Move(selectedGroup, getDirection(
								e.getX(), e.getY()), game.getSide());

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
							// moveRequested = true;
							// TODO notification
							Log.d("group", "NOMOVE");

						}
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

	Direction getDirection(float x, float y) {
		PointF tempPoint = getCentrPointOfSelectedGroup();
		double angle = Math.atan((y - tempPoint.y) / (x - tempPoint.x));

		if (x - tempPoint.x < 0) {

			angle = Math.PI + angle;

		} else if ((y - tempPoint.y < 0)) {
			angle += Math.PI * 2;
		}
		double tAngle = angle + Math.PI / 6d;
		if (tAngle >= 2 * Math.PI) {
			tAngle -= 2 * Math.PI;
		}
		int t = (int) (tAngle / (Math.PI / 3d));
		Direction d = null;
		switch (t) {
		case 0:
			d = Direction.East;
			break;
		case 1:
			d = Direction.SouthEast;
			break;
		case 2:
			d = Direction.South;
			break;
		case 3:
			d = Direction.West;
			break;
		case 4:
			d = Direction.NorthWest;
			break;
		case 5:
			d = Direction.North;
			break;
		}
		Log.d("input", "angle = " + angle / Math.PI * 180 + " t=" + t + " " + d);
		return d;
	}

	Direction getDirectionFromCell(float x, float y, Cell c) {
		PointF tempPoint = getPointByCell(c);
		double angle = Math.atan((y - tempPoint.y) / (x - tempPoint.x));

		if (x - tempPoint.x < 0) {

			angle = Math.PI + angle;

		} else if ((y - tempPoint.y < 0)) {
			angle += Math.PI * 2;
		}
		double tAngle = angle + Math.PI / 6d;
		if (tAngle >= 2 * Math.PI) {
			tAngle -= 2 * Math.PI;
		}
		int t = (int) (tAngle / (Math.PI / 3d));
		Direction d = null;
		switch (t) {
		case 0:
			d = Direction.East;
			break;
		case 1:
			d = Direction.SouthEast;
			break;
		case 2:
			d = Direction.South;
			break;
		case 3:
			d = Direction.West;
			break;
		case 4:
			d = Direction.NorthWest;
			break;
		case 5:
			d = Direction.North;
			break;
		}
		Log.d("input", "angle = " + angle / Math.PI * 180 + " t=" + t + " " + d);
		return d;
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
		return Cell.get(row, column);
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
		PointF point;
		for (Cell cell : moveType.getMovedCells().getCells()) {
			float x, y;
			 point = getPointByCell(cell);
			x = point.x;
			y = point.y;
			emptyBalls.add(new Ball(x, y, Layout.E));
			animBals.add(new Ball(x, y, board.getState(cell)));
		}

		animation = true;

		// postInvalidate();

		for (int i = 0; i < N; i++) {
			for (Ball ball : animBals) {
				ball.x += (1d / (double) N) * Math.cos(angle) * ballSize;
				ball.y += (1d / (double) N) * Math.sin(angle) * ballSize;
			}

			
			try {
				Thread.sleep(T);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			postInvalidate();
		}
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// private void out(String out){
	// TextView tw = (TextView) findViewById(R.id.tempOut);
	// tw.setText(out);
	// }
	//

	public PointF getCentrPointOfSelectedGroup() {
		PointF t = getPointByCell(selectedGroup.getFirstEnd());
		float x1 = t.x;
		float y1 = t.y;

		t = getPointByCell(selectedGroup.getSecondEnd());
		float x2 = t.x;
		float y2 = t.y;

		t = new PointF((x1 + x2) / 2f, (y1 + y2) / 2f);

		return t;
	}

	public void setGame(Game game) {
		this.game = game;
		drawBoard(game.getBoard());

	}

	public void screenChanged() {

		drawBoard();

	}

	@Override
	public void win(byte side) {

		final String sideString = (side == Board.BLACK) ? r
				.getString(R.string.black) : r.getString(R.string.white);

		parent.runOnUiThread(new Runnable() {

			@Override
			public void run() {

				new AlertDialog.Builder(getContext())
						.setMessage(
								sideString + " " + r.getString(R.string.wins))
						.setTitle(r.getString(R.string.game_over))
						.setCancelable(false).setNeutralButton("Ok", null)
						.show();

			}
		});

	}

	@Override
	public void ballCaptured(byte side) {
		((GameActivity)parent).ballCaptured(side);
		
	}

	public float getBallSize() {
		return ballSize;
	}
}
