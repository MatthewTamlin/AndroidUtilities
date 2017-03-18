/*
 * Copyright 2016 Matthew Tamlin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.matthewtamlin.android_utilities.library.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.matthewtamlin.android_utilities.library.R;

import static com.matthewtamlin.android_utilities.library.R.styleable.SquareImageView_derivedDimension;

/**
 * An ImageView where one dimension is constrained so that both width and height are equal.
 */
public class SquareImageView extends ImageView {
	private static final Dimension DEFAULT_DERIVED_DIMENSION = Dimension.HEIGHT;

	private Dimension derivedDimension;

	/**
	 * Constructs a new SquareImageViewByWidth.
	 *
	 * @param context
	 * 		the context this view is operating in, not null
	 */
	public SquareImageView(final Context context) {
		super(context);
		init(null, 0, 0);
	}

	/**
	 * Constructs a new SquareImageViewByWidth.
	 *
	 * @param context
	 * 		the context this view is operating in, not null
	 * @param attrs
	 * 		configuration attributes, null allowed
	 */
	public SquareImageView(final Context context, final AttributeSet attrs) {
		super(context, attrs);
		init(attrs, 0, 0);
	}

	/**
	 * Constructs a new SquareImageViewByWidth.
	 *
	 * @param context
	 * 		the context this view is operating in, not null
	 * @param attrs
	 * 		configuration attributes, null allowed
	 * @param defStyleAttr
	 * 		an attribute in the current theme which supplies default attributes, pass 0	to ignore
	 */
	public SquareImageView(
			final Context context,
			final AttributeSet attrs,
			final int defStyleAttr) {

		super(context, attrs, defStyleAttr);
		init(attrs, defStyleAttr, 0);
	}

	/**
	 * Constructs a new SquareImageViewByWidth.
	 *
	 * @param context
	 * 		the context this view is operating in, not null
	 * @param attrs
	 * 		configuration attributes, null allowed
	 * @param defStyleAttr
	 * 		an attribute in the current theme which supplies default attributes, pass 0	to ignore
	 * @param defStyleRes
	 * 		a resource which supplies default attributes, only used if {@code defStyleAttr}	is 0, pass
	 * 		0 to ignore
	 */
	public SquareImageView(
			final Context context,
			final AttributeSet attrs,
			final int defStyleAttr,
			final int defStyleRes) {

		super(context, attrs, defStyleAttr);
		init(attrs, defStyleAttr, defStyleRes);
	}

	/**
	 * Initialises the view. This method should only be called from a constructor.
	 *
	 * @param attrs
	 * 		configuration attributes, null allowed
	 * @param defStyleAttr
	 * 		an attribute in the current theme which supplies default attributes, pass 0	to ignore
	 * @param defStyleRes
	 * 		a resource which supplies default attributes, only used if {@code defStyleAttr}	is 0, pass
	 * 		0 to ignore
	 */
	private void init(final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
		final TypedArray attributes = getContext().obtainStyledAttributes(attrs,
				R.styleable.SquareImageView, defStyleAttr, defStyleRes);

		final int constrainedDimensionOrdinal = attributes.getInt(
				SquareImageView_derivedDimension,
				DEFAULT_DERIVED_DIMENSION.ordinal());

		derivedDimension = Dimension.values()[constrainedDimensionOrdinal];

		attributes.recycle();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		final int dimensions =
				derivedDimension == Dimension.WIDTH ?
						getMeasuredHeight() :
						getMeasuredWidth();

		setMeasuredDimension(dimensions, dimensions);
	}

	/**
	 * The dimension which is forced to equal the other.
	 */
	private enum Dimension {
		WIDTH,
		HEIGHT
	}
}