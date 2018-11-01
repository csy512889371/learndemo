package com.ctoedu.business.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * 仿美丽说登陆拖拽布局
 */
public class DragLayout extends RelativeLayout {

	private static final int SNAP_VELOCITY = 800;


	private static final int SNAP_SHAKE = 20;


	private View mContentView;


	private int bottomFlexHeight = 50;

	private int touchHeight = 60;

	private int mMaxtranslationY;


	private float mDownX, mDownY;


	private boolean isDeal;


	private VelocityTracker velocityTracker;

	private ScrollTopListener mTopListener;
	private ScrollBottomListener mBottomListener;

	public DragLayout(Context context) {
		this(context, null);
	}

	public DragLayout(Context context, AttributeSet attrs) {
		super(context, attrs, 0);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		mContentView = getChildAt(0);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		/**
		 * 在布局的时候将mContentView平移下去
		 */
		mMaxtranslationY = mContentView.getHeight() - bottomFlexHeight;
		mContentView.setTranslationY(mMaxtranslationY);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		super.onTouchEvent(event);

		/**
		 * 测量本次事件的速度
		 */
		addVelocityTracker(event);
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			mDownX = event.getX();
			mDownY = event.getY();
			if (!isInRect(mDownX, mDownY)) {
				isDeal = false;
			} else {
				isDeal = true;
			}

			break;
		case MotionEvent.ACTION_MOVE:
			if (isDeal) {
				float moveY = event.getY();
				float deltY = moveY - mDownY;
				if (Math.abs(deltY) >= SNAP_SHAKE) {
					mContentView.setTranslationY(moveY);
				}
			}
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:
			int velocityY = getScrollYVelocity();
			if (isDeal) {
				if (mContentView.getTranslationY() < (mMaxtranslationY / 2)) {
					if (velocityY > SNAP_VELOCITY) {
						scrollToBottom();
					} else {
						//
						scrollToTop();
					}
				} else {
					//
					if (velocityY <= -SNAP_VELOCITY) {
						scrollToTop();
					} else {
						//
						scrollToBottom();
					}
				}
			}
			isDeal = false;
			recycleVelocityTracker();
			break;
		}
		return true;
	}

	private void scrollToTop() {
		ObjectAnimator topAnimation = ObjectAnimator.ofFloat(mContentView, "translationY",
				mContentView.getTranslationY(), 0);
		if (mTopListener != null) {
			topAnimation.addListener(new AnimatorListenerAdapter() {
				@Override
				public void onAnimationEnd(Animator animation) {
					mTopListener.onScrollTop();
				}
			});
		}
		topAnimation.start();
	}

	private void scrollToBottom() {
		ObjectAnimator bottomAnimation = ObjectAnimator.ofFloat(mContentView, "translationY",
				mContentView.getTranslationY(), (mMaxtranslationY));
		if (mBottomListener != null) {
			bottomAnimation.addListener(new AnimatorListenerAdapter() {
				@Override
				public void onAnimationEnd(Animator animation) {
					mBottomListener.onScrollBottom();
				}
			});
		}
		bottomAnimation.start();
	}


	private boolean isInRect(float downX, float downY) {
		if (downX > mContentView.getLeft() && downX < mContentView.getRight()) {
			if (downY >= mContentView.getTranslationY() && downY <= mContentView.getTranslationY() + touchHeight) {
				return true;
			}
		}
		return false;
	}


	private void addVelocityTracker(MotionEvent event) {
		if (velocityTracker == null) {
			velocityTracker = VelocityTracker.obtain();
		}
		velocityTracker.addMovement(event);
	}


	private void recycleVelocityTracker() {
		if (velocityTracker != null) {
			velocityTracker.recycle();
			velocityTracker = null;
		}
	}


	private int getScrollYVelocity() {
		velocityTracker.computeCurrentVelocity(1000);
		int velocity = (int) velocityTracker.getYVelocity();
		return velocity;
	}

	public void setmTopListener(ScrollTopListener mTopListener) {
		this.mTopListener = mTopListener;
	}

	public void setmBottomListener(ScrollBottomListener mBottomListener) {
		this.mBottomListener = mBottomListener;
	}

	public interface ScrollBottomListener {
		public void onScrollBottom();
	}


	public interface ScrollTopListener {
		public void onScrollTop();
	}
}