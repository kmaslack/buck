/*
 * Copyright 2012-present Facebook, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain
 * a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package com.facebook.buck.parser.exceptions;

import com.facebook.buck.model.BuildTarget;
import java.nio.file.Path;

/** Thrown when build target definition is missing in corresponding build file */
public class NoSuchBuildTargetException extends BuildTargetException {

  public NoSuchBuildTargetException(BuildTarget target) {
    this(String.format("No such target: '%s'", target));
  }

  private NoSuchBuildTargetException(String message) {
    super(message);
  }

  /** @param buildTarget the failing {@link com.facebook.buck.model.BuildTarget} */
  public static NoSuchBuildTargetException createForMissingBuildRule(
      BuildTarget buildTarget, Path buckFilePath) {
    String message =
        String.format(
            "The rule %s could not be found.\nPlease check the spelling and whether it exists in %s.",
            buildTarget.getFullyQualifiedName(), buckFilePath);
    return new NoSuchBuildTargetException(message);
  }

  @Override
  public String getHumanReadableErrorMessage() {
    return getMessage();
  }
}
