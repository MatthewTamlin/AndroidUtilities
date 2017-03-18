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

package com.matthewtamlin.android_utilities.library.helpers;

import android.content.res.AssetManager;

import com.matthewtamlin.java_utilities.testing.Tested;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import timber.log.Timber;

/**
 * Copies files from assets to a directory.
 */
@Tested(testMethod = "automated")
public class AssetsHelper {
	/**
	 * Copies one or more assets to a directory.
	 *
	 * @param assetsManager
	 * 		provides access to the assets, not null
	 * @param targetDirectory
	 * 		the directory to copy the assets to, not null
	 * @param assetFiles
	 * 		the names of the assets to copy (including any extensions), not null
	 * @throws IOException
	 * 		if an unspecified IO error occurs while writing to the target directory
	 * @throws IllegalArgumentException
	 * 		if {@code assetsManager} is null
	 * @throws IllegalArgumentException
	 * 		if {@code assetFiles} is null
	 * @throws IllegalArgumentException
	 * 		if {@code targetDirectory} is null
	 */
	public static void copyAssetsToDirectory(final AssetManager assetsManager,
			final File targetDirectory,
			final String... assetFiles) throws IOException {
		if (assetsManager == null) {
			throw new IllegalArgumentException("assetsManager cannot be null");
		} else if (assetFiles == null) {
			throw new IllegalArgumentException("assetFiles cannot be null");
		} else if (targetDirectory == null) {
			throw new IllegalArgumentException("targetDirectory cannot be null");
		}

		for (final String filename : assetFiles) {
			// Create a new file in the output directory to receive the asset data
			final File fileInTargetDirectory = new File(targetDirectory, filename);

			// Initialise streams outside of try block so that they can be closed later
			InputStream streamFromAssets = null;
			OutputStream streamToTargetFile = null;

			try {
				// IOExceptions may be thrown
				streamToTargetFile = new FileOutputStream(fileInTargetDirectory);
				streamFromAssets = assetsManager.open(filename);
				copyFile(streamFromAssets, streamToTargetFile);
			} finally {
				// An IOException is probably unrecoverable so just abort and close the streams
				closeStream(streamFromAssets);
				closeStream(streamToTargetFile);
			}
		}
	}

	/**
	 * Copies data from the source stream to the target stream.
	 *
	 * @param source
	 * 		the stream to copy from, not null
	 * @param target
	 * 		the stream to copy to, not null
	 * @throws IOException
	 * 		if an unspecified IO error occurs while copying data
	 * @throws IllegalArgumentException
	 * 		if {@code source} is null
	 * @throws IllegalArgumentException
	 * 		if {@code target} is null
	 */
	private static void copyFile(final InputStream source, final OutputStream target) throws
			IOException {
		if (source == null) {
			throw new IllegalArgumentException("source cannot be null");
		} else if (target == null) {
			throw new IllegalArgumentException("target cannot be null");
		}

		// Data is moved from the input stream to the output stream through buffer
		final byte[] buffer = new byte[1024];

		// Read data into the buffer from the source
		int numberOfBytesRead = source.read(buffer);

		// If the buffer received data, write data from the buffer to the output stream
		while (numberOfBytesRead != -1) {
			target.write(buffer, 0, numberOfBytesRead);
			numberOfBytesRead = source.read(buffer); // read the next "lot" of data
		}
	}

	/**
	 * Closes a stream.
	 *
	 * @param stream
	 * 		the stream to close, not null
	 * @throws IllegalArgumentException
	 * 		if {@code stream} is null
	 */
	private static void closeStream(final Closeable stream) {
		if (stream == null) {
			throw new IllegalArgumentException("stream cannot be null");
		}

		try {
			stream.close();
		} catch (final IOException e) {
			Timber.e("Error closing output stream.", e);
		}
	}
}