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

package com.matthewtamlin.android_utilities_unit_testing;

import android.support.test.runner.AndroidJUnit4;

import com.matthewtamlin.android_utilities_library.helpers.ColorHelper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(AndroidJUnit4.class)
public class TestColorHelper {
	private static final int color1 = 0xFFFFFFFF;
	private static final int color2 = 0x00000000;

	@Before
	public void init() {

	}

	@Test(expected = IllegalArgumentException.class)
	public void testBlendColors_invalidArg_ratioLessThanZero() {
		ColorHelper.blendColors(color1, color2, -1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBlendColors_invalidArg_ratioGreaterThanOne() {
		ColorHelper.blendColors(color1, color2, 2);
	}

	@Test
	public void testBlendColors_validArgs_ratioOfZero() {
		final int blendedColor = ColorHelper.blendColors(color1, color2, 0);

		assertThat("Colors did not blend correctly.", blendedColor, is(color1));
	}

	@Test
	public void testBlendColors_validArgs_ratioOfOne() {
		final int blendedColor = ColorHelper.blendColors(color1, color2, 1);

		assertThat("Colors did not blend correctly.", blendedColor, is(color2));
	}

	@Test
	public void testBlendColors_validArgs_ratioBetweenZeroAndOne() {
		int blendedColor = ColorHelper.blendColors(color1, color2, 0.5f);

		assertThat("Colors did not blend correctly.", blendedColor, is(0x7F7F7F7F));
	}
}